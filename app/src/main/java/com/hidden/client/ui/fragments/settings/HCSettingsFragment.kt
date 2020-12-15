package com.hidden.client.ui.fragments.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide

import com.hidden.client.R
import com.hidden.client.apis.ConversationApi
import com.hidden.client.datamodels.HCLoginResponse
import com.hidden.client.datamodels.HCProfileResponse
import com.hidden.client.helpers.AppPreferences
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.json.SimpleResponseJson
import com.hidden.client.models_.HCLogin
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.ui.activities.HCCompanyDetailActivity
import com.hidden.client.ui.activities.HCSentEmailActivity
import com.hidden.client.ui.activities.HomeActivity
import com.hidden.client.ui.activities.LoginActivity
import com.hidden.client.ui.activities.settings.*
import com.hidden.client.ui.fileupload.UploadResponse
import com.hidden.client.ui.fragments.home.dashboard.DashboardFragment
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.DashboardVM
import com.hidden.horizontalswipelayout.CircleImageView
import com.urbanairship.UAirship
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCSettingsFragment : Fragment(), View.OnClickListener {
    private lateinit var viewModel: DashboardVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        // Add OnClickListener
        val imgBack: ImageButton = root.findViewById(R.id.button_back_to_dashboard)
        imgBack.setOnClickListener(this)

        val layoutCandidateDirectory: LinearLayout = root.findViewById(R.id.layout_candidate_directory)
        // candidate directory visible/gone
        if (HCGlobal.getInstance().isAdmin) {
            layoutCandidateDirectory.visibility = View.VISIBLE
        } else {
            layoutCandidateDirectory.visibility = View.GONE
        }

        layoutCandidateDirectory.setOnClickListener(this)

        val layoutEditDetails: LinearLayout = root.findViewById(R.id.layout_edit_your_detail)
        layoutEditDetails.setOnClickListener(this)
        val circleImageViewYourPhoto: de.hdodenhof.circleimageview.CircleImageView = root.findViewById(R.id.circle_image_view_your_photo)
        Glide.with(HCGlobal.getInstance().currentActivity).load(HCGlobal.getInstance().currentClientUrl).into(circleImageViewYourPhoto)

        val layoutViewCompanyProfile: LinearLayout = root.findViewById(R.id.layout_view_company_profile)
        layoutViewCompanyProfile.setOnClickListener(this)
        val circleImageViewCompanyLogo: de.hdodenhof.circleimageview.CircleImageView = root.findViewById(R.id.circle_image_view_company_logo)
        Glide.with(HCGlobal.getInstance().currentActivity).load(HCGlobal.getInstance().currentCompanyLogoUrl).into(circleImageViewCompanyLogo)

        // show profile image and company logo, but if use it, loading speed is slow
        RetrofitClient.instance.getProfile(AppPreferences.apiAccessToken)
            .enqueue(object: Callback<HCProfileResponse> {
                override fun onFailure(call: Call<HCProfileResponse>, t: Throwable) {
                }
                override fun onResponse(
                    call: Call<HCProfileResponse>,
                    response: Response<HCProfileResponse>
                ) {
                    if (response.isSuccessful) {
                        Glide.with(HCGlobal.getInstance().currentActivity).load(response.body()!!.asset_client__cloudinary_url).into(circleImageViewYourPhoto)
                        Glide.with(HCGlobal.getInstance().currentActivity).load(response.body()!!.company.company_logo_asset__cloudinary_url).into(circleImageViewCompanyLogo)
                        if (response.body()!!.client__is_admin) {
                            layoutCandidateDirectory.visibility = View.VISIBLE
                        } else {
                            layoutCandidateDirectory.visibility = View.GONE
                        }
                    }
                }
            })

        val layoutTermsService: LinearLayout = root.findViewById(R.id.layout_terms_of_service)
        layoutTermsService.setOnClickListener(this)

        val layoutPrivacyPolicy: LinearLayout = root.findViewById(R.id.layout_privacy_policy)
        layoutPrivacyPolicy.setOnClickListener(this)

        val layoutResetPassword: LinearLayout = root.findViewById(R.id.layout_reset_password)
        layoutResetPassword.setOnClickListener(this)

        val layoutLogout: LinearLayout = root.findViewById(R.id.layout_logout)
        layoutLogout.setOnClickListener(this)

        return root

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.button_back_to_dashboard -> {
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, DashboardFragment()).commit()
            }
            R.id.layout_candidate_directory -> {
                val intent = Intent(context, CandidateListActivity::class.java)
                startActivity(intent)
                (activity as HomeActivity).overridePendingVTransitionEnter()
            }
            R.id.layout_edit_your_detail -> {
                val intent = Intent(context, HCMyProfileActivity::class.java)
                startActivity(intent)
                (activity as HomeActivity).overridePendingVTransitionEnter()
            }
            R.id.layout_view_company_profile -> {
                val intent = Intent(context, HCCompanyDetailActivity::class.java)
                startActivity(intent)
                (activity as HomeActivity).overridePendingVTransitionEnter()
            }
            R.id.layout_terms_of_service -> {
                val intent = Intent(context, HCTermsOfServiceActivity::class.java)
                startActivity(intent)
                (activity as HomeActivity).overridePendingVTransitionEnter()
            }
            R.id.layout_privacy_policy -> {
                val intent = Intent(context, HCPrivacyStatementActivity::class.java)
                startActivity(intent)
                (activity as HomeActivity).overridePendingVTransitionEnter()
            }
            R.id.layout_reset_password -> {
                val intent = Intent(context, HCResetPasswordActivity::class.java)
                startActivity(intent)
                (activity as HomeActivity).overridePendingVTransitionEnter()
            }
            R.id.layout_logout -> {
                viewModel = ViewModelProviders.of(this, ViewModelFactory(context!!)).get(DashboardVM::class.java)
                viewModel.logOut()

                viewModel.navigateHome.observe(this, Observer {
                    it.getContentIfNotHandled()?.let {
                        AppPreferences.myFullName = ""
                        AppPreferences.apiAccessToken = ""
                        AppPreferences.myId = 0

                        UAirship.shared().namedUser.id = null
                        UAirship.shared().namedUser.forceUpdate()

                        val intent = Intent(HCGlobal.getInstance().currentActivity, LoginActivity::class.java)
                        startActivity(intent)
                        HCGlobal.getInstance().currentActivity.finish()
                    }
                })
            }
        }
    }
}

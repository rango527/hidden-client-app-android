package com.hidden.client.ui.fragments.settings

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout

import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.ui.fragments.home.dashboard.HCDashboardFragment

class HCSettingsFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = HCSettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        // Add OnClickListener

        var imgBack: ImageButton = root.findViewById(R.id.button_back_to_dashboard);
        imgBack.setOnClickListener(this)

        var layoutCandidateDirectory: LinearLayout = root.findViewById(R.id.layout_candidate_directory);
        layoutCandidateDirectory.setOnClickListener(this);

        var layoutEditDetails: LinearLayout = root.findViewById(R.id.layout_edit_your_detail);
        layoutEditDetails.setOnClickListener(this);

        var layoutViewCompanyProfile: LinearLayout = root.findViewById(R.id.layout_view_company_profile);
        layoutViewCompanyProfile.setOnClickListener(this);

        var layoutTermsService: LinearLayout = root.findViewById(R.id.layout_terms_of_service);
        layoutTermsService.setOnClickListener(this);

        var layoutPrivacyPolicy: LinearLayout = root.findViewById(R.id.layout_privacy_policy);
        layoutPrivacyPolicy.setOnClickListener(this);

        var layoutResetPassword: LinearLayout = root.findViewById(R.id.layout_reset_password);
        layoutResetPassword.setOnClickListener(this);

        var layoutLogout: LinearLayout = root.findViewById(R.id.layout_logout);
        layoutLogout.setOnClickListener(this);

        return root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.button_back_to_dashboard -> {
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HCDashboardFragment()).commit()
            }
            R.id.layout_candidate_directory -> {

            }
            R.id.layout_edit_your_detail -> {

            }
            R.id.layout_view_company_profile -> {

            }
            R.id.layout_terms_of_service -> {

            }
            R.id.layout_privacy_policy -> {

            }
            R.id.layout_reset_password -> {

            }
            R.id.layout_logout -> {

            }
        }
    }
}

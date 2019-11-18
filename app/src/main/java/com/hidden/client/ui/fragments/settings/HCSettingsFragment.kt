package com.hidden.client.ui.fragments.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout

import com.hidden.client.R
import com.hidden.client.ui.activities.HCCompanyDetailActivity
import com.hidden.client.ui.activities.HomeActivity
import com.hidden.client.ui.activities.settings.*
import com.hidden.client.ui.fragments.home.dashboard.HCDashboardFragment

class HCSettingsFragment : Fragment(), View.OnClickListener {

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
        // TODO: Use the ViewModel
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.button_back_to_dashboard -> {
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HCDashboardFragment()).commit()
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

            }
        }
    }
}

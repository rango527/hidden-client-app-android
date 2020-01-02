package com.hidden.client.ui.activities.shortlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models_.HCBrand
import com.hidden.client.models_.HCProject
import com.hidden.client.models_.HCWorkExperience
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.activities.HomeActivity
import com.hidden.client.ui.adapters.HCBrandAdapter
import com.hidden.client.ui.adapters.HCProjectAdapter
import com.hidden.client.ui.adapters.HCWorkExperienceAdapter
import com.hidden.client.ui.custom.SkillItemView
import com.hidden.client.ui.viewmodels.main.ShortlistViewVM
import com.hidden.client.ui.viewmodels___.HCBrandViewModel
import com.hidden.client.ui.viewmodels___.HCProjectViewModel
import com.hidden.client.ui.viewmodels___.HCWorkExperienceViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_shortlist_detail.*

class ShortlistDetailActivity : BaseActivity(), View.OnClickListener {
    private lateinit var imgPhoto: CircleImageView
    private lateinit var textName: TextView
    private lateinit var textLocation: TextView
    private lateinit var textJobTitle: TextView
    private lateinit var textSnapshot: TextView
    private lateinit var layoutSkill: FlexboxLayout

    // RecyclerView for Brand
    private lateinit var rvBrand: RecyclerView
    private lateinit var brandViewModel: HCBrandViewModel
    private lateinit var brandAdapter: HCBrandAdapter
    private lateinit var layoutBrandManager: RecyclerView.LayoutManager

    // RecyclerView for Brand
    private lateinit var rvProject: RecyclerView
    private lateinit var projectViewModel: HCProjectViewModel
    private lateinit var projectAdapter: HCProjectAdapter
    private lateinit var layoutProjectdManager: RecyclerView.LayoutManager

    // RecyclerView for Work Experience
    private lateinit var rvWorkExperience: RecyclerView
    private lateinit var workExperienceViewModel: HCWorkExperienceViewModel
    private var workExperienceList: MutableList<HCWorkExperience> = mutableListOf()
    private lateinit var workExperienceAdapter: HCWorkExperienceAdapter

    // Approve & Reject
    private lateinit var layoutApprove: LinearLayout
    private lateinit var layoutReject: LinearLayout

    // Labels
    private lateinit var lblSnapshot: TextView
    private lateinit var lblBrand: TextView
    private lateinit var lblProject: TextView
    private lateinit var lblSkills: TextView
    private lateinit var lblExperience: TextView

    // Background
    private lateinit var layoutBackground: ConstraintLayout

    private lateinit var videwModel: ShortlistViewVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shortlist_detail)

        HCGlobal.getInstance().currentActivity = this

        initCloseButton()

//        viewModel = HCGlobal.getInstance().shortlistCandidateVMList

        imgPhoto = findViewById(R.id.image_photo)
        textName = findViewById(R.id.text_name)
        textLocation = findViewById(R.id.text_location)
        textJobTitle = findViewById(R.id.text_job_title)
        textSnapshot = findViewById(R.id.text_snapshot)

        layoutApprove = findViewById(R.id.layout_approve)
        layoutApprove.setOnClickListener(this)
        layoutReject = findViewById(R.id.layout_reject)
        layoutReject.setOnClickListener(this)

        lblSnapshot = findViewById(R.id.lbl_snapshot)
        lblBrand = findViewById(R.id.lbl_brand)
        lblProject = findViewById(R.id.lbl_project)
        lblSkills = findViewById(R.id.lbl_skills)
        lblExperience = findViewById(R.id.lbl_experience)

        layoutBackground = findViewById(R.id.layout_background)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.layout_approve -> {
                val intent = Intent(this, ApproveActivity::class.java)
                startActivity(intent)
            }
            R.id.layout_reject -> {
                val intent = Intent(this, RejectActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

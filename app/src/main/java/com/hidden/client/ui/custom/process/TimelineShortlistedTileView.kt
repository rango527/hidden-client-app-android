package com.hidden.client.ui.custom.process

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.MapView
import com.hidden.client.R
import com.hidden.client.helpers.Enums
import com.hidden.client.helpers.HCDate
import com.hidden.client.helpers.Utility
import com.hidden.client.helpers.extension.safeValue
import com.hidden.client.models.entity.DashboardTileEntity
import com.hidden.client.models.entity.TimelineEntity
import com.hidden.client.ui.adapters.NumberTileListAdapter
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.DashboardTileListVM
import kotlinx.android.synthetic.main.view_dashboard_number_tile.view.*
import java.util.*

@SuppressLint("ViewConstructor")
class TimelineShortlistedTileView(
    context: Context,
    private val candiateName: String,
    private val data: TimelineEntity,
    private val separator: Boolean
) :
    LinearLayout(context) {

    private var txtTitle: TextView
    private var txtShortlistedOn: TextView
    private var txtApprovedOn: TextView
    private var layoutViewFeedback: ConstraintLayout
    private var imgSeparator: ImageView

    init {
        inflate(context, R.layout.view_process_timeline_shortlisted_tile, this)

        txtTitle = findViewById(R.id.text_title)
        txtShortlistedOn = findViewById(R.id.text_shortlisted_on)
        txtApprovedOn = findViewById(R.id.text_approved_on)
        layoutViewFeedback = findViewById(R.id.layout_view_feedback)
        imgSeparator = findViewById(R.id.image_separator)

        initView()
    }

    private fun initView() {
        if (separator) {
            imgSeparator.visibility = View.VISIBLE
        }

        txtTitle.text = String.format(
            context.getString(R.string.shortlisted),
            Utility.getFirstNameFromFullName(candiateName)
        )

        val shortlistedDate: Date? = HCDate.stringToDate(data.submittedAt!!, null)
        txtShortlistedOn.text = Html.fromHtml(
            String.format(
                context.getString(R.string.shortlisted_on),
                HCDate.dayPrefix(HCDate.dateToString(shortlistedDate!!, "d").safeValue()),
                HCDate.dateToString(shortlistedDate, "MMMM").safeValue(),
                HCDate.dateToString(shortlistedDate, "H:m").safeValue()
            )
        )

        val approvedDate: Date? = HCDate.stringToDate(data.acceptedAt!!, null)
        txtApprovedOn.text = Html.fromHtml(
            String.format(
                context.getString(R.string.approved_on),
                HCDate.dayPrefix(HCDate.dateToString(approvedDate!!, "d").safeValue()),
                HCDate.dateToString(approvedDate, "MMMM").safeValue(),
                HCDate.dateToString(approvedDate, "H:m").safeValue()
            )
        )

        layoutViewFeedback.setOnClickListener {

        }
    }
}
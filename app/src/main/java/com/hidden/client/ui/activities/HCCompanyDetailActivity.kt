package com.hidden.client.ui.activities

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.hidden.client.R
import com.hidden.client.networks.RetrofitClient
import com.hidden.client.datamodels.HCCompanyResponse
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models_.HCJobDetailTile
import com.hidden.client.ui.HCBaseActivity
import com.hidden.client.ui.adapters.HCJobDetailTileAdapter
import com.hidden.client.ui.custom.CompanyDetailBadgeView
import com.hidden.client.ui.viewmodels.HCJobDetailTileViewModel
import pl.droidsonroids.gif.GifImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HCCompanyDetailActivity : HCBaseActivity(), View.OnClickListener {

    private lateinit var imgClose: ImageView

    private lateinit var imgCompanyBg: GifImageView
    private lateinit var imgCompanyLogo: ImageView
    private lateinit var txtCompanyName: TextView
    private lateinit var layoutBadge: FlexboxLayout
    private lateinit var txtHiddenSays: TextView

    private lateinit var rvCompanyDetailTile: RecyclerView
    private lateinit var companyDetailTileViewModel: HCJobDetailTileViewModel
    private lateinit var companyDetailTileAdapter: HCJobDetailTileAdapter

    companion object {
        val default_badge_show_count = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_detail)

        imgClose = findViewById(R.id.img_close)
        imgClose.setOnClickListener(this)

        /** Init View **/
        imgCompanyBg = findViewById(R.id.img_company_bg)
        imgCompanyLogo = findViewById(R.id.img_company_logo)
        txtCompanyName = findViewById(R.id.text_company)
        layoutBadge = findViewById(R.id.layout_badge)
        txtHiddenSays = findViewById(R.id.text_hidden_says)

        /** Company Detail Tile RecyclerView **/
        rvCompanyDetailTile = findViewById(R.id.recyclerview_company_detail_tile)
        companyDetailTileViewModel = ViewModelProviders.of(this).get(HCJobDetailTileViewModel::class.java)
        companyDetailTileViewModel.getJobDetailTileList().observe(this, Observer {jobDetailTileViewModels->
            companyDetailTileAdapter = HCJobDetailTileAdapter(applicationContext, jobDetailTileViewModels)
            rvCompanyDetailTile.layoutManager = LinearLayoutManager(applicationContext)
            rvCompanyDetailTile.setHasFixedSize(true)

            rvCompanyDetailTile.adapter = companyDetailTileAdapter
        })

        // Fetch Company Detail API
        RetrofitClient.instance.getCompanyProfile(HCGlobal.getInstance().myInfo.getBearerToken())
            .enqueue(object: Callback<HCCompanyResponse> {
                override fun onFailure(call: Call<HCCompanyResponse>, t: Throwable) {
                    Toast.makeText(this@HCCompanyDetailActivity, "Failed...", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<HCCompanyResponse>,
                    response: Response<HCCompanyResponse>
                ) {
                    if (response.isSuccessful) {

                        Glide.with(this@HCCompanyDetailActivity).load(response.body()!!.company_cover_image_asset__cloudinary_url).into(imgCompanyBg)
                        Glide.with(this@HCCompanyDetailActivity).load(response.body()!!.company_logo_asset__cloudinary_url).into(imgCompanyLogo)

                        txtCompanyName.text = response.body()!!.company__name
                        txtHiddenSays.text = response.body()!!.company__hidden_says

                        /** Add Badge View **/
                        var brandItemBadge = CompanyDetailBadgeView(this@HCCompanyDetailActivity,
                            response.body()!!.company_type__name, R.drawable.brand_type, R.drawable.progress_item_purple_12)
                        var sizeItemBadge = CompanyDetailBadgeView(this@HCCompanyDetailActivity,
                            response.body()!!.company_size__name, R.drawable.company_size, R.drawable.progress_item_complete_12)

                        layoutBadge.addView(brandItemBadge)
                        layoutBadge.addView(sizeItemBadge)

                        // add `+ $(cnt) more`
                        val cityLocationList = response.body()!!.company__cities
                        val badgeCount = cityLocationList.size

                        var defaultShowCount = if (badgeCount <= default_badge_show_count) badgeCount else default_badge_show_count

                        for (i in 0 until defaultShowCount) {
                            var location = cityLocationList[i].city__name

                            var locationItemBadge = CompanyDetailBadgeView(this@HCCompanyDetailActivity,
                                location, R.drawable.pin, R.drawable.progress_item_black_24)

                            layoutBadge.addView(locationItemBadge)
                        }

                        // add `+ $(cnt) more`
                        if (badgeCount > default_badge_show_count) {
                            var textAddMore = TextView(this@HCCompanyDetailActivity)
                            var params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT, // This will define text view width
                                LinearLayout.LayoutParams.MATCH_PARENT // This will define text view height
                            )
                            params.leftMargin = 5

                            textAddMore.layoutParams = params
                            textAddMore.setText(String.format(resources.getString(R.string.add_more), badgeCount - defaultShowCount))
                            textAddMore.gravity = Gravity.CENTER_VERTICAL

                            textAddMore.setOnClickListener(object: View.OnClickListener {
                                override fun onClick(v: View?) {
                                    v!!.visibility = View.GONE

                                    for (i in defaultShowCount until badgeCount) {
                                        var location = cityLocationList[i].city__name
                                        var locationItemBadge = CompanyDetailBadgeView(this@HCCompanyDetailActivity,
                                            location, R.drawable.pin, R.drawable.progress_item_black_24)

                                        layoutBadge.addView(locationItemBadge)
                                    }
                                }
                            })
                            layoutBadge.addView(textAddMore)
                        }

                        var companyDetailTileList: ArrayList<HCJobDetailTile> = arrayListOf()
                        for (detail_tile in response.body()!!.company__tiles) {

                            val companyDetailTile: HCJobDetailTile = HCJobDetailTile()
                            companyDetailTile.setJobDetailTitleId(detail_tile.tile__tile_id)
                            companyDetailTile.setJobDetailTileTitle(detail_tile.tile__title)
                            companyDetailTile.setJobDetailTileContent(detail_tile.tile__content)
                            companyDetailTile.setJobDetailTileType(detail_tile.tile__type)
                            companyDetailTile.setJobDetailTileSortOrder(detail_tile.tile__sort_order)
                            companyDetailTile.setJobDetailTileImg(detail_tile.tile_asset__cloudinary_url)

                            companyDetailTileList.add(companyDetailTile)
                        }

                        companyDetailTileViewModel.setJobDetailTileList(companyDetailTileList)

                    } else {
                        Toast.makeText(this@HCCompanyDetailActivity, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }

    override fun onResume() {
        super.onResume()
        HCGlobal.getInstance().currentActivity = this
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.img_close -> {
                finish()
            }
        }
    }
}

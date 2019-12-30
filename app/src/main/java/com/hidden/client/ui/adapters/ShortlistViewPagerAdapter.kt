package com.hidden.client.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import com.hidden.client.databinding.ShortlistItemViewBinding
import com.hidden.client.ui.viewmodels.main.ShortlistViewVM

class ShortlistViewPagerAdapter(
    private val context: Context,
    private val candidateVMList: List<ShortlistViewVM>,
    private val fragment: Fragment
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean =
        view == `object` as View

    override fun getCount(): Int {
        return candidateVMList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val view = inflater.inflate(R.layout.viewpager_candidate_item, container, false)
//        var viewModel = candidateVMList[position]
        // Init ViewModel
//        viewModel = ViewModelProviders.of(fragment, ViewModelFactory(context!!)).get(ShortlistViewVM::class.java)

        val binding: ShortlistItemViewBinding = ShortlistItemViewBinding.inflate(inflater, container, false)
        binding.viewModel = candidateVMList[position]

//        val imgAvatar: CircleImageView = view.findViewById(R.id.image_avatar)
//        Glide.with(context).load(candidateList[position].avatarImage).into(imgAvatar)

//        val txtAvatarName: TextView = view.findViewById(R.id.text_avatar_name)
//        txtAvatarName.text = candidateList[position].avatarName

//        val txtCity: TextView = view.findViewById(R.id.text_city)
//        txtCity.text = candidateList[position].cityName

//        val txtJobTitle: TextView = view.findViewById(R.id.text_job_title)
//        txtJobTitle.text = Utility.makeJobString(
//            candidateList[position].jobTitle_1,
//            candidateList[position].jobTitle_2,
//            candidateList[position].jobTitle_3
//        )
//
//        txtJobTitle.setTextColor(
//            ContextCompat.getColor(
//                context,

//            )
//        )

//        val snapshotView: SnapshotView = view.findViewById(R.id.snapshot)
//        snapshotView.


        val view = binding.root
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getPageWidth(position: Int): Float {
        return 1.0f
    }
}
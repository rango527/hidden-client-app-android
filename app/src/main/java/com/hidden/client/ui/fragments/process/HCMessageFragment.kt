package com.hidden.client.ui.fragments.process

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hidden.client.R
import com.hidden.client.databinding.MessageListBinding
import com.hidden.client.helpers.HCDialog
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.entity.ConversationEntity
import com.hidden.client.models.entity.ProcessEntity
import com.hidden.client.ui.TestMapsActivity
import com.hidden.client.ui.activities.HCProcessFilterActivity
import com.hidden.client.ui.activities.ProcessActivity
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.MessageListVM
import com.kaopiz.kprogresshud.KProgressHUD

class HCMessageFragment(
    private val conversationId: Int
) : Fragment(), View.OnClickListener {

    private lateinit var binding: MessageListBinding
    private lateinit var viewModel: MessageListVM

    private lateinit var layoutBtnFilterSearch: LinearLayout
//    private lateinit var swipeContainer: SwipeRefreshLayout

    private lateinit var progressDlg: KProgressHUD

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("sdf", "sdsdf $conversationId")

        viewModel = ViewModelProviders.of(this, ViewModelFactory(context!!)).get(MessageListVM::class.java)

        viewModel.conversationId = conversationId
        viewModel.loadMessage(false, conversationId)

        progressDlg = HCDialog.KProgressDialog(context!!)
        viewModel.loadingVisibility.observe(this, Observer { show ->
            if (show) {
                progressDlg.show()
            }
            else {
                progressDlg.dismiss()
            }
        })

        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = MessageListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        val view = binding.root

        binding.recyclerviewProcesses.layoutManager = LinearLayoutManager(context!!)

        layoutBtnFilterSearch = view.findViewById(R.id.layout_filter_search)
        layoutBtnFilterSearch.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.layout_filter_search -> {
                val intent = Intent(context, HCProcessFilterActivity::class.java)
            }
        }
    }
}
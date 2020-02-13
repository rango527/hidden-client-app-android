package com.hidden.client.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.UserManagerItemBinding
import com.hidden.client.models.custom.UserManager
import com.hidden.client.ui.viewmodels.main.UserManagerViewVM

class UserManagerListAdapter: RecyclerView.Adapter<UserManagerListAdapter.ViewHolder>() {

    private lateinit var userManagerList: List<UserManager>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: UserManagerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_row_user_manager, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userManagerList[position])
    }

    override fun getItemCount(): Int {
        return if(::userManagerList.isInitialized) userManagerList.size else 0
    }

    fun updateUserManagerList(userManagerList: List<UserManager>){
        this.userManagerList = userManagerList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: UserManagerItemBinding):RecyclerView.ViewHolder(binding.root){

        private val viewModel = UserManagerViewVM()

        fun bind(user: UserManager){
            viewModel.bind(user)
            binding.viewModel = viewModel
        }
    }
}
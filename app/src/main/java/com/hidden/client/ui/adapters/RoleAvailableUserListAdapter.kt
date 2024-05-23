package com.hidden.client.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hidden.client.R
import com.hidden.client.databinding.RoleAvailableUserItemBinding
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.models.custom.RoleAvailableUser
import com.hidden.client.ui.viewmodels.main.RoleAvailableUserViewVM
import com.hidden.client.ui.activities.JobAddRoleActivity
import com.hidden.client.ui.activities.ProcessAddRoleActivity
import com.hidden.client.ui.activities.process.AddInterviewersActivity


class RoleAvailableUserListAdapter: RecyclerView.Adapter<RoleAvailableUserListAdapter.ViewHolder>() {

    private lateinit var roleAvailableUserList: List<RoleAvailableUser>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RoleAvailableUserItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_row_role_available_user, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(roleAvailableUserList[position])

        val imgTick: ImageView = holder.itemView.findViewById(R.id.img_tick)
        if (roleAvailableUserList[position].tick) {
            imgTick.setImageResource(R.drawable.tick_on)
        } else {
            imgTick.setImageResource(R.drawable.tick_off)
        }

        imgTick.setOnClickListener {
            roleAvailableUserList[position].tick = ! roleAvailableUserList[position].tick
            if (roleAvailableUserList[position].tick) {
                imgTick.setImageResource(R.drawable.tick_on)
            } else {
                imgTick.setImageResource(R.drawable.tick_off)
            }

            // Set Save Button Enable/Disable
            if (HCGlobal.getInstance().currentActivity is JobAddRoleActivity) {
                (HCGlobal.getInstance().currentActivity as JobAddRoleActivity).setSaveButtonEnable()
            } else if (HCGlobal.getInstance().currentActivity is ProcessAddRoleActivity) {
                (HCGlobal.getInstance().currentActivity as ProcessAddRoleActivity).setSaveButtonEnable()
            } else if (HCGlobal.getInstance().currentActivity is AddInterviewersActivity) {
                (HCGlobal.getInstance().currentActivity as AddInterviewersActivity).setSaveButtonEnable()
            }
        }
    }

    fun getRoleAvailableUserList(): List<RoleAvailableUser> {
        return  roleAvailableUserList
    }

    override fun getItemCount(): Int {
        return if(::roleAvailableUserList.isInitialized) roleAvailableUserList.size else 0
    }

    fun updateUserManagerList(roleAvailableUserList: List<RoleAvailableUser>){
        this.roleAvailableUserList = roleAvailableUserList

        // Set Save Button Enable/Disable
        if (HCGlobal.getInstance().currentActivity is JobAddRoleActivity) {
            (HCGlobal.getInstance().currentActivity as JobAddRoleActivity).setSaveButtonEnable()
        }

        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: RoleAvailableUserItemBinding):RecyclerView.ViewHolder(binding.root){

        private val viewModel = RoleAvailableUserViewVM()

        fun bind(roleAvailableUser: RoleAvailableUser){
            viewModel.bind(roleAvailableUser)
            binding.viewModel = viewModel
        }
    }
}
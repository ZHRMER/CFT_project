package com.example.sweethome.hrhelper.presentation.screens.event.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.data.dto.MemberDto

class MemberListAdapter(
    var myAdapterMemberList: List<MemberDto>?,
    private val myMemberListAdapterContract: MemberListAdapterContract
) : RecyclerView.Adapter<MemberListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.member_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (myAdapterMemberList != null) {
            val userName = myAdapterMemberList!![position].firstName + " " + myAdapterMemberList!![position].lastName
            holder.itemPosition = position
            holder.memberNameTextView.text = userName
            holder.memberIdTextView.text = myAdapterMemberList!![position].id.toString()
            holder.memberArrivedSwitch.isChecked = myAdapterMemberList!![position].isArrived
        }
    }

    override fun getItemCount(): Int =
        myAdapterMemberList?.size ?: 0


    fun updateMemberList(memberList: List<MemberDto>) {
        myAdapterMemberList = memberList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var memberNameTextView: TextView = itemView.findViewById(R.id.member_name_text_view)
        var memberIdTextView: TextView = itemView.findViewById(R.id.member_id_text_view)
        var memberArrivedSwitch: Switch = itemView.findViewById(R.id.member_switch)
        var itemPosition = 0

        init {
            itemView.setOnClickListener { myMemberListAdapterContract.onMemberClick(myAdapterMemberList!![itemPosition]) }
            memberArrivedSwitch.setOnCheckedChangeListener { _, isChecked ->
                myAdapterMemberList?.get(itemPosition)!!.isArrived = isChecked
                myMemberListAdapterContract.onMemberArrivedStateChanged(myAdapterMemberList?.get(itemPosition)!!)
            }
        }
    }

    interface MemberListAdapterContract {
        fun onMemberClick(member: MemberDto?)
        fun onMemberArrivedStateChanged(member: MemberDto)
    }
}
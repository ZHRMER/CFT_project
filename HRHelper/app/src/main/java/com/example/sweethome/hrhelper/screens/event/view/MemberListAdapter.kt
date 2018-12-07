package com.example.sweethome.hrhelper.screens.event.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.common_models.User

class MemberListAdapter(
    private val memberList: List<User>?
) : RecyclerView.Adapter<MemberListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.member_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (memberList != null) {
            val userName = memberList[position].firstName + " " + memberList[position].lastName
            holder.itemPosition = position
            holder.memberNameTextView.text = userName
            holder.memberIdTextView.text = memberList[position].id.toString()
        }
    }

    override fun getItemCount(): Int {
        return memberList?.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var memberNameTextView: TextView = itemView.findViewById(R.id.member_name_text_view)
        var memberIdTextView: TextView = itemView.findViewById(R.id.member_id_text_view)
        var itemPosition = 0

        init {
            itemView.setOnClickListener { }
        }
    }
}
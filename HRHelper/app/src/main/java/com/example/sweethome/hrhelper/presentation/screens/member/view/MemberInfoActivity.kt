package com.example.sweethome.hrhelper.presentation.screens.member.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.example.sweethome.hrhelper.data.utils.Constants.KEY_CURRENT_MEMBER

class MemberInfoActivity : AppCompatActivity() {
    private lateinit var myMemberInfoActivityView: MemberInfoActivityView
    private lateinit var myMember: MemberDto

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, MemberInfoActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myMember = intent.getParcelableExtra(KEY_CURRENT_MEMBER)
        setContentView(R.layout.activity_member_info)
        myMemberInfoActivityView =
                MemberInfoActivityView(this, myMember)
        myMemberInfoActivityView.onCreate()
    }

    override fun onResume() {
        super.onResume()
        myMemberInfoActivityView.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        myMemberInfoActivityView.onPause()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}

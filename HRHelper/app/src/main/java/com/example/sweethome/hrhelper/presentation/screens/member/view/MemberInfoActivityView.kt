package com.example.sweethome.hrhelper.presentation.screens.member.view

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import android.widget.ListView
import android.widget.SimpleAdapter
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.data.dto.MemberDto
import com.example.sweethome.hrhelper.presentation.screens.member.presenter.MemberInfoPresenter


class MemberInfoActivityView(private var myActivity: AppCompatActivity?, private var myMember: MemberDto) {

    private lateinit var myMemberInfoPresenter: MemberInfoPresenter

    fun onCreate() {
        initToolbar()
        initMemberInfo()
        myMemberInfoPresenter =
                MemberInfoPresenter(myActivity)
    }

    fun onResume(activity: AppCompatActivity) {
        myActivity = activity
        myMemberInfoPresenter.attach(myActivity)
    }

    fun onPause() {
        myActivity = null
        myMemberInfoPresenter.detach()
    }

    private fun initMemberInfo() {
        val listView = myActivity?.findViewById(R.id.member_info_list_view) as ListView


        var map: HashMap<String, String> = HashMap(1)
        val arrayList: ArrayList<HashMap<String, String>> = ArrayList()
        map["Item"] = "Patronymic"
        map["Value"] = myMember.patronymic!!
        arrayList.add(map)

        map = HashMap(1)
        map["Item"] = "Phone"
        map["Value"] = myMember.phone!!
        arrayList.add(map)

        map = HashMap(1)
        map["Item"] = "Email"
        map["Value"] = myMember.email!!
        arrayList.add(map)

        map = HashMap(1)
        map["Item"] = "CityDto"
        map["Value"] = myMember.city!!
        arrayList.add(map)

        map = HashMap(1)
        map["Item"] = "Company"
        map["Value"] = myMember.company!!
        arrayList.add(map)

        map = HashMap(1)
        map["Item"] = "Position"
        map["Value"] = myMember.position!!
        arrayList.add(map)

        map = HashMap(1)
        map["Item"] = "Addition"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            map["Value"] = Html.fromHtml(myMember.addition!!, Html.FROM_HTML_MODE_LEGACY).toString()
        }
        arrayList.add(map)

        val adapter = SimpleAdapter(
            myActivity, arrayList, R.layout.member_info_item,
            arrayOf("Item", "Value"),
            intArrayOf(R.id.member_item_name_text_view, R.id.member_item_value_text_view)
        )
        listView.adapter = adapter
    }

    private fun initToolbar() {
        val toolbar = myActivity?.findViewById<Toolbar>(R.id.toolbar_activity_member_info)
        myActivity?.setSupportActionBar(toolbar)
        myActivity?.supportActionBar?.title = "${myMember.firstName} ${myMember.lastName}"
        myActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}

package com.example.sweethome.hrhelper.presentation.screens.member.view

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import android.widget.ListView
import android.widget.SimpleAdapter
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.data.dto.MemberDto


class MemberInfoActivityView(private var myActivity: AppCompatActivity?, private var myMember: MemberDto) {
    private val KEY_ITEM_NAME = "Item"
    private val KEY_ITEM_VALUE = "Value"

    fun onCreate() {
        initToolbar()
        initMemberInfo()
    }

    fun onResume(activity: AppCompatActivity) {
        myActivity = activity
    }

    fun onPause() {
        myActivity = null
    }

    private fun initMemberInfo() {
        val listView = myActivity?.findViewById(R.id.member_info_list_view) as ListView


        var map: HashMap<String, String> = HashMap(1)
        val arrayList: ArrayList<HashMap<String, String>> = ArrayList()
        map[KEY_ITEM_NAME] = "Patronymic"
        map[KEY_ITEM_VALUE] = myMember.patronymic!!
        arrayList.add(map)

        map = HashMap(1)
        map[KEY_ITEM_NAME] = "Phone"
        map[KEY_ITEM_VALUE] = myMember.phone!!
        arrayList.add(map)

        map = HashMap(1)
        map[KEY_ITEM_NAME] = "Email"
        map[KEY_ITEM_VALUE] = myMember.email!!
        arrayList.add(map)

        map = HashMap(1)
        map[KEY_ITEM_NAME] = "CityDto"
        map[KEY_ITEM_VALUE] = myMember.city!!
        arrayList.add(map)

        map = HashMap(1)
        map[KEY_ITEM_NAME] = "Company"
        map[KEY_ITEM_VALUE] = myMember.company!!
        arrayList.add(map)

        map = HashMap(1)
        map[KEY_ITEM_NAME] = "Position"
        map[KEY_ITEM_VALUE] = myMember.position!!
        arrayList.add(map)

        map = HashMap(1)
        map[KEY_ITEM_NAME] = "Addition"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            map[KEY_ITEM_VALUE] = Html.fromHtml(myMember.addition!!, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            map[KEY_ITEM_VALUE] = myMember.addition!!
        }
        arrayList.add(map)

        val adapter = SimpleAdapter(
            myActivity, arrayList, R.layout.member_info_item,
            arrayOf(KEY_ITEM_NAME, KEY_ITEM_VALUE),
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

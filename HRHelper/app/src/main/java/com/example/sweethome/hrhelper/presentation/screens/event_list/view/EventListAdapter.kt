package com.example.sweethome.hrhelper.presentation.screens.event_list.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sweethome.hrhelper.R
import com.example.sweethome.hrhelper.data.utils.Constants.KEY_EVENT_DATE_FORMAT
import com.example.sweethome.hrhelper.domain.entity.Event
import java.text.SimpleDateFormat
import java.util.*

class EventListAdapter(
    private val eventList: List<Event>?,
    private val myEventListAdapterContract: EventListAdapterContract
) : RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    private val simpleDateFormatOutput = SimpleDateFormat(KEY_EVENT_DATE_FORMAT, Locale("ru"))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (eventList != null) {
            holder.itemPosition = position
            holder.eventDateTextView.text = simpleDateFormatOutput.format(eventList[position].beginDate)
            holder.eventTitleTextView.text = eventList[position].title
            holder.eventDescriptionTextView.text = eventList[position].description
        }
    }

    override fun getItemCount(): Int =
        eventList?.size ?: 0


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var eventTitleTextView: TextView = itemView.findViewById(R.id.event_title_text_view)
        var eventDescriptionTextView: TextView = itemView.findViewById(R.id.event_description_text_view)
        var eventDateTextView: TextView = itemView.findViewById(R.id.event_data_text_view)
        var itemPosition = 0

        init {
            itemView.setOnClickListener { myEventListAdapterContract.onEventClick(eventList?.get(itemPosition)) }
        }
    }

    interface EventListAdapterContract {
        fun onEventClick(event: Event?)
    }
}

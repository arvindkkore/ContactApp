package com.test.contactapp.presenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.contactapp.R
import com.test.contactapp.data.models.CallModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_call_log.*


class CallLogAdapter(val context: Context,val list: MutableList<CallModel>) :
    RecyclerView.Adapter<CallLogAdapter.ViewHolder>() {


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_call_log, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val data = list.get(position)
      holder.username.text =data.contactNumber
    }

}

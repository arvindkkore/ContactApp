package com.test.contactapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.test.contactapp.R
import com.test.contactapp.models.CallModel

class CallListAdapter(val context: Context,val list:List<CallModel>) : RecyclerView.Adapter<CallListAdapter.ViewHolder>() {

    class ViewHolder(view:View): RecyclerView.ViewHolder(view)
    {
        val textView:TextView
        init
        {
            textView = view .findViewById(R.id.call_log)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_call_log,parent,false)
        return  ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val model =list.get(position)
        holder.textView.text=model.contactNumber
    }


}

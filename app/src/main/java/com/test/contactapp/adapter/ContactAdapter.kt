package com.test.contactapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.contactapp.R
import com.test.contactapp.models.ContactDTO

class ContactAdapter(val context: Context, val list: List<ContactDTO> ) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {


    class ViewHolder(val view:View) :RecyclerView.ViewHolder(view)
    {
     val textView:TextView
    init {
        textView =view.findViewById(R.id.call_log)
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_call_log,parent,false)
        return ViewHolder(view = view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
      val  contactDTO =list.get(position)
        holder.textView.text = contactDTO.displayName
    }
}
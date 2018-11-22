package com.test.contactapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MissedCallAdapter(val context: Context,val list: MutableList<String>) : RecyclerView.Adapter<MissedCallAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissedCallAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_call_log, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = 10

    override fun onBindViewHolder(holder: MissedCallAdapter.ViewHolder, position: Int) {

    }

}

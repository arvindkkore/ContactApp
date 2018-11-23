package com.test.contactapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.contactapp.R
import com.test.contactapp.models.CallModel

import android.widget.AdapterView
import androidx.appcompat.widget.ListPopupWindow


class CallListAdapter(val context: Context,val list:List<CallModel>) : RecyclerView.Adapter<CallListAdapter.ViewHolder>() {

    class ViewHolder(view:View): RecyclerView.ViewHolder(view)
    {
        val textViewName:TextView
        val textViewNumber:TextView
        val textViewTime:TextView
        val imageview_profile : ImageView
        init
        {
            textViewName = view .findViewById(R.id.textview_name)
            textViewNumber = view .findViewById(R.id.textview_phone)
            textViewTime = view .findViewById(R.id.textview_time)
            imageview_profile = view .findViewById(R.id.imageview_profile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_call_log_new,parent,false)
        return  ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val model =list.get(position)
        holder.textViewName.text="Dummy Name"
        holder.textViewNumber.text =model.contactNumber
        holder.textViewTime.text ="dummy_time"
        Glide.with(context).load(R.drawable.img_avatar).apply(RequestOptions().circleCrop()).into(holder.imageview_profile)
        holder.textViewTime.setOnClickListener {
            showListPopupWindow(holder.textViewTime)

        }

    }

    /**
     * I show MATCH_PARENT popup here, you can show a specific with for popup (eg: 100px, 200px,...)
     * If you set popup width = WRAP_CONTENT, popup width will equals ANCHOR width
     */
    private fun showListPopupWindow(anchor: View) {
        val listPopupItems = ArrayList<String>()
        listPopupItems.add("Menu 1")
        listPopupItems.add("Menu 2")
        listPopupItems.add("Menu 3")

        val listPopupWindow = createListPopupWindow(anchor, ViewGroup.LayoutParams.MATCH_PARENT, listPopupItems)
        listPopupWindow.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            listPopupWindow.dismiss()
            Toast.makeText(context, "clicked at $position", Toast.LENGTH_SHORT)
                .show()
        })
        listPopupWindow.show()
    }

    private fun createListPopupWindow(
        anchor: View, width: Int,
        items: List<String>
    ): ListPopupWindow {
        val popup = ListPopupWindow(context)
        val adapter = ListPopupWindowAdapter(items)
        popup.setAnchorView(anchor)
        popup.setWidth(width)
        popup.setAdapter(adapter)
        return popup
    }
}

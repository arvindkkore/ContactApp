package com.test.contactapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.contactapp.R
import com.test.contactapp.models.CallModel
import kotlinx.android.synthetic.main.activity_home.*

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

    }


}

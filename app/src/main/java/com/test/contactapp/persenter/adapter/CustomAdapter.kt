package com.test.contactapp.persenter.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.test.contactapp.R
import com.test.contactapp.data.models.Contact_Model

//http://www.androhub.com/android-read-contacts-using-content-provider/
class CustomAdapter(internal var mContext: Context, private val list: MutableList<Contact_Model>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact_new, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //        Glide.with(mContext).load(R.drawable.lady).into(holder.imageView);
        val model = list[position]
        /* if (position %2 == 0)
        {
            Glide.with(mContext)
                    .load(R.drawable.profile)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(holder.imageView);
        }else
        {
            Glide.with(mContext)
                    .load(R.drawable.lady)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(holder.imageView);
        }*/
        // Set items to all view
        if (model.contactName != "" && model.contactName != null) {
            holder.textview_name.text = model.contactName
        } else {
            holder.textview_name.text = "No Name"
        }


        if (model.contactNumber != "" && model.contactNumber != null) {
            holder.contactNumber.text = "CONTACT NUMBER - n" + model.contactNumber
        } else {
            holder.contactNumber.text = "CONTACT NUMBER - n" + "No Contact Number"
        }
        if (model.contactPhoto != "" && model.contactPhoto != null) {
            Glide.with(mContext)
                .load(model.contactPhoto)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(holder.imageView)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var contactNumber: TextView
        var textview_name: TextView
        internal var imageView: ImageView

        init {
            textview_name = itemView.findViewById(R.id.textView)
            contactNumber = itemView.findViewById(R.id.textView2)
            imageView = itemView.findViewById(R.id.user_img)

        }
    }


}

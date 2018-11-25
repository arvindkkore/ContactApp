package com.test.contactapp.persenter.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.ListPopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.test.contactapp.R
import com.test.contactapp.data.models.Contact_Model
import com.test.contactapp.data.models.Phone
import kotlinx.android.extensions.LayoutContainer

//http://www.androhub.com/android-read-contacts-using-content-provider/
class CustomAdapter(internal var mContext: Context, private val list: MutableList<Contact_Model>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact_new1, parent, false)
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


       /* if (model.contactNumber != "" && model.contactNumber != null) {
            holder.contactNumber.text = "CONTACT NUMBER - n" + model.contactNumber
        } else {
            holder.contactNumber.text = "CONTACT NUMBER - n" + "No Contact Number"
        }*/
        if (model.contactPhoto != "" && model.contactPhoto != null) {

            Glide.with(mContext)
                .load(model.contactPhoto)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
              //  .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                .into(holder.imageView)
        }
        holder.imageViewIcon.setOnClickListener {
            showListPopupWindow(holder.imageViewIcon)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

       // var contactNumber: TextView
        var textview_name: TextView
        internal var imageView: ImageView
        internal var imageViewIcon: ImageView

        init {
            textview_name = itemView.findViewById(R.id.textview_name)
           // contactNumber = itemView.findViewById(R.id.textView2)
            imageView = itemView.findViewById(R.id.user_img)
            imageViewIcon = itemView.findViewById(R.id.imageview_phone)

        }
    }

    /**
     * I show MATCH_PARENT popup here, you can show a specific with for popup (eg: 100px, 200px,...)
     * If you set popup width = WRAP_CONTENT, popup width will equals ANCHOR width
     */
    private fun showListPopupWindow(anchor: View) {
        val listPopupItems = ArrayList<Phone>()
        listPopupItems.add(Phone("+919713151639","Personal"))
        listPopupItems.add(Phone("+916263239374","Work"))
        listPopupItems.add(Phone("+919754747759","Home"))

        val listPopupWindow = createListPopupWindow(anchor, ViewGroup.LayoutParams.MATCH_PARENT, listPopupItems)
        listPopupWindow.setOnItemClickListener{parent, view, position, id ->
            listPopupWindow.dismiss()
            Toast.makeText(mContext, "clicked at $position", Toast.LENGTH_SHORT)
                .show()
        }
        listPopupWindow.show()
    }

    private fun createListPopupWindow(
        anchor: View, width: Int,
        items: List<Phone>
    ): ListPopupWindow {
        val popup = ListPopupWindow(mContext)
        val adapter = ListPopupWindowAdapter(items)
        popup.setAnchorView(anchor)
        popup.setWidth(width)
        popup.setAdapter(adapter)
        return popup
    }

}

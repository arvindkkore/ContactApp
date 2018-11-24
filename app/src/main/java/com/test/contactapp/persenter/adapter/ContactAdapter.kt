package com.test.contactapp.persenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.test.contactapp.R
import com.test.contactapp.data.models.ContactDTO

class ContactAdapter(val context: Context, val list: List<ContactDTO> ) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {


    class ViewHolder(val view:View) :RecyclerView.ViewHolder(view)
    {
     val textView:TextView
    init {
        textView =view.findViewById(R.id.textview_number)
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
        holder.textView.setOnClickListener {

            //https@ //android.jlelse.eu/avatarview-custom-implementation-of-imageview-4bcf0714d09d

            //creating a popup menu
            val popup = PopupMenu(context, holder.textView)
            //inflating menu from xml resource
            popup.inflate(R.menu.options_menu)
            //adding click listener
            popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
               override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.getItemId()) {
                        R.id.menu1 -> {
                            Toast.makeText(context,"menu1",Toast.LENGTH_LONG).show()
                        }
                        R.id.menu2 -> {
                            Toast.makeText(context,"menu2",Toast.LENGTH_LONG).show()
                        }
                        R.id.menu3 -> {
                            Toast.makeText(context,"menu3",Toast.LENGTH_LONG).show()
                        }
                    }//handle menu1 click
                    //handle menu2 click
                    //handle menu3 click
                    return false
                }
            })
            //displaying the popup
            popup.show()
        }
    }
}
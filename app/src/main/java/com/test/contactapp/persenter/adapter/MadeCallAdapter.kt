package com.test.contactapp.persenter.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.test.contactapp.R
import com.test.contactapp.data.models.CallModel

class MadeCallAdapter(val context: Context, val list: MutableList<CallModel>) : RecyclerView.Adapter<MadeCallAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_call_log_homefragment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val model =list.get(position)
        holder.textview_number.text=model.contactNumber
        holder.imageView_type.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_call_made))
        holder.imageView_type.setColorFilter(ContextCompat.getColor(context, R.color.green))
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textview_number =itemView.findViewById<TextView>(R.id.textview_number)
        val imageView_type =itemView.findViewById<ImageView>(R.id.image_call_type)
    }

}

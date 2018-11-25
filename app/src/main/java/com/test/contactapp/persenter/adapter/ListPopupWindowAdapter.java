package com.test.contactapp.persenter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.test.contactapp.R;
import com.test.contactapp.data.models.Phone;

import java.util.List;

public class ListPopupWindowAdapter extends BaseAdapter {
    private List<Phone> items;

    public ListPopupWindowAdapter(List<Phone> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Phone getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_context_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textview_phone.setText(getItem(position).getPhoneNumber());
        holder.textview_type.setText(getItem(position).getNumberType());
        return convertView;
    }

    static class ViewHolder {
        TextView textview_phone;
        TextView textview_type;


        ViewHolder(View view) {
            textview_phone = view.findViewById(R.id.textview_phone);
            textview_type = view.findViewById(R.id.textview_type);

        }
    }
}

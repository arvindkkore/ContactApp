package com.test.contactapp.persenter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.test.contactapp.R;

import java.util.List;

public class ListPopupWindowAdapter extends BaseAdapter {
    private List<String> items;

    public ListPopupWindowAdapter(List<String> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public String getItem(int i) {
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

        /*holder.tvTitle.setText(getItem(position).getTitle());
        holder.ivImage.setImageResource(getItem(position).getImageRes());*/
        return convertView;
    }

    static class ViewHolder {
      /*  TextView tvTitle;
        ImageView ivImage;*/

        ViewHolder(View view) {
         /*   tvTitle = view.findViewById(R.id.text);
            ivImage = view.findViewById(R.id.image);*/
        }
    }
}

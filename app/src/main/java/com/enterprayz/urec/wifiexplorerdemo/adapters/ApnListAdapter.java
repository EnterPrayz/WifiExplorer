package com.enterprayz.urec.wifiexplorerdemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.enterprayz.urec.wifiexplorerdemo.R;
import com.enterprayz.urec.wifiexplorerdemo.items.APNClientListItem;

import java.util.ArrayList;

/**
 * Created by con on 03.01.15.
 */
public class ApnListAdapter extends BaseAdapter {
    private ArrayList<APNClientListItem> items;
    private Holder holder;
    private Context context;
    private final LayoutInflater inflater;

    static class Holder {
        public TextView tvIpAddress;
        public TextView tvHardAddress;
        public TextView tvItemVendorName;

    }

    public ApnListAdapter(ArrayList<APNClientListItem> items, Context context) {
        this.items = items;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addItem(APNClientListItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void setItems(ArrayList<APNClientListItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public ArrayList<APNClientListItem> getItems() {
        return items;
    }

    public void removeItem(String HWAddr) {
        int index = 0;
        for (APNClientListItem item : items) {
            if (item.getHWAddr().equals(HWAddr)) {
                items.remove(index);
                notifyDataSetChanged();
                break;
            }
            index++;
        }
    }

    public void removeAll() {
        items.clear();
        notifyDataSetChanged();
    }

    public boolean isItemPresent(String HWAddr) {
        boolean isPresent = false;
        for (APNClientListItem item : items) {
            if (item.getHWAddr().equals(HWAddr)) {
                isPresent = true;
                break;
            }
        }
        return isPresent;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public APNClientListItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        APNClientListItem item = items.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_apn_list, null);
            holder = new Holder();
            holder.tvHardAddress = (TextView) convertView
                    .findViewById(R.id.tv_item_sub_title_apn_list);
            holder.tvIpAddress = (TextView) convertView
                    .findViewById(R.id.tv_item_title_apn_list);
            holder.tvItemVendorName = (TextView) convertView
                    .findViewById(R.id.tv_item_vendor_name);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tvHardAddress.setText(item.getHWAddr());
        holder.tvIpAddress.setText(item.getIpAddr());
        holder.tvItemVendorName.setText(item.getDeviceVendor());
        return convertView;

    }
}

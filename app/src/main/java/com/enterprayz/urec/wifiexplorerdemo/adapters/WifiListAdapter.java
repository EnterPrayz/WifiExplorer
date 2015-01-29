package com.enterprayz.urec.wifiexplorerdemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.enterprayz.urec.wifiexplorerdemo.R;
import com.enterprayz.urec.wifiexplorerdemo.items.WifiListItem;
import com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions.WifiOptionsBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by con on 26.12.14.
 */
public class WifiListAdapter extends BaseAdapter {

    private ArrayList<WifiListItem> items;
    private Holder holder;
    private Context context;
    private final LayoutInflater inflater;

    static class Holder {
        public TextView tvTitle;
        public TextView tvSubTite;
        public ImageView ivWifiLevel;
        public ImageView ivWifiLock;
    }

    public WifiListAdapter(ArrayList<WifiListItem> items, Context context) {
        this.items = items;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addItem(WifiListItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<WifiListItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void removeItem(long itemId) {
        int index = 0;
        for (WifiListItem item : items) {
            if (item.getId() == itemId) {
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

    public boolean isItemPresent(long itemId) {
        boolean isPresent = false;
        for (WifiListItem item : items) {
            if (item.getId() == itemId) {
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
    public WifiListItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        WifiListItem item = items.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_wifi_list, null);
            holder = new Holder();
            holder.tvTitle = (TextView) convertView
                    .findViewById(R.id.tv_item_title);
            holder.tvSubTite = (TextView) convertView
                    .findViewById(R.id.tv_item_sub_title);
            holder.ivWifiLevel = (ImageView) convertView
                    .findViewById(R.id.iv_wifi_level);
            holder.ivWifiLock = (ImageView) convertView
                    .findViewById(R.id.iv_wifi_lock);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tvTitle.setText(item.getTitle());

        Picasso.with(context)
                .load(item.getSignalLevelImagePath())
                .error(R.drawable.ic_launcher)
                .into(holder.ivWifiLevel);

        int secureKey = WifiOptionsBuilder.getWifiSecure(item.getInfoItem().getCapabilities())[0];
        if (secureKey != WifiOptionsBuilder.NO_KEY_NET_ATTR) {
            Picasso.with(context)
                    .load(item.getLockWifiImagePath())
                    .error(R.drawable.ic_launcher)
                    .into(holder.ivWifiLock);
        }else {
            holder.ivWifiLock.setImageResource(R.color.transparent);
        }
        holder.tvSubTite.setText(item.getSubtitle());


        return convertView;

    }
}
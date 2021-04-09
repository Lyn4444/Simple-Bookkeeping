package com.example.simplebookkeeping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simplebookkeeping.R;
import com.example.simplebookkeeping.db.AccountBean;

import java.util.List;

public class MainListAdapter extends BaseAdapter {

    Context context;
    List<AccountBean> accountBeanList;
    LayoutInflater inflater;

    public MainListAdapter(Context context, List<AccountBean> accountBeanList) {
        this.context = context;
        this.accountBeanList = accountBeanList;
//    布局加载, inflater初始化
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return accountBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return accountBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewItem viewItem;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.main_item, parent, false);
            viewItem = new ViewItem(convertView);
            convertView.setTag(viewItem);
        } else {
            viewItem = (ViewItem) convertView.getTag();
        }
        AccountBean accountBean = accountBeanList.get(position);
        viewItem.imageView.setImageResource(accountBean.getSelectedImage());
        viewItem.titleView.setText(accountBean.getTypeName());
        viewItem.remarkView.setText(accountBean.getRemark());
        viewItem.moneyView.setText("￥ " + accountBean.getMoney());
        viewItem.timeView.setText(accountBean.getTime());
        return convertView;
    }

    class ViewItem {

        ImageView imageView;
        TextView titleView, remarkView, moneyView, timeView;

        public ViewItem(View view) {
            imageView = view.findViewById(R.id.main_item_img);
            titleView = view.findViewById(R.id.main_item_title);
            remarkView = view.findViewById(R.id.main_item_remarks);
            moneyView = view.findViewById(R.id.main_item_money);
            timeView = view.findViewById(R.id.main_item_time);
        }
    }

}

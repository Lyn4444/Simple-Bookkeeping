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
import com.example.simplebookkeeping.db.TypeBean;

import java.util.List;

/*
 *
 * 暂时不考虑复用问题（item已全部显示，不会因为滑动消失，没有多余convertView）
 */
public class TypeBaseAdapter extends BaseAdapter {
    private final Context context;
    private final List<TypeBean> typeBeanList;
    public int selectedPosition = 0;
    private final int kind;

    public TypeBaseAdapter(Context context, List<TypeBean> typeBeanList, int kind) {
        this.context = context;
        this.typeBeanList = typeBeanList;
        this.kind = kind;
    }

    @Override
    public int getCount() {
        return typeBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return typeBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_record_out_gv, parent, false);
//        查找布局控件
        ImageView imageView = convertView.findViewById(R.id.item_record_out_imageView);
        TextView textView = convertView.findViewById(R.id.item_record_out_textView);

        TypeBean typeBean = typeBeanList.get(position);
        textView.setText(typeBean.getTypeName());
        if (selectedPosition == position) {
            imageView.setImageResource(typeBean.getSelectedImageId());
            if (kind == 0) {
                textView.setTextColor(0xFFd4237a);
            } else {
                textView.setTextColor(0xFF1296db);
            }
        } else {
            imageView.setImageResource(typeBean.getImageId());
        }
        return convertView;
    }
}

package com.example.simplebookkeeping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.simplebookkeeping.R;
import com.example.simplebookkeeping.db.ChartBean;

import java.text.DecimalFormat;
import java.util.List;

/*
 *
 *  ChartBaseFragment 的 chartListView适配器
 *
 * 显示chart_item里面的内容
 *
 */
public class ChartItemAdapter extends BaseAdapter {

    Context context;
    List<ChartBean> chartBeanList;
    LayoutInflater inflater;

    public ChartItemAdapter(Context context, List<ChartBean> chartBeanList) {
        this.context = context;
        this.chartBeanList = chartBeanList;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return chartBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return chartBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        ViewItem viewItem;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.chart_item, parent, false);
            viewItem = new ViewItem(convertView);
            convertView.setTag(viewItem);
        } else {
            viewItem = (ViewItem) convertView.getTag();
        }

        ChartBean bean = chartBeanList.get(position);
        viewItem.imageView.setImageResource(bean.getSelectedImageId());
        viewItem.typeName.setText(bean.getTypeName());
        viewItem.ratio.setText(decimalFormat.format(bean.getRatio() * 100) + "%");
        viewItem.money.setText("￥" + bean.getTotalMoney());

        return convertView;
    }

    class ViewItem {
        ImageView imageView;
        TextView typeName, ratio, money;

        public ViewItem(View view) {
            imageView = view.findViewById(R.id.chart_item_img);
            typeName = view.findViewById(R.id.chart_item_typeName);
            ratio = view.findViewById(R.id.chart_item_ratio);
            money = view.findViewById(R.id.chart_item_money);
        }
    }
}

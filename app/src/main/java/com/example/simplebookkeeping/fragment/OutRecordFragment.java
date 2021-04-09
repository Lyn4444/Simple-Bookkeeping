package com.example.simplebookkeeping.fragment;

import android.view.View;

import com.example.simplebookkeeping.R;
import com.example.simplebookkeeping.adapter.TypeBaseAdapter;
import com.example.simplebookkeeping.db.DBManager;
import com.example.simplebookkeeping.db.TypeBean;

import java.util.ArrayList;
import java.util.List;

public class OutRecordFragment extends BaseRecordFragment {


    @Override
    public void initComponent(View view) {
        super.initComponent(view);
//        组件绑定
        keyboardView = view.findViewById(R.id.record_fragment_out_keyboard);
        moneyEdit = view.findViewById(R.id.record_fragment_out_edit_money);
        typeImage = view.findViewById(R.id.record_fragment_out_selected_ic);
        typeGrid = view.findViewById(R.id.record_fragment_out_gridView);
        typeView = view.findViewById(R.id.record_fragment_out_selected_text);
        remarksView = view.findViewById(R.id.record_fragment_out_remark);
        timeView = view.findViewById(R.id.record_fragment_out_time);

        remarksView.setOnClickListener(this);
        timeView.setOnClickListener(this);
    }

    @Override
    public void saveAccountBeanToDB() {
        super.saveAccountBeanToDB();

        accountBean.setKind(0);
        DBManager.insertToAccountTable(accountBean);
    }

    @Override
    public void loadDataToGridView() {
        super.loadDataToGridView();

        typeBeanList = new ArrayList<>();
        typeBaseAdapter = new TypeBaseAdapter(getContext(), typeBeanList, 0);
        typeGrid.setAdapter(typeBaseAdapter);
//        获取数据库中内的内容
        List<TypeBean> _typeBeanList = DBManager.getTypeBeanList(0);
        typeBeanList.addAll(_typeBeanList);
        typeBaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.record_fragment_out_time:
                showTimeDialog();
                break;
            case R.id.record_fragment_out_remark:
                showRemarkDialog();
                break;
        }
    }

}

package com.example.simplebookkeeping.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simplebookkeeping.R;
import com.example.simplebookkeeping.adapter.TypeBaseAdapter;
import com.example.simplebookkeeping.db.AccountBean;
import com.example.simplebookkeeping.db.DBManager;
import com.example.simplebookkeeping.db.TypeBean;

import java.util.ArrayList;
import java.util.List;

public class InRecordFragment extends BaseRecordFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_record_in, container, false);

        accountBean = new AccountBean();

        accountBean.setTypeName("薪资");
        accountBean.setSelectedImage(R.mipmap.in_xinzi_fs);
        onCreateMethod(view);
        return view;
    }

    @Override
    public void initComponent(View view) {
        super.initComponent(view);
//        组件绑定
        keyboardView = view.findViewById(R.id.record_fragment_in_keyboard);
        moneyEdit = view.findViewById(R.id.record_fragment_in_edit_money);
        typeImage = view.findViewById(R.id.record_fragment_in_selected_ic);
        typeGrid = view.findViewById(R.id.record_fragment_in_gridView);
        typeView = view.findViewById(R.id.record_fragment_in_selected_text);
        remarksView = view.findViewById(R.id.record_fragment_in_remark);
        timeView = view.findViewById(R.id.record_fragment_in_time);

        remarksView.setOnClickListener(this);
        timeView.setOnClickListener(this);
    }

    @Override
    public void saveAccountBeanToDB() {
        super.saveAccountBeanToDB();

        accountBean.setKind(1);
        DBManager.insertToAccountTable(accountBean);
    }

    @Override
    public void loadDataToGridView() {
        super.loadDataToGridView();

        typeBeanList = new ArrayList<>();
        typeBaseAdapter = new TypeBaseAdapter(getContext(), typeBeanList, 1);
        typeGrid.setAdapter(typeBaseAdapter);
//        获取数据库中内的内容
        List<TypeBean> _typeBeanList = DBManager.getTypeBeanList(1);
        typeBeanList.addAll(_typeBeanList);
        typeBaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.record_fragment_in_time:
                showTimeDialog();
                break;
            case R.id.record_fragment_in_remark:
                showRemarkDialog();
                break;
        }
    }

}

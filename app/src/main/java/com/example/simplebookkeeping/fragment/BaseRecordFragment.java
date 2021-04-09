package com.example.simplebookkeeping.fragment;

import android.annotation.SuppressLint;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simplebookkeeping.R;
import com.example.simplebookkeeping.adapter.TypeBaseAdapter;
import com.example.simplebookkeeping.db.AccountBean;
import com.example.simplebookkeeping.db.TypeBean;
import com.example.simplebookkeeping.utils.KeyboardUtil;
import com.example.simplebookkeeping.utils.RemarkDialog;
import com.example.simplebookkeeping.utils.TimeDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * The Base Fragment Of Record Fragment.
 */
public abstract class BaseRecordFragment extends Fragment implements View.OnClickListener {

    KeyboardView keyboardView;
    EditText moneyEdit;
    ImageView typeImage;
    TextView typeView, remarksView, timeView;
    GridView typeGrid;
    List<TypeBean> typeBeanList;
    TypeBaseAdapter typeBaseAdapter;
    AccountBean accountBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record_out, container, false);

        accountBean = new AccountBean();
        accountBean.setTypeName("餐饮");
        accountBean.setSelectedImage(R.mipmap.ic_canyin_fs);
        onCreateMethod(view);

        return view;
    }

    public void onCreateMethod(View view) {

        initView(view);
//        初始化获取当前时间
        initTime();
//        GridView填充数据
        loadDataToGridView();
//        设置监听GridView
        setGridViewListener();
    }

    public void initComponent(View view) {
//        组件绑定
//        keyboardView = view.findViewById(R.id.record_fragment_out_keyboard);
//        moneyEdit = view.findViewById(R.id.record_fragment_out_edit_money);
//        typeImage = view.findViewById(R.id.record_fragment_out_selected_ic);
//        typeGrid = view.findViewById(R.id.record_fragment_out_gridView);
//        typeView = view.findViewById(R.id.record_fragment_out_selected_text);
//        remarksView = view.findViewById(R.id.record_fragment_out_remark);
//        timeView = view.findViewById(R.id.record_fragment_out_time);

    }

//    子类进行重写方法，父类调用
    public void saveAccountBeanToDB() {
    }

    /*
     *
     * abstract类不一定要求重写onClick接口
     * 重写接口进行判断remarksView，timeView是否被调用
     * 最后调用相应Dialog
     */
    @Override
    public void onClick(View v) {
    }

    /*
     *
     * TimeDialog弹出,获取时间并保存
     */
    public void showTimeDialog() {
        TimeDialog dialog = new TimeDialog(Objects.requireNonNull(getContext()));
        dialog.show();

        dialog.setOnEnsureListener(() -> {
            timeView.setText(dialog.time);
            accountBean.setTime(dialog.time);
            accountBean.setYear(dialog.year);
            accountBean.setMonth(dialog.month);
            accountBean.setDay(dialog.day);
        });
    }

    /*
     *
     * RemarkDialog弹出,获取备注并保存
     */
    public void showRemarkDialog() {
        String remark_tmp = remarksView.getText().toString().trim();
        RemarkDialog dialog = new RemarkDialog(Objects.requireNonNull(getContext()));
        dialog.show();
        dialog.setRemarkDialog();
        dialog.setEditText(remark_tmp);
        dialog.setOnEnsureListener(() -> {
            String remarkEditText = dialog.getEditText();
            if (!TextUtils.isEmpty(remarkEditText)) {
                remarksView.setText(remarkEditText);
                accountBean.setRemark(remarkEditText);
            }
            dialog.cancel();
        });
    }

    private void initView(View view) {
        initComponent(view);
//        显示自定义键盘
        KeyboardUtil keyboardUtil = new KeyboardUtil(keyboardView, moneyEdit);
        keyboardUtil.showKeyboard();
//        监听接口设置,获取信息保存在数据库中，返回上一级页面
        keyboardUtil.setOnEnsureListener(() -> {
            String money_str = moneyEdit.getText().toString().replace(" ", "");
            if (TextUtils.isEmpty(money_str) || money_str.equals("0")) {
                Objects.requireNonNull(getActivity()).finish();
                ;
                return;
            }
            float money_float = Float.parseFloat(money_str);
            accountBean.setMoney(money_float);
            saveAccountBeanToDB();
            Objects.requireNonNull(getActivity()).finish();
        });

    }

    private void initTime() {
        Date date = new Date();
        String pattern = "yyyy/MM/dd";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String time = dateFormat.format(date);
        timeView.setText(time);
        accountBean.setTime(time);
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int month = instance.get(Calendar.MONTH) + 1;
        int day = instance.get(Calendar.DAY_OF_MONTH);
        accountBean.setYear(year);
        accountBean.setMonth(month);
        accountBean.setDay(day);
    }

    public void loadDataToGridView() {
//        typeBeanList = new ArrayList<>();
//        typeBaseAdapter = new TypeBaseAdapter(getContext(), typeBeanList, 0);
//        typeGrid.setAdapter(typeBaseAdapter);
//        获取数据库中内的内容
//        List<TypeBean> _typeBeanList = DBManager.getTypeBeanList(0);
//        typeBeanList.addAll(_typeBeanList);
//        typeBaseAdapter.notifyDataSetChanged();
    }

    private void setGridViewListener() {
        typeGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                typeBaseAdapter.selectedPosition = position;
//                刷新
                typeBaseAdapter.notifyDataSetChanged();
                TypeBean typeBean = typeBeanList.get(position);
//                动态更改图标和文字
                String typeName = typeBean.getTypeName();
                typeView.setText(typeName);
                accountBean.setTypeName(typeName);
                int selectedImageId = typeBean.getSelectedImageId();
                typeImage.setImageResource(selectedImageId);
                accountBean.setSelectedImage(selectedImageId);
            }
        });
    }

}
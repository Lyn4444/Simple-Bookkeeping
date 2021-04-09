package com.example.simplebookkeeping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simplebookkeeping.adapter.MainListAdapter;
import com.example.simplebookkeeping.db.AccountBean;
import com.example.simplebookkeeping.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ListView searchList;
    EditText searchEdit;
    TextView startView, emptyView;

    List<AccountBean> accountBeanList;
    MainListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
//        配置数据列表和适配器
        accountBeanList = new ArrayList<>();
        listAdapter = new MainListAdapter(this, accountBeanList);
        searchList.setAdapter(listAdapter);
    }

    private void initView() {
        searchList = findViewById(R.id.search_listView);
        searchEdit = findViewById(R.id.search_edit);
        startView = findViewById(R.id.search_start);
        emptyView = findViewById(R.id.search_data_empty);

//        软键盘enter搜索
        searchEdit.setImeOptions(EditorInfo.IME_ACTION_SEND);
        searchEdit.setSingleLine();
        searchEdit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String searchStr = searchEdit.getText().toString().trim();
                if (!TextUtils.isEmpty(searchStr)) {
                    List<AccountBean> resBeanList = searchDataFromDB(searchStr);
                    if (resBeanList.size() == 0) {
                        startView.setVisibility(View.GONE);
                        searchList.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        startView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.GONE);
                        searchList.setVisibility(View.VISIBLE);
                        accountBeanList.clear();
                        accountBeanList.addAll(resBeanList);
                        listAdapter.notifyDataSetChanged();
                    }
                    return true;
                }
            }
            return false;
        });

        startView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        searchList.setVisibility(View.GONE);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back:
//                退出结束该页面
                finish();
                break;
        }
    }

    /*
     *
     * 从数据库进行搜索
     *
     * 模糊搜索
     *
     * @Return List<AccountBean>
     */
    private List<AccountBean> searchDataFromDB(String searchStr) {
        return DBManager.searchDataFromAccountTable(searchStr);
    }


}
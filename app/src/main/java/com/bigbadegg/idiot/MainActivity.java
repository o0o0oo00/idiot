package com.bigbadegg.idiot;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目按照阿里巴巴java开发规范，整个项目的架构采用设计模式，
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String SAVEKAY = "savekey";
    private static final String IS_FIRST = "isfirst";
    private RecyclerView recyclerView;
    private EditText mScan;
    private TextView mAdd, mClear;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ListAdapter adapter;
    private Gson gson = new Gson();
    private List<WarpBean> mDatas = new ArrayList<>();
    private String jsonData;
    private boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("content", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        initView();
        getJson();
        initRecycler();
        setListener();
    }


    /**
     * 初始化view
     */
    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_notyet);
        mScan = (EditText) findViewById(R.id.et_content);
        mAdd = (TextView) findViewById(R.id.tv_add);
        mClear = (TextView) findViewById(R.id.tv_clear);
    }

    /**
     * 添加监听事件
     */
    private void setListener() {
        mAdd.setOnClickListener(this);
        mClear.setOnClickListener(this);
    }

    /**
     * 初始化列表
     */
    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new ListAdapter(new ArrayList<Bean>()));

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WarpBean warpBean = (WarpBean) adapter.getData().get(position);
                if (warpBean.t.getStatus() == 0) {
                    warpBean.t.setStatus(1);
                    mDatas.add(mDatas.size(), warpBean);
                    mDatas.remove(position);
                } else {
                    mDatas.add(1, warpBean);
                    mDatas.remove(position + 1);
                    warpBean.t.setStatus(0);
                }
                MainActivity.this.adapter.notifyDataSetChanged();
            }
        });


        adapter.setNewData(mDatas);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                addToBeDone();
                mScan.setText("");
                break;
            case R.id.tv_clear:
                mScan.setText("");
                break;
        }
    }

    /**
     * 添加待办事项
     */
    private void addToBeDone() {
        String content = mScan.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "内容为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Bean bean = new Bean();
        bean.setContent(content);
        bean.setStartDate(System.currentTimeMillis() + "");
        bean.setStatus(0);
        mDatas.add(1, new WarpBean(bean));
        adapter.notifyDataSetChanged();
    }


    /**
     * 获取列表
     */
    private void getJson() {
        isFirst = sharedPreferences.getBoolean(IS_FIRST, true);
        jsonData = sharedPreferences.getString(SAVEKAY, "");
        if (isFirst) {
            WarpBean warpBean = new WarpBean(true, "未完成");
            mDatas.add(warpBean);
        }


        if (!TextUtils.isEmpty(jsonData)) {
            List<WarpBean> warpBeen = gson.fromJson(jsonData, new TypeToken<List<WarpBean>>() {
            }.getType());
            mDatas.addAll(warpBeen);
        }

        if (isFirst) {
            WarpBean warpBean2 = new WarpBean(true, "已经完成");
            mDatas.add(warpBean2);
        }


    }

    @Override
    public void finish() {
        save();
        super.finish();
    }

    private void save() {
        editor.putBoolean(IS_FIRST, false);
        jsonData = gson.toJson(mDatas);
        editor.putString(SAVEKAY, jsonData);
        editor.commit();
    }
}

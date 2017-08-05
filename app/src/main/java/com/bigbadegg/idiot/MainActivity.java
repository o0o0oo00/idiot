package com.bigbadegg.idiot;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
    private static final String SAVEKAY2 = "savekey2";
    private RecyclerView mNotYet, mHas;
    private EditText mScan;
    private ImageView mAdd, mClear;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ListAdapter adapterHas, adapterNotYet;
    private Gson gson = new Gson();
    private List<Bean> listHas = new ArrayList<>();
    private List<Bean> listNotYet = new ArrayList<>();
    private String jsonNotYet, jsonHas;


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
        mNotYet = (RecyclerView) findViewById(R.id.rv_notyet);
        mHas = (RecyclerView) findViewById(R.id.rv_has);
        mScan = (EditText) findViewById(R.id.et_content);
        mAdd = (ImageView) findViewById(R.id.iv_add);
        mClear = (ImageView) findViewById(R.id.iv_clear);
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
        mNotYet.setLayoutManager(new LinearLayoutManager(this));
        mHas.setLayoutManager(new LinearLayoutManager(this));
        mNotYet.setAdapter(adapterNotYet = new ListAdapter(new ArrayList<Bean>()));
        mHas.setAdapter(adapterHas = new ListAdapter(new ArrayList<Bean>()));

        adapterNotYet.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bean bean = (Bean) adapter.getData().get(position);
                bean.setStatus(1);
                listHas.add(0, bean);
                adapterHas.notifyDataSetChanged();

                listNotYet.remove(bean);
                adapterNotYet.notifyDataSetChanged();
            }
        });

        adapterHas.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bean bean = (Bean) adapter.getData().get(position);
                bean.setStatus(0);
                listNotYet.add(0,bean);
                adapterNotYet.notifyDataSetChanged();

                listHas.remove(bean);
                adapterHas.notifyDataSetChanged();


            }
        });
        adapterHas.setNewData(listHas);
        adapterNotYet.setNewData(listNotYet);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                addToBeDone();
                mScan.setText("");
                break;
            case R.id.iv_clear:
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

        listNotYet.add(0, bean);
        adapterNotYet.notifyDataSetChanged();
    }


    /**
     * 获取列表
     */
    private void getJson() {

        jsonNotYet = sharedPreferences.getString(SAVEKAY, "");
        jsonHas = sharedPreferences.getString(SAVEKAY2, "");
        if (!TextUtils.isEmpty(jsonNotYet)) {
            listNotYet = gson.fromJson(jsonNotYet, new TypeToken<List<Bean>>() {
            }.getType());
        }
        if (!TextUtils.isEmpty(jsonHas)) {
            listHas = gson.fromJson(jsonHas, new TypeToken<List<Bean>>() {
            }.getType());
        }


    }

    @Override
    public void finish() {
        save();
        super.finish();
    }

    private void save() {

        jsonNotYet = gson.toJson(listNotYet);
        jsonHas = gson.toJson(listHas);
        Log.e("asd", jsonHas + "-----" + jsonNotYet);
        editor.putString(SAVEKAY, jsonNotYet);
        editor.putString(SAVEKAY2, jsonHas);
        editor.commit();
    }
}

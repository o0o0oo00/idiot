package com.bigbadegg.idiot;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by hasee on 2017/8/4.
 */

public class ListAdapter extends BaseQuickAdapter<Bean, BaseViewHolder> {


    public ListAdapter(@Nullable List<Bean> data) {
        super(R.layout.item_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bean item) {

        helper.setText(R.id.tv_content, item.getContent());
        helper.setText(R.id.tv_day, DateFormat.format("yyyy-MM-dd", item.getStartDate()));
        if (item.getStatus() == 0) {//1,完成，0,未完成
            helper.setImageResource(R.id.iv_status, R.drawable.edit);

        } else {
            helper.setImageResource(R.id.iv_status, R.drawable.mark);
        }
        helper.addOnClickListener(R.id.iv_remove);
        helper.addOnClickListener(R.id.iv_status);
    }
}

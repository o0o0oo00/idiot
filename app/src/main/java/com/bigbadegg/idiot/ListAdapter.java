package com.bigbadegg.idiot;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;

import java.util.List;

/**
 * Created by hasee on 2017/8/4.
 */
public class ListAdapter extends BaseSectionQuickAdapter<WarpBean, BaseViewHolder> {


    public ListAdapter(@Nullable List data) {
        super(R.layout.item_list, R.layout.item_header, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, WarpBean warpBean) {
        if (warpBean.isHeader) {
            helper.setText(R.id.tv_item_header, warpBean.header);

        } else {

        }
    }

    @Override
    protected void convert(BaseViewHolder helper, WarpBean warpBean) {
        Bean item = warpBean.t;
        if (item == null) {
            return;
        }
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

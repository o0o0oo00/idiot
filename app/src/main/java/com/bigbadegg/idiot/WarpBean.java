package com.bigbadegg.idiot;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by hasee on 2017/10/31.
 */

public class WarpBean extends SectionEntity<Bean> {
    public WarpBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public WarpBean(Bean bean) {
        super(bean);
    }
}

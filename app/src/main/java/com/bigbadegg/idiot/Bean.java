package com.bigbadegg.idiot;

import java.io.Serializable;

/**
 * Created by hasee on 2017/8/4.
 */

public class Bean implements Serializable {
    private String content;//内容
    private String startDate;//创建日期
    private String endDate;//结束日期
    private int status;//状态是否完成1,完成，0,未完成

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

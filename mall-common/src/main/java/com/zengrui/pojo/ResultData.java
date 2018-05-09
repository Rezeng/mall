package com.zengrui.pojo;



import java.util.List;

/**
 * Created by Zeng Rui on 2018/2/24.
 */
public class ResultData {
    private Integer totalNum;
    private List<?> itemList;

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public List<?> getItemList() {
        return itemList;
    }

    public void setItemList(List<?> itemList) {
        this.itemList = itemList;
    }
}

package com.zengrui.pojo;

import java.io.Serializable;
import java.util.List;

/**dataTables 的数据格式
 * Created by Zeng Rui on 2018/3/11.
 */
public class DataTbResult implements Serializable {
    private Boolean success;
    //请求次数计数器，每次发送给服务器后原封返回，因为请求是异步的，为了确保每次请求都能对应到服务器返回到的数据
    private int draw;

    private int recordsTotal;

    private int recordsFiltered;

    private String error;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    private List<?> data;



}

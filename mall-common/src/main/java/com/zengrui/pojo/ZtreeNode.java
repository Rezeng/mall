package com.zengrui.pojo;

import java.io.Serializable;

/**
 * 返回给zTree组件的Json Obj
 * pid parentId
 * name 节点内容
 * sortOrder 排序码
 * icon 图标UML
 * isParent 是否是父节点
 * open  展开/折叠 状态
 * remark 备注
 * status 状态码（错误时）
 * Created by Zeng Rui on 2018/3/5.
 */
public class ZtreeNode implements Serializable {
    //zTree
    private int id;
    private int pid;
    private String name;
    private int sortOrder;
    private int num;
    private String icon;
    private boolean isParent;
    private boolean open;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(boolean parent) {
        isParent = parent;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    //else
    private String remark;
    private int status;


}

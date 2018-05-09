package com.zengrui.service;

import com.zengrui.manager.jsonObj.front.ProductDet;
import com.zengrui.manager.jsonObj.front.ProductHome;
import com.zengrui.manager.model.Content;
import com.zengrui.pojo.AllGoodsResult;
import com.zengrui.pojo.DataTbResult;
import com.zengrui.service.content.ContentService;

/**
 * Created by Zeng Rui on 2018/4/5.
 */
public class ContentServiceImp implements ContentService{
    @Override
    public DataTbResult getContentListByCid(Long cid) {
        return null;
    }

    @Override
    public Content getContentById(Long id) {
        return null;
    }

    @Override
    public ProductHome getProductHome() {
        return null;
    }

    @Override
    public ProductDet getProductDet(Long id) {
        return null;
    }

    @Override
    public AllGoodsResult getAllProduct(int page, int size, String sort, int priceGt, int priceLte) {
        return null;
    }
}

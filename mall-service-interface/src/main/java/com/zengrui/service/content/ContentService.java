package com.zengrui.service.content;


import com.zengrui.manager.model.Content;
import com.zengrui.pojo.AllGoodsResult;
import com.zengrui.manager.jsonObj.front.ProductHome;
import com.zengrui.manager.jsonObj.front.ProductDet;
import com.zengrui.pojo.DataTbResult;


public interface ContentService {

    DataTbResult getContentListByCid(Long cid);

    Content getContentById(Long id);

    ProductHome getProductHome();

    ProductDet getProductDet(Long id);

    AllGoodsResult getAllProduct(int page, int size, String sort, int priceGt, int priceLte);
}

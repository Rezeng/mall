package com.zengrui.front.controller;

import com.zengrui.manager.jsonObj.front.Product;
import com.zengrui.manager.jsonObj.front.ProductHome;
import com.zengrui.pojo.Result;
import com.zengrui.service.content.ContentService;
import com.zengrui.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Zeng Rui on 2018/4/5.
 */
@RestController
public class ProductController {
    @Autowired
    private ContentService contentService;

    /**
     * 获取主页的商品信息
     * @return
     */
    @RequestMapping(value = "/product/home", method = RequestMethod.GET)
    public Result getHomePageData() {

        ProductHome productHome;
        if ((productHome = contentService.getProductHome()) != null) {
            ResultUtils<ProductHome> utils = new ResultUtils<>(true);
            utils.setData(productHome);
            return utils.getResult();
        } else {
            return new ResultUtils(false).getResult();
        }


    }






}

package com.zengrui.manager.controller;

import com.zengrui.manager.service.interfaces.ItemCateService;
import com.zengrui.pojo.ZtreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Zeng Rui on 2018/3/11.
 */
@RestController
public class ItemCatController {

    @Autowired
    private ItemCateService itemCateService;

    @RequestMapping(value = "/item/cat", method = RequestMethod.GET)
    public List<ZtreeNode> getItemCatTree(@RequestParam(name = "id", defaultValue = "0") int parentId) {

        return itemCateService.getItemCategory(parentId);

    }




}

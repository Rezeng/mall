package com.zengrui.manager.service.interfaces;

import com.zengrui.manager.model.ItemCat;
import com.zengrui.pojo.ZtreeNode;

import java.util.List;

/**
 * Created by Zeng Rui on 2018/3/5.
 */
public interface ItemCateService {
    List<ZtreeNode> getItemCategory(long parentId);
    ItemCat getItemCategorybyId(long catId);
}

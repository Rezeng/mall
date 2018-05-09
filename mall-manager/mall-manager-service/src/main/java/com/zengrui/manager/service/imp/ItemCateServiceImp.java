package com.zengrui.manager.service.imp;

import com.zengrui.manager.dao.ItemCatMapper;
import com.zengrui.manager.model.ItemCat;
import com.zengrui.manager.model.ItemCatExample;
import com.zengrui.manager.service.interfaces.ItemCateService;
import com.zengrui.pojo.ZtreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zeng Rui on 2018/3/5.
 */
@Service
public class ItemCateServiceImp implements ItemCateService {




    @Autowired
    ItemCatMapper itemCatMapper;




    @Override
    public List<ZtreeNode> getItemCategory(long parentId) {
        ItemCatExample example = new ItemCatExample();
        //获取设置查询条件的对象
        ItemCatExample.Criteria criteria  =example.createCriteria();
        //按照sortOrder字段排序
        example.setOrderByClause("sort_order");
        //设置查询条件 where parentId =xx
        criteria.andParentIdEqualTo(parentId);
        List<ItemCat> list  = itemCatMapper.selectByExample(example);
        List<ZtreeNode> ztreeNodes= new ArrayList<ZtreeNode>();
        // ItemCat -> ZtreeNode
        for (ItemCat cat:list){
            ZtreeNode node  =new ZtreeNode();
            //数据库里是long,这里转为转int9
            node.setId(Math.toIntExact(cat.getId()));
            node.setName(cat.getName());
            node.setIcon(null);
            node.setStatus(cat.getStatus());
            node.setIsParent(cat.getIsParent());
            node.setPid(Math.toIntExact(cat.getParentId()));
            node.setRemark("123456");
            node.setOpen(!cat.getIsParent());
            node.setSortOrder(cat.getSortOrder());
            ztreeNodes.add(node);
        }

        return ztreeNodes;
    }

    /**
     * 根据分类的id获取分类实体
     * @param catId
     * @return catItem
     */

    @Override
    public ItemCat getItemCategorybyId(long catId) {
        ItemCat itemCat;
        try {
            itemCat = itemCatMapper.selectByPrimaryKey(catId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return itemCat;
    }
}

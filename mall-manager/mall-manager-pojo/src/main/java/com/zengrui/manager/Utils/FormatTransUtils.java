package com.zengrui.manager.Utils;

import com.zengrui.manager.jsonObj.ItemObj;
import com.zengrui.manager.model.Item;

/**pojo对象转换工具类
 * Created by Zeng Rui on 2018/3/17.
 */
public class FormatTransUtils {


    /**
     * itemObj -> item
     * @param obj
     * @return
     */
    public static Item itemAndItemObj(ItemObj obj){

        Item tbItem =new Item();

        tbItem.setTitle(obj.getTitle());
        tbItem.setPrice(obj.getPrice());
        tbItem.setCid(obj.getCid());
        tbItem.setImage(obj.getImage());
        tbItem.setSellPoint(obj.getSellPoint());
        tbItem.setNum(obj.getNum());

        return tbItem;
    }

    /**
     * item -> itemObj
     * @param item
     * @return
     */
    public static ItemObj itemAndItemObj(Item item){


        ItemObj obj =new ItemObj();

        obj.setTitle(item.getTitle());
        obj.setPrice(item.getPrice());
        obj.setCid(item.getCid());
        obj.setImage(item.getImage());
        obj.setSellPoint(item.getSellPoint());
        obj.setNum(item.getNum());

        return obj;
    }




}

package com.zengrui.manager.controller;

import com.zengrui.manager.Utils.FormatTransUtils;
import com.zengrui.manager.jsonObj.ItemObj;
import com.zengrui.manager.model.Item;
import com.zengrui.manager.model.ItemCat;
import com.zengrui.manager.service.interfaces.ItemCateService;
import com.zengrui.manager.service.interfaces.ItemService;
import com.zengrui.pojo.*;
import com.zengrui.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Zeng Rui on 2018/2/25.
 */
@RestController
public class ItemController {

    @Autowired
    private ItemCateService itemCateService;

    @Autowired
    private ItemService itemService;


    /**
     * 获取商品列表
     *
     * @param draw     绘制次数
     * @param start    当前页数据起始条数
     * @param length   当前页长度
     * @param cid      分类 id
     * @param search   搜索关键字
     * @param orderCol 排序的列名
     * @param orderDir 升序或者降序
     * @return
     */

    @RequestMapping(value = "/item/list", method = RequestMethod.GET)
    public DataTbResult getItemList(int draw, int start, int length, int cid, @RequestParam("search[value]") String search,
                                    @RequestParam("order[0][column]") int orderCol, @RequestParam("order[0][dir]") String orderDir) {
        //item的列名顺序
        String[] cols = {"checkbox", "id", "image", "title", "sell_point", "price", "created", "updated", "status"};
        //获取到表格排序的列名字
        String orderColumn = cols[orderCol];
        //默认按照 创建日期的降序排序
        if (orderColumn.isEmpty()) {
            orderColumn = "created";
        }
        if (orderDir.isEmpty()) {
            orderDir = "DESC";
        }
        //获取商品的列表
        DataTbResult result = itemService.getItemList(start, length, cid, search, orderColumn, orderDir);
        //生成返回json数据
        result.setSuccess(true);
        result.setDraw(draw);

        return result;
    }

    /**
     * @param draw      绘制次数
     * @param start     起始数据条数
     * @param length    单页数据条数
     * @param cid       分类id
     * @param searchKey 搜索关键字
     * @param minDate   起始日期
     * @param maxDate   截止日期
     * @param search    dataTables中的搜素的关键字
     * @param orderCol  排序列名
     * @param orderDir  排序规则
     * @return dataTable 组件的返回值
     */
    @RequestMapping("item/search")
    public DataTbResult getItemListForSearch(int draw, int start, int length, int cid, String searchKey, String minDate, String maxDate,
                                             @RequestParam("search[value]") String search, @RequestParam("order[0][column]") int orderCol,
                                             @RequestParam("order[0][dir]") String orderDir) {
        //item的列名顺序
        String[] cols = {"checkbox", "id", "image", "title", "sell_point", "price", "created", "updated", "status"};
        //获取到表格排序的列名字
        String orderColumn = cols[orderCol];
        //默认按照 创建日期的降序排序
        if (orderColumn.isEmpty()) {
            orderColumn = "created";
        }
        if (orderDir.isEmpty()) {
            orderDir = "DESC";
        }
        if (searchKey.isEmpty() && (!search.isEmpty())) {
            searchKey = search;
        }
        DataTbResult result = itemService.searchItem(start, length, cid, searchKey, minDate, maxDate, orderColumn, orderDir);
        //生成返回json数据
        result.setSuccess(true);
        result.setDraw(draw);

        return result;
    }

    /**
     * 根据id获取商品信息
     *
     * @param itemId
     * @return 商品信息
     */
    @RequestMapping("/item/get/{itemId}")
    public Result getItemById(@PathVariable long itemId) {

        //tb_item表获取商品
        Item item = itemService.getItem(itemId);
        ItemCat cat;
        //生成返回信息
        if (item != null) {
            ItemObj obj = FormatTransUtils.itemAndItemObj(item);
            if ((cat = itemCateService.getItemCategorybyId(item.getCid())) != null) {
                obj.setCname(cat.getName());
            }
            obj.setTitle(item.getTitle());
            //获取商品详细信息
            try {
                obj.setDetail(itemService.getDesc(itemId).getItemDesc());
            } catch (NullPointerException e) {
                e.printStackTrace();
                obj.setDetail(null);
            }
            ResultUtils<ItemObj> utils = new ResultUtils<>(true);
            utils.setData(obj);
            return utils.getResult();
        } else {
            ResultUtils<ItemObj> utils = new ResultUtils<>(false);
            utils.setErrorMsg("商品不存在");
            return utils.getResult();

        }


    }


    /**
     * 获取商品总数
     *
     * @return
     */

    @RequestMapping(value = "/item/count", method = RequestMethod.GET)
    public DataTbResult getCount() {

        long count = itemService.getItemCount();
        DataTbResult result = new DataTbResult();
        if (count != -1) {
            result.setRecordsTotal(Math.toIntExact(count));
        } else {
            result.setSuccess(false);
            result.setRecordsTotal(-1);
        }
        return result;

    }


    /**
     * 添加商品
     *
     * @param item
     * @return
     */
    @RequestMapping(value = "/item/add", method = RequestMethod.POST)
    public Result<Object> addItem(ItemObj item) {

        //保存商品
        if (itemService.saveItem(item)) {
            //生成返回数据
            ResultUtils<Object> utils = new ResultUtils<>(true);
            utils.setData(null);
            return utils.getResult();
        } else {
            ResultUtils<Object> utils = new ResultUtils<>(false);
            utils.setErrorMsg("保存失败");
            return utils.getResult();
        }


    }


    /**
     * 更新商品
     *
     * @param itemId
     * @param obj
     * @return
     */
    @RequestMapping("/item/update/{itemId}")
    public Result updateItem(@PathVariable Long itemId, ItemObj obj) {
        //对象格式转换
        Item item  = FormatTransUtils.itemAndItemObj(obj);
        //更新
        item = itemService.updateItem(itemId, item, obj.getDetail());
        if (item != null) {
            ResultUtils<Item> utils = new ResultUtils<>(true);
            utils.setData(item);
            return utils.getResult();
        } else {
            ResultUtils utils = new ResultUtils(false);
            utils.setErrorMsg("更新失败");
            return utils.getResult();
        }
    }

    @RequestMapping("/item/delete/{itemId}")
    public Result deleteItem(@PathVariable Long itemId){

        if(itemService.deleteItem(itemId))
        {
            ResultUtils utils = new ResultUtils(true);
            return utils.getResult();
        }
        else{
            ResultUtils utils = new ResultUtils(false);
            utils.setErrorMsg("删除失败");
            return utils.getResult();
        }

    }

    /**
     *上架商品
     * @param itemId
     * @return
     */

    @RequestMapping("/item/up/{itemId}")
    public Result<Item> upItem(@PathVariable Long itemId ){
        Item item = itemService.inverseState(itemId,1);
        if(item!=null){
            ResultUtils<Item> utils = new ResultUtils<>(true);
            utils.setData(item);
            return utils.getResult();

        }
        else
        {
            ResultUtils<Item> utils = new ResultUtils<>(false);
            utils.setErrorMsg("上架失败");
            return utils.getResult();
        }
    }


    /**
     * 下架商品
     * @param itemId
     * @return
     */

    @RequestMapping("/item/down/{itemId}")
    public Result<Item> downItem(@PathVariable Long itemId ){
        Item item = itemService.inverseState(itemId,0);
        if(item!=null){
            ResultUtils<Item> utils = new ResultUtils<>(true);
            utils.setData(item);
            return utils.getResult();


        }
        else
        {
            ResultUtils<Item> utils = new ResultUtils<>(false);
            utils.setErrorMsg("下架失败");
            return utils.getResult();
        }
    }



}

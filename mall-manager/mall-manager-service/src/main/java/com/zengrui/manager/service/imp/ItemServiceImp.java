package com.zengrui.manager.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zengrui.manager.dao.ItemDescMapper;
import com.zengrui.manager.dao.ItemMapper;
import com.zengrui.manager.jsonObj.ItemObj;
import com.zengrui.manager.model.Item;
import com.zengrui.manager.model.ItemDesc;
import com.zengrui.manager.model.ItemExample;
import com.zengrui.manager.service.interfaces.ItemService;
import com.zengrui.pojo.DataTbResult;

import com.zengrui.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

/**
 * Created by Zeng Rui on 2018/2/24.
 */
@Service("itemService")
public class ItemServiceImp implements ItemService {


    private ItemDescMapper itemDescMapper;
    private ItemMapper itemMapper;
    @Value("${FTP_URL}")
    String FTP_URL;

    @Autowired
    public void setDescMapper(ItemDescMapper itemDescMapper) {
        this.itemDescMapper = itemDescMapper;
    }

    @Autowired
    public void setItemMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    /**
     * 获取所有商品列表
     *
     * @return 商品列表
     */
    @Override
    public DataTbResult getItemList(int start, int length, int cid, String keyword, String orderCol, String orderDir) {


        //分页
        PageHelper.startPage((start / length) + 1, length);
        List<Item> list;
        //搜索关键字为空
        if (keyword.isEmpty()) {
            //设置orderBy 条件
            ItemExample example = new ItemExample();
            example.setOrderByClause(orderCol + " " + orderDir);
            //获取所有商品
            list = itemMapper.selectByExample(example);
        } else {
            list = itemMapper.selectByKeyword(cid, keyword, orderCol, orderDir);

        }
        //设置返回信息
        DataTbResult result = new DataTbResult();
        result.setData(list);
        result.setRecordsTotal(Math.toIntExact(this.getItemCount()));
        PageInfo<Item> pageInfo = new PageInfo<>(list);
        result.setRecordsFiltered((int) pageInfo.getTotal());
        return result;
    }

    @Override
    public DataTbResult searchItem(int start, int length, long cid, String keyword, String minDate, String maxDate, String orderCol, String orderDir) {


        //分页
        PageHelper.startPage((start / length) + 1, length);
        List<Item> list;
        //搜索
        list = itemMapper.selectByKeywordAndDate((int) cid, "%" + keyword + "%", maxDate, minDate, orderCol, orderDir);
        //设置返回信息
        DataTbResult result = new DataTbResult();
        result.setData(list);
        result.setRecordsTotal(Math.toIntExact(this.getItemCount()));
        PageInfo<Item> pageInfo = new PageInfo<>(list);
        result.setRecordsFiltered((int) pageInfo.getTotal());
        return result;
    }

    @Transactional
    @Override
    public boolean saveItem(ItemObj item1) {
        Date date = new Date();
        //该item为数据库的item
        Item dbItem = new Item();
        dbItem.setCid(item1.getCid());
        //生成商品ID
        long id = IdUtils.getItemId();
        //防止id重复
        while (itemMapper.selectByPrimaryKey(id) != null) {
            id = IdUtils.getItemId();
        }
        dbItem.setId(id);
        //没有上传图片显示默认图片
        if (item1.getImage().isEmpty()) {
            dbItem.setImage(FTP_URL + "/1/default.jpg");
        } else
            dbItem.setImage(item1.getImage());
        dbItem.setNum(item1.getNum());
        dbItem.setStatus((byte) 1);
        dbItem.setTitle(item1.getTitle());
        dbItem.setPrice(item1.getPrice());
        //添加时间
        dbItem.setCreated(date);
        //修改时间
        dbItem.setUpdated(date);
        dbItem.setSellPoint(item1.getSellPoint());
        //问题字段
        dbItem.setBarcode(null);

        //pojo of tb_item_describe
        ItemDesc desc = new ItemDesc();
        desc.setCreated(date);
        desc.setUpdated(date);
        desc.setItemId(id);
        //描述字段
        desc.setItemDesc(item1.getDetail());

        //insert into database
        try {
            itemMapper.insert(dbItem);
            itemDescMapper.insert(desc);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    /**
     * 获取所有商品的数量
     *
     * @return count
     */
    @Override
    public long getItemCount() {
        long count = 0;
        try {
            count = itemMapper.countByExample(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        return count;

    }

    @Override
    public Item getItem(long id) {
        Item item = null;
        try {
            item = itemMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            return item;
        }
    }

    /**
     * 获取商品的描述信息
     *
     * @param itemId
     * @return ItemDesc
     */
    @Override
    public ItemDesc getDesc(long itemId) {

        try {
            ItemDesc desc = itemDescMapper.selectByPrimaryKey(itemId);
            return desc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Item updateItem(Long id, Item item, String details) {
        Item oldItem = itemMapper.selectByPrimaryKey(id);
        ItemDesc oldDesc = itemDescMapper.selectByPrimaryKey(id);
        ItemDesc desc = new ItemDesc();


        if (item.getImage().isEmpty()) {
            item.setImage(oldItem.getImage());
        }
        item.setId(id);
        item.setStatus(oldItem.getStatus());
        item.setCreated(oldItem.getCreated());
        item.setUpdated(new Date());

        desc.setItemId(id);
        desc.setItemDesc(details);
        desc.setUpdated(new Date());
        desc.setCreated(oldDesc.getCreated());

        try {
            //提交事务
            if (commitUpdate(item, desc))
                return item;
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    @Override
    public boolean deleteItem(Long itemId) {

        try {
            if (itemMapper.deleteByPrimaryKey(itemId) == 1) {
                return true;
            } else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改状态
     * @param itemId
     * @return
     */
    @Override
    public Item inverseState(Long itemId,int state) {
        Item item = itemMapper.selectByPrimaryKey(itemId);
        item.setUpdated(new Date());
        if (state == 1)
            item.setStatus((byte) 1);
        if(state ==0)
            item.setStatus((byte) 0);
        try {
            if(itemMapper.updateByPrimaryKey(item)!=1){
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return item;

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private boolean commitUpdate(Item item, ItemDesc desc) throws Exception {

        if (itemMapper.updateByPrimaryKey(item) == 1 && itemDescMapper.updateByPrimaryKey(desc) == 1) {
            return true;
        } else
            return false;


    }

}

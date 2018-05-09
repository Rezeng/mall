package com.zengrui.manager.service.interfaces;
import com.zengrui.manager.jsonObj.ItemObj;
import com.zengrui.manager.model.Item;
import com.zengrui.manager.model.ItemDesc;
import com.zengrui.pojo.DataTbResult;

/**
 * Created by Zeng Rui on 2018/2/24.
 */
public interface ItemService {

     DataTbResult getItemList(int start, int length, int cid, String keyword, String orderCol, String orderDir);
     DataTbResult searchItem(int start,int length,long cid,String keyWord,String minDate,String maxDate,String orderCol,String orderDir);
     boolean saveItem(ItemObj item1);
     long getItemCount();
     Item getItem(long id);
     ItemDesc getDesc(long itemId);
     Item updateItem(Long id,Item obj,String details);
     boolean deleteItem(Long itemId);
     Item inverseState(Long itemId,int state);




}

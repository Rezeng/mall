package com.zengrui.manager.dao;

import com.zengrui.manager.model.Item;
import com.zengrui.manager.model.ItemExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ItemMapper {
    long countByExample(ItemExample example);

    int deleteByExample(ItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Item record);

    int insertSelective(Item record);

    List<Item> selectByExample(ItemExample example);

    List<Item> selectByKeyword(@Param("cid") int cid, @Param("keyWord") String keyWord,
                               @Param("orderCol") String orderCol, @Param("orderDir") String orderDir);

    List<Item> selectByKeywordAndDate(@Param("cid") int cid, @Param("keyWord") String keyWord,
                                      @Param("maxDate") String maxDate, @Param("minDate") String minDate, @Param("orderCol") String orderCol, @Param("orderDir") String orderDir);


    Item selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Item record, @Param("example") ItemExample example);

    int updateByExample(@Param("record") Item record, @Param("example") ItemExample example);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);
}
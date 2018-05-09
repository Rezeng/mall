package com.zengrui.manager.dao;

import com.zengrui.manager.model.ItemDesc;
import com.zengrui.manager.model.ItemDescExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemDescMapper {
    long countByExample(ItemDescExample example);

    int deleteByExample(ItemDescExample example);

    int deleteByPrimaryKey(Long itemId);

    /**
     *
     * @param record
     * @return 主键
     */
    int insert(ItemDesc record);

    int insertSelective(ItemDesc record);

    List<ItemDesc> selectByExampleWithBLOBs(ItemDescExample example);

    List<ItemDesc> selectByExample(ItemDescExample example);

    ItemDesc selectByPrimaryKey(Long itemId);

    int updateByExampleSelective(@Param("record") ItemDesc record, @Param("example") ItemDescExample example);

    int updateByExampleWithBLOBs(@Param("record") ItemDesc record, @Param("example") ItemDescExample example);

    int updateByExample(@Param("record") ItemDesc record, @Param("example") ItemDescExample example);

    int updateByPrimaryKeySelective(ItemDesc record);

    int updateByPrimaryKeyWithBLOBs(ItemDesc record);

    /**
     *
     * @param record
     * @return 更新的条数
     */
    int updateByPrimaryKey(ItemDesc record);
}
package com.itwillbs.db.items.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.itwillbs.db.items.dto.ItemDTO;

@Mapper
public interface ItemMapper {

	List<ItemDTO> findAllItems();

}

package com.itwillbs.db.items.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itwillbs.db.items.entity.ItemImg;

@Repository
public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
	
	
}

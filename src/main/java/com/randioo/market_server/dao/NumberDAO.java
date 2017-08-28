package com.randioo.market_server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.randioo.market_server.entity.bo.NumbersBO;
import com.randioo.randioo_server_base.annotation.MyBatisGameDaoAnnotation;
import com.randioo.randioo_server_base.db.BaseDao;


@MyBatisGameDaoAnnotation
public interface NumberDAO extends BaseDao<NumbersBO> {
	// 根据用户 ，类型获取数量 (卖出的时候和成交的时候)
	NumbersBO selectNumber(@Param("num_id") int num_id, @Param("num_type") String num_type);

	// 根据类型用户，修改商品数量
	void updateCount(@Param("num_id") int num_id, @Param("num_type") String num_type);

	List<NumbersBO> getNum();
}

package com.randioo.market_server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.randioo.market_server.entity.po.DaykBO;
import com.randioo.randioo_server_base.annotation.MyBatisGameDaoAnnotation;
import com.randioo.randioo_server_base.db.BaseDao;

@MyBatisGameDaoAnnotation
public interface DaykDAO extends BaseDao<DaykBO> {
	// 根据类型获得所有日K数据
	List<DaykBO> selectAll(@Param("day_type") String day_type);

	Integer selectId();

	// 获取上一天的值，取收盘价格
	DaykBO getLastDayk(@Param("day_type") String day_type);
	
	// 获取上一天的值，取收盘价格
	DaykBO getLastTwoDayk(@Param("day_type") String day_type);
	
	List<DaykBO> getTypeDayk();
}

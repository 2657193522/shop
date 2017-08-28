package com.randioo.market_server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.randioo.market_server.entity.po.MinkBO;
import com.randioo.randioo_server_base.annotation.MyBatisGameDaoAnnotation;
import com.randioo.randioo_server_base.db.BaseDao;

@MyBatisGameDaoAnnotation
public interface ThirtyMinkDAO extends BaseDao<MinkBO>{
	
	Integer selectId();

	//获取当天分K图
	List<MinkBO> getNowDay(@Param("min_type") String min_type);

	//获取上一个分K图
	MinkBO getLastMink(@Param("min_type") String min_type);

	MinkBO getEndMink(@Param("min_type") String min_type);
}

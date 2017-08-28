package com.randioo.market_server.dao;

import org.apache.ibatis.annotations.Param;

import com.randioo.market_server.entity.bo_remote.ControlBO;
import com.randioo.randioo_server_base.annotation.MyBatisGameDaoAnnotation;
import com.randioo.randioo_server_base.db.BaseDao;

@MyBatisGameDaoAnnotation
public interface ControlDAO extends BaseDao<ControlBO>{

	Integer selectId(@Param("id") int id);
}

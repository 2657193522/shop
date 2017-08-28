package com.randioo.market_server.dao;

import com.randioo.market_server.entity.bo.BuyerBO;
import com.randioo.randioo_server_base.annotation.MyBatisGameDaoAnnotation;
import com.randioo.randioo_server_base.db.BaseDao;

@MyBatisGameDaoAnnotation
public interface BuyerDAO extends BaseDao<BuyerBO> {

	//数据库Id
	Integer selectId();
}

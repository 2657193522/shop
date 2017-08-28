package com.randioo.market_server.dao_remote;

import org.apache.ibatis.annotations.Param;

import com.randioo.market_server.entity.bo_remote.Currency;
import com.randioo.randioo_server_base.annotation.MyBatisGameDaoAnnotation;
import com.randioo.randioo_server_base.db.BaseDao;

@MyBatisGameDaoAnnotation
public interface CurrencyDAO extends BaseDao<Currency> {

	String getType(@Param("currency_id") int currency_id);// 类型

	String getTypeId(@Param("currency_id") int currency_id);// 交易代码

}

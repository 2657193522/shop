package com.randioo.market_server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.randioo.market_server.entity.bo.TradingBO;
import com.randioo.randioo_server_base.annotation.MyBatisGameDaoAnnotation;
import com.randioo.randioo_server_base.db.BaseDao;

@MyBatisGameDaoAnnotation
public interface TradingDAO extends BaseDao<TradingBO> {
	Integer selectId();

	// 根据用户查成交记录
	List<TradingBO> selectListByUser(@Param("trad_account") String trad_account);

	// 根据类型查找最后数据
	TradingBO getEndTrading(@Param("trad_type") String trad_type);

	// 查找当天成交信息
	List<TradingBO> selectListByTypeToday(@Param("trad_type") String trad_type);
	
	//查看当天所有成交记录
	List<TradingBO> getAllTrading(@Param("trad_type") String trad_type,@Param("trad_state") int trad_state);
	
	List<TradingBO> getManyTrading();
}

package com.randioo.market_server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.randioo_server_base.annotation.MyBatisGameDaoAnnotation;
import com.randioo.randioo_server_base.db.BaseDao;

@MyBatisGameDaoAnnotation
public interface SellerDAO extends BaseDao<SellerBO> {
	// 根据类型和状态获取卖的信息 （进入请求所有卖的信息的时候）
	List<SellerBO> ListByType(@Param("sell_type") String sell_type, @Param("sell_active") int sell_active);

	Integer selectId();

	// 通过信息查找卖的信息（成交的时候匹配该卖的信息并进行交易）
	List<SellerBO> selectSellerByAll(@Param("sell_id") int sell_id, @Param("sell_active") int sell_active);

	// 通过用户找到该用户的所有卖的信息
	List<SellerBO> getListByAccount(@Param("sell_account") String sell_account, @Param("sell_active") int active);

	void updateActive(@Param("sell_id") int sell_id, @Param("sell_active") int sell_active);

	SellerBO selectSeller(@Param("sell_id") int sell_id,@Param("sell_active") int sell_active);
	
	List<SellerBO> selectSellerByState(@Param("sell_active") int sell_active);
}

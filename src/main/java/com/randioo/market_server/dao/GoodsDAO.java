package com.randioo.market_server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.randioo.market_server.entity.bo.GoodsBO;
import com.randioo.randioo_server_base.annotation.MyBatisGameDaoAnnotation;
import com.randioo.randioo_server_base.db.BaseDao;

@MyBatisGameDaoAnnotation
public interface GoodsDAO extends BaseDao<GoodsBO> {
	// 通过类型，用户，状态查找商品详情 （上架商品后改变商品属性）
	List<GoodsBO> selectListByTypeUser(@Param("goods_type") String goods_type,
			@Param("goods_account") String goods_account, @Param("goods_active") int goods_active);

	// 卖的Id（商品成交时改变商品属性）
	List<GoodsBO> selectTypeByAll(@Param("goods_sell_id") int goods_sell_id, @Param("goods_active") int goods_active);

	// 根据交易Id查找所有已成交商品
	List<GoodsBO> selectListByTrad(@Param("goods_trad_id") int goods_trad_id);

	void updateGoodsActive(@Param("goods_sell_id") int goods_sell_id);

	// 上架的时候改变状态 0改为1 停止售卖改成正在售卖
	void updateGoodsSeller(@Param("goods_price") double goods_price, @Param("goods_sell_id") int goods_sell_id,
			@Param("goods_activey") int goods_activey, @Param("goods_time") String goods_time,
			@Param("goods_type") String goods_type, @Param("goods_account") String goods_account,
			@Param("goods_activen") int goods_activen,@Param("count") int count);

	// 购买的时候改变状态 1改为0
	void updateGoodsBuyer(@Param("goods_account") String goods_account, @Param("goods_activen") int goods_active,
			@Param("goods_trad_id") int goods_trad_id, @Param("goods_time") String goods_time,
			@Param("goods_sell_id") int goods_sell_id, @Param("goods_activey") int goods_active1,@Param("count") int count);

	// 撤销时候改变
	void updateGoodsRevoke(@Param("goods_activen") int goods_activen, @Param("goods_time") String goods_time,
			@Param("goods_sell_id") int goods_sell_id, @Param("goods_activey") int goods_activey,@Param("count") int count);
}

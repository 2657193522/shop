package com.randioo.market_server.module.seller.component.num;

import com.randioo.market_server.entity.bo.NumbersBO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;


public interface SellerLogicNumComponent extends ObserveBaseServiceInterface{
	// 根据帐号类型获取拥有数量
	NumbersBO getNumbers(String account, String type);
	
	//修改用户拥有商品数量(减少)
	void updateNumbers(SellerBO sellerBO,int sellRoleId);
	//根据帐号获得用户信息(撤销新增)
	Role getRoleByAccount(String account);
	//撤销用户商品数量
	void updateRevokeNumbers(SellerBO sellerBO,int count);
}

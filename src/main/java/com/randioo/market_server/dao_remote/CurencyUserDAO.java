package com.randioo.market_server.dao_remote;

import org.apache.ibatis.annotations.Param;

import com.randioo.market_server.entity.bo_remote.CurencyUser;
import com.randioo.randioo_server_base.annotation.MyBatisGameDaoAnnotation;
import com.randioo.randioo_server_base.db.BaseDao;

@MyBatisGameDaoAnnotation
public interface CurencyUserDAO extends BaseDao<CurencyUser> {
	//类型和用户Id
	void addUpdate(@Param("num") int num,@Param("member_id") int member_id,@Param("currency_id") int currency_id);
	void jianUpdate(@Param("num") int num,@Param("member_id") int member_id,@Param("currency_id") int currency_id);
}

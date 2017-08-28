package com.randioo.market_server.dao_remote;

import org.apache.ibatis.annotations.Param;

import com.randioo.market_server.entity.bo_remote.Member;
import com.randioo.randioo_server_base.annotation.MyBatisGameDaoAnnotation;
import com.randioo.randioo_server_base.db.BaseDao;

@MyBatisGameDaoAnnotation
public interface MemberDAO extends BaseDao<Member> {
	//用户ID
	void addUpdate(@Param("rmb") double rmb,@Param("member_id") int member_id);
	void jianUpdate(@Param("rmb") double rmb,@Param("member_id") int member_id);
}

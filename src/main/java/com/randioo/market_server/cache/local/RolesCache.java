package com.randioo.market_server.cache.local;

import java.util.HashMap;
import java.util.Map;

import com.randioo.market_server.entity.bo.Role;

public class RolesCache {
	private static Map<String,Role> roleMap=new HashMap<>();
	

	public static Role getRole(String account){
		return roleMap.get(account);
	}
	public static Map<String,Role>getRoleMap(){
		return roleMap;
	}

}

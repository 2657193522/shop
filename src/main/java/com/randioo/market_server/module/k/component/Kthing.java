package com.randioo.market_server.module.k.component;

import java.util.List;

import com.randioo.market_server.entity.po.DaykBO;
import com.randioo.market_server.entity.po.MinkBO;
import com.randioo.market_server.protocol.Entity.KData;

public interface Kthing {
	KData getMinkData(MinkBO mink);
	KData parseKData(DaykBO day, long time);
	List<DaykBO> getDinkListType(String type);
	KData getDaykData(DaykBO dayk);
	List<MinkBO> addMinkByToday(String type);
	
	KData getAllMinkData(MinkBO minks, int num);
	
	 List<String> addTypeList();
}

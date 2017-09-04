package com.randioo.market_server.module.seller.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.randioo.market_server.cache.local.SellerCache;
import com.randioo.market_server.cache.local.TypeCache;
import com.randioo.market_server.comparator.GoodsComparator;
import com.randioo.market_server.dao.SellerDAO;
import com.randioo.market_server.dao_remote.CurrencyDAO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.market_server.module.Constant;
import com.randioo.market_server.module.seller.component.num.SellerLogicNumComponent;
import com.randioo.market_server.protocol.Entity.GoodsTypeData;
import com.randioo.market_server.protocol.Seller.SCSellerMyMessage;
import com.randioo.market_server.protocol.Seller.SellerMyResponse;
import com.randioo.market_server.protocol.ServerMessage.SC;
import com.randioo.market_server.util.SessionUtils;
import com.randioo.randioo_server_base.service.ObserveBaseService;

@Service("sellerSelectService")
public class SellerSelectServiceImpl extends ObserveBaseService implements SellerSelectService {
	@Autowired
	private SellerDAO sellerDao;
	@Autowired
	private CurrencyDAO currencyDAO;
	@Autowired
	private SellerLogicNumComponent sellerLogicNumComponent;
	@Autowired
	private GoodsComparator goodsComparator;

	@Override
	public void selectMySeller(String account, IoSession session) {
		// Role roles = sellerLogicNumComponent.getRoleByAccount(account);
		SC scs = SC.newBuilder().setSellerMyResponse(SellerMyResponse.newBuilder()).build();
		SessionUtils.sc(session, scs);

		SCSellerMyMessage.Builder builders = SCSellerMyMessage.newBuilder();
		List<SellerBO> sellerList = getSellerByAccount(account);
		Collections.sort(sellerList, goodsComparator);
		if (sellerList != null) {
//			int c = 0;
			for (SellerBO seller : sellerList) {
//				if (c >= 10) {
//					break;
//				}
				builders.addGoodsTypeData(
						GoodsTypeData.newBuilder().setCount(seller.getSell_count() - seller.getSell_overCount())
								.setPrice(String.valueOf(seller.getSell_price())).setSellId(seller.getSell_id())
								.setType(getTypeId(seller.getSell_type())).setSellAccount(account));
//				c++;
			}
		}
		SC sc = SC.newBuilder().setSCSellerMyMessage(builders).build();
		SessionUtils.sc(session, sc);
	}

	@Override
	public List<SellerBO> getSellerByAccount(String account) {
		String accountActive = account + String.valueOf(Constant.YES);
		List<SellerBO> sellerList = SellerCache.getSellerListByAccount(accountActive);
		if (sellerList == null || sellerList.size() == 0) {
			sellerList = new ArrayList<>();
			List<SellerBO> list = sellerDao.getListByAccount(account, Constant.YES);
			if (list != null) {
				sellerList.addAll(list);
			}
			SellerCache.getSellerMapByAccount().put(accountActive, sellerList);
		}
		return sellerList;
	}

	@Override
	public void scMySellerRequesr(String account) {
		Role roles = sellerLogicNumComponent.getRoleByAccount(account);
		List<SellerBO> sellerList = getSellerByAccount(account);
		SCSellerMyMessage.Builder builders = SCSellerMyMessage.newBuilder();
		if (sellerList != null) {
			for (SellerBO seller : sellerList) {
				builders.addGoodsTypeData(
						GoodsTypeData.newBuilder().setCount(seller.getSell_count() - seller.getSell_overCount())
								.setPrice(String.valueOf(seller.getSell_price())).setSellId(seller.getSell_id())
								.setType(getTypeId(seller.getSell_type())).setSellAccount(account));
			}
		}
		SC sc = SC.newBuilder().setSCSellerMyMessage(builders).build();
		SessionUtils.sc(roles.getRoleId(), sc);
	}

	/**
	 * 找到类型代码
	 * 
	 * @param types
	 * @return
	 */
	public String getTypeId(String types) {
		String type = TypeCache.getType(types);
		if (type == null) {
			type = currencyDAO.getTypeId(Integer.parseInt(types));
			if (type != null) {
				TypeCache.getTypeMap().put(types, type);
			}
		}
		return type;
	}
}

package com.randioo.market_server.module.buyer.component.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.randioo.market_server.cache.file.CountConfigCache;
import com.randioo.market_server.dao.SellerDAO;
import com.randioo.market_server.entity.bo.NumbersBO;
import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.entity.bo.SellerBO;
import com.randioo.market_server.entity.file.CountConfig;
import com.randioo.market_server.module.Constant;
import com.randioo.market_server.module.seller.component.num.SellerLogicNumComponent;
import com.randioo.market_server.module.seller.service.SellerSelectService;
import com.randioo.market_server.util.DateUtils;
import com.randioo.randioo_server_base.service.ObserveBaseService;

@Service("buyerLevelComponent")
public class BuyerLevelComponentImpl extends ObserveBaseService implements BuyerLevelComponent {
	@Autowired
	private SellerLogicNumComponent sellerLogicNumComponent;
	@Autowired
	private SellerDAO sellerDAO;
	@Autowired
	private SellerSelectService selectService;

	@Override
	public int getLevel(String account, String type, int sellId, int count) {
		CountConfig countConfig = CountConfigCache.getConfig();
		Role role = sellerLogicNumComponent.getRoleByAccount(account);
		NumbersBO num = sellerLogicNumComponent.getNumbers(account, type);
		if (role.getVip_level() == 0) {
			if (num.getOperation_time() != null) {
				if (!num.getOperation_time().equals("0")) {
					long time = DateUtils.getLongTime(num.getOperation_time());
					if (!DateUtils.isThisWeek(time)) {
						num.setOperation_count(0);
					}
				}
			}
			if (num != null) {
				SellerBO sel = sellerDAO.selectSeller(sellId, Constant.YES);
				List<SellerBO> seller = selectService.getSellerByAccount(account);
				int a = 0;
				int c = 0;
				if (!role.getAccount().equals(sel.getSell_account())) {
					for (SellerBO s : seller) {
						a = s.getSell_count() - s.getSell_overCount();
						c = c + a;
					}
				}
				int b = num.getOperation_count() + count + c;
				if (Math.abs(b) > countConfig.count) {
					return 0;
				}
			}
		} else if (role.getVip_level() == 1) {
			if (num != null) {
				// 输入数量不正确
				if (count <= 0 || count > countConfig.count) {
					return 1;
				}
			}
		}
		return -1;
	}
}

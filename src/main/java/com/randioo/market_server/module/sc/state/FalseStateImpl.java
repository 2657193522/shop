package com.randioo.market_server.module.sc.state;

public class FalseStateImpl{
//	@Autowired
//	private Kthing kthing;

//	@Override
//	public void refushMessage(String type) {
//		SCRefushMink.Builder builders = SCRefushMink.newBuilder();
//		List<MinkBO> minkList = kthing.addMinkByToday(type);
//		System.out.println(minkList);
//		if (minkList != null && minkList.size() > 0) {
//			if (!StateCache.isPanduan()) {
//				MinkBO minkBO = minkList.get(minkList.size() - 1);
//				double percentage = DateUtils.getPercentage(minkBO.getMin_start_price(), minkBO.getMin_end_price());
//				
//				builders.addKData(KData.newBuilder().setDayTime(DateUtils.getTime(minkBO.getMin_time()))
//						.setEndPrice(String.valueOf(minkBO.getMin_end_price()))
//						.setMinPrice(String.valueOf(minkBO.getMin_min_price()))
//						.setPercentage(String.valueOf(percentage))
//						.setStartPrice(String.valueOf(minkBO.getMin_start_price()))
//						.setSumCount(minkBO.getMin_sum_count() * 2)
//						.setSumPrice(String.valueOf(DateUtils.changeSum(2, minkBO.getMin_sum_price())))
//						.setMaxPrice(String.valueOf(minkBO.getMin_max_price())));
//				SC sc = SC.newBuilder().setSCRefushMink(builders).build();
//				for (IoSession session : SessionCache.getAllSession()) {
//					SessionUtils.sc(session, sc);
//				}
//
//			}
//		}
//	}
	
	

}

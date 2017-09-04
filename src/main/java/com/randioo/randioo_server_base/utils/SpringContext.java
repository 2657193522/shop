package com.randioo.randioo_server_base.utils;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Spring 配置
 * @author xjd
 *
 */
public class SpringContext {
	private static FileSystemXmlApplicationContext ctx;

	/**
	 * 加载spring配置
	 */
	public static void initSpringCtx(String filePath) {
		ctx = new FileSystemXmlApplicationContext(filePath);
		ctx.registerShutdownHook();
	}

	/**
	 * 根据beanId获取bean
	 * 
	 * @param beanId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanId) {
		return (T) ctx.getBean(beanId);
	}
}

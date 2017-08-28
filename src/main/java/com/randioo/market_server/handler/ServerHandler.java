package com.randioo.market_server.handler;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.randioo.market_server.entity.bo.Role;
import com.randioo.market_server.protocol.ClientMessage.CS;
import com.randioo.market_server.protocol.Heart.HeartResponse;
import com.randioo.market_server.protocol.Heart.SCHeart;
import com.randioo.randioo_server_base.cache.RoleCache;
import com.randioo.randioo_server_base.handler.GameServerHandlerAdapter;
import com.randioo.randioo_server_base.log.HttpLogUtils;
import com.randioo.randioo_server_base.utils.TimeUtils;

@Component
public class ServerHandler extends GameServerHandlerAdapter {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	private String scHeartStr = SCHeart.class.getSimpleName();
	private String heartResponseStr = HeartResponse.class.getSimpleName();

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("roleId:" + session.getAttribute("roleId") + " sessionCreated");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.info("roleId:" + session.getAttribute("roleId") + " sessionOpened");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		logger.info("roleId:" + session.getAttribute("roleId") + " sessionClosed");
		Role role = (Role) RoleCache.getRoleBySession(session);

		try {
			if (role != null) {
				SessionCloseHandler.asynManipulate(role);
			}
		} catch (Exception e) {
			logger.error("sessionClosed error:", e);
		} finally {
			// session.close(true);
			// StackTraceElement[] elements =
			// Thread.currentThread().getStackTrace();
			// StringBuilder builder = new StringBuilder();
			// for (StackTraceElement e : elements) {
			// builder.append(e);
			// }
			// System.out.println(builder.toString());
		}

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {

	}

	@Override
	public void exceptionCaught(IoSession session, Throwable e) throws Exception {
		e.printStackTrace();
	}

	// @Override
	// public void messageReceived(IoSession session, Object messageObj) throws
	// Exception {
	//
	// InputStream input = (InputStream) messageObj;
	//
	// try {
	// CS message = CS.parseDelimitedFrom(input);
	// logger.info(message.toString());
	// actionDispatcher(message, session);
	// } catch (Exception e) {
	// logger.error("", e);
	// } finally {
	// if (input != null) {
	// input.close();
	// }
	// }
	//
	// }

	@Override
	public void messageReceived(IoSession session, Object messageObj) throws Exception {

		try {
			CS message = (CS) messageObj;
			logger.info(message.toString());
			actionDispatcher(message, session);
		} catch (Exception e) {
			logger.error("", e);
		}

	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		if (!message.toString().contains("scFightKeyFrame") && !message.toString().contains("PingResponse")) {
			Role role = (Role) RoleCache.getRoleBySession(session);
			String msg = HttpLogUtils.role(role, getMessage(message, session));
			logger.info(msg);
		}
		String messageStr = message.toString();
		if (messageStr.contains(scHeartStr) || messageStr.contains(heartResponseStr))
			return;

	}

	private String getMessage(Object message, IoSession session) {
		Integer roleId = (Integer) session.getAttribute("roleId");
		String roleAccount = null;
		String roleName = null;
		if (roleId != null) {
			Role role = (Role) RoleCache.getRoleById(roleId);
			if (role != null) {
				roleAccount = role.getAccount();
				roleName = role.getName();
			}
		}

		StringBuilder sb = new StringBuilder();
		sb.append(TimeUtils.getDetailTimeStr()).append(" [roleId:").append(roleId).append(",account:")
				.append(roleAccount).append(",name:").append(roleName).append("] ").append(message);
		String output = sb.toString();
		if (output.length() < 120) {
			output = output.replaceAll("\n", " ").replace("\t", " ").replace("  ", "");
		}

		return output;
	}

}

package com.randioo.market_server.handler;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.springframework.stereotype.Component;

@Component
public class HeartTimeOutHandler implements KeepAliveRequestTimeoutHandler {

	@Override
	public void keepAliveRequestTimedOut(KeepAliveFilter arg0, IoSession arg1) throws Exception {
		// System.out.println("time out");
		// System.exit(0);
		// for(int i = 0;i<1000;i++)
		// System.out.println(TimeUtils.getDetailTimeStr() + "
		// keepAliveRequestTimedOut");
		// Role role = (Role) RoleCache.getRoleBySession(arg1);

		// arg1.close(true);
	}
	//
	// /**
	// * Reads variable length 32bit int from buffer
	// *
	// * @return decoded int if buffers readerIndex has been forwarded else
	// * nonsense value
	// */
	// private static int readRawVarint32(IoBuffer buffer) {
	// if (!buffer.hasRemaining()) {
	// return 0;
	// }
	// buffer.mark();
	// byte tmp = buffer.get();
	// if (tmp >= 0) {
	// return tmp;
	// } else {
	// int result = tmp & 127;
	// if (!buffer.hasRemaining()) {
	// buffer.reset();
	// return 0;
	// }
	// if ((tmp = buffer.get()) >= 0) {
	// result |= tmp << 7;
	// } else {
	// result |= (tmp & 127) << 7;
	// if (!buffer.hasRemaining()) {
	// buffer.reset();
	// return 0;
	// }
	// if ((tmp = buffer.get()) >= 0) {
	// result |= tmp << 14;
	// } else {
	// result |= (tmp & 127) << 14;
	// if (!buffer.hasRemaining()) {
	// buffer.reset();
	// return 0;
	// }
	// if ((tmp = buffer.get()) >= 0) {
	// result |= tmp << 21;
	// } else {
	// result |= (tmp & 127) << 21;
	// if (!buffer.hasRemaining()) {
	// buffer.reset();
	// return 0;
	// }
	// result |= (tmp = buffer.get()) << 28;
	// if (tmp < 0) {
	// throw new CorruptedFrameException("malformed varint.");
	// }
	// }
	// }
	// }
	// return result;
	// }
	// }

}

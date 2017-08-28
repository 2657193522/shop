package com.randioo.randioo_server_base.protocol.protobuf;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.MessageLite;
import com.randioo.randioo_server_base.error.UserFakeMsgException;

public class ProtoDecoder extends CumulativeProtocolDecoder {
	private final MessageLite prototype;
	private final ExtensionRegistryLite extensionRegistry;

	public ProtoDecoder(MessageLite messageLite, ExtensionRegistryLite extensionRegistry) {
		this.prototype = messageLite.getDefaultInstanceForType();
		this.extensionRegistry = extensionRegistry;
	}

	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {

		in.order(ByteOrder.LITTLE_ENDIAN);

		in.mark();
		int preIndex = in.markValue();
		int length = readRawVarint32(in);
		if (preIndex == in.position()) {
			return false;
		}
		if (length < 0) {
			throw new UserFakeMsgException("negative length: " + length);
		}

		if (in.remaining() < length) {
			in.reset();
			return false;
		}
		byte[] bodyBytes = new byte[length];
		in.get(bodyBytes); // 读取body部分
		if (extensionRegistry == null) {
			try{
				out.write(prototype.newBuilderForType().mergeFrom(bodyBytes, 0, length).build());
				
			}catch(Exception exceptio){
				System.out.println();				
			}
		} else {
			out.write(prototype.newBuilderForType().mergeFrom(bodyBytes, 0, length, extensionRegistry).build());
		}
		return true;

	}

	// int readRawVarint32(IoBuffer input) throws IOException,
	// UserFakeMsgException {
	// input.mark();
	// int firstByte = input.get();
	// if (firstByte == -1)
	// throw new UserFakeMsgException("truncatedMessage");
	//
	// int size = readRawVarint32(firstByte, input);
	// input.reset();
	// return size;
	// }
	/**
	 * Reads variable length 32bit int from buffer
	 *
	 * @return decoded int if buffers readerIndex has been forwarded else
	 *         nonsense value
	 * @throws UserFakeMsgException
	 */
	private static int readRawVarint32(IoBuffer buffer) throws UserFakeMsgException {
		if (!buffer.hasRemaining()) {
			return 0;
		}
		buffer.mark();
		byte tmp = buffer.get();
		if (tmp >= 0) {
			return tmp;
		} else {
			int result = tmp & 127;
			if (!buffer.hasRemaining()) {
				buffer.reset();
				return 0;
			}
			if ((tmp = buffer.get()) >= 0) {
				result |= tmp << 7;
			} else {
				result |= (tmp & 127) << 7;
				if (!buffer.hasRemaining()) {
					buffer.reset();
					return 0;
				}
				if ((tmp = buffer.get()) >= 0) {
					result |= tmp << 14;
				} else {
					result |= (tmp & 127) << 14;
					if (!buffer.hasRemaining()) {
						buffer.reset();
						return 0;
					}
					if ((tmp = buffer.get()) >= 0) {
						result |= tmp << 21;
					} else {
						result |= (tmp & 127) << 21;
						if (!buffer.hasRemaining()) {
							buffer.reset();
							return 0;
						}
						result |= (tmp = buffer.get()) << 28;
						if (tmp < 0) {
							throw new UserFakeMsgException("malformed varint.");
						}
					}
				}
			}
			return result;
		}
	}
}

package com.ai.msg.impl;

import com.ai.msg.IMsgSender;
import com.ai.msg.KafkaProducer;


/**
 * kafka 发送器 Date: 2016年4月27日 <br>
 * 
 * @author zhoushanbin Copyright (c) 2016 asiainfo.com <br>
 */
public class KafkaSender implements IMsgSender {

	private KafkaProducer produce = null;;

	@Override
	public void sendMessage(String msg) throws Exception {
		synchronized (KafkaSender.class) {

			if (null == produce) {
				throw new Exception("StatKafkaSender is not init");
			}
			produce.sendMessage(msg);
		}

	}

	@Override
	public void init(String param) {
		produce = new KafkaProducer(param);
	}

	@Override
	public void close() {
		if (null != produce) {
			produce.close();
		}
	}

}

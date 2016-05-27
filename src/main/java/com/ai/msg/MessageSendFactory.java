package com.ai.msg;

import com.ai.msg.impl.KafkaSender;
import com.ai.msg.impl.MDSSender;

/**
 * 发送器工厂
 * Date: 2016年4月27日 <br>
 * @author zhoushanbin
 * Copyright (c) 2016 asiainfo.com <br>
 */
public class MessageSendFactory {
	
	private static MessageSendFactory instance = null;
	
	
	private static final String MDS = "mds";
	
	private static final String KAFKA = "kafka";
	
	static{
	
	}
	private MessageSendFactory(){
		
	}
	
	/**
	 * 实例
	 * @return
	 */
	public static MessageSendFactory getInstance(){
		
		if(null != instance){
			return instance;
		}
		else{
			synchronized (MessageSendFactory.class) {
				if(null == instance){
					instance = new MessageSendFactory();
				}
			}
		}
		
		return instance;
		
	}
	
	
	/**
	 * 根据策略获取对应的发送器 默认策略为kafka
	 * @param strategy
	 * @return
	 */
	public IMsgSender getSender(String strategy){
		
		if(MDS.equals(strategy)){
			return new MDSSender();
		}
		if(KAFKA.equals(strategy)){
			return new KafkaSender();
		}
		return new KafkaSender();
	}
	
}

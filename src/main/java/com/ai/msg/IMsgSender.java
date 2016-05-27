package com.ai.msg;

/**
 * 统计msg 发送controller
 * Date: 2016年4月27日 <br>
 * @author zhoushanbin
 * Copyright (c) 2016 asiainfo.com <br>
 */
public interface IMsgSender {
	
	
	String VIV_STAT_STRATEGY_MDS = "mds";
	String VIV_STAT_STRATEGY_KFK = "kafka";
	String VIV_STAT_STRATEGY = "viv.stat.strategy";
	String VIV_STAT_PARAM = "viv.stat.param";
	
	/**
	 * 
	 * @param param
	 */
	void init(String param);
	
	/**
	 * 消息发送
	 * @param msg
	 */
	void sendMessage(String msg) throws Exception;
	
	/**
	 * 关闭
	 */
	void close();
}

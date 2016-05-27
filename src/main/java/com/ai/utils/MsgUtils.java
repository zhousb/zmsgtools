package com.ai.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.ai.constansts.Consts;
import com.ai.msg.IMsgSender;
import com.ai.msg.MessageSendFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Date: 2016年5月9日 <br>
 * @author zhoushanbin
 * Copyright (c) 2016 asiainfo.com <br>
 */
public final class MsgUtils {
	
	public static final String FIELD_SPLIT = new String(new char[] { (char) 1 });
	public static final String RECORD_SPLIT = new String(new char[] { (char) 2 });
	
	public static void sendMsg(Map<String,String> map,String type){
		if(null == map || StringUtils.isEmpty(type) || map.isEmpty()){
			return;
		}
		if( !(map instanceof LinkedHashMap)){
			throw new RuntimeException("MsgUtils.sendMsg 入参必须为LinkedHashMap");
		}
		if(Consts.MsgConst.STR_MSG.equals(type)){
			StringBuilder builder = new StringBuilder();
			for(Entry<String,String> entry : map.entrySet()){
				builder.append(entry.getValue()+FIELD_SPLIT);
			}
			try {
				getMsgSender().sendMessage(builder.substring(0, builder.length() - 1).toString());
			} catch (Exception e) {
				throw new RuntimeException("MsgUtils.sendMsg 发送消息错误",e);
			}
		}
		if(Consts.MsgConst.JSON_MSG.equals(type)){
			try {
				Gson gson = new Gson();
				getMsgSender().sendMessage(gson.toJson(map));
			} catch (Exception e) {
				throw new RuntimeException("MsgUtils.sendMsg 发送消息错误",e);
			}
		}
	}
	
	private static IMsgSender getMsgSender(){
		
		checkParam();
		String strategy = CommonUtils.setConf.get(Consts.MsgConst.MSG_STRATEGY).toLowerCase();
		JsonObject param = new JsonObject();
		if(Consts.MsgConst.STRATEGY_MDS.equals(strategy)){
			param.addProperty("topic", CommonUtils.setConf.get(Consts.MsgConst.MDS_TOPIC));
			param.addProperty("partition", CommonUtils.setConf.get(Consts.MsgConst.MDS_PARTITION));
			param.addProperty("srvId", CommonUtils.setConf.get(Consts.MsgConst.MDS_SRVID));
			param.addProperty("authAddr", CommonUtils.setConf.get(Consts.MsgConst.MDS_AUTHADDR));
			param.addProperty("pId", CommonUtils.setConf.get(Consts.MsgConst.MDS_PID));
			param.addProperty("password", CommonUtils.setConf.get(Consts.MsgConst.MDS_PWD));
		}
		else if(Consts.MsgConst.STRATEGY_KAFKA.equals(strategy)){
			param.addProperty("kafka.topic", CommonUtils.setConf.get(Consts.MsgConst.KAFKA_TOPIC));
			param.addProperty("kafka.broker.list", CommonUtils.setConf.get(Consts.MsgConst.KAFKA_BROKER_LIST));
		}
		else {
			throw new RuntimeException("strategy error!");
		}
		IMsgSender sender = MessageSendFactory.getInstance().getSender(strategy);
		sender.init(param.toString());
		return sender;
	}
	
	
	public static void loadSetConfToShm(){
		Map<String,String> map = CommonUtils.loadSetConf();
		CommonUtils.setConf.clear();
		CommonUtils.setConf.putAll(map);
	}
	
	private static void checkParam(){
		loadSetConfToShm();
		String strategy = CommonUtils.setConf.get(Consts.MsgConst.MSG_STRATEGY).toLowerCase();
		if(StringUtils.isEmpty(strategy)){
			
			throw new RuntimeException("strategy 不能为空");
		}
		if(Consts.MsgConst.STRATEGY_MDS.equals(strategy)){
			if(StringUtils.isEmpty(CommonUtils.setConf.get(Consts.MsgConst.MDS_TOPIC))){
				throw new RuntimeException("strategy 不能为空");
			}
			if(StringUtils.isEmpty(CommonUtils.setConf.get(Consts.MsgConst.MDS_PARTITION))){
				throw new RuntimeException(Consts.MsgConst.MDS_TOPIC+"不能为空");
			}
			if(StringUtils.isEmpty(CommonUtils.setConf.get(Consts.MsgConst.MDS_SRVID))){
				throw new RuntimeException(Consts.MsgConst.MDS_SRVID+"不能为空");
			}
			if(StringUtils.isEmpty(CommonUtils.setConf.get(Consts.MsgConst.MDS_AUTHADDR))){
				throw new RuntimeException(Consts.MsgConst.MDS_AUTHADDR+"不能为空");
			}
			if(StringUtils.isEmpty(CommonUtils.setConf.get(Consts.MsgConst.MDS_PID))){
				throw new RuntimeException(Consts.MsgConst.MDS_PID+"不能为空");
			}
			if(StringUtils.isEmpty(CommonUtils.setConf.get(Consts.MsgConst.MDS_PWD))){
				throw new RuntimeException(Consts.MsgConst.MDS_PWD+"不能为空");
			}
		}
		else if(Consts.MsgConst.STRATEGY_KAFKA.equals(strategy)){
			if(StringUtils.isEmpty(CommonUtils.setConf.get(Consts.MsgConst.KAFKA_TOPIC))){
				throw new RuntimeException(Consts.MsgConst.KAFKA_TOPIC+"不能为空");
			}
			if(StringUtils.isEmpty(CommonUtils.setConf.get(Consts.MsgConst.KAFKA_BROKER_LIST))){
				throw new RuntimeException(Consts.MsgConst.KAFKA_BROKER_LIST+"不能为空");
			}
		}
		
	}
	
}

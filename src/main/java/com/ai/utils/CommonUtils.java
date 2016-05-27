package com.ai.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JTextArea;

import com.ai.constansts.Consts;
import com.ai.crypto.CryptoUtils;
import com.google.gson.Gson;

/**
 * Date: 2016年5月8日 <br>
 * 
 * @author zhoushanbin 
 * 
 * Copyright (c) 2016 asiainfo.com <br>
 */
public final class CommonUtils {

	private static final String SET_CONF = "setCon.conf";
	private static final String STR_MSG = "strMsg.tmp";
	private static final String JSON_MSG = "jsonMsg.tmp";
	
	private static final String STR_MSG_BMC = "strMsgBmc.tmp";
	private static final String STR_MSG_STAT = "strMsgStat.tmp";
	private static final String JSON_MSG_OMC = "jsonMsgOmc.tmp";
	
	private static String confHome = "";

	public static Map<String,String> setConf = new LinkedHashMap<String,String>();
	
	static {
		confHome = System.getProperty("user.dir") + File.separator + "src"
				+ File.separator + "main" + File.separator + "resource"
				+ File.separator + "conf";
	}
	
	public static void init(String confHome){
		CommonUtils.confHome = confHome;
	}
	
	public static void saveStrMsgAndSend(Map<String, String> map){
		if(map.isEmpty()){
			return;
		}
		Gson gson = new Gson();
		String vp = gson.toJson(map).toString();
		BufferedOutputStream out = null;
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(new File(confHome + File.separator+STR_MSG));
			out = new BufferedOutputStream(fout);
			out.write(CryptoUtils.encrypt(vp).getBytes());
			out.flush();
			MsgUtils.sendMsg(map,Consts.MsgConst.STR_MSG);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("saveStrMsgAndSend error",e);
		} catch (IOException e) {
			throw new RuntimeException("saveStrMsgAndSend error",e);
		}catch(Exception e){
			throw new RuntimeException("saveStrMsgAndSend error",e);
		}
		finally{
			if(fout != null){
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void saveJsonMsgAndSend(Map<String, String> map){
		if(map.isEmpty()){
			return;
		}
		Gson gson = new Gson();
		String vp = gson.toJson(map).toString();
		BufferedOutputStream out = null;
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(new File(confHome + File.separator+JSON_MSG));
			out = new BufferedOutputStream(fout);
			out.write(CryptoUtils.encrypt(vp).getBytes());
			out.flush();
			MsgUtils.sendMsg(map,Consts.MsgConst.JSON_MSG);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("saveJsonMsgAndSend error",e);
		} catch (IOException e) {
			throw new RuntimeException("saveJsonMsgAndSend error",e);
		} catch(Exception e){
			throw new RuntimeException("saveJsonMsgAndSend error",e);
		}
		finally{
			if(fout != null){
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void saveSetPro(Map<String, String> map) {
		if(map.isEmpty()){
			return;
		}
		
		Gson gson = new Gson();
		String vp = gson.toJson(map).toString();
		BufferedOutputStream out = null;
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(new File(confHome + File.separator+SET_CONF));
			out = new BufferedOutputStream(fout);
			out.write(CryptoUtils.encrypt(vp).getBytes());
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(fout != null){
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> loadStrMsg(){
		String confPath = confHome + File.separator + STR_MSG;
		
		File file = new File(confPath);
		FileReader reader = null;
		BufferedReader buf = null;
		
		try {
			StringBuffer sb = new StringBuffer();
			String str = null;
			buf = new BufferedReader(new FileReader(file));
			while(null != (str = buf.readLine())){
				sb.append(str);
			}
			String cf = CryptoUtils.decrypt(sb.toString());
			Gson gson = new Gson();
			
			LinkedHashMap<String,String> map = gson.fromJson(cf, LinkedHashMap.class);
			return map;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(null != reader){
				try {
					reader.close();
				} catch (IOException e) {
				
					e.printStackTrace();
				}
			}
			if(null != buf){
				try {
					buf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> loadJsonMsg(){
		String confPath = confHome + File.separator + JSON_MSG;
		
		File file = new File(confPath);
		FileReader reader = null;
		BufferedReader buf = null;
		
		try {
			StringBuffer sb = new StringBuffer();
			String str = null;
			buf = new BufferedReader(new FileReader(file));
			while(null != (str = buf.readLine())){
				sb.append(str);
			}
			String cf = CryptoUtils.decrypt(sb.toString());
			Gson gson = new Gson();
			
			LinkedHashMap<String,String> map = gson.fromJson(cf, LinkedHashMap.class);
			return map;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(null != reader){
				try {
					reader.close();
				} catch (IOException e) {
				
					e.printStackTrace();
				}
			}
			if(null != buf){
				try {
					buf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> loadSetConf(){
		//Map<String,String> map = new LinkedHashMap<String,String>();
		String confPath = confHome + File.separator + SET_CONF;
		File file = new File(confPath);
		FileReader reader = null;
		BufferedReader buf = null;
		
		try {
			StringBuffer sb = new StringBuffer();
			String str = null;
			buf = new BufferedReader(new FileReader(file));
			while(null != (str = buf.readLine())){
				sb.append(str);
			}
			String cf = CryptoUtils.decrypt(sb.toString());
			Gson gson = new Gson();
			LinkedHashMap<String,String> map = gson.fromJson(cf, LinkedHashMap.class);
			return map;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(null != reader){
				try {
					reader.close();
				} catch (IOException e) {
				
					e.printStackTrace();
				}
			}
			if(null != buf){
				try {
					buf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static void logger(JTextArea log,String msg){
		
		//ViewMsg viewMsg = new ViewMsg();
		//viewMsg.init();
		//String preMsg = log.getText();
		//log.setText(preMsg+System.getProperty("line.separator")+msg);
	}
	
	
	public static void loadTemplate(String type){
		
		System.out.println("加载报文模板");
		if("omc".equals(type)){
			System.out.println("加载信控模板");
			String srcPath = confHome + File.separator + JSON_MSG_OMC;
			String curPath = confHome + File.separator + JSON_MSG;
			loadToCurrent(srcPath,curPath);
		}
		else if("bmc".equals(type)){
			System.out.println("加载计费模板");
			String srcPath = confHome + File.separator + STR_MSG_BMC;
			String curPath = confHome + File.separator + STR_MSG;
			loadToCurrent(srcPath,curPath);
		}
		else if("stat".equals(type)){
			System.out.println("加载统计模板");
			String srcPath = confHome + File.separator + STR_MSG_STAT;
			String curPath = confHome + File.separator + STR_MSG;
			loadToCurrent(srcPath,curPath);
		}
	}
	
	private static void loadToCurrent(String srcPath,String currentPath){
		FileReader reader = null;
		BufferedReader buf = null;
		FileWriter writer = null;
		
		try {
			reader = new FileReader(new File(srcPath));
			buf = new BufferedReader(reader);
			writer = new FileWriter(new File(currentPath));
			String str = buf.readLine();
			if(StringUtils.isEmpty(str)){
				return;
			}
			writer.write(str);
			writer.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(null != reader){
				try {
					reader.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if(null != buf){
				try {
					buf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null != writer){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
			
	}
	
	public static void main(String args[]) {
		
		Map<String,String> map = CommonUtils.loadSetConf();
		for(String str:map.keySet()){
			System.out.println(str);
			System.out.println(map.get(str));
		}
		
		System.out.println("###########################");
		Map<String,String> map2 = CommonUtils.loadStrMsg();
		for(String str:map2.keySet()){
			System.out.println(str);
			System.out.println(map2.get(str));
		}
		
		System.out.println("##########################");
		
		Map<String,String> map1 = CommonUtils.loadJsonMsg();
		for(String str:map1.keySet()){
			System.out.println(str);
			System.out.println(map1.get(str));
		}
	}
}

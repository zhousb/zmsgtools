package com.ai.utils;

/**
 * Date: 2016年5月8日 <br>
 * @author zhoushanbin
 * Copyright (c) 2016 asiainfo.com <br>
 */
public final class StringUtils {
	
	public static boolean isEmpty(String str){
		if(null == str || "".equals(str)){
			return true;
		}
		return false;
	}
}

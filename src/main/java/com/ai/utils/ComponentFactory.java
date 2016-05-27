package com.ai.utils;

import com.ai.constansts.Consts;
import com.ai.model.IComponent;
import com.ai.model.LbLbLbComponent;
import com.ai.model.TfTfBtComponent;

/**
 * Date: 2016年5月8日 <br>
 * @author zhoushanbin
 * Copyright (c) 2016 asiainfo.com <br>
 */
public class ComponentFactory {
		
	private static ComponentFactory instance;
	
	private ComponentFactory(){
		
	}
	
	public static ComponentFactory getInstance(){
		
		if(null != instance){
			return instance;
		}
		synchronized (ComponentFactory.class) {
			if(null != instance){
				return instance;
			}
			instance = new ComponentFactory();
		}
		return instance;
	}
	
	public IComponent getComponent(String type){
		if(Consts.CompConst.TFTFBT.equals(type)){
			return new TfTfBtComponent();
		}
		if(Consts.CompConst.LBLBLB.equals(type)){
			return new LbLbLbComponent();
		}
		return null;
	}
	
}

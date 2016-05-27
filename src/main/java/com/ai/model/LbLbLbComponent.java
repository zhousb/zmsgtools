package com.ai.model;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Date: 2016年5月8日 <br>
 * @author zhoushanbin
 * Copyright (c) 2016 asiainfo.com <br>
 */
public class LbLbLbComponent implements IComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5624647778067308948L;
	private JLabel lb;
	private JLabel lb2;
	private JLabel lb3;
	private JPanel jpanel;
	
	public JPanel getJpanel() {
		return jpanel;
	}


	public void setJpanel(JPanel jpanel) {
		this.jpanel = jpanel;
	}


	public LbLbLbComponent(){
		
		jpanel = new JPanel(new GridLayout(1,3));
		lb = new JLabel();
		lb2 = new JLabel();
		lb3 = new JLabel();
		jpanel.add(lb);
		jpanel.add(lb2);
		jpanel.add(lb3);
		
	}
	
	
	public JLabel getLb() {
		return lb;
	}
	public void setLb(JLabel lb) {
		this.lb = lb;
	}
	public JLabel getLb2() {
		return lb2;
	}
	public void setLb2(JLabel lb2) {
		this.lb2 = lb2;
	}
	public JLabel getLb3() {
		return lb3;
	}
	public void setLb3(JLabel lb3) {
		this.lb3 = lb3;
	}
	
	
	
}

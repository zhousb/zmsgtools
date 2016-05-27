package com.ai.model;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Date: 2016年5月8日 <br>
 * 
 * @author zhoushanbin 
 * 
 * Copyright (c) 2016 asiainfo.com <br>
 */
public class TfTfBtComponent implements IComponent{

	private static final long serialVersionUID = -4516096377181548250L;

	private JTextField jt;

	private JTextField jtx;

	private JButton jbut;

	private ActionListener btListener;
	
	private JPanel jpanel;

	public TfTfBtComponent() {
		
		jpanel = new JPanel(new GridLayout(1,3));
		jt = new JTextField();
		jt.setName("name1");
		jtx = new JTextField();
		jtx.setName("name2");
		jbut = new JButton();
		jbut.setName("name3");
		jpanel.add(jt);
		jpanel.add(jtx);
		jpanel.add(jbut);
		
	}
	
	public void addActionListenerToBut(ActionListener listener){
		jbut.addActionListener(listener);
	}
	
	public JTextField getJt() {
		return jt;
	}

	public void setJt(JTextField jt) {
		this.jt = jt;
	}

	public JTextField getJtx() {
		return jtx;
	}

	public void setJtx(JTextField jtx) {
		this.jtx = jtx;
	}

	public JButton getJbut() {
		return jbut;
	}

	public void setJbut(JButton jbut) {
		this.jbut = jbut;
	}

	public ActionListener getBtListener() {
		return btListener;
	}

	public void setBtListener(ActionListener btListener) {
		this.btListener = btListener;
	}

	public JPanel getJpanel() {
		return jpanel;
	}

	public void setJpanel(JPanel jpanel) {
		this.jpanel = jpanel;
	}
		

}
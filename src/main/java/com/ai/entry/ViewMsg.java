package com.ai.entry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ViewMsg implements ActionListener{
	
	private JFrame frame;
	
	
	public ViewMsg(){
		frame = new JFrame();
		frame.setBounds(300, 300, 700, 500);
		frame.setTitle("消息查看窗口");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void init(){
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
	
	}

}

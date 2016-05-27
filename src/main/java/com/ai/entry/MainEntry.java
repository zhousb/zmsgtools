package com.ai.entry;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.ai.constansts.Consts;
import com.ai.model.IComponent;
import com.ai.model.LbLbLbComponent;
import com.ai.model.TfTfBtComponent;
import com.ai.utils.CommonUtils;
import com.ai.utils.ComponentFactory;
import com.ai.utils.StringUtils;
import com.google.gson.Gson;

/**
 * Date: 2016年5月7日 <br>
 * 
 * @author zhoushanbin Copyright (c) 2016 asiainfo.com <br>
 */
public class MainEntry implements ActionListener {

	private JFrame frame;

	private JToolBar toolBar;

	private JPanel strMsgPanel;
	private JPanel strMsgHeadPanel;
	private JPanel strMsgButPanel;

	private JPanel jsonMsgPanel;
	private JPanel jsonMsgHeadPanel;
	private JPanel jsonMsgButPanel;
	private JPanel desktop;

	private JPanel setPanel;

	private JPanel setButPanel;
	private JPanel setHeadPanel;

	private JTextArea logLabel;
	private JTextArea logLabel2;

	private Map<String, String> setProMap = new LinkedHashMap<String, String>();
	private Map<String, String> strMsgProMap = new LinkedHashMap<String, String>();
	private Map<String, String> jsonMsgProMap = new LinkedHashMap<String, String>();

	private Map<String, JPanel> setMap = new LinkedHashMap<String, JPanel>();

	private Map<String, JPanel> strMsgMap = new LinkedHashMap<String, JPanel>();
	private Map<String, JPanel> jsonMsgMap = new LinkedHashMap<String, JPanel>();
	private int setLimit = 25;

	public MainEntry() {

		logLabel = new JTextArea();
		logLabel.setName("logLabel");
		logLabel.setBackground(Color.white);
		logLabel.setLineWrap(true);

		logLabel2 = new JTextArea();
		logLabel2.setName("logLabel2");
		logLabel2.setBackground(Color.white);
		logLabel2.setLineWrap(true);

		frame = new JFrame();
		frame.setBounds(100, 100, 500, 650);
		frame.setTitle("报文发送器 (version 1.0) BY ZSB");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildDestop();
		buildToolBar();

		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread t, Throwable e) {
				e.printStackTrace();
			}

		});

	}

	private void buildDestop() {
		desktop = new JPanel();
		desktop.setVisible(true);
		desktop.setPreferredSize(new Dimension(500, 570));
		desktop.setLayout(new CardLayout());
		buildSetPanel();
		buildStrMsgPanel();
		buildJSONMsgPanel();
		frame.getContentPane().add(desktop, BorderLayout.SOUTH);

	}

	private void buildToolBar() {
		toolBar = new JToolBar();
		toolBar.setFloatable(true);
		JButton setting = new JButton("配置设置");
		setting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setFun();
				desktop.validate();
			}
		});
		toolBar.add(setting);
		toolBar.setPreferredSize(new Dimension(500, 40));
		JButton comStr = new JButton("发送普通字符串报文");
		comStr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendStrMsg();
				desktop.validate();
			}
		});
		toolBar.add(comStr);
		JButton jsonStr = new JButton("发送JSON字符串报文");

		jsonStr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendJSONMsg();
				desktop.validate();
			}
		});
		toolBar.add(jsonStr);
		JButton viewTopic = new JButton("查看消息队列");

		viewTopic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				viewTopic();
				desktop.validate();
			}
		});
		toolBar.add(viewTopic);

		frame.add(toolBar, BorderLayout.NORTH);

	}

	private void buildSetPanel() {
		setPanel = new JPanel();
		setButPanel = new JPanel(new GridLayout(1, 3));
		LbLbLbComponent com = (LbLbLbComponent) ComponentFactory.getInstance()
				.getComponent(Consts.CompConst.LBLBLB);
		com.getLb().setText(" 属性名");
		com.getLb2().setText(" 属性值");
		com.getLb3().setText(" 操作");
		setHeadPanel = com.getJpanel();

		JButton jb = new JButton("添加行");
		jb.setName("setConfAdd");
		jb.addActionListener(this);
		JButton jbadd = new JButton("保存配置");
		jbadd.setName("setConfSave");
		jbadd.addActionListener(this);
		JLabel jbcz = new JLabel();
		setButPanel.add(jb);
		setButPanel.add(jbadd);
		setButPanel.add(jbcz);
		setMap.put("head", setHeadPanel);

		Map<String, String> cfMap = CommonUtils.loadSetConf();
		if (cfMap != null && !cfMap.isEmpty()) {
			setPanel.setLayout(new GridLayout(cfMap.size() + 2, 1));
			for (String str : cfMap.keySet()) {
				TfTfBtComponent cfCom = (TfTfBtComponent) ComponentFactory
						.getInstance().getComponent(Consts.CompConst.TFTFBT);
				Random rd = new Random();
				cfCom.getJt().setText(str);

				cfCom.getJtx().setText(cfMap.get(str));

				final String pname = String.valueOf(rd
						.nextInt(Integer.MAX_VALUE));
				cfCom.getJpanel().setName(pname);
				cfCom.getJbut().setText("删除");
				cfCom.addActionListenerToBut(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(null, "", "提示",
								JOptionPane.WARNING_MESSAGE);
						removePanelFmList(setMap, pname);
						buildCnt(setMap, setPanel, "set");
					}
				});
				setMap.put(pname, cfCom.getJpanel());
			}
		} else {
			setPanel.setLayout(new GridLayout(2, 1));
		}
		setMap.put("button", setButPanel);
		buildCnt(setMap, setPanel, "set");
		desktop.add("setting", setPanel);
		((CardLayout) desktop.getLayout()).show(desktop, "setting");
	}

	private void setFun() {
		((CardLayout) desktop.getLayout()).show(desktop, "setting");
	}

	private void sendStrMsg() {
		((CardLayout) desktop.getLayout()).show(desktop, "strMsg");
	}

	private void sendJSONMsg() {

		((CardLayout) desktop.getLayout()).show(desktop, "jsonMsg");
	}

	private void viewTopic() {

	}

	private void buildStrMsgPanel() {
		strMsgPanel = new JPanel();
		strMsgButPanel = new JPanel(new GridLayout(1, 3));
		LbLbLbComponent com = (LbLbLbComponent) ComponentFactory.getInstance()
				.getComponent(Consts.CompConst.LBLBLB);
		com.getLb().setText(" 属性名");
		com.getLb2().setText(" 属性值");
		com.getLb3().setText(" 操作");
		strMsgHeadPanel = com.getJpanel();

		JButton jb = new JButton("添加行");
		jb.addActionListener(this);
		jb.setName("strMsgAdd");
		JButton jb2 = new JButton("发送");
		jb2.setName("strMsgSend");
		jb2.addActionListener(this);

		JLabel jb3 = new JLabel();
		strMsgButPanel.add(jb);
		strMsgButPanel.add(jb2);
		strMsgButPanel.add(jb3);
		strMsgMap.clear();
		strMsgMap.put("strMsgHead", strMsgHeadPanel);

		Map<String, String> strMap = CommonUtils.loadStrMsg();
		if (strMap != null && !strMap.isEmpty()) {
			strMsgPanel.setLayout(new GridLayout(strMap.size() + 3, 1));
			for (String str : strMap.keySet()) {
				TfTfBtComponent cfCom = (TfTfBtComponent) ComponentFactory
						.getInstance().getComponent(Consts.CompConst.TFTFBT);
				Random rd = new Random();
				cfCom.getJt().setText(str);
				cfCom.getJtx().setText(strMap.get(str));
				final String pname = String.valueOf(rd
						.nextInt(Integer.MAX_VALUE));
				cfCom.getJpanel().setName(pname);
				cfCom.getJbut().setText("删除");
				cfCom.addActionListenerToBut(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						removePanelFmList(strMsgMap, pname);
						buildCnt(strMsgMap, strMsgPanel, "strMsg");
					}
				});
				strMsgMap.put(pname, cfCom.getJpanel());
			}
		} else {
			strMsgPanel.setLayout(new GridLayout(3, 1));
		}

		strMsgMap.put("strMsgBut", strMsgButPanel);

		buildCnt(strMsgMap, strMsgPanel, "strMsg");
		desktop.add("strMsg", strMsgPanel);
	}

	private void buildJSONMsgPanel() {
		jsonMsgPanel = new JPanel();
		jsonMsgButPanel = new JPanel(new GridLayout(1, 3));
		LbLbLbComponent com = (LbLbLbComponent) ComponentFactory.getInstance()
				.getComponent(Consts.CompConst.LBLBLB);
		com.getLb().setText(" 属性名");
		com.getLb2().setText(" 属性值");
		com.getLb3().setText(" 操作");
		jsonMsgHeadPanel = com.getJpanel();

		JButton jb = new JButton("添加行");
		jb.addActionListener(this);
		jb.setName("jsonMsgAdd");
		JButton jb2 = new JButton("发送");
		jb2.setName("jsonMsgSend");
		jb2.addActionListener(this);

		JLabel jb3 = new JLabel();
		jsonMsgButPanel.add(jb);
		jsonMsgButPanel.add(jb2);
		jsonMsgButPanel.add(jb3);
		jsonMsgMap.clear();
		jsonMsgMap.put("jsonMsgHead", jsonMsgHeadPanel);

		Map<String, String> strMap = CommonUtils.loadJsonMsg();
		if (strMap != null && !strMap.isEmpty()) {
			jsonMsgPanel.setLayout(new GridLayout(strMap.size() + 3, 1));
			for (String str : strMap.keySet()) {
				TfTfBtComponent cfCom = (TfTfBtComponent) ComponentFactory
						.getInstance().getComponent(Consts.CompConst.TFTFBT);
				Random rd = new Random();
				cfCom.getJt().setText(str);
				cfCom.getJtx().setText(strMap.get(str));
				final String pname = String.valueOf(rd
						.nextInt(Integer.MAX_VALUE));
				cfCom.getJpanel().setName(pname);
				cfCom.getJbut().setText("删除");
				cfCom.addActionListenerToBut(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						removePanelFmList(jsonMsgMap, pname);
						buildCnt(jsonMsgMap, jsonMsgPanel, "jsonMsg");
					}
				});
				jsonMsgMap.put(pname, cfCom.getJpanel());
			}
		} else {
			jsonMsgPanel.setLayout(new GridLayout(3, 1));
		}

		jsonMsgMap.put("jsonMsgBut", jsonMsgButPanel);
		// jsonMsgMap.put("logPanel2", logPanel2);
		buildCnt(jsonMsgMap, jsonMsgPanel, "jsonMsg");
		desktop.add("jsonMsg", jsonMsgPanel);
	}

	public void init() {

	}

	public static void main(String[] args) {
		CommonUtils.init(args[0]);
		MainEntry w = new MainEntry();
		w.init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton btn = (JButton) e.getSource();
		if ("setConfAdd".equals(btn.getName())) {

			IComponent addComp = ComponentFactory.getInstance().getComponent(
					Consts.CompConst.TFTFBT);
			TfTfBtComponent com = (TfTfBtComponent) addComp;
			Random rd = new Random();
			final String pname = String.valueOf(rd.nextInt(Integer.MAX_VALUE));
			com.getJpanel().setName(pname);
			com.getJbut().setText("删除");
			com.addActionListenerToBut(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "", "提示",
							JOptionPane.WARNING_MESSAGE);
					removePanelFmList(setMap, pname);
					buildCnt(setMap, setPanel, "set");
				}
			});
			setMap.put(pname, com.getJpanel());
			buildCnt(setMap, setPanel, "set");

		} else if ("setConfSave".equals(btn.getName())) {

			for (Entry<String, JPanel> entry : setMap.entrySet()) {
				if ("buttonSp".equals(entry.getKey())
						|| "button".equals(entry.getKey())
						|| "head".equals(entry.getKey())) {
					continue;
				}
				JPanel panel = entry.getValue();
				Component[] coms = panel.getComponents();
				String key = "";
				String value = "";
				for (int i = 0; i < coms.length; i++) {

					if ("name1".equals(coms[i].getName())) {
						key = ((JTextField) coms[i]).getText();

					} else if ("name2".equals(coms[i].getName())) {
						value = ((JTextField) coms[i]).getText();

					}

				}
				if (StringUtils.isEmpty(key)) {
					continue;
				}
				setProMap.put(key, org.apache.commons.lang.StringUtils.defaultIfBlank(value, ""));
			}
			if (!setProMap.isEmpty()) {
				CommonUtils.saveSetPro(setProMap);
			}
			setProMap.clear();
			JOptionPane.showMessageDialog(null, "保存成功", "提示",
					JOptionPane.NO_OPTION);
		} else if ("strMsgAdd".equals(btn.getName())) {

			IComponent addComp = ComponentFactory.getInstance().getComponent(
					Consts.CompConst.TFTFBT);
			TfTfBtComponent com = (TfTfBtComponent) addComp;
			Random rd = new Random();
			final String pname = String.valueOf(rd.nextInt(Integer.MAX_VALUE));
			com.getJpanel().setName(pname);
			com.getJbut().setText("删除");
			com.addActionListenerToBut(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					removePanelFmList(strMsgMap, pname);
					buildCnt(strMsgMap, strMsgPanel, "strMsg");
				}
			});
			strMsgMap.put(pname, com.getJpanel());
			buildCnt(strMsgMap, strMsgPanel, "strMsg");

		} else if ("strMsgSend".equals(btn.getName())) {
			for (Entry<String, JPanel> entry : strMsgMap.entrySet()) {
				if ("buttonSp".equals(entry.getKey())
						|| "strMsgBut".equals(entry.getKey())
						|| "strMsgHead".equals(entry.getKey())) {
					continue;
				}
				JPanel panel = entry.getValue();
				Component[] coms = panel.getComponents();
				String key = "";
				String value = "";
				for (int i = 0; i < coms.length; i++) {

					if ("name1".equals(coms[i].getName())) {
						key = ((JTextField) coms[i]).getText();

					} else if ("name2".equals(coms[i].getName())) {
						value = ((JTextField) coms[i]).getText();

					}

				}
				if (StringUtils.isEmpty(key)) {
					continue;
				}
				strMsgProMap.put(key, org.apache.commons.lang.StringUtils.defaultIfBlank(value, ""));
			}
			boolean flg = true;
			if (!strMsgProMap.isEmpty()) {
				Gson gson = new Gson();
				CommonUtils.logger(logLabel,
						"发送字符串：" + gson.toJson(strMsgProMap));
				try {
					CommonUtils.saveStrMsgAndSend(strMsgProMap);
				} catch (Exception e1) {
					e1.printStackTrace();
					flg = false;
					CommonUtils.logger(logLabel, e1.getMessage());
				}
				if (!flg) {
					JOptionPane.showMessageDialog(null, "发送失败", "提示",
							JOptionPane.NO_OPTION);
				}
				else{
					JOptionPane.showMessageDialog(null, "发送成功", "提示",
							JOptionPane.NO_OPTION);
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "不能发送空消息", "提示",
						JOptionPane.NO_OPTION);
			}
			strMsgProMap.clear();
			
		} else if ("jsonMsgAdd".equals(btn.getName())) {

			IComponent addComp = ComponentFactory.getInstance().getComponent(
					Consts.CompConst.TFTFBT);
			TfTfBtComponent com = (TfTfBtComponent) addComp;
			Random rd = new Random();
			final String pname = String.valueOf(rd.nextInt(Integer.MAX_VALUE));
			com.getJpanel().setName(pname);
			com.getJbut().setText("删除");
			com.addActionListenerToBut(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					removePanelFmList(jsonMsgMap, pname);
					buildCnt(jsonMsgMap, jsonMsgPanel, "jsonMsg");
				}
			});
			jsonMsgMap.put(pname, com.getJpanel());
			buildCnt(jsonMsgMap, jsonMsgPanel, "jsonMsg");

		} else if ("jsonMsgSend".equals(btn.getName())) {
			for (Entry<String, JPanel> entry : jsonMsgMap.entrySet()) {
				if ("buttonSp".equals(entry.getKey())
						|| "jsonMsgBut".equals(entry.getKey())
						|| "jsonMsgHead".equals(entry.getKey())) {
					continue;
				}
				JPanel panel = entry.getValue();
				Component[] coms = panel.getComponents();
				String key = "";
				String value = "";
				for (int i = 0; i < coms.length; i++) {

					if ("name1".equals(coms[i].getName())) {
						key = ((JTextField) coms[i]).getText();

					} else if ("name2".equals(coms[i].getName())) {
						value = ((JTextField) coms[i]).getText();

					}

				}
				if (StringUtils.isEmpty(key)) {
					continue;
				}
				jsonMsgProMap.put(key, org.apache.commons.lang.StringUtils.defaultIfBlank(value, ""));
			}
			boolean flg = true;
			if (!jsonMsgProMap.isEmpty()) {
				Gson gson = new Gson();
				CommonUtils.logger(logLabel2,
						"发送字符串：" + gson.toJson(jsonMsgProMap));
				try {
					CommonUtils.saveJsonMsgAndSend(jsonMsgProMap);
				} catch (Exception e1) {
					e1.printStackTrace();
					flg = false;
					CommonUtils.logger(logLabel2, e1.getMessage());
				}
				if (!flg) {
					JOptionPane.showMessageDialog(null, "发送失败", "提示",
							JOptionPane.NO_OPTION);
				}
				else{
					JOptionPane.showMessageDialog(null, "发送成功", "提示",
							JOptionPane.NO_OPTION);
				}
			} else {
				JOptionPane.showMessageDialog(null, "不能发送空消息", "提示",
						JOptionPane.NO_OPTION);
			}
			jsonMsgProMap.clear();

		}
	}

	private void removePanelFmList(Map<String, JPanel> map, String name) {
		map.remove(name);
	}

	private void buildCnt(Map<String, JPanel> map, JPanel panel, String type) {

		panel.removeAll();
		panel.setLayout(new GridLayout(setLimit, 1));
		if ("set".equals(type)) {
			map.remove("button");
			map.remove("buttonSp");
		}
		if ("strMsg".equals(type)) {
			map.remove("strMsgBut");
			map.remove("buttonSp");
		}
		if ("jsonMsg".equals(type)) {
			map.remove("jsonMsgBut");
			map.remove("buttonSp");
		}
		LbLbLbComponent com = (LbLbLbComponent) ComponentFactory.getInstance()
				.getComponent(Consts.CompConst.LBLBLB);
		if ("set".equals(type)) {
			map.put("buttonSp", com.getJpanel());
			map.put("button", setButPanel);
		}
		if ("strMsg".equals(type)) {
			map.put("buttonSp", com.getJpanel());
			map.put("strMsgBut", strMsgButPanel);
		}
		if ("jsonMsg".equals(type)) {
			map.put("buttonSp", com.getJpanel());
			map.put("jsonMsgBut", jsonMsgButPanel);
		}
		for (Entry<String, JPanel> entry : map.entrySet()) {
			panel.add(entry.getValue());
		}
		if ("strMsg".equals(type)) {
			JPanel temple = new JPanel(new GridLayout(1,3));
			JButton btn1 = new JButton("加载计费报文模板");
			btn1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CommonUtils.loadTemplate("bmc");
					strMsgPanel.removeAll();
					strMsgPanel.validate();
					strMsgPanel.repaint();
					buildStrMsgPanel();
					sendStrMsg();
				}
			});
			JButton btn2 = new JButton("加载统计报文模板");
			btn2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CommonUtils.loadTemplate("stat");
					strMsgPanel.removeAll();
					strMsgPanel.validate();
					strMsgPanel.repaint();
					buildStrMsgPanel();
					sendStrMsg();
				}
			});
			temple.add(btn1);
			temple.add(btn2);
			panel.add(temple);
			
		} else if ("jsonMsg".equals(type)) {
			JPanel temple = new JPanel(new GridLayout(1,3));
			JButton btn1 = new JButton("加载信控报文模板");
			btn1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CommonUtils.loadTemplate("omc");
					jsonMsgPanel.removeAll();
					jsonMsgPanel.validate();
					jsonMsgPanel.repaint();
					buildJSONMsgPanel();
					sendJSONMsg();
				}
			});
			
			temple.add(btn1);
			panel.add(temple);
		}
		panel.validate();
		panel.repaint();
	}

}
package com.tej6;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;


import com.tej6.IConnectionModeData.ConnectionMode;
import com.tej6.IPlayModeData.PlayMode;
//import org.eclipse.wb.swing.FocusTraversalOnArray;


public class GamePlayConnectionView implements IConnectionView, ActionListener {

	
	private IConnectionModeData _connectionmode = new GameConnectionModeData(null);
	private IConnectionInfoData infodata = new GameConnectionInfoData();
	private JFrame frame2 = null;
	private JFormattedTextField t2 = new JFormattedTextField(createFormatter("####"));
	private JTextField t1 = new JTextField();
	Font font1 = new Font("Times New Roman", Font.BOLD, 15);
	private Object state = new Object();

	public GamePlayConnectionView() {
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	public IConnectionInfoData show() {
		if(frame2 == null)
			frame2 = new JFrame("Connection Controller");
		frame2.setBounds(500,350,350,200);
		frame2.setMinimumSize(new Dimension(200,200));
		frame2.setResizable(false);
	
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Mode", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBackground(SystemColor.inactiveCaptionBorder);
		frame2.getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Create");
		panel.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setFont(font1);
		rdbtnNewRadioButton.addActionListener(this);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Join");
		panel.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setFont(font1);
		rdbtnNewRadioButton_1.addActionListener(this);
		rdbtnNewRadioButton_1.setSelected(true);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Properties", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frame2.getContentPane().add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new GridLayout(0,1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("IP:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel);	
		lblNewLabel.setFont(font1);
		
		panel_1.add(t1);
		t1.setColumns(15);		
		JLabel lblNewLabel_1 = new JLabel("PORT:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.setFont(font1);
		
		panel_1.add(t2);
		t2.setColumns(4);
		//panel_1.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblNewLabel, t1, lblNewLabel_1, t2}));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame2.getContentPane().add(panel_2, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Connect");
		panel_2.add(btnNewButton);
		btnNewButton.addActionListener(this);
		btnNewButton.setFont(font1);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		panel_2.add(btnNewButton_1);
		btnNewButton_1.addActionListener(this);
		btnNewButton_1.setFont(font1);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnNewRadioButton_1);
		group.add(rdbtnNewRadioButton);
		
		
		//panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{rdbtnNewRadioButton, rdbtnNewRadioButton_1}));
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setVisible(true);
		System.out.println("I am here multi.2");
		synchronized(state) {
			try {
				state.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return infodata;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if((e.getActionCommand().compareToIgnoreCase("Create")) == 0) {
			_connectionmode.setMode(ConnectionMode.create);
			t1.setText(getIp());
		}
		else if((e.getActionCommand().compareToIgnoreCase("Join")) == 0) {
			_connectionmode.setMode(ConnectionMode.join);
		}
		else if((e.getActionCommand().compareToIgnoreCase("Connect")) == 0) {
			infodata.setConnectionMode(_connectionmode);
			if(!t2.getText().trim().isEmpty())
				infodata.setPort(Integer.parseInt(t2.getText()));
			else {
				  JOptionPane.showMessageDialog(frame2,"Please enter valid port number!");
				  return;
			}
			String ipcheck = t1.getText();
			int count = 0;
			for(int i = 0; i < ipcheck.length(); i++)
			    if(ipcheck.charAt(i) == '.')
			        count ++;
			if(count == 3) 
				infodata.setIpAddress(t1.getText());
			else{
				 JOptionPane.showMessageDialog(frame2,"Please enter valid IP address!");
				 return;
			}
			frame2.setVisible(false);
			synchronized(state) {	
				state.notify();
			}
		}
		else if((e.getActionCommand().compareToIgnoreCase("Cancel")) == 0) {
			frame2.setVisible(false);
			infodata = null;
			synchronized(state) {	
				state.notify();
			}
		}
	}

	protected static MaskFormatter createFormatter(String s) {
	    MaskFormatter formatter = null;
	    try {
	        formatter = new MaskFormatter(s);
	    } catch (java.text.ParseException exc) {
	        System.err.println("formatter is bad: " + exc.getMessage());
	        System.exit(-1);
	    }
	    return formatter;
	}
	
	public static String getIp() {
		InetAddress ownIp = null;
		try{
			ownIp= InetAddress.getLocalHost();
			System.out.println("IP of my system is := "+ownIp.getHostAddress());
		}catch (Exception e){
			System.out.println("Exception caught ="+e.getMessage());
		}
		return ownIp.getHostAddress();
	}
}

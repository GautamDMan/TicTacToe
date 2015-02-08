package com.tej6;

import javax.swing.*;

import com.tej6.IController.Player;
import com.tej6.IGamePlayData.Sign;

import java.awt.*;
import java.awt.event.*;

public class GamePlaySessionView  implements ActionListener {

	private IManager manager = null;
	private static JFrame frame3 = null;
	private static JFrame frame4 = null;
	Font font1 = new Font("Times New Roman", Font.BOLD, 15);
	public static JTextField score = null;
	public Icon GameCross = null; //new ImageIcon("C:\\Users\\gautam\\Workspace\\TicTackToe\\cross.png");
	public Icon GameCircle = null; //new ImageIcon("C:\\Users\\gautam\\Workspace\\TicTackToe\\circle.png");
	public Integer state = 0;
	private Sign currentsign = Sign.Empty;
	JButton[] buttonarray = new JButton[9];
	JButton btnNewButton_9 = null;
	JButton btnNewButton_10 = null;
	private JTextField textField;

	public GamePlaySessionView(IManager manager) {
		this.manager = manager;
		java.net.URL imageURL = GamePlaySessionView.class.getResource("cross.png");
		if (imageURL != null)
			GameCross = new ImageIcon(imageURL);
		imageURL = GamePlaySessionView.class.getResource("circle.png");
		if (imageURL != null)
			GameCircle = new ImageIcon(imageURL);

	}
	/**
	 * @wbp.parser.entryPoint
	 */
	public void show() {
		frame3 = new JFrame("Session");
		frame3.setBounds(500,350,300,330);
		frame3.setMinimumSize(new Dimension(200,200));
		frame3.setResizable(false);
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame3.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton btnNewButton = new JButton("");
		buttonarray[0] = btnNewButton;
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		buttonarray[1] = btnNewButton_1;
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("");
		buttonarray[2] = btnNewButton_2;
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("");
		buttonarray[3] = btnNewButton_3;
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("");
		buttonarray[4] = btnNewButton_4;
		panel.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("");
		buttonarray[5] = btnNewButton_5;
		panel.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("");
		buttonarray[6] = btnNewButton_6;
		panel.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("");
		buttonarray[7] = btnNewButton_7;
		panel.add(btnNewButton_7);
		
		JButton btnNewButton_8 = new JButton("");
		buttonarray[8] = btnNewButton_8;
		panel.add(btnNewButton_8);
		
		for(int i = 0; i < 9; i++) {
			buttonarray[i].addActionListener(this);
			buttonarray[i].setEnabled(false);
		}
		
		JPanel panel_1 = new JPanel();
		frame3.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(1, 3, 0, 0));
		
		btnNewButton_9 = new JButton("Refresh");
		btnNewButton_9.setFont(font1);
		panel_1.add(btnNewButton_9);
		btnNewButton_9.addActionListener(this);
		
		textField = new JTextField();
		textField.setEditable(false);
		panel_1.add(textField);
		textField.setColumns(10);
		textField.setFont(font1);
		textField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
		
		btnNewButton_10 = new JButton("Exit");
		btnNewButton_10.setFont(font1);
		panel_1.add(btnNewButton_10);
		btnNewButton_10.addActionListener(this);
		
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if((e.getActionCommand().compareToIgnoreCase("Refresh")) == 0)  {
			manager.onRefresh();
		}
		else if((e.getActionCommand().compareToIgnoreCase("Exit")) == 0)  {
			WindowEvent wev = new WindowEvent(frame3, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
			manager.onExit();
		}
		else {
			JButton button = (JButton)e.getSource();
			if(currentsign == Sign.Circle)
				button.setIcon(GameCircle);
			else if(currentsign == Sign.Cross)
				button.setIcon(GameCross);
			else 
				button.setIcon(null);
			IGamePlayData data = manager.getSessionData();
			for( int i = 0;i < 9; i++) {
				buttonarray[i].setEnabled(false);
				btnNewButton_9.setEnabled(false);
				data.setSign(i,getButtonState(i));
			}
			manager.onEveryClick();
		}
	}

	public void setButtonState(int index,Sign sign,boolean state) {
		if(sign == Sign.Empty) {
			buttonarray[index].setIcon(null); 
		}
		else if(sign == Sign.Circle) {
			buttonarray[index].setIcon(GameCircle); 
		}
		else if(sign == Sign.Cross) {
			buttonarray[index].setIcon(GameCross); 
		}
		buttonarray[index].setEnabled(state);
	}
	
	public Sign getButtonState(int index) {
		if(buttonarray[index].getIcon() == null) {
			return Sign.Empty;
		}
		else if(buttonarray[index].getIcon() == GameCircle) {
			return Sign.Circle;
		}
		else {
			return Sign.Cross;
		}
	}
	public void setCurrentSign(Sign sign) {
		currentsign = sign;
	}
	
	public void buttonEnabler(IGamePlayData data,Player player,boolean state) {
		if(player == player.Me) {
			btnNewButton_9.setEnabled(state);
			for(int i = 0;i < 9; i++) {
				if(data.getSign(i) == Sign.Empty) {
					buttonarray[i].setIcon(null);
					buttonarray[i].setEnabled(state);
				}
				else if(data.getSign(i) == Sign.Circle)
					buttonarray[i].setIcon(GameCircle);
				else
					buttonarray[i].setIcon(GameCross);
			}
		}
	}
	public void setScore(String score) {
		textField.setText(score);
	}
}

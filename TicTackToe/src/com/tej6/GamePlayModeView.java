package com.tej6;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.tej6.IPlayModeData.PlayMode;

public class GamePlayModeView implements IPlayModeView, ActionListener {

	private IPlayModeData _playmodedata  = new GamePlayModeData();
	private static JFrame frame1 = null;
	Font font1 = new Font("Times New Roman", Font.BOLD, 15);
	public Object state = new Object();

	public GamePlayModeView() {
		_playmodedata.setPlayMode(PlayMode.noGame);
	}
	
	public IPlayModeData show(boolean show) {
		if(show) {
			if(frame1 == null)
				frame1 = new JFrame("Tic Tack Toe");
			frame1.setBounds(500,350,300,300);
			frame1.setMinimumSize(new Dimension(200,200));
			frame1.getContentPane().setLayout(new BorderLayout());
			frame1.setResizable(false);
			JPanel p = new JPanel(new GridLayout(3,1));
			JButton b1 = new JButton("Single-Player");
			b1.setFont(font1);
			p.add(b1);
			b1.addActionListener(this);
			JButton b2 = new JButton("Multi-Player");
			b2.setFont(font1);
			p.add(b2);
			b2.addActionListener(this);
			JButton b3 = new JButton("Exit");
			b3.setFont(font1);
			p.add(b3);
			b3.addActionListener(this);
			frame1.getContentPane().add(p);
			frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame1.setVisible(true);
			synchronized(state) {	
				try {
					state.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return _playmodedata;
		}
		else {
			frame1.setVisible(false);
			return null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if((e.getActionCommand().compareToIgnoreCase("Single-Player")) == 0) {
			_playmodedata.setPlayMode(PlayMode.singlePlayer);
			synchronized(state) {	
				state.notify();
			}
		}
		else if((e.getActionCommand().compareToIgnoreCase("Multi-Player")) == 0) {
			_playmodedata.setPlayMode(PlayMode.multiPlayer);
			synchronized(state) {	
				state.notify();
			}
			//WindowEvent wev = new WindowEvent(frame1, WindowEvent.WINDOW_CLOSING);
            //Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
		}
		else if((e.getActionCommand().compareToIgnoreCase("Exit")) == 0) {
			_playmodedata.setPlayMode(PlayMode.noGame);
			synchronized(state) {	
				state.notify();
			}
			WindowEvent wev = new WindowEvent(frame1, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
		}
	}

}

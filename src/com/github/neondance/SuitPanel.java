package com.github.neondance;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JToggleButton;

public class SuitPanel extends JPanel {
	private JTextField textFieldName;
	private JTextField textFieldIp;
	private JPanel panelHeartbeat;
	private JTextField txtOutput;
	private SuitThread suitThread;
	private JTextField heartbeatTime;

	/**
	 * Create the panel.
	 */
	public SuitPanel(SuitThread suitThread) {
		this.suitThread = suitThread;
		
		JLabel lblName = new JLabel("Name:");
		
		JLabel lblIp = new JLabel("IP:");
		
		textFieldName = new JTextField();
		textFieldName.setEditable(false);
		textFieldName.setColumns(10);
		
		textFieldIp = new JTextField();
		textFieldIp.setEditable(false);
		textFieldIp.setColumns(10);
		
		JLabel lblHeartbeat = new JLabel("Heartbeat");
		
		panelHeartbeat = new JPanel();
		
		txtOutput = new JTextField();
		txtOutput.setHorizontalAlignment(SwingConstants.CENTER);
		txtOutput.setColumns(10);
		
		JButton btnStartButton = new JButton("Start");
		
		heartbeatTime = new JTextField();
		heartbeatTime.setEditable(false);
		heartbeatTime.setColumns(10);
		
		JButton btnFlash = new JButton("Flash");
		
		JButton btnBlink = new JButton("Blink");
		
		JToggleButton tglbtnOnOff = new JToggleButton("I/O");
		
		JButton btnRandom = new JButton("Rand");
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBackground(new Color(220, 20, 60));
		btnDisconnect.setForeground(new Color(255, 0, 0));
		
		JSeparator separator_1 = new JSeparator();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(btnDisconnect, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnStartButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(txtOutput)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblHeartbeat)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelHeartbeat, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(heartbeatTime, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(tglbtnOnOff, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
									.addComponent(btnFlash, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(btnRandom, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
									.addComponent(btnBlink, 0, 0, Short.MAX_VALUE))))
						.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblIp)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(textFieldIp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblName)
								.addGap(18)
								.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(10, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName)
						.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldIp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblIp))
					.addGap(33)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblHeartbeat)
						.addComponent(heartbeatTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelHeartbeat, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
					.addComponent(txtOutput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnStartButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnFlash)
						.addComponent(btnBlink))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tglbtnOnOff)
						.addComponent(btnRandom))
					.addGap(8)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(8)
					.addComponent(btnDisconnect)
					.addGap(21))
		);
		setLayout(groupLayout);

		MainFrame mainFrame = MainFrame.getInstance();
		mainFrame.getSuitePane().setViewportView(this);
//		mainFrame.getSuitePane().revalidate();
		//or
//		mainFrame.getSuitePane().repaint();
//		mainFrame.getSuitePane().validate();
	}

	
	public void flashHearbeat(){
		if (panelHeartbeat.getBackground().equals(Color.YELLOW)) {
			panelHeartbeat.setBackground(Color.WHITE);
		} else {
			panelHeartbeat.setBackground(Color.YELLOW);
		}
	}
	
	public JTextField getTextFieldName() {
		return textFieldName;
	}

	public JTextField getTextFieldIp() {
		return textFieldIp;
	}
	public JTextField getTxtOutput() {
		return txtOutput;
	}
	public JTextField getHeartbeatTime() {
		return heartbeatTime;
	}
}

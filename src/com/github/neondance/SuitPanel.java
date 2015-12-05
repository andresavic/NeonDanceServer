package com.github.neondance;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.omg.PortableInterceptor.SUCCESSFUL;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SuitPanel extends JPanel {
	private JTextField textFieldName;
	private JTextField textFieldIp;
	private JPanel panelHeartbeat;
	private JTextField txtOutput;
	private SuitThread suitThread;
	private JTextField heartbeatTime;
	private MainFrame mainFrame;

	/**
	 * Create the panel.
	 */
	public SuitPanel(SuitThread suitThread) {
		mainFrame = MainFrame.getInstance();
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
		
		heartbeatTime = new JTextField();
		heartbeatTime.setEditable(false);
		heartbeatTime.setColumns(10);
		
		JButton btnFlash = new JButton("Flash");
		btnFlash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				suitThread.sendCommand(SuitThread.FLASH);
			}
		});
		
		JButton btnBlink = new JButton("Blink");
		btnBlink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				suitThread.sendCommand(SuitThread.BLINK);
			}
		});
		
		JToggleButton tglbtnOnOff = new JToggleButton("I/O");
		tglbtnOnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (suitThread.isiO()) {
					suitThread.setiO(false);
					suitThread.sendCommand(SuitThread.OFF);
				} else {
					suitThread.setiO(true);
					suitThread.sendCommand(SuitThread.ON);
				}
			}
		});
		
		JButton btnRandom = new JButton("Rand");
		btnRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				suitThread.sendCommand(SuitThread.RANDOM);
			}
		});
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Do you really want to disconnect the client?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==0) {
					suitThread.interrupt();
				}
			}
		});
		btnDisconnect.setBackground(new Color(220, 20, 60));
		btnDisconnect.setForeground(Color.WHITE);
		
		JSeparator separator_1 = new JSeparator();
		
		JToggleButton tglbtnStartStop = new JToggleButton("Start/Stop");
		tglbtnStartStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (suitThread.isShowRunning()) {
					suitThread.setShowRunning(false);
					suitThread.sendCommand(SuitThread.STOP_SHOW);
				} else {
					suitThread.setShowRunning(true);
					suitThread.sendCommand(SuitThread.START_SHOW);
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(tglbtnStartStop, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnDisconnect, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(txtOutput)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblHeartbeat)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelHeartbeat, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(heartbeatTime, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(btnFlash, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(tglbtnOnOff, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
									.addComponent(btnBlink, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
									.addComponent(btnRandom, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblIp)
								.addComponent(lblName))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldName, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
								.addComponent(textFieldIp, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))))
					.addContainerGap())
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
						.addComponent(lblIp)
						.addComponent(textFieldIp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(33)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(lblHeartbeat)
							.addComponent(panelHeartbeat, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(heartbeatTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(txtOutput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnStartStop)
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

		
		mainFrame.getSuitePane().add(this);
		mainFrame.getSuitePane().validate();
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
	
	public void destroy() {
		mainFrame.getSuitePane().remove(this);
		mainFrame.getSuitePane().validate();
		mainFrame.getSuitePane().repaint();
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

package com.github.neondance;

import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class SuitPanel extends JPanel {
	private JTextField textFieldName;
	private JTextField textFieldVersion;
	private JTextField textFieldIp;
	private JPanel panelHeartbeat;
	private JTextField txtOutput;
	private SuitThread suitThread;

	/**
	 * Create the panel.
	 */
	public SuitPanel(SuitThread suitThread) {
		this.suitThread = suitThread;
		
		JLabel lblName = new JLabel("Name:");
		
		JLabel lblVersion = new JLabel("Version:");
		
		JLabel lblIp = new JLabel("IP:");
		
		textFieldName = new JTextField();
		textFieldName.setEditable(false);
		textFieldName.setColumns(10);
		
		textFieldVersion = new JTextField();
		textFieldVersion.setEditable(false);
		textFieldVersion.setColumns(10);
		
		textFieldIp = new JTextField();
		textFieldIp.setEditable(false);
		textFieldIp.setColumns(10);
		
		JSeparator separator = new JSeparator();
		
		JLabel lblHeartbeat = new JLabel("Heartbeat");
		
		panelHeartbeat = new JPanel();
		
		txtOutput = new JTextField();
		txtOutput.setHorizontalAlignment(SwingConstants.CENTER);
		txtOutput.setColumns(10);
		
		JButton btnStartButton = new JButton("Start");
		
		JButton btnUploadButton = new JButton("Upload");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtOutput, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
						.addComponent(separator, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblName)
							.addGap(18)
							.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblVersion)
								.addComponent(lblIp))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldIp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldVersion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblHeartbeat)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelHeartbeat, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnStartButton, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
						.addComponent(btnUploadButton, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
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
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVersion)
						.addComponent(textFieldVersion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblIp)
						.addComponent(textFieldIp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(8)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panelHeartbeat, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblHeartbeat))
					.addGap(18)
					.addComponent(txtOutput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnStartButton)
					.addGap(18)
					.addComponent(btnUploadButton)
					.addGap(79))
		);
		setLayout(groupLayout);

		MainFrame mainFrame = MainFrame.getInstance();
		mainFrame.getSuitePane().add(this);
	}

	
	public void flashHearbeat(){
		if (panelHeartbeat.getBackground().equals(Color.RED)) {
			panelHeartbeat.setBackground(Color.WHITE);
		} else {
			panelHeartbeat.setBackground(Color.RED);
		}
	}
	
	public JTextField getTextFieldName() {
		return textFieldName;
	}

	public JTextField getTextFieldVersion() {
		return textFieldVersion;
	}

	public JTextField getTextFieldIp() {
		return textFieldIp;
	}
	public JTextField getTxtOutput() {
		return txtOutput;
	}
}

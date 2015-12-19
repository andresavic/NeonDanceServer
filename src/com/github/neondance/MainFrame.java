package com.github.neondance;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.DefaultCaret;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.ScrollPaneConstants;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;

public class MainFrame {

	private JFrame frame;
	private static MainFrame mainFrame;
	private JPanel status;
	private JTextArea errorText;
	private Server server;
	private JScrollPane suiteScrollPane;
	private JPanel suitePane;
	private JPanel panel;
	private JLabel lblConnectedClients;
	private JTextField oscQueueTextField;
	private JTextField oscIpTextField;
	private JSpinner heartBeatTimeoutSpinner;
	private JSpinner startShowTimeoutSpinner;
	private JCheckBox chckbxUseOsc;
	private JCheckBox chckbxAutoRemoveSuit;
	private JButton btnStop;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = MainFrame.getInstance();
					window.frame.setVisible(true);
					window.server = new Server();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 928, 727);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblServerStatus = new JLabel("Server status");
		
		JButton btnStartStop = new JButton("Start / Stop");
		btnStartStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (server == null) {
					server = new Server();
				}
				if (server.isAlive()) {
					server.interrupt();
					server = null;
				} else {
					server = null;
					server = new Server();
					server.start();
				}
			}
		});
		
		status = new JPanel();
		status.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JScrollPane scrollPane = new JScrollPane();
		
		suiteScrollPane = new JScrollPane();
		suiteScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		suiteScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "All", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblVersion = new JLabel(Server.version);
		
		lblConnectedClients = new JLabel("Connected Clients: 0");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "OSC", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(suiteScrollPane, GroupLayout.DEFAULT_SIZE, 892, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 892, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblServerStatus)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(status, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 756, Short.MAX_VALUE)
							.addComponent(lblVersion))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnStartStop)
							.addPreferredGap(ComponentPlacement.RELATED, 703, Short.MAX_VALUE)
							.addComponent(lblConnectedClients))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 318, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(status, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblServerStatus, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(lblVersion))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnStartStop)
						.addComponent(lblConnectedClients))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(suiteScrollPane, GroupLayout.PREFERRED_SIZE, 354, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		
		JLabel lblHeartbeatTimeout = new JLabel("Heartbeat Timeout");
		
		heartBeatTimeoutSpinner = new JSpinner();
		heartBeatTimeoutSpinner.setModel(new SpinnerNumberModel(new Integer(30000), new Integer(10000), null, new Integer(5000)));
		
		JLabel lblStartShowTimeout = new JLabel("Start Show Timeout");
		
		startShowTimeoutSpinner = new JSpinner();
		startShowTimeoutSpinner.setModel(new SpinnerNumberModel(new Integer(200), new Integer(10), null, new Integer(10)));
		
		chckbxUseOsc = new JCheckBox("Use OSC");
		chckbxUseOsc.setSelected(true);
		
		chckbxAutoRemoveSuit = new JCheckBox("Auto remove suit");
		chckbxAutoRemoveSuit.setSelected(true);
		
		JCheckBox chckbxOnAir = new JCheckBox("On Air");
		chckbxOnAir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxOnAir.isSelected()) {
					btnStop.setEnabled(false);
				} else {
					btnStop.setEnabled(true);
				}
			}
		});
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblStartShowTimeout, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblHeartbeatTimeout, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(heartBeatTimeoutSpinner)
						.addComponent(startShowTimeoutSpinner))
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxUseOsc)
						.addComponent(chckbxAutoRemoveSuit)
						.addComponent(chckbxOnAir))
					.addContainerGap(127, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblHeartbeatTimeout)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(heartBeatTimeoutSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblStartShowTimeout)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(startShowTimeoutSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxUseOsc)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxAutoRemoveSuit)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxOnAir)))
					.addContainerGap(40, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		oscQueueTextField = new JTextField();
		oscQueueTextField.setText("/cue/neondance/");
		oscQueueTextField.setColumns(10);
		
		JLabel lblQueue = new JLabel("Queue");
		
		JLabel lblIp = new JLabel("IP");
		
		oscIpTextField = new JTextField();
		oscIpTextField.setColumns(10);
		
		JButton btnLearnQlab = new JButton("Fire");
		btnLearnQlab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Osc.getInstance().sendStart();
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblQueue)
						.addComponent(oscQueueTextField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
						.addComponent(lblIp)
						.addComponent(oscIpTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnLearnQlab))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(lblQueue)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(oscQueueTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblIp)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(oscIpTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLearnQlab)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JButton button = new JButton("Flash");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.sendCommandToAll(Suit.FLASH);
			}
		});
		
		JButton button_1 = new JButton("Blink");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.sendCommandToAll(Suit.BLINK);
			}
		});
		
		JButton button_2 = new JButton("Rand");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.sendCommandToAll(Suit.RANDOM);
			}
		});
		
		JButton btnOn = new JButton("On");
		btnOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.sendCommandToAll(Suit.ON);
			}
		});
		
		JButton btnOff = new JButton("Off");
		btnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.sendCommandToAll(Suit.OFF);
			}
		});
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.sendCommandToAll(Suit.START_SHOW);
			}
		});
		
		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.sendCommandToAll(Suit.STOP_SHOW);
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnOn, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(button, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
							.addGap(6)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnOff, 0, 0, Short.MAX_VALUE)
								.addComponent(button_1, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnStop, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnStart, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)))
						.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(button)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(button_1)
							.addComponent(btnStart)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnOn)
								.addComponent(btnOff))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_2))
						.addComponent(btnStop))
					.addGap(4))
		);
		panel.setLayout(gl_panel);
		
		suitePane = new JPanel();
		suiteScrollPane.setViewportView(suitePane);
		suitePane.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		errorText = new JTextArea();
		DefaultCaret caret = (DefaultCaret)errorText.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		errorText.setEditable(false);
		scrollPane.setViewportView(errorText);
		frame.getContentPane().setLayout(groupLayout);
	}

	
	public static MainFrame getInstance() {
		if (mainFrame == null) {
			mainFrame = new MainFrame();
		}
		return mainFrame;
	}

	public JPanel getStatus() {
		return status;
	}

	public JTextArea getErrorText() {
		return errorText;
	}

	public JLabel getLblConnectedClients() {
		return lblConnectedClients;
	}

	public JPanel getSuitePane() {
		return suitePane;
	}

	public JTextField getQlcTextField() {
		return oscQueueTextField;
	}

	public JTextField getOscIpTextField() {
		return oscIpTextField;
	}

	public JSpinner getHeartBeatTimeOutSpinner() {
		return heartBeatTimeoutSpinner;
	}

	public JSpinner getStartShowTimeoutSpinner() {
		return startShowTimeoutSpinner;
	}

	public JCheckBox getChckbxUseOsc() {
		return chckbxUseOsc;
	}

	public JCheckBox getChckbxAutoRemoveSuit() {
		return chckbxAutoRemoveSuit;
	}
}

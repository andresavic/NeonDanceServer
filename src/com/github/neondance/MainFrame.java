package com.github.neondance;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.DefaultCaret;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.ScrollPaneConstants;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JToggleButton;

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
		frame.setBounds(100, 100, 928, 670);
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
		
		JButton btnLearnQlab = new JButton("Learn QLAB");
		btnLearnQlab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.oscGo();
			}
		});
		
		lblConnectedClients = new JLabel("");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(suiteScrollPane, GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblServerStatus)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(status, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 756, Short.MAX_VALUE)
							.addComponent(lblVersion))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnStartStop)
							.addGap(18)
							.addComponent(btnLearnQlab)
							.addPreferredGap(ComponentPlacement.RELATED, 606, Short.MAX_VALUE)
							.addComponent(lblConnectedClients))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 318, GroupLayout.PREFERRED_SIZE))
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
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnStartStop)
							.addComponent(btnLearnQlab))
						.addComponent(lblConnectedClients))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(suiteScrollPane, GroupLayout.PREFERRED_SIZE, 354, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(52))
		);
		
		JButton button = new JButton("Flash");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.sendCommandToAll(SuitThread.FLASH);
			}
		});
		
		JButton button_1 = new JButton("Blink");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.sendCommandToAll(SuitThread.BLINK);
			}
		});
		
		JButton button_2 = new JButton("Rand");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.sendCommandToAll(SuitThread.RANDOM);
			}
		});
		
		JButton btnOn = new JButton("On");
		btnOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.sendCommandToAll(SuitThread.ON);
			}
		});
		
		JButton btnOff = new JButton("Off");
		btnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.sendCommandToAll(SuitThread.OFF);
			}
		});
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.sendCommandToAll(SuitThread.START_SHOW);
			}
		});
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.sendCommandToAll(SuitThread.STOP_SHOW);
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
}

package com.github.neondance;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

import org.joda.time.Instant;

public class Suit {
	
	private Socket socket;
	public Parameter parameter;
	private Logger log;
	private SuitPanel suitPanel;
	private HeartBeat heartBeat;
	private BufferedWriter writer;
	private Server server;
	private boolean showRunning, iO;
	private Instant sendShowStart, recievedShowStart;
	
	public static final String START_SHOW = "S;\r\n";
	public static final String FLASH = "Flash;\r\n";
	public static final String ON = "On;\r\n";
	public static final String OFF = "Off;\r\n";
	public static final String BLINK = "Blink;\r\n";
	public static final String RANDOM = "Random;\r\n";
	public static final String STOP_SHOW = "E;\r\n";
	
	public Suit(Socket socket, Server server) {
		super();
		this.socket = socket;
		this.parameter = new Parameter();
		this.log = Logger.getInstance();
		this.server = server;
		this.showRunning = false;
		this.iO = false;
	}

	/*
	 * Suit has connected, creating resources
	 */
	public void connect() {
		try {
			this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			//Parameters are filled
			parameter.name = "UNKNOWN";
			parameter.ip = socket.getInetAddress().getHostAddress();
			//Set parameters to display on panel
			setPanelParameters();
			//Create new heartbeat and start it
			this.heartBeat = new HeartBeat(socket, this);
			this.heartBeat.start();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Suitthread is stopped
	 */
	public void disconnect() {
		try {
			writer.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		heartBeat.interrupt();
		suitPanel.destroy();
		server.suitDisconnected(this);
	}

	public void sendCommand(String command) {
		try {
			writer.write(command);
			writer.flush();
			if (showRunning) {
//				heartBeat.interrupt(true);
				sendShowStart = new Instant();
				suitPanel.getTxtOutput().setText("SHOW STARTED");
			} else if (command.equals(Suit.STOP_SHOW)) {
//				heartBeat = null;
//				heartBeat = new HeartBeat(socket, this);
//				heartBeat.start();
//				suitPanel.getTxtOutput().setText("");
			}
		} catch (IOException e) {
			log.log(Logger.WARNING, parameter.name + ": Writing failed! " + command);
		}
	}
	
	//Assign suitpanel to thread
	public void setSuitPanel(SuitPanel suitPanel) {
		this.suitPanel = suitPanel;
		setPanelParameters();
	}
	
	//Set panel parameters
	private void setPanelParameters() {
		if (suitPanel != null) {
			suitPanel.getTextFieldName().setText(parameter.name);
			suitPanel.getTextFieldIp().setText(parameter.ip);
		}
	}
	
	public void setSuitName(String name){
		parameter.name = name;
		setPanelParameters();
	}

	public SuitPanel getSuitPanel() {
		return suitPanel;
	}
	
	public boolean isShowRunning() {
		return showRunning;
	}

	public void setShowRunning(boolean showRunning) {
		this.showRunning = showRunning;
	}

	public boolean isiO() {
		return iO;
	}

	public void setiO(boolean iO) {
		this.iO = iO;
	}

	public Instant getSendShowStart() {
		return sendShowStart;
	}

	public void setSendShowStart(Instant sendShowStart) {
		this.sendShowStart = sendShowStart;
	}

	public Instant getRecievedShowStart() {
		return recievedShowStart;
	}

	public void setRecievedShowStart(Instant recievedShowStart) {
		this.recievedShowStart = recievedShowStart;
	}

	public class Parameter {
		public String name;
		public String ip;
	}

}

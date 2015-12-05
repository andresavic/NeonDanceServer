package com.github.neondance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class SuitThread extends Thread {
	
	private Socket socket;
	private Parameter parameter;
	private Logger log;
	private SuitPanel suitPanel;
	private HeartBeat heartBeat;
	private BufferedReader reader;
	private BufferedWriter writer;
	private Server server;
	private boolean showRunning, iO;
	
	public static final String START_SHOW = "S;";
	public static final String FLASH = "Flash;";
	public static final String ON = "On;";
	public static final String OFF = "Off;";
	public static final String BLINK = "Blink;";
	public static final String RANDOM = "Random;";
	public static final String STOP_SHOW = "E;";
	
	public SuitThread(Socket socket, Server server) {
		super();
		this.socket = socket;
		this.parameter = new Parameter();
		this.log = Logger.getInstance();
		this.server = server;
		this.showRunning = false;
		this.iO = false;
	}

	/*
	 * Suitthread is started
	 */
	@Override
	public void run() {
		try {
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			initialize(reader, writer);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Suitthread is stopped
	 */
	@Override
	public void interrupt() {
		try {
			reader.close();
			writer.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		heartBeat.interrupt(true);
		suitPanel.destroy();
		super.interrupt();
		server.threadInterrupted(this);
	}

	//INITIALIZE CONNECTION
	//
	private void initialize(BufferedReader reader, BufferedWriter writer) throws IOException, SocketException, JSONException{
		//Timeout to abort if no data is recieved
		socket.setSoTimeout(10000);
		//Parameters are filled
		String input = reader.readLine();
		parameter.name = input;
		parameter.ip = socket.getInetAddress().getHostAddress();
		Thread.currentThread().setName(parameter.name);
		//Server version is send to client
		writer.write(Server.version);
		writer.flush();
		//Set timout back to infinite
		socket.setSoTimeout(0);
		//Set parameters to display on panel
		setPanelParameters();
		//Create new heartbeat and start it
		this.heartBeat = new HeartBeat(socket, this);
		this.heartBeat.start();
	}
	
	public void sendCommand(String command) {
		try {
			if (showRunning) {
				heartBeat.interrupt(true);
			} else if (command.equals(SuitThread.STOP_SHOW)) {
				heartBeat = null;
				heartBeat = new HeartBeat(socket, this);
				heartBeat.start();
				suitPanel.getTxtOutput().setText("");
			}
			writer.write(command);
			writer.flush();
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
		suitPanel.getTextFieldName().setText(parameter.name);
		suitPanel.getTextFieldIp().setText(parameter.ip);
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

	public class Parameter {
		public String name;
		public String ip;
	}

}

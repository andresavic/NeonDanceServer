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

import org.json.JSONObject;

public class SuitThread extends Thread {
	
	private Socket socket;
	private Parameter parameter;
	private Logger log;
	private SuitPanel suitPanel;
	private HeartBeat heartBeat;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public SuitThread(Socket socket) {
		super();
		this.socket = socket;
		this.parameter = new Parameter();
		this.log = Logger.getInstance();
	}

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
		}
	}
	
	@Override
	public void interrupt() {
		try {
			reader.close();
			writer.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.interrupt();
	}

	private void initialize(BufferedReader reader, BufferedWriter writer) throws IOException, SocketException{
		socket.setSoTimeout(10000);
		JSONObject json = new JSONObject(reader.readLine());
		parameter.name = json.getString("name");
		parameter.version = json.getString("version");
		parameter.ip = socket.getInetAddress().getHostAddress();
		writer.write(Server.version);
		socket.setSoTimeout(0);
		setPanelParameters();
		this.heartBeat = new HeartBeat(socket, this);
		this.heartBeat.start();
	}
	
	private void setPanelParameters() {
		suitPanel.getTextFieldName().setText(parameter.name);
		suitPanel.getTextFieldVersion().setText(parameter.version);
		suitPanel.getTextFieldIp().setText(parameter.ip);
	}

	public class Parameter {
		public String name;
		public String version;
		public String ip;
	}
	
	public SuitPanel getSuitPanel() {
		return suitPanel;
	}
	
	public void setSuitPanel(SuitPanel suitPanel) {
		this.suitPanel = suitPanel;
		this.suitPanel.getTextFieldName().setText(parameter.name);
		this.suitPanel.getTextFieldIp().setText(parameter.ip);
		this.suitPanel.getTextFieldVersion().setText(parameter.version);
	}

	public class HeartBeat extends Thread {
		
		private Socket socket;
		private SuitThread suitThread;
		private Boolean cleanInterrupt;

		public HeartBeat(Socket socket, SuitThread suitThread) {
			super();
			this.suitThread = suitThread;
			this.socket = socket;
			this.cleanInterrupt = false;
		}

		@Override
		public void run() {
			try {
				while(true) {
					socket.setSoTimeout(10000);
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					reader.readLine();
					suitThread.getSuitPanel().flashHearbeat();
				}
			} catch (SocketException e) {
				if (!cleanInterrupt) {
					suitThread.getSuitPanel().getTxtOutput().setText("HEARTBEAT ERROR");
					suitThread.interrupt();
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void interrupt(Boolean cleanInterrupt) {
			this.cleanInterrupt = cleanInterrupt;
			super.interrupt();
		}
		
	}

}

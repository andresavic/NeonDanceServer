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
	
	public SuitThread(Socket socket) {
		super();
		this.socket = socket;
		this.parameter = new Parameter();
	}

	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
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
	}
	
	public class Parameter {
		public String name;
		public String version;
		public String ip;
	}
	
	public class HeartBeat extends Thread {
		
		private Socket socket;
		private SuitThread suitThread;

		public HeartBeat(Socket socket, SuitThread suitThread) {
			super();
			this.suitThread = suitThread;
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				while(true) {
					socket.setSoTimeout(10000);
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					reader.readLine();
				}
			} catch (SocketException e) {
				suitThread.interrupt();
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}

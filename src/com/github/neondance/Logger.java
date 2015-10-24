package com.github.neondance;

import java.awt.Color;

public class Logger {

	private static Logger logger;
	
	private int serverStatus;
	private MainFrame frame;
	
	public static final int INFO = 0, WARNING = 1, ERROR = 2, DEBUG = 3;
	public static final int UNKNOWN = 0, UP = 1, DOWN = 2, ISSUES = 3;
	
	public Logger() {
		this.serverStatus = UNKNOWN;
		this.frame = MainFrame.getInstance();
	}
	
	public void log(int severity, String text) {
		StringBuilder sb = new StringBuilder();
		frame = MainFrame.getInstance();
		
		switch (severity) {
		case INFO:
			sb.append("INFO: ");
			break;

		case WARNING:
			sb.append("WARNING: ");
			break;
			
		case ERROR:
			sb.append("ERROR: ");
			setServerStatus(ISSUES);
			break;
			
		case DEBUG:
			sb.append("DEBUG: ");
			break;
			
		default:
			break;
		}
		
		sb.append(text + "\n");
		frame.getErrorText().append(sb.toString());
	}
	
	public void handleError(Exception e) {
		log(ERROR, e.getMessage());
	}
	
	public static Logger getInstance(){
		if (logger == null) {
			logger = new Logger();
		}
		return logger;
	}

	public int getServerStatus() {
		return serverStatus;
	}

	public void setServerStatus(int serverStatus) {
		this.serverStatus = serverStatus;
		
		switch (serverStatus) {
		
		case UNKNOWN:
			frame.getStatus().setBackground(Color.GRAY);
			break;
			
		case UP:
			frame.getStatus().setBackground(Color.GREEN);
			break;
			
		case DOWN:
			frame.getStatus().setBackground(Color.RED);
			break;
			
		case ISSUES:
			frame.getStatus().setBackground(Color.YELLOW);
			break;

		default:
			frame.getStatus().setBackground(Color.GRAY);
			break;
		}
	}
	
}

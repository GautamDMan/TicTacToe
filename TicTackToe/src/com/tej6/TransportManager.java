package com.tej6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.tej6.IConnectionModeData.ConnectionMode;

public class TransportManager implements IConnector {

	private IConnectionInfoData infodata = null;
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private BufferedReader bufreader = null;
	private DataOutputStream outstream = null;

	public TransportManager(IConnectionInfoData infodata) throws IOException {
		this.infodata = infodata;
		if(infodata.getConnectionMode().getMode() == IConnectionModeData.ConnectionMode.create)
	         serverSocket = new ServerSocket(infodata.getPort());
		
	}
	public void sendMessage(String message) throws IOException {
		outstream.writeBytes(message+"\n");
	}

	public String receiveMessage() throws IOException {	
		String data = bufreader.readLine();
		return data;
	}

	@Override
	public void connect() throws IOException, SocketTimeoutException {
		if(infodata.getConnectionMode().getMode() == ConnectionMode.create) {
			serverSocket.setSoTimeout(10000);
			clientSocket = serverSocket.accept();
			outstream = new DataOutputStream(clientSocket.getOutputStream());
			bufreader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}
		else if(infodata.getConnectionMode().getMode() == ConnectionMode.join) {
			clientSocket = new Socket(infodata.getIpAddress(),infodata.getPort());
			outstream = new DataOutputStream(clientSocket.getOutputStream());
			bufreader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}
	}

}

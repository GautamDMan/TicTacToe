package com.tej6;

import java.io.IOException;
import java.net.SocketTimeoutException;

public interface IConnector {

	public void sendMessage(String message) throws IOException;
	public String receiveMessage() throws IOException;
	public void connect() throws IOException,SocketTimeoutException;
	
}

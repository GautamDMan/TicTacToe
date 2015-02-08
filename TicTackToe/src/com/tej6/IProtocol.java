package com.tej6;

import java.io.IOException;

import com.tej6.IController.Player;
import com.tej6.IGamePlayData.Sign;

public interface IProtocol {
	public void startPlay() throws IOException;
	public void sendPosition(IGamePlayData playdata,Player player) throws IOException;
	public void refresh() throws IOException;
	public void exit() throws IOException;
}

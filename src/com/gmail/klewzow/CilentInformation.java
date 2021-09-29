package com.gmail.klewzow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class CilentInformation implements Runnable {
	private Socket socket;
	private String str;

	public CilentInformation(Socket socket, String str) {
		super();
		this.socket = socket;
		this.str = str;
		new Thread(this).start();
	}

	public CilentInformation() {
		super();
	}

	@Override
	public void run() {
		System.out.println("client conect");
		String response = "HTTP/1.1 200 OK\r\n" + "Server: My_Server\r\n" + "Content-Type:" + "text/html\r\n"
				+ "Content-Length: " + "\r\n" + "Connection: close\r\n\r\n";
		try (InputStream is = this.socket.getInputStream(); OutputStream os = this.socket.getOutputStream()) {
			PrintWriter pw = new PrintWriter(os);
			byte[] b = new byte[is.available()];
			is.read(b);
			pw.write(response + this.str);
			pw.flush();
		} catch (IOException e) {
			System.out.println("Client error." + e.getMessage());
		}
	}

}

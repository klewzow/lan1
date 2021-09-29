package com.gmail.klewzow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerInformation implements Runnable {

	private Thread tr = new Thread(this);
	private int port = 8080;

	public ServerInformation() {
		super();
		this.tr.start();
	}

	/**
	 * @return the tr
	 */
	public Thread getTr() {
		return tr;
	}

	private String createMap() {
		Map<String, Object> map = new HashMap<>();
		map.put(" Operating system name : ", System.getProperty("os.name"));
		map.put(" Operating system version : ", System.getProperty("os.version"));
		map.put(" User home directory : ", System.getProperty("user.home"));
		map.put(" User name : ", System.getProperty("user.name"));
		map.put(" Total amount of memory : ", Runtime.getRuntime().totalMemory() / (1024l*1024l) + " Mb");
		map.put(" Amount of free memory : ", Runtime.getRuntime().freeMemory()/ (1024l*1024l) + " Mb");
		map.put(" Maximum amount of memory : ", Runtime.getRuntime().maxMemory()/ (1024l*1024l) + " Mb");
		map.put(" Number of processors available : ", Runtime.getRuntime().availableProcessors());
		StringBuilder sb = new StringBuilder();
		map.forEach((K, V) -> {
			sb.append("<tr><td style=\"height: 10px; width: 231.469px; text-align: center;\">" + K + "</td>"
					+ "<td style=\"height: 10px; width: 231.469px; text-align: center;\">" + map.get(K) + "</td></tr>");
		});
		return sb.toString();
	}

	private String pageGenerator(File file) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String tmp = "";
			while ((tmp = br.readLine()) != null) {
				if (tmp.equals("tableData")) {
					tmp = createMap();
				}
				sb.append(tmp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
 
	private void startServer() {
 
		try (ServerSocket svs = new ServerSocket(this.port)) {
			System.out.println("Port : " + this.port);
			Socket cli = svs.accept();
			new CilentInformation(cli, pageGenerator(new File("index.txt")));
		} catch (IOException e) {
			System.out.println("Server start eror. " + e.getMessage() );
 
		}
}
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			startServer();
		}
		System.out.println("Serv stop");

	}

}

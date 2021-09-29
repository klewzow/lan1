package com.gmail.klewzow;
 
public class Main {

	public static void main(String[] args) {

		ServerInformation s = new ServerInformation();
		try {
			s.getTr().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 
	}

}

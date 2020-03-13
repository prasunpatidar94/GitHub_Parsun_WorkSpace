package com.pp.iteanz.utility;

import java.util.Random;

public class PasswordGenrator {

	public  String passGenretor() {

		String letters = "ABCDE#@$!%FGHIJKLM0123456789NOPQRSTUVWXYZa0123456789bcdefghijklmn#@$!%opqrstuvwxyz";
//		String number = "0123456789";
//		String sChar = "#@$!%";
		int length = 6;

//		String pass = letters + number + sChar;

		Random random = new Random();
		char[] newPass = new char[length];
		for (int i = 0; i < length; i++) {

			newPass[i] = letters.charAt(random.nextInt(letters.length()));

		}

		return new String(newPass);

	}
	
//	public static void main(String[] args) {
//		
//		String res = passGenretor();
//		
//		System.out.println(res);
//		
//	}

}

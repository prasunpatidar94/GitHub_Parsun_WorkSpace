package com.pp.iteanz.xmlParse;

public class File_Name_Read {

	private static final String FOLDER_PATH = "C:\\Users\\Iteanz Emp\\Desktop\\READ\\";

	public static void main(String[] args) {

		for (String file : ReturnName.ReturnData(FOLDER_PATH)) {

			System.out.println("file path is the :   " + file);

		}

	}
}

package com.pp.iteanz.entity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Utility {
	    
	    /**
	     * Use Files class from Java 1.7 to write files, internally uses OutputStream
	     * @param data
	     */
	
	    public static void writeUsingFiles(String data) {
	        try {
	            Files.write(Paths.get("/home/dhiraj/Desktop/test.txt"), data.getBytes());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }



}

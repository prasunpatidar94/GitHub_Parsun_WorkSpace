package com.iteanz.srl.utility;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FileFormFTP {
	
	
	public  String fileData (String pono) throws SocketException, IOException {
		
		
		String server = "192.168.0.51";
        int port = 21;
        String user = "xiftp";
        String pass = "mail#236";
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(server, port);
        ftpClient.login(user, pass);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        
        
        boolean pdfFileDirectory=new File(System.getProperty("user.home")+"\\Documents\\SRL\\POPFD").mkdirs();

         
        // lists files and directories in the current working directory
        FTPFile[] files = ftpClient.listFiles("/VMS/POACK");//VMS/POACK/
        for (FTPFile file : files) {
        	
        	
        System.out.println(file.getName());
        
        if ((file.getName()==(""+pono+".pdf")) || ( (file.getName().toString()).equals(""+pono+".pdf")) ) {
        	
        	

        	//PR_3000051882_20191203-131651-700.xml
        	 // APPROACH #1: using retrieveFile(String, OutputStream)
            String remoteFile1 = "/VMS/POACK/"+file.getName();
            File downloadFile1 = new File(System.getProperty("user.home")+"\\Documents\\SRL\\POPFD\\"+file.getName());
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            boolean success = ftpClient.retrieveFile(remoteFile1, outputStream1);
            outputStream1.close();

            if (success) {
                System.out.println("File #1 has been downloaded successfully.");
                return "download file successfully";
            }
            else {
                System.out.println("File is not found in the directory.");
                return "download file fail plz try again ";
			}
            
            
            // iterates over the files and prints details for each
             
           
			
		}
        	
			
		}
        
//        else {
//			return"file not found to this po";
//		}
        
       
        
      
        ftpClient.logout();
        ftpClient.disconnect();
		return "pocess is all file is done ";
	
		
		
	
		
		
	}
	
	
	
	

}

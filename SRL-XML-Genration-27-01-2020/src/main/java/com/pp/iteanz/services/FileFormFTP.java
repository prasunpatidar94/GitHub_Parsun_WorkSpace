package com.pp.iteanz.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FileFormFTP {
	static int count;

	private final static String server = "192.168.0.51";
	private final static int port = 21;
	private final static String user = "xiftp";
	private final static String pass = "mail#236";

	public String fileData(String ftpPath) throws IOException {

//		String server = "192.168.0.51";
//	
//        int port = 21;
//        String user = "xiftp";
//        String pass = "mail#236";
		FTPClient ftpClient = new FTPClient();
		
			// TODO Auto-generated catch block
		
			ftpClient.connect(server, port);

			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			// lists files and directories in the current working directory
//        FTPFile[] files = ftpClient.listFiles("/VMS/PR");
			FTPFile[] files = ftpClient.listFiles(ftpPath);

			for (FTPFile file : files) {

				System.out.println(file.getName());

				// PR_3000051882_20191203-131651-700.xml
				// APPROACH #1: using retrieveFile(String, OutputStream)
//            String remoteFile1 = "/VMS/PR/"+file.getName();
				String remoteFile1 = ftpPath + file.getName();

				File downloadFile1 = new File(
						System.getProperty("user.home") + "\\Documents\\SRL\\input\\" + file.getName());
				OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
				boolean success = ftpClient.retrieveFile(remoteFile1, outputStream1);
				outputStream1.close();

				if (success) {
					System.out.println("File #1 has been downloaded successfully.");
				}

				// iterates over the files and prints details for each
				count++;

				System.out.println("file read from FTP for  " + ftpPath + " ::  " + count);

			}

		
				ftpClient.logout();
				ftpClient.disconnect();
			


		return "pocess is all file is done ";

	}
	
	public boolean uploadFTP(String ftpPath, String fileName, String inputFilePath) {

		// String server = "192.168.0.51";
//	
//        int port = 21;
//        String user = "xiftp";
//        String pass = "mail#236";
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(server, port);

			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			// lists files and directories in the current working directory
//        FTPFile[] files = ftpClient.listFiles("/VMS/PR");
			// APPROACH #1: uploads first file using an InputStream
			File firstLocalFile = new File(inputFilePath + fileName + ".xml");// local system file path with file name

			String firstRemoteFile = ftpPath + fileName + ".xml";// remote file path
			InputStream inputStream = new FileInputStream(firstLocalFile);

			System.out.println("Start uploading first file");
			boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
			inputStream.close();

			if (done) {
				System.out.println("The first file is uploaded successfully.");
				return true;

			} else {
				System.out.println("The first file is uploaded unsuccessfully.");
				return false;
			}

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return false;

	}

	public boolean deleteFileFromFTP(String ftpPath, String fileName) {

//		String server = "192.168.0.51";
//	
//        int port = 21;
//        String user = "xiftp";
//        String pass = "mail#236";

		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(server, port);

			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			// Delete file

			boolean exist = ftpClient.deleteFile(ftpPath + fileName + ".xml");// file name with path of ftp server

			// Notify user for deletion
			if (exist) {
				System.out.println("File '" + fileName + ".xml" + "' deleted...");
			}
			// Notify user that file doesn't exist
			else
				System.out.println("File '" + fileName + ".xml" + "' doesn't exist...");

//     ftpClient.logout();
//     ftpClient.disconnect();

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;

		} finally {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return true;

	}

}

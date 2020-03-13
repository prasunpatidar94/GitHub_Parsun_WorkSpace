package com.pp.iteanz.xmlParse;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReturnName {
	
	
    public  static List<String> ReturnData(String FOLDER_PATH) {
    	
    	
    	Path folderPath = Paths.get(FOLDER_PATH);

        // prepare a data structure for a file's name and content
        List<String> filesName = new ArrayList<String>();

        // retrieve a list of the files in the folder
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folderPath)) {
        //	System.out.println(directoryStream.toString());
            for (Path path : directoryStream) {
                fileNames.add(path.toString());
            }
        } catch (IOException ex) {
            System.err.println("Error reading files");
            ex.printStackTrace();
        }

        // go through the list of files
        for (String file : fileNames) {
            // put the file's name and its content into the data structure
            // List<String> lines = Files.readAllLines(folderPath.resolve(file));
			filesName.add(file);
        }

//        // finally, print everything
//        filesName.forEach((String fileName) -> {
//        	return fileName;
//          
//            
//        });
//    
//    	
    	
    	
    	
    	return fileNames;
    }

}

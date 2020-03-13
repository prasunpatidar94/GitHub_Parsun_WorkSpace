package com.pp.iteanz.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class UtilityServices {

	@SuppressWarnings("resource")
	public Boolean cutAndPast(String input, String output) {

		boolean deleted = false;
		FileChannel src = null;
		FileChannel dest = null;

		try {
			src = new FileInputStream(input).getChannel();
			dest = new FileOutputStream(output).getChannel();
			long a = dest.transferFrom(src, 0, src.size());
			System.out.println(a);

			src.close();
			dest.close();
			// deleted = new File(input).delete();
			File file = new File(input);

			System.out.println(file.getAbsolutePath());
			if (file.delete()) {
				System.out.println("File deleted successfully");
				deleted = true;
			} else {
				System.out.println("Failed to delete the file");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				src.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				dest.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (deleted) {
			return true;

		} else {
			return false;

		}

	}

}

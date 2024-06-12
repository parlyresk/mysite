package com.poscodx.mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	private static String SAVE_PATH = "/mysite-files";
	private static String URL_PATH = "/upload-images";

	public String restore(MultipartFile file) {
		String url = null;

		try {
			File uploadDirectory = new File(SAVE_PATH);
			
			System.out.println("restore come");
			if (!uploadDirectory.exists()) {
				uploadDirectory.mkdirs();
				System.out.println("mkdirs come");
			}

			if (file.isEmpty()) {
				return url;
			}

			String originFilename = file.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf(".") + 1);
			String saveFilename = generateSaveFilename(extName);
			Long fileSize = file.getSize();

			System.out.println("#########" + originFilename);
			System.out.println("#########" + saveFilename);
			System.out.println("#########" + fileSize);

			byte[] data = file.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFilename);
			os.write(data);
			os.close();

			url = URL_PATH + "/" + saveFilename;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return url;
	}

	private String generateSaveFilename(String extName) {
		String filename = "";

		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH) + 1;
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		return filename;
	}

}

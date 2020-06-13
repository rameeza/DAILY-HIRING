package org.dailyhiring.controller;


import org.dailyhiring.entity.DBFile;
import org.dailyhiring.service.DBFileStorageService;
//import com.example.filedemo.model.DBFile;
//import com.example.filedemo.payload.UploadFileResponse;
//import com.example.filedemo.service.DBFileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.stereotype.Controller;

@Controller
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private DBFileStorageService dbFileStorageService;

	@PostMapping("/uploadFile")
	public String uploadFile(@RequestParam("file") MultipartFile file, String email) {
		DBFile dbFile;
		try {
			dbFile = dbFileStorageService.storeFile(file, email);
		} catch (Exception e) {
			System.out.println("File upload failed");
			logger.error("File upload failed");
			e.printStackTrace();
		}

		//String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				//.path(dbFile.getId()).toUriString();

		return "ok";
	}

}

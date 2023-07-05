package com.main.miniproject.product.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.entity.ProductImage;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class FileService {


		//파일을 만드는 동작
		public String uploadFile(String uploadPath, String oriFileName, byte[] fileData) throws IOException {
			//임의의 UUID를 자동으로 만들어준다.
			UUID uuid = UUID.randomUUID();
			//file의 확장자만 가져오기 위함.
			String extension = oriFileName.substring(oriFileName.lastIndexOf("."));
			//uuid는 문자열이 아니여서 toString을 꼭 해줘야 함. 거기에 확장자만 붙여줌.
			String savedFileName = uuid.toString() + extension;
			//실제 파일 경로 -> D://......./ uuid로 만든 파일 이름.extension으로 저장.
			String fileUploadUrl = uploadPath + "/" + savedFileName;
			FileOutputStream fos = new FileOutputStream(fileUploadUrl);
			fos.write(fileData);
			fos.close();

			return savedFileName;
		}

	//파일 지우는 동작
	public void deleteFile(String filePath) {
		File deleteFile = new File(filePath);

		if(deleteFile.exists()) {
			deleteFile.delete();
			log.info("파일을 삭제했습니다.");
		}else {
			log.info("파일이 존재하지 않습니다.");
		}

	}

}

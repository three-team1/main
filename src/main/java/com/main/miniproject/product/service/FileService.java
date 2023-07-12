package com.main.miniproject.product.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.entity.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class FileService {


	//파일을 만드는 동작
		public List<ProductImage> saveFiles(Product product, MultipartFile[] files) {
			List<ProductImage> productImages = new ArrayList<>();

			//파일이 첨부되지 않은 경우 처리
//			if(files.length == 0) {
//				log.info("상품 이미지를 첨부해주세요.");
//
//				return productImages;
//			}


			for(MultipartFile file : files) {

				if(!file.isEmpty()) {

					String fileOrigin = file.getOriginalFilename();
					String fileExt = fileOrigin.substring(fileOrigin.lastIndexOf("."));
					String fileName = UUID.randomUUID().toString() + fileExt;
					String filePath = "C:/miniproject/images/" + fileName;

					try {

						file.transferTo(new File(filePath));

						ProductImage productImage = ProductImage.builder()
								.product(product)
								.name(fileName)
								.originName(fileOrigin)
								.url(filePath)
								.build();

						productImages.add(productImage);

					}catch (IOException ie) {
						log.error("이미지 파일 저장에 실패했습니다.");
						ie.getStackTrace();
					}

				}

			}


			return productImages;
		}




	//파일 지우는 동작
	public void deleteFile(String filePath) {
			System.out.println(filePath);
		File deleteFile = new File(filePath);

		if(deleteFile.exists()) {
			if (deleteFile.delete()) {
				log.info("파일을 삭제했습니다.");
			} else {
				log.info("파일 삭제에 실패했습니다.");
			}
		}else {
			log.info("파일이 존재하지 않습니다.");
		}

	}



}

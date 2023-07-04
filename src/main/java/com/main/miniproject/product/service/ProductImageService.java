package com.main.miniproject.product.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.entity.ProductImage;
import com.main.miniproject.product.repository.ProductImageRepository;

@Service
@Transactional
public class ProductImageService {

    @Value(value = "${itemImgLocation}")
    private String itemImgLocation;
    private final ProductImageRepository productImageRepository;
    private final FileService fileService;

    @Autowired
    public ProductImageService(ProductImageRepository productImageRepository, FileService fileService) {
        this.productImageRepository = productImageRepository;
        this.fileService = fileService;
    }

    public List<ProductImage> getAllProductImages() {

        return productImageRepository.findAll();
    }

    public List<ProductImage> getProductImagesByProduct(Product product) {

    	return productImageRepository.findByProduct(product);
    }
    public void saveProductImg(ProductImage productImage, MultipartFile productImgFile) throws IOException {
        String oriImgName = productImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //원래 경로가 값이 비어있는지 타임리프 유틸을 이용해서 확인.
        if(!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, productImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }
        //아이템 이미지 저장
        productImage.updateProductImage(oriImgName, imgName, imgUrl);
        productImageRepository.save(productImage);
    }

    //이미지 업데이트(수정)
    public void updateItemImg(Long productImgIds, MultipartFile productImgFile) throws IOException {
        if(!productImgFile.isEmpty()) {
            ProductImage productImage = productImageRepository.findById(productImgIds).orElseThrow(EntityNotFoundException::new);

            if(!StringUtils.isEmpty(productImage.getName())) {
                //itemImgLocation은 application properties 에서 설정한 경로.
                //경로폴더에 있는 itemImg를 삭제
                fileService.deleteFile(itemImgLocation + "/" + productImage.getName());
            }
            //새로 수정 등록하기
            String oriName = productImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriName, productImgFile.getBytes());
            //이 프로젝트 안에서의 경로
            String imgUrl = "/images/item/" + imgName;

            productImage.updateProductImage(oriName, imgName, imgUrl);


        }


    }

    public void deleteItemImg(Long productImgIds){
        productImageRepository.deleteById(productImgIds);
    }


}

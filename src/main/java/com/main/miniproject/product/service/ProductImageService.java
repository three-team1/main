package com.main.miniproject.product.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import com.main.miniproject.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final ProductImageRepository productImageRepository;

    private final ProductRepository productRepository;
    private final FileService fileService;

    @Autowired
    public ProductImageService(ProductImageRepository productImageRepository, ProductRepository productRepository, FileService fileService) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
        this.fileService = fileService;
    }


    public List<ProductImage> getProductImagesByProduct(Product product) {

    	return productImageRepository.findByProduct(product);
    }
    public void saveProductImg(ProductImage productImage) {
        productImageRepository.save(productImage);

    }

    //이미지 업데이트(수정)
    public ProductImage updateItemImg(Long productImgIds, MultipartFile files) throws IOException {

        ProductImage  productImage = productImageRepository.findById(productImgIds).orElseThrow(EntityNotFoundException::new);

        System.out.println("================1111111" + productImage);

//        if (!files.isEmpty()) {
////            productImage = productImageRepository.findById(productImgIds).orElseThrow(EntityNotFoundException::new);
//
//            //기존 파일 삭제
//            if (!StringUtils.isEmpty(productImage.getName())) {
//                //itemImgLocation은 application properties 에서 설정한 경로.
//                //경로폴더에 있는 itemImg를 삭제
//                System.out.println(productImage.getName());
//                fileService.deleteFile("C:/miniproject/images/" + productImage.getName());
//            }
//
//            productImageRepository.deleteById(productImgIds);

            //새로 수정 등록하기
            String fileOrigin = files.getOriginalFilename();
            String fileExt = fileOrigin.substring(fileOrigin.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + fileExt;
            String imgUrl = "/images/item/" + fileName;

            productImage.setOriginName(fileOrigin);
            productImage.setName(fileName);
            productImage.setUrl(imgUrl);

//        }
        System.out.println("=====================22222222222" + productImage);
        return productImageRepository.save(productImage);


    }

    public void deleteItemImg(Long id){

        //productService 의 delete 메소드 처럼  for문으로 돌리면 전체 다 삭제된다.
        // 해당 이미지 id 받아와서 하나만 삭제될 수 있게

        List<ProductImage> productImages = productImageRepository.findByProduct(getProductById(id));

        for(ProductImage productImage : productImages){

            try {
                // DB에서 삭제되면 로컬저장폴더에서도 삭제
                fileService.deleteFile("C:/miniproject/images/" + productImage.getName());


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("삭제할 이미지가 존재하지 않습니다.");
            }

            productImageRepository.deleteById(id);

        }




    }

    private Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }


}

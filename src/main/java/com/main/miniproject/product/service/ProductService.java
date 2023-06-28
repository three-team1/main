package com.main.miniproject.product.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.main.miniproject.product.dto.ProductDTO;
import com.main.miniproject.product.dto.ProductFormDto;
import com.main.miniproject.product.dto.ProductImgDto;
import com.main.miniproject.product.dto.ProductSearchDto;
import com.main.miniproject.product.entity.ProductImage;
import com.main.miniproject.product.repository.ProductImageRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.repository.ProductRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Log4j2
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductImageRepository productImageRepository;
	private final ProductImageService productImageService;


	@Autowired
	public ProductService(ProductRepository productRepository, ProductImageRepository productImageRepository, ProductImageService productImageService) {
		this.productRepository = productRepository;
		this.productImageRepository=productImageRepository;
		this.productImageService=productImageService;
	}

	public List<Product> getAllProducts() {

		return productRepository.findAll();
	}

	public Product getProductById (Long productId) {

		return productRepository.findById(productId).get();
	}

	//itemFormDto 값을 넘겨받고, multipart 형식으로 되어 있는 리스트를 받아온다.
	public Long saveProduct(ProductFormDto productFormDto, List<MultipartFile> itemImgFileList) throws IOException {

		Product product = productFormDto.createProduct();		//dto를 entity로 변환. create에서 mapper로 바꿨으니까
		productRepository.save(product);

		//그림 저장하기
		for (int i = 0; i < itemImgFileList.size(); i++) {
			ProductImage productImage = new ProductImage();
			productImage.setProduct(product);		//등록하는 이미지와 아이템 순서에 맞게 연결

			productImageService.saveProductImg(productImage, itemImgFileList.get(i));
		}
		return product.getId();
	}


	//상품 상세정보 조회(상품 등록 폼에서)
	public ProductFormDto getProductDetail(Long itemId) {
		Product product = productRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

		//itemImg DB에서 List 형태로 데이터 가져오기
		//productId로 찾아야 해당 상품Id에 해당하는 모든 이미지들 찾아옴. 그래서 findByProduct로 찾아와야 함.
		List<ProductImage> productImageList = productImageRepository.findByProduct(product);

		//entity를 dto로 넘겨주기 위한 List 만들기
		List<ProductImgDto> productImgDtoList = new ArrayList<>();

		//itemImgList들을 가져와서 ItemImg 형태로 만들것이다.
		for (ProductImage productImage : productImageList) {
			//entity 받아서 dto로 변환
			ProductImgDto productImgDto = ProductImgDto.of(productImage);
			productImgDtoList.add(productImgDto);
		}

		//리턴 타입이 Optional<Product>이다. Optional로 받거나 EntityNotFoundException로 예외처리해줘야 함.(엔티티가 없을 수 있기 때문)

		//itemFormDto에 item 정보와 itemImgDtoList(itemImg)정보가 모두 넣어준다.
		ProductFormDto productFormDto = ProductFormDto.of(product);
		productFormDto.setProductImgDtoList(productImgDtoList);

		return productFormDto;
	}

	//상품 업데이트(수정)
	public Long updateProduct(ProductFormDto productFormDto, List<MultipartFile> itemImgFileList) throws IOException {
		Product product = productRepository.findById(productFormDto.getId()).orElseThrow(EntityNotFoundException::new);

		product.updateProduct(productFormDto);

		//********** dto에서 받아온 것을 db에 저장해야 함.
		productRepository.save(product);

		List<ProductImage> productImgIds =productImageRepository.findByProduct(product);

		productImageRepository.saveAll(productImgIds);

		//업데이트를 위해서는 itemImgIds와 itemImgFileList의 인덱스를 알아야 함.
		log.info("아이디 번호값 : " + itemImgFileList.size());


		for (int i = 0; i < itemImgFileList.size(); i++) {
//			log.info("아이디 번호값 : " + productImgIds.get(i));
			log.info("아이디 번호값 : " + itemImgFileList.get(i));

			productImageService.updateItemImg(productImgIds.get(i).getId(), itemImgFileList.get(i));
			//itemImgFileList 를 못 불러옴..

		}



		//수정한 정보(상품)가 무엇인지 알려줌.
		return product.getId();
	}



	//상품 삭제하기

	 public void deleteProduct(Product product){

		 productRepository.delete(product);
	 }




	//ItemRepositoryCustom, ItemRepositoryCustomImpl에서 작성한 쿼리문 출력하기
	public Page<Product> getAdminProductPage(ProductSearchDto productSearchDto, Pageable pageable){
		return productRepository.getAdminProductPage(productSearchDto, pageable);
	}

	public List<ProductDTO> searchProducts(String searchKeyword) {

		List<Product> productList = productRepository.findByProductTitleContaining(searchKeyword);

		List<ProductDTO> productDTOList = new ArrayList<>();

		for(Product product : productList) {

			ProductDTO productDTO = ProductDTO.builder()
					.productId(product.getId())
					.productTitle(product.getProductTitle())
					.productContent(product.getProductContent())
					.productPrice(product.getProductPrice())
					.productQuantity(product.getProductQuantity())
					.productType(product.getProductType())
					.build();

			productDTOList.add(productDTO);

		}


		return productDTOList;
	}

}

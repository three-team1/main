package com.main.miniproject.product.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Subgraph;
import javax.persistence.criteria.*;

import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.product.entity.ProductSellStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.main.miniproject.product.dto.ProductDTO;
import com.main.miniproject.product.dto.ProductFormDto;
import com.main.miniproject.product.dto.ProductImgDto;
import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.entity.ProductImage;
import com.main.miniproject.product.repository.ProductImageRepository;
import com.main.miniproject.product.repository.ProductRepository;

import lombok.extern.log4j.Log4j2;
import org.thymeleaf.util.StringUtils;

@Service
@Log4j2
@Transactional
public class ProductService {


	private final ProductRepository productRepository;
	private final ProductImageRepository productImageRepository;
	private final ProductImageService productImageService;

	private final FileService fileService;

	@Autowired
	public ProductService(ProductRepository productRepository, ProductImageRepository productImageRepository, ProductImageService productImageService, FileService fileService) {
		this.productRepository = productRepository;
		this.productImageRepository=productImageRepository;
		this.productImageService=productImageService;
		this.fileService = fileService;
	}

	public List<Product> getAllProducts() {

		return productRepository.findAll();
	}

	public Product getProductById (Long productId) {

		return productRepository.findById(productId).get();

	}

	//itemFormDto 값을 넘겨받고, multipart 형식으로 되어 있는 리스트를 받아온다.
	public Product saveProduct(Product product) {

		return productRepository.save(product);

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
//	public Long updateProduct(ProductFormDto productFormDto, MultipartFile[] files) throws IOException {
//		Product product = productRepository.findById(productFormDto.getId()).orElseThrow(EntityNotFoundException::new);
//
//		product.updateProduct(productFormDto);
//
//
//		System.out.println("111111111111111111" + product);
//
//		//********** dto에서 받아온 것을 db에 저장해야 함.
//		productRepository.save(product);
//
//		List<ProductImage> productImages =productImageRepository.findByProduct(product);
//		System.out.println("2222222222222222222" + productImages);
//
//
//		//이미지 업데이트
//		for (int i = 0; i < productImages.size(); i++) {
////			if(i < files.length){
////				MultipartFile file = files[i];
////				ProductImage productImage = productImages.get(i);
//				productImageService.updateItemImg(productImages.get(i).getId(),files[i] );
//
//
////				updatedProductImages.add(productImage);
//
//				System.out.println("33333333333333333333333" + files[i].getOriginalFilename());
////			}
//
//		}
//
//		productImageRepository.saveAll(productImages);
//
//		//수정한 정보(상품)가 무엇인지 알려줌.
//		return product.getId();
//	}


	public Long updateProduct(ProductFormDto productFormDto){
		Product product = productRepository.findById(productFormDto.getId()).orElseThrow(EntityNotFoundException::new);
		product.updateProduct(productFormDto);

		productRepository.save(product);
		System.out.println("productService==============================="+product);
		//수정한 정보(상품)가 무엇인지 알려줌
		return product.getId();
	}



	//상품 삭제하기

	 public void deleteProduct(Long id){


		List<ProductImage> productImages = productImageRepository.findByProduct(getProductById(id));

		for(ProductImage productImage : productImages){

			System.out.println(productImage);
			try{
				// DB에서 삭제되면 로컬저장폴더에서도 삭제
				fileService.deleteFile("C:/miniproject/images/" + productImage.getName());
//
			}catch (Exception e){
				e.printStackTrace();
				System.out.println("삭제할 이미지가 존재하지 않습니다." + id);
			}

			productImageRepository.delete(productImage);

		}

		productRepository.deleteById(id);


	 }



	//페이징 처리
	//page는 현재페이지. 숫자는 현재 페이지에 나타낼 레코드의 갯수
	public Page<Product> getList(int page, String keyword){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.asc("productQuantity"));
		Pageable pageable = PageRequest.of(page, 1, Sort.by(sorts));
		Specification<Product> productSpecification = search(keyword);

		return productRepository.findAll(productSpecification, pageable);
	}




	//키워드 검색
	private Specification<Product> search(String keyword) {
		return new Specification<Product>() {

			@Override
			public Predicate toPredicate(Root<Product> productRoot, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				query.distinct(true);   //중복을 제거

				return criteriaBuilder.or(
						criteriaBuilder.like(productRoot.get("productTitle"), "%" + keyword + "%"),
						criteriaBuilder.like(productRoot.get("productType"), "%" + keyword + "%"));

			}
		};
	}

			public List<ProductDTO> searchProducts(String searchKeyword) {

				List<Product> productList = productRepository.findByProductTitleContaining(searchKeyword);

				List<ProductDTO> productDTOList = new ArrayList<>();

				for (Product product : productList) {

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

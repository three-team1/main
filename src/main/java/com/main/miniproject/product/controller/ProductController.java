package com.main.miniproject.product.controller;


import com.main.miniproject.product.dto.ProductFormDto;
import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.entity.ProductImage;
import com.main.miniproject.product.entity.ProductSellStatus;
import com.main.miniproject.product.service.FileService;
import com.main.miniproject.product.service.ProductImageService;
import com.main.miniproject.product.service.ProductService;
import com.main.miniproject.product.service.RealTimeSearchService;
import com.main.miniproject.user.service.UserDetail;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;


@Controller
@RequiredArgsConstructor
@Log4j2
public class ProductController {

	@Autowired
	private final RealTimeSearchService realTimeSearchService;

	@Autowired
	private final ProductService productService;

	@Autowired
	private final ProductImageService productImageService;

	@Autowired
	private final FileService fileService;



	@GetMapping("/admin/item/new")
	public String itemForm(Model model) {
		//현재 아무값도 없기 때문에 빈 객체라도 들고 가야 함.
		//빈 dto를 날리는 것. itemFormDto를 model 객체에 담아서 뷰로 전달.
		model.addAttribute("itemFormDto", new ProductFormDto());
		return "item/itemForm";
	}

	//상품 등록
	@PostMapping("/admin/item/new")
	public String itemNew(Product product, @RequestParam("itemImgFile") MultipartFile[] multipartFiles)  {
		System.out.println(product);

		System.out.println(multipartFiles);
		productService.saveProduct(product);

		List<ProductImage> productImages = fileService.saveFiles(product, multipartFiles);

		for(ProductImage productImage : productImages){

			productImageService.saveProductImg(productImage);
		}

		return "redirect:/admin/items";
	}


	//상품 상세정보 조회((상품 등록 폼에서)
	@GetMapping("/admin/item/{itemId}")
	//path(경로) 상에 있는 variable 설정
	public String itemDetail(@PathVariable("itemId") Long itemId, Model model) {

		try {
			//itemService에 있는 getItemDetail 메소드
			ProductFormDto productFormDto = productService.getProductDetail(itemId);

			model.addAttribute("itemFormDto", productFormDto);
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
			//오류난 후 itemForm으로 다시 돌아가기 위함.
			//현재 아무값도 없기 때문에 빈 객체라도 들고 가야 함.
			//빈 dto를 날리는 것. itemFormDto를 model 객체에 담아서 뷰로 전달.
			model.addAttribute("itemFormDto", new ProductFormDto());
			return "item/itemForm";
		}

		return "/item/itemForm";
	}

	//상품 정보 수정
//	@PostMapping("/admin/item/{itemId}")
//	public String itemUpdate(@Valid ProductFormDto productFormDto, Product product, @PathVariable("itemId") Long id,
//							 Model model, @RequestParam("itemImgFile") MultipartFile[] files,
//							 @RequestParam("method") String method) {
//
//
//		//itemService에 있는 updateItem 메소드 실행.
//		//진행 중 오류 발생 시 item/itemForm으로 되돌아감.
//		try {
//			List<ProductImage> productImages = fileService.saveFiles(product, files);
//
//			System.out.println("=======================여기영기"+product);
//
//			for(ProductImage productImage : productImages){
//				productImageService.saveProductImg(productImage);
//				System.out.println("=======================여기" + productImage);
//			}
//
//			productService.updateProduct(productFormDto, files);
//
//
//		} catch (IOException e) {
//			model.addAttribute("errorMessage", "상품 수정 중에 오류가 발생했습니다.");
//			return "/item/itemForm";
//		}
//
//		return "redirect:/admin/item/{itemId}";
//	}

	@PostMapping("/admin/item/{itemId}")
	public String itemUpdate(@Valid ProductFormDto productFormDto,Model model, @PathVariable("itemId") Long id,
							 MultipartFile files){
		try{
			productService.updateProduct(productFormDto);
//			productImageService.updateItemImg(id, files);
		}catch (Exception e){
			model.addAttribute("errorMessage", "상품 정보 수정 중 오류가 발생했습니다.");
			return "/item/itemForm";

		}

		return "redirect:/admin/item/{itemId}";
	}




	//상품 이미지 삭제
	@PostMapping("/admin/itemImageDel")
	public ResponseEntity<String> deleteImage(@RequestBody Long id){
//		try{
//			productImageService.deleteItemImg(id);
//
//			return ResponseEntity.ok().build();
//		}catch (Exception e){
//			return ResponseEntity.badRequest().body("이미지 삭제 중 오류가 발생했습니다.");
//		}
		productImageService.deleteItemImg(id);

		return ResponseEntity.ok("이미지가 삭제되었습니다.");

	}




	//상품관리 탭에서 상품목록 가져오기( +페이징 기능, 검색 기능 )
	@GetMapping("/admin/items")	//페이지 정보 없는 것, 있는 것 둘 다 처리 가능.
	public String itemList(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
						   @RequestParam(value = "keyword", defaultValue = "") String keyword,
						   Product product) {

		Page<Product> productPage = productService.getList(page, keyword);

		model.addAttribute("productPage", productPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("productSearch", product);

		return "item/itemList";
	}

//	@GetMapping("/admin/items/{page}")	//페이지 정보 없는 것, 있는 것 둘 다 처리 가능.
//	public String itemListWithPage(Model model, @PathVariable("page") int page,
//						   @RequestParam(value = "keyword", defaultValue = "") String keyword,
//						   @RequestParam(value = "category1", defaultValue = "") String category,
//						   Product product) {
//
//		Page<Product> productPage = productService.getList(page, keyword);
//
//		model.addAttribute("productPage", productPage);
//		model.addAttribute("keyword", keyword);
//		model.addAttribute("category", category);
//		model.addAttribute("productSearch", product);
//
//		return "item/itemList";
//	}


	//상품 삭제하기
//	@PostMapping("/admin/items")
//	public String deleteProduct(Long id){
//
//		productService.deleteProduct(id);
//
//		return "redirect:/admin/items";
//	}

	//상품 삭제하기
	@PostMapping("/admin/items")
	public ResponseEntity<String> deleteProduct(@RequestBody List<Long> idList) {
		// 리스트 데이터 처리 로직
		for (Long s : idList) {

			productService.deleteProduct(s);
		}
		return ResponseEntity.ok("Data processed successfully.");
	}


	@GetMapping("/product/productList")
	public String searchList(@AuthenticationPrincipal UserDetail userDetail,Model model) {

		realTimeSearchService.getTop10Searches();
		
		model.addAttribute("userDetail",userDetail);

		return "/product/productList";

	}




}


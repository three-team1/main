package com.main.miniproject.product.repository;

//쿼리 작성 시 QItem 동일하게 계속 작성해야 하므로 static으로 저장해 놓고 item만 작성할 수 있도록 설정.
//아래에서 쿼리 작성 시 item을 클릭하고 ctrl + 1(숫자) 눌러서 QItem static으로 설정하기

import com.main.miniproject.product.dto.ProductSearchDto;
import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.entity.ProductSellStatus;
import com.main.miniproject.product.entity.QProduct;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

	// querydsl 사용하기 위한 준비
	private JPAQueryFactory queryFactory;



	// 생성자 만들기
	// query를 던질 준비 완료
	public ProductRepositoryCustomImpl(EntityManager em) {
		queryFactory = new JPAQueryFactory(em);
	}

	
	
	@Override
	public Page<Product> getAdminProductPage(ProductSearchDto productSearchDto, Pageable pageable) {

		// selectFrom은 select와 from을 한 번에 같이 불러올 수 있음.
		List<Product> content = queryFactory.selectFrom(QProduct.product)
				// where절에서 , 로 이어진 부분은 and의 의미.
				// 조건을 여러개를 걸 수 있다. 그러나 모든 조건을 써야하는 것은 아니고 null값으로 생략도 가능하다.
				// 즉, 갖고 있는 값에 따라 조건을 다양하게 걸 수 있다. (동적 쿼리. 쿼리dsl)
				.where(searchSellStatusEq(productSearchDto.getSearchSellStatus()),
						searchByLike(productSearchDto.getSearchBy(), productSearchDto.getSearchQuery()))
				.orderBy(QProduct.product.productQuantity.asc())
				.offset(pageable.getOffset()) // 페이지 시작할 위치.
				.limit(pageable.getPageSize()) // 페이지 몇 개.
				.fetch();

		// Wildcard.count는 count(*)와 동일. 갯수 세는 집합함수
		// where절의 조건을 부합하는 데이터의 전체 갯수를 센다.
		long total = queryFactory.select(Wildcard.count).from(QProduct.product)
				.where(searchSellStatusEq(productSearchDto.getSearchSellStatus()),
						searchByLike(productSearchDto.getSearchBy(), productSearchDto.getSearchQuery()))
				.fetchOne();

		//Page<Item>형태로 리턴해야 함. 
		return new PageImpl<>(content, pageable, total);
	}

	
	
	// 쿼리 dsl의 메소드
	// null이거나 null이 아닌 값을 같은지 확인해서 boolean 비교
	private BooleanExpression searchSellStatusEq(ProductSellStatus searchSellStatus) {
		return searchSellStatus == null ? null : QProduct.product.productSellStatus.eq(searchSellStatus);
	}

	
	
//	private BooleanExpression regDtsAfter(String searchDateType) {
//
//		LocalDateTime dateTime = LocalDateTime.now();
//
//		if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
//			return null;
//		} else if (StringUtils.equals("1d", searchDateType)) {
//			// 하루 전부터 지금까지의 데이터 검색
//			dateTime = dateTime.minusDays(1);
//		} else if (StringUtils.equals("1w", searchDateType)) {
//			// 일주 전부터 지금까지의 데이터 검색
//			dateTime = dateTime.minusWeeks(1);
//		} else if (StringUtils.equals("1m", searchDateType)) {
//			// 한 달 전부터 지금까지의 데이터 검색
//			dateTime = dateTime.minusMonths(1);
//		} else if (StringUtils.equals("6m", searchDateType)) {
//			// 6개월 전부터 지금까지의 데이터 검색
//			dateTime = dateTime.minusMonths(6);
//		}
//		// item의 등록시간을 리턴
//		return item.regTime.after(dateTime);
//	}

	
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {

		// 상품명으로 검색
		if (StringUtils.equals("itemNm", searchBy)) {
			return QProduct.product.productTitle.like("%" + searchQuery + "%");
		}

		return null;

	}

}

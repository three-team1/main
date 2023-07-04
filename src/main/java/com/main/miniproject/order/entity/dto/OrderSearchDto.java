package com.main.miniproject.order.entity.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class OrderSearchDto {

    //날짜로 검색
    private LocalDateTime searchDate;

    //검색어 카테고리
    private String searchBy;
    //검색어 검색
    private String keyword = "";


}

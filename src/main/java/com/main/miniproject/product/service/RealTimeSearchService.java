package com.main.miniproject.product.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.miniproject.product.entity.RealTimeSearch;
import com.main.miniproject.product.repository.RealTimeSearchRepository;

@Service
public class RealTimeSearchService {

    @Autowired
    private RealTimeSearchRepository realTimeSearchRepository;

    public void saveSearchKeyword(String searchKeyword) {

        // 검색어가 비어있거나 공백만 있는 경우는 저장하지 않음
        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            return;
        }

        RealTimeSearch realTimeSearchKeyword =  realTimeSearchRepository.findBysearchKeyword(searchKeyword);

        if(realTimeSearchKeyword == null) {

            realTimeSearchKeyword = new RealTimeSearch();

            realTimeSearchKeyword.setSearchKeyword(searchKeyword);

            realTimeSearchRepository.save(realTimeSearchKeyword);

        } else {

            realTimeSearchKeyword.setSearchCnt(realTimeSearchKeyword.getSearchCnt() + 1);


            realTimeSearchRepository.save(realTimeSearchKeyword);
        }
    }


    public List<RealTimeSearch> searchList () {

        return realTimeSearchRepository.findAll();
    }


    public List<RealTimeSearch> getTop10Searches() {

        return realTimeSearchRepository.findTop10ByOrderBySearchCntDesc();
    }



}
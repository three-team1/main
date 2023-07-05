package com.main.miniproject.review.service;

import com.main.miniproject.review.entity.Review;
import com.main.miniproject.review.entity.ReviewImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewFileService {
    List<ReviewImage> saveFiles(Review review, MultipartFile[] files);
}
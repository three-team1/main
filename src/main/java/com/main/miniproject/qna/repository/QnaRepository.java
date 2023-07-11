package com.main.miniproject.qna.repository;

import com.main.miniproject.qna.entity.QNA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaRepository extends JpaRepository<QNA, Long> {
    Page<QNA> findByQnaTitleContainingOrQnaContentContaining(String keyword, String keyword1, Pageable pageable);
}

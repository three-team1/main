package com.main.miniproject.qna.repository;

import com.main.miniproject.qna.entity.QNA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaRepository extends JpaRepository<QNA, Long> {

    Page<QNA> findByQnaTitleContaining(String keyword, Pageable pageable);

    Page<QNA> findByQnaContentContaining(String keyword, Pageable pageable);
}

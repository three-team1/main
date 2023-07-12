package com.main.miniproject.qna.service;

import com.main.miniproject.comment.entity.Comment;
import com.main.miniproject.comment.repository.CommentRepository;
import com.main.miniproject.qna.entity.QNA;

import com.main.miniproject.qna.repository.QnaRepository;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnaService {
    @Autowired
    private final QnaRepository qnaRepository;

    @Autowired
    private CommentRepository commentRepository;


    public void qna(QNA qna) {

        User user = getCurrentUser();

        qna.setUser(user);

        qnaRepository.save(qna);
    }

    public Page<QNA> getList(Pageable pageable) {
        Page<QNA> qnaList = qnaRepository.findAll(pageable);

        return qnaList;
    }


    public QNA getDetail(Long id) {
        //Optional은 값의 존재 여부를 나타내는 컨테이너 클래스로
        //값이 존재할 경우에는 Optional에 해당값을 감싸고
        //값이 존재하지 않을 경우에는 빈 Optional을 반환한다.
        Optional<QNA> optionalQNA = qnaRepository.findById(id);

        if (optionalQNA.isPresent()) {
            QNA qna = optionalQNA.get();
            return qna;
        } else {
            return null;
        }
    }


    public QNA update(QNA qna) {
        qnaRepository.save(qna);
        //레파지토리에 담았다가
        //다시 도로 DTO형태로 꺼내서 화면에 던저줘야함

        return qna;
    }

    @Transactional
    public void delete(Long id) {
        List<Comment> commentList = commentRepository.findByBoardIdAndBoardType(id, "qna");

        for (Comment comment : commentList) {

            commentRepository.delete(comment);
        }

        qnaRepository.deleteById(id);
    }


    public void updateCnt(QNA qna) {
        qna.setQnaCnt(qna.getQnaCnt() + 1);
        qnaRepository.save(qna);
    }

    public User getCurrentUser() {                                            // 사용자 인증정보 반환

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        return userDetail.getUser();

    }

    public Page<QNA> paging(Pageable pageable) {
        return qnaRepository.findAll(pageable);
    }


    public Page<QNA> searchQNAByTitle(Pageable pageable, String keyword) {
        return qnaRepository.findByQnaTitleContaining(keyword, pageable);
    }


    public Page<QNA> searchQNAByQnaContent(Pageable pageable, String keyword) {
        return qnaRepository.findByQnaContentContaining(keyword, pageable);

    }

    public Page<QNA> searchQNAByQnaWriter(Pageable pageable, String keyword) {
        return qnaRepository.findByQnaWriterContaining(keyword, pageable);
    }
}

package com.main.miniproject.qna.service;

import com.main.miniproject.qna.entity.QNA;
import com.main.miniproject.qna.repository.QnaRepository;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;

    public void qna(QNA qna) {

        User user = getCurrentUser();

        qna.setUser(user);

        qnaRepository.save(qna);
    }

    public List<QNA> getList(Pageable pageable) {
        Page<QNA> qnaList = qnaRepository.findAll(pageable);
        List<QNA> qnaQNAList = new ArrayList<>();


        for (QNA qna : qnaList) {
            qnaQNAList.add(qna);
        }

        return qnaQNAList;
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


    public void delete(Long id) {
        qnaRepository.deleteById(id);
    }


    public Page<QNA> paging(Pageable pageable) {

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize(); //한 페이지에 보여줄 글 갯수

        Page<QNA> qnaPage =
                qnaRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        
        return qnaPage;
    }

    public void updateCnt(QNA qna) {
    }

    public User getCurrentUser() {											// 사용자 인증정보 반환

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        return userDetail.getUser();

    }
}

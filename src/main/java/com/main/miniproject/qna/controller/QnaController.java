package com.main.miniproject.qna.controller;

import com.main.miniproject.comment.entity.Comment;
import com.main.miniproject.comment.service.CommentService;
import com.main.miniproject.qna.entity.QNA;
import com.main.miniproject.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class QnaController {

   @Autowired
    private final QnaService qnaService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/qna/list")
    public String qnaList(Model model, @RequestParam(required = false) String keyword, @RequestParam(value = "page", defaultValue = "0") int currentPage) {
        int pageSize = 10; // 페이지당 게시글 수
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        Page<QNA> qnaPage;

        if(keyword != null) {
            qnaPage = qnaService.searchQNA(pageable, keyword);
        } else {
            qnaPage = qnaService.getList(pageable);
        }


        model.addAttribute("qnaList", qnaPage);

        // 페이징 정보
        int totalPages = qnaPage.getTotalPages(); // 총 페이지 수
        long totalItems = qnaPage.getTotalElements(); // 총 아이템 수


        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);

        // 페이지 목록
        int maxPageNumbers = 5; // 페이지 목록에 표시할 최대 페이지 수
        int startPage = Math.max(0, Math.min(currentPage - maxPageNumbers / 2, totalPages - maxPageNumbers)); // 시작 페이지 계산
        int endPage = Math.min(startPage + maxPageNumbers - 1, totalPages - 1); // 끝 페이지 계산

        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("pageNumbers", pageNumbers);

        return "/qna/qnaList";
    }





    @GetMapping("/qna/insert")
    public String qnaGetInsert() {
        return "/qna/qnaInsert";
    } //단순히 이동하는 것. 모델값 없음.

    @PostMapping("/qna/postInsert") //insert form의 액션값. insert form을 submit하면 이게 실행.
    public String qnaInsert(QNA qna) { //@ModelAttribute를 사용하면 HTTP(폼 등..)로부터 전달받은 데이터를 파라미터에 자동으로 매핑. => DTO타입의 qnaDTO객체에 모든 데이터가 담겨짐.
        qnaService.qna(qna);
        return "redirect:/qna/list";
    }

    //게시물 조회 증가
    @GetMapping("/updateBoardCnt/{id}")
    public String updateBoardCnt(@PathVariable Long id){
        QNA qna = qnaService.getDetail(id);
        qnaService.updateCnt(qna);

        return "redirect:/qna/list/" + id;
    }


    @GetMapping("/qna/list/{id}")
    public String qnaDetail(@PathVariable Long id, Model model) {
        QNA qna = qnaService.getDetail(id);

        List<Comment> commentList = commentService.commentList(id, "qna");

        model.addAttribute("qnaDetail", qna);
        model.addAttribute("comments",commentList);

        return "/qna/qnaDetail";
    }

    @GetMapping("/qna/list/update/{id}")
    public String qnaUpdateForm(@PathVariable Long id, Model model) {
        QNA qna = qnaService.getDetail(id);
        model.addAttribute("qnaUpdate", qna);

        return "/qna/qnaUpdate";
    }

    @PostMapping("/qna/list/update")
    public String qnaUpdate(QNA qna) {
        qnaService.update(qna);
        return "redirect:/qna/list/" + qna.getId();
    }

    @GetMapping("/qna/list/delete/{id}")
    public String qnaDelete(@PathVariable Long id) {
        qnaService.delete(id);

        return "redirect:/qna/list";
    }

}

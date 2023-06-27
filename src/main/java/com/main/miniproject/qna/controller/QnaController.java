package com.main.miniproject.qna.controller;

import com.main.miniproject.qna.entity.QNA;
import com.main.miniproject.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class QnaController {
    private final QnaService qnaService;

    @GetMapping("/qna/list") //a 태그는 get매핑. db에 값을 보내주는 거 없이 페이지로 이동하는 건 get.
    public String qnaList(Model model, @PageableDefault Pageable pageable) { //보여줘야하니까 Model값이 있어야 함.
        //DB에서 전체 게시글 데이터를 가져와서 qnaList.html에 보여준다
        List<QNA> qnaList = qnaService.getList(pageable);

        Page<QNA> qnaPage = qnaService.paging(pageable);

        int nowPage = qnaPage.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, qnaPage.getTotalPages());

        model.addAttribute("qnaList", qnaPage);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

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

    @GetMapping("/qna/list/{id}")
    public String qnaDetail(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse response) {
        QNA qna = qnaService.getDetail(id);
        qnaService.updateCnt(qna);
        model.addAttribute("qnaDetail", qna);
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

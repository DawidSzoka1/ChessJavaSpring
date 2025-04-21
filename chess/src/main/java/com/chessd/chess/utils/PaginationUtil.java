package com.chessd.chess.utils;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class PaginationUtil {
    public void setPagination(Page<?> page, Model model){
        int startPagination = Math.max(page.getNumber() - 3, 0);
        int endPagination = Math.max(Math.min(startPagination + 10, page.getTotalPages()) - 1, 0);
        model.addAttribute("page", page)
                .addAttribute("start", startPagination)
                .addAttribute("end", endPagination);
    }
}

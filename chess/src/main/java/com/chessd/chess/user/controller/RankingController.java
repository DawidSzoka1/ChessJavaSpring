package com.chessd.chess.user.controller;

import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.service.UserService;
import com.chessd.chess.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/users")
public class RankingController {

    private final UserService userService;
    private final PaginationUtil paginationUtil;

    @Autowired
    public RankingController(UserService userService, PaginationUtil paginationUtil) {
        this.userService = userService;
        this.paginationUtil = paginationUtil;
    }

    @GetMapping("/rankings")
    public String rankings(Model model,
                           @RequestParam(
                                   value = "pageNumber", defaultValue = "0", required = false
                           ) int pageNumber,
                           @RequestParam(
                                   value = "pageSize", defaultValue = "10", required = false
                           ) int pageSize
    ) {
        Page<User> page = userService.findAllByRanking(pageNumber, pageSize);
        paginationUtil.setPagination(page, model);
        return "users/rankings";
    }
}

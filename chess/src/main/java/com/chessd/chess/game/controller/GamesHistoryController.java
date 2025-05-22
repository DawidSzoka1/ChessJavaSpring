package com.chessd.chess.game.controller;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.game.service.GameService;
import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.service.UserService;
import com.chessd.chess.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class GamesHistoryController {
    private final UserService userService;
    private final GameService gameService;
    private final PaginationUtil paginationUtil;

    @Autowired
    public GamesHistoryController(UserService userService, GameService gameService, PaginationUtil paginationUtil) {
        this.userService = userService;
        this.gameService = gameService;
        this.paginationUtil = paginationUtil;
    }

    @GetMapping("/users/history/{userName}")
    public String getHistory(Model model,
                             @PathVariable String userName,
                             @RequestParam(value = "pageNum",
                                     defaultValue = "0",
                                     required = false) int pageNum,
                             @RequestParam(value = "pageSize",
                                     defaultValue = "10",
                                     required = false) int pageSize
    ) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            return "redirect:/";
        }
        Page<Game> games = gameService.getGamesByPlayer(pageNum, pageSize, user);
        paginationUtil.setPagination(games, model);
        model.addAttribute("user", user);

        return "games-history";
    }
}

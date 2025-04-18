package com.chessd.chess.game.controller;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.game.service.GameService;
import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class GamesHistoryController {
    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public GamesHistoryController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @GetMapping("/users/history/{userName}")
    public String getHistory(Model model, @PathVariable String userName) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            return "redirect:/";
        }
        List<Game> games = gameService.getGamesByPlayer(user);
        model
                .addAttribute("user", user)
                .addAttribute("games", games);

        return "games-history";
    }
}

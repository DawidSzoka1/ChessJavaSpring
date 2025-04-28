package com.chessd.chess.user.controller;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.game.service.GameService;
import com.chessd.chess.game.utils.GameResult;
import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ProfileController {
    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public ProfileController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @GetMapping("users/profile/{userName}")
    public String showUserProfile(@PathVariable String userName, Model model){
        User user = userService.findByUserName(userName);
        if(user == null){
            throw new IllegalArgumentException("User with that userName does not exist");
        }
        Page<Game> games = gameService.getGamesByPlayer(0, 6, user);

        model.addAttribute("user", user)
                .addAttribute("won", gameService.countWonGames(user))
                .addAttribute("lost", gameService.countLostGames(user))
                .addAttribute("draw", gameService.countDrawGames(user))
                .addAttribute("recentGames", games.getContent());
        return "users/profile";
    }
}

package com.chessd.chess.game.controller;
import com.chessd.chess.game.service.GameService;
import com.chessd.chess.game.service.GameTypeService;
import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LobbyController {

    private final UserService userService;
    private final GameService gameService;
    private final GameTypeService gameTypeService;

    @Autowired
    public LobbyController(UserService userService, GameService gameService, GameTypeService gameTypeService) {
        this.userService = userService;
        this.gameService = gameService;
        this.gameTypeService = gameTypeService;
    }

    @GetMapping("/play")
    public String showLobby(@AuthenticationPrincipal UserDetails userDetails, Model model){

        String playerName = userDetails == null ? "player" : userDetails.getUsername();
        User user = userService.findByUserName(playerName);
        model.addAttribute("gameTypes", gameTypeService.findAllTaken());
        if(user == null){
            return "game/lobby";
        }
        model.addAttribute("user", user)
                .addAttribute("won", gameService.countWonGames(user))
                .addAttribute("lost", gameService.countLostGames(user))
                .addAttribute("draw", gameService.countDrawGames(user));
        return "game/lobby";
    }
}

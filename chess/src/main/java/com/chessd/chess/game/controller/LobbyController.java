package com.chessd.chess.game.controller;
import com.chessd.chess.game.service.GameService;
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

    @Autowired
    public LobbyController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @GetMapping("/play")
    public String showLobby(@AuthenticationPrincipal UserDetails userDetails, Model model){

        String playerName = userDetails == null ? "player" : userDetails.getUsername();
        User user = userService.findByUserName(playerName);
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

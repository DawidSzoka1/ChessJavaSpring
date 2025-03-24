package com.chessd.chess.game.controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LobbyController {

    @GetMapping("/play")
    public String showLobby(@AuthenticationPrincipal UserDetails userDetails, Model model){

        String playerName = userDetails == null ? "player" : userDetails.getUsername();
        model.addAttribute("user", playerName);
        return "game/lobby";
    }
}

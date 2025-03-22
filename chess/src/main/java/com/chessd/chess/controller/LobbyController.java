package com.chessd.chess.controller;
import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class LobbyController {

    @GetMapping("/play")
    public String showLobby(@AuthenticationPrincipal UserDetails userDetails, Model model){

        String playerName = userDetails == null ? "player" : userDetails.getUsername();
        model.addAttribute("user", playerName);
        return "game/lobby";
    }
}

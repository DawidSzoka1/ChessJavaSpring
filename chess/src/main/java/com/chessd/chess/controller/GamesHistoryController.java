package com.chessd.chess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GamesHistoryController {


    @GetMapping("/user/{id}/history")
    public String getHistory(Model model, @PathVariable String id){

        return "games-history";
    }
}

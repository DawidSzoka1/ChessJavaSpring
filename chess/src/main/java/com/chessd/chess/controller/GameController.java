package com.chessd.chess.controller;

import com.chessd.chess.utils.Board;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
public class GameController {

    @GetMapping("/classic")
    public String classic(Model model) {
        model.addAttribute("board", new Board());
        return "game/classic-board";
    }

}

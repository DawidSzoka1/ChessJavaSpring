package com.chessd.chess.controller;

import com.chessd.chess.utils.Board;
import com.chessd.chess.utils.Player;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
public class GameController {



    @GetMapping("/classic")
    public String classic(Model model) {
        Player whitePlayer = new Player();
        Player blackPlayer = new Player();
        Board board = new Board(whitePlayer, blackPlayer, "on_going");
        board.startGame();
        model.addAttribute("board", board);
        model.addAttribute("player", whitePlayer);
        return "game/classic-board";
    }

}

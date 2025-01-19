package com.chessd.chess.controller;

import com.chessd.chess.entity.Game;
import com.chessd.chess.service.GameService;
import com.chessd.chess.service.RandomUniqIdGenerator;
import com.chessd.chess.utils.Column;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/game")
public class GameController {
    GameService gameService;
    RandomUniqIdGenerator randomUniqIdGenerator;

    @Autowired
    public GameController(GameService gameService, RandomUniqIdGenerator randomUniqIdGenerator) {
        this.gameService = gameService;
        this.randomUniqIdGenerator = randomUniqIdGenerator;
    }

    @GetMapping("/classic")
    public String classic(Model model){
        Game g = new Game(randomUniqIdGenerator.generateUniqId());
        gameService.save(g);

        gameService.startGame(g);
        model
                .addAttribute("columns", Column.values())
                .addAttribute("game", g)
                .addAttribute("gameBoard", gameService.getBoard(g))
                .addAttribute("gameService", gameService);
        return "game/classic-board";
    }
}

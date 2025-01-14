package com.chessd.chess.controller;

import com.chessd.chess.entity.gameEntity.Game;
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
    public String classic(Model model) throws JsonProcessingException {
        Game g = gameService.getGameById("0d259cc2-2492-4ee8-809f-3c1104d36808").get();
        gameService.startGame(g);
        ObjectMapper objectMapper = new ObjectMapper();
        String boardJson = objectMapper.writeValueAsString(g.getBoard());
        model
                .addAttribute("columns", Column.values())
                .addAttribute("game", g)
                .addAttribute("gameBoard", boardJson)
                .addAttribute("gameService", gameService);
        return "game/classic-board";
    }
}

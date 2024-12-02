package com.chessd.chess.controller;

import com.chessd.chess.entity.gameEntity.Game;
import com.chessd.chess.service.GameService;
import com.chessd.chess.service.RandomUniqIdGenerator;
import com.chessd.chess.utils.Column;
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
    public String classic(Model model) {
        Game game = new Game(randomUniqIdGenerator.generateUniqId());
        gameService.startGame(game);
        model
                .addAttribute("game", game)
                .addAttribute("col", Column.values())
                .addAttribute("row", new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        return "game/classic-board";
    }

}

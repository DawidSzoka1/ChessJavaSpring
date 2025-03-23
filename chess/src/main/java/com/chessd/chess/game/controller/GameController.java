package com.chessd.chess.game.controller;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.game.service.GameService;
import com.chessd.chess.game.service.RandomUniqIdGenerator;
import com.chessd.chess.figure.utils.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/game")
public class GameController {
    GameService gameService;
    RandomUniqIdGenerator randomUniqIdGenerator;
    private Set<String> initializedGames = new HashSet<>();
    private HashMap<String, Set<String>> playersInGame = new HashMap<>();

    @Autowired
    public GameController(GameService gameService, RandomUniqIdGenerator randomUniqIdGenerator) {
        this.gameService = gameService;
        this.randomUniqIdGenerator = randomUniqIdGenerator;
    }

    @GetMapping("/classic")
    public String classic(Model model){
//        Game g = gameService.getGameById("9397a5d8-e1a1-4332-b96b-3815aa77214e").get();

        Game g = new Game(randomUniqIdGenerator.generateUniqId());
        gameService.save(g);
        gameService.startGame(g);
        model
                .addAttribute("white", g.getWhite())
                .addAttribute("black", g.getBlack())
                .addAttribute("columns", Column.values())
                .addAttribute("game", g)
                .addAttribute("gameBoard", gameService.getBoardAsTable(g))
                .addAttribute("gameService", gameService);
        return "game/classic-board";
    }

}

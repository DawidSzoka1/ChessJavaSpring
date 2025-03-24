package com.chessd.chess.game.controller;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.game.service.GameService;
import com.chessd.chess.game.service.RandomUniqIdGenerator;
import com.chessd.chess.figure.utils.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Controller
public class GameController {
    GameService gameService;
    RandomUniqIdGenerator randomUniqIdGenerator;

    @Autowired
    public GameController(GameService gameService, RandomUniqIdGenerator randomUniqIdGenerator) {
        this.gameService = gameService;
        this.randomUniqIdGenerator = randomUniqIdGenerator;
    }

    @GetMapping("/play/{gamId}")
    public String classic(Model model, @PathVariable String gamId){
        Optional<Game> optional = gameService.getGameById(gamId);
        if(optional.isEmpty()){
            return "game/lobby";
        }
        Game g = gameService.getGameById(gamId).get();
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

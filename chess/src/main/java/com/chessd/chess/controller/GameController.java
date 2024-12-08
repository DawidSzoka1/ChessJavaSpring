package com.chessd.chess.controller;

import com.chessd.chess.entity.gameEntity.Game;
import com.chessd.chess.service.GameService;
import com.chessd.chess.service.RandomUniqIdGenerator;
import com.chessd.chess.utils.Column;
import com.chessd.chess.utils.Pawn;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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

//    @PostConstruct
//    public void init() {
//        Game game = new Game(randomUniqIdGenerator.generateUniqId());
//        gameService.startGame(game);
//    }
    @GetMapping("/classic")
    public String classic(Model model) {
        Pawn p = new Pawn("W", "d2");
        model
                .addAttribute("pawn", p)
                .addAttribute("game", gameService.getGameById("0d259cc2-2492-4ee8-809f-3c1104d36808").get())
                .addAttribute("gameService", gameService)
                .addAttribute("col", Column.values())
                .addAttribute("row", new String[]{"8", "7", "6", "5", "4", "3", "2", "1"});
        return "game/classic-board";
    }

}

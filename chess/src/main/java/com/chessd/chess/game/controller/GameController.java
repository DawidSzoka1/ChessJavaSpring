package com.chessd.chess.game.controller;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.game.service.GameService;
import com.chessd.chess.game.service.RandomUniqIdGenerator;
import com.chessd.chess.figure.utils.Column;
import com.chessd.chess.move.entity.Move;
import com.chessd.chess.move.service.MoveService;
import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.security.Principal;
import java.util.Optional;


@Controller
public class GameController {
    private final UserService userService;
    private final MoveService moveService;
    GameService gameService;
    RandomUniqIdGenerator randomUniqIdGenerator;

    @Autowired
    public GameController(GameService gameService, RandomUniqIdGenerator randomUniqIdGenerator,
                          UserService userService, MoveService moveService) {
        this.gameService = gameService;
        this.randomUniqIdGenerator = randomUniqIdGenerator;
        this.userService = userService;
        this.moveService = moveService;
    }

    @GetMapping("/play/{gamId}")
    public String classic(Model model, @PathVariable String gamId, Principal principal) {
        Optional<Game> optional = gameService.getGameById(gamId);
        if (optional.isEmpty()) {
            return "game/lobby";
        }
        Game g = optional.get();
        List<Move> moveList = moveService.getAllByGame(g);
        String playerName = principal != null ? principal.getName() : "";
        User player = userService.findByUserName(playerName);
        if(player == null || (!player.equals(g.getBlack()) && !player.equals(g.getWhite()))){
            player = g.getWhite();
        }
        User enemy = g.getBlack().getUserName().equals(player.getUserName()) ? g.getWhite() : g.getBlack();
        model
                .addAttribute("white", g.getWhite())
                .addAttribute("black", g.getBlack())
                .addAttribute("columns", Column.values())
                .addAttribute("game", g)
                .addAttribute("gameBoard", gameService.getBoardAsTable(g))
                .addAttribute("gameService", gameService)
                .addAttribute("moveList", moveList)
                .addAttribute("player", player)
                .addAttribute("enemy", enemy);
        return "game/classic-board";
    }

}

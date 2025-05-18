package com.chessd.chess.game.controller;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.game.service.GameService;
import com.chessd.chess.game.service.RandomUniqIdGenerator;
import com.chessd.chess.figure.utils.Column;
import com.chessd.chess.move.entity.Move;
import com.chessd.chess.move.service.MoveService;
import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.service.UserService;
import com.chessd.chess.web_socket.utils.UserHelper;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.security.Principal;
import java.util.Optional;


@Controller
public class GameController {
    private final UserService userService;
    private final MoveService moveService;
    private final UserHelper userHelper;
    GameService gameService;
    RandomUniqIdGenerator randomUniqIdGenerator;

    @Autowired
    public GameController(GameService gameService, RandomUniqIdGenerator randomUniqIdGenerator, UserService userService, MoveService moveService, UserHelper userHelper) {
        this.gameService = gameService;
        this.randomUniqIdGenerator = randomUniqIdGenerator;
        this.userService = userService;
        this.moveService = moveService;
        this.userHelper = userHelper;
    }

    @GetMapping("/play/test")
    public String test(Model model, Principal principal){
        Game g = new Game(randomUniqIdGenerator.generateUniqId());
        if(principal != null){
            g.setWhite(userService.findByUserName(principal.getName()));
        }
        g.setBlack(userService.findById(2).get());
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

    @GetMapping("/play/{gamId}")
    public String classic(Model model, @PathVariable String gamId, Principal principal){
        Optional<Game> optional = gameService.getGameById(gamId);
        if(optional.isEmpty()){
            return "game/lobby";
        }
        Game g = optional.get();
        List<Move> moveList = moveService.getAllByGame(g);
        String player = principal != null ? principal.getName() : "";
        model
                .addAttribute("white", g.getWhite())
                .addAttribute("black", g.getBlack())
                .addAttribute("columns", Column.values())
                .addAttribute("game", g)
                .addAttribute("gameBoard", gameService.getBoardAsTable(g))
                .addAttribute("gameService", gameService)
                .addAttribute("moveList", moveList)
                .addAttribute("player", player);
        return "game/classic-board";
    }

}

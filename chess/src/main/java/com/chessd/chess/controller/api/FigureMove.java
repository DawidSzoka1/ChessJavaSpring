package com.chessd.chess.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/api")
@Controller
public class FigureMove {


    @GetMapping("/move/{gameID}")
    public int[] move(@PathVariable int gameID, @RequestParam int figureId, @RequestParam int x, @RequestParam int y) {

        return new int[2];
    }
}

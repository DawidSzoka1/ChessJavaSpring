package com.chessd.chess.game.controller;

import com.chessd.chess.game.entity.GameType;
import com.chessd.chess.game.service.GameTypeService;
import com.chessd.chess.ranking.entity.Ranking;
import com.chessd.chess.ranking.service.RankingService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class GameTypeController {

    private final GameTypeService gameTypeService;
    private final RankingService rankingService;

    public GameTypeController(GameTypeService gameTypeService, RankingService rankingService) {
        this.gameTypeService = gameTypeService;
        this.rankingService = rankingService;
    }

    @GetMapping("gameType/form")
    public String gameTypeForm(Model model){
        GameType gameType = new GameType();
        model.addAttribute("gameType", gameType);
        return "gameType/form";
    }

    @PostMapping("gameType/form")
    public RedirectView handleCreate(
            @ModelAttribute("gameType") GameType gameType,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ){
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for(ObjectError error : bindingResult.getAllErrors()){
                errorMessages.add(error.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute("error", errorMessages);
            return new RedirectView("/admin/gameType/form");
        }
        GameType existing = gameTypeService.findByType(gameType.getType());
        if(existing != null){
            redirectAttributes.addFlashAttribute("error", "Ten tryb gry juz istnieje, daj inna nazwe");
            return  new RedirectView("/admin/gameType/form");
        }
        gameTypeService.save(gameType);
        redirectAttributes.addFlashAttribute("success", "Poprawnie dodano tryb gry");
        return new RedirectView("/admin/profile");
    }

    @PostMapping("gameType/delete")
    public RedirectView handleDelete(@RequestParam("gameTypeId") String id, RedirectAttributes redirectAttributes){
        int idValue;
        try {
            idValue = Integer.parseInt(id);
        }catch (NumberFormatException e){
            redirectAttributes.addFlashAttribute("error", "Nie ma takiego trybu gry");
            return new RedirectView("/admin/profile");
        }
        Optional<GameType> gameType = gameTypeService.findById(idValue);
        if(gameType.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Nie ma takiego trybu gry");
            return new RedirectView("/admin/profile");
        }
        Ranking ranking = rankingService.findByGameType(gameType.get());
        ranking.setGameType(null);
        rankingService.save(ranking);
        gameTypeService.delete(gameType.get());
        redirectAttributes.addFlashAttribute("success", "Poprawnie usunieto tryb gry");
        return new RedirectView("/admin/profile");
    }
}

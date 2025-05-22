package com.chessd.chess.ranking.controller;

import com.chessd.chess.game.service.GameTypeService;
import com.chessd.chess.ranking.entity.Ranking;
import com.chessd.chess.ranking.entity.RankingPosition;
import com.chessd.chess.ranking.service.RankingPositionService;
import com.chessd.chess.ranking.service.RankingService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ranking")
public class RankingController {

    private final RankingPositionService rankingPositionService;
    private final RankingService rankingService;
    private final GameTypeService gameTypeService;

    @Autowired
    public RankingController(RankingPositionService rankingPositionService, RankingService rankingService, GameTypeService gameTypeService) {
        this.rankingPositionService = rankingPositionService;
        this.rankingService = rankingService;
        this.gameTypeService = gameTypeService;
    }

    @GetMapping("")
    public String rankings(Model model) {
        List<Ranking> rankings = rankingService.findAll();
        LinkedHashMap<Ranking, List<RankingPosition>> rankingListHashMap = new LinkedHashMap<>();
        for (Ranking r : rankings) {
            rankingListHashMap.put(r,
                    rankingPositionService
                            .findAllByRanking(r, 0, 5).getContent()
            );
        }
        model.addAttribute("rankings", rankingListHashMap);
        return "ranking/all";
    }

    @GetMapping("/{rankingName}/all")
    public String allByRankingName(Model model, @PathVariable String rankingName,
                                   @RequestParam(
                                           value = "pageNumber", defaultValue = "0", required = false
                                   ) int pageNumber,
                                   @RequestParam(
                                           value = "pageSize", defaultValue = "10", required = false
                                   ) int pageSize
    ) {
        Ranking ranking = rankingService.findByName(rankingName);
        if (ranking == null) {
            return "error/404";
        }
        Page<RankingPosition> page = rankingPositionService.findAllByRanking(ranking, pageNumber, pageSize);
        model.addAttribute("page", page)
                .addAttribute("ranking", ranking);
        return "ranking/specificRanking";
    }

    @GetMapping("admin/create")
    public String showForm(Model model){
        Ranking ranking = new Ranking();
        model.addAttribute("gameTypes", gameTypeService.findAll())
                .addAttribute("ranking", ranking);
        return "ranking/form";
    }

    @PostMapping("admin/create")
    public RedirectView handleFrom(
            @ModelAttribute("ranking") Ranking ranking,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ){
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for(ObjectError error : bindingResult.getAllErrors()){
                errorMessages.add(error.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute("error", errorMessages);
            return new RedirectView("/ranking/create");
        }

        Ranking existing = rankingService.findByName(ranking.getName());
        if(existing != null){
            redirectAttributes.addFlashAttribute("error", "Ranking o tej nazwie juz istnieje");
            return  new RedirectView("/ranking/create");
        }
        rankingService.save(ranking);
        redirectAttributes.addFlashAttribute("success", "Poprawnie dodano rankig: " + ranking.getName());
        return new RedirectView("/ranking");
    }

    @PostMapping("admin/delete")
    public RedirectView handleDelete(
            @RequestParam("ranking_id") String id,
            RedirectAttributes redirectAttributes){
        int idValue = Integer.parseInt(id.substring(1));
        Optional<Ranking> ranking = rankingService.findById(idValue);
        if(ranking.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Nie ma takiego rankingu");
            return new RedirectView("/ranking");
        }
        rankingPositionService.deletAllByRanking(ranking.get());
        rankingService.delete(ranking.get());
        redirectAttributes.addFlashAttribute("success", "Pomyślnie usunieto ranking");
        return new RedirectView("/ranking");
    }
}

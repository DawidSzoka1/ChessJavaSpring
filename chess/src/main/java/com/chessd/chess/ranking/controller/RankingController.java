package com.chessd.chess.ranking.controller;

import com.chessd.chess.game.service.GameTypeService;
import com.chessd.chess.ranking.entity.Ranking;
import com.chessd.chess.ranking.entity.RankingPosition;
import com.chessd.chess.ranking.service.RankingPositionService;
import com.chessd.chess.ranking.service.RankingService;
import com.chessd.chess.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final PaginationUtil paginationUtil;

    @Value("${redirect.attribute-name.error}")
    private String errorType;

    @Value("${redirect.attribute-name.success}")
    private String successType;

    private static final String SUCCESS_URL = "/ranking";
    private static final String RANKING = "ranking";

    @Autowired
    public RankingController(RankingPositionService rankingPositionService, RankingService rankingService, GameTypeService gameTypeService, PaginationUtil paginationUtil) {
        this.rankingPositionService = rankingPositionService;
        this.rankingService = rankingService;
        this.gameTypeService = gameTypeService;
        this.paginationUtil = paginationUtil;
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
    public String allByRankingName(Model model,
                                   @PathVariable String rankingName,
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
        paginationUtil.setPagination(page, model);
        model.addAttribute(RANKING, ranking);
        return "ranking/specificRanking";
    }

    @GetMapping("admin/create")
    public String showForm(Model model){
        Ranking ranking = new Ranking();
        model.addAttribute("gameTypes", gameTypeService. findAllAvailable())
                .addAttribute(RANKING, ranking);
        return "ranking/form";
    }
    private String validRanking(Ranking ranking){
        Ranking existing = rankingService.findByName(ranking.getName());
        if(existing != null){
            return "Ranking o tej nazwie juz istnieje";
        }
        Ranking byGameType = rankingService.findByGameType(ranking.getGameType());
        if(byGameType != null){
            return "Ranking z tym trybem gry juz istnieje";
        }
        return "";
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
            redirectAttributes.addFlashAttribute(errorType, errorMessages);
            return new RedirectView("/ranking/admin/create");
        }
        String result = this.validRanking(ranking);
        if(!result.isEmpty()){
            redirectAttributes.addFlashAttribute(errorType, result);
            return new RedirectView("/ranking/admin/create");
        }

        rankingService.save(ranking);
        redirectAttributes.addFlashAttribute(successType, "Poprawnie dodano rankig: " + ranking.getName());
        return new RedirectView(SUCCESS_URL);
    }

    @PostMapping("admin/delete")
    public RedirectView handleDelete(
            @RequestParam("ranking_id") String id,
            RedirectAttributes redirectAttributes){
        int idValue = Integer.parseInt(id.substring(1));
        Optional<Ranking> ranking = rankingService.findById(idValue);
        if(ranking.isEmpty()){
            redirectAttributes.addFlashAttribute(errorType, "Nie ma takiego rankingu");
            return new RedirectView(SUCCESS_URL);
        }
        rankingPositionService.deleteAllByRanking(ranking.get());
        rankingService.delete(ranking.get());
        redirectAttributes.addFlashAttribute(successType, "Pomyślnie usunieto ranking");
        return new RedirectView(SUCCESS_URL);
    }

    @GetMapping("admin/update/{id}")
    public String showUpdateForm(@PathVariable int id, Model model){
        Optional<Ranking> ranking = rankingService.findById(id);
        if(ranking.isEmpty()){
            return "ranking/all";
        }
        model.addAttribute(RANKING, ranking.get())
                .addAttribute("gameTypes", gameTypeService.findAllAvailable());
        return "ranking/updateForm";
    }

    @PostMapping("admin/update")
    public RedirectView handleUpdate(
            @ModelAttribute("ranking") Ranking ranking,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for(ObjectError error : bindingResult.getAllErrors()){
                errorMessages.add(error.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute(errorType, errorMessages);
            return new RedirectView("/ranking/admin/update/" + ranking.getId());
        }
        Ranking existing = rankingService.findByName(ranking.getName());
        if(existing != null && !existing.equals(ranking)){
            redirectAttributes.addFlashAttribute(errorType,
                    "Ranking o takiej nazwie juz istnieje");
            return new RedirectView("/ranking/admin/update/" + ranking.getId());
        }
        rankingService.save(ranking);
        redirectAttributes.addFlashAttribute(successType,
                "Poprawnie zmieniono ranking: " + ranking.getName());
        return new RedirectView(SUCCESS_URL);
    }
}

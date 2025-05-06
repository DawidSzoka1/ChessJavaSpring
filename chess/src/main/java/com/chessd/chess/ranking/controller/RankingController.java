package com.chessd.chess.ranking.controller;

import com.chessd.chess.ranking.entity.Ranking;
import com.chessd.chess.ranking.entity.RankingPosition;
import com.chessd.chess.ranking.service.RankingPositionService;
import com.chessd.chess.ranking.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;

@Controller
@RequestMapping("/ranking")
public class RankingController {

    private final RankingPositionService rankingPositionService;
    private final RankingService rankingService;

    @Autowired
    public RankingController(RankingPositionService rankingPositionService, RankingService rankingService) {
        this.rankingPositionService = rankingPositionService;
        this.rankingService = rankingService;
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
}

package com.chessd.chess.ranking.controller;

import com.chessd.chess.ranking.entity.Ranking;
import com.chessd.chess.ranking.entity.RankingPosition;
import com.chessd.chess.ranking.service.RankingPositionService;
import com.chessd.chess.ranking.service.RankingService;
import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.service.UserService;
import com.chessd.chess.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/ranking")
public class RankingController {

    private final UserService userService;
    private final PaginationUtil paginationUtil;
    private final RankingPositionService rankingPositionService;
    private final RankingService rankingService;

    @Autowired
    public RankingController(UserService userService, PaginationUtil paginationUtil, RankingPositionService rankingPositionService, RankingService rankingService) {
        this.userService = userService;
        this.paginationUtil = paginationUtil;
        this.rankingPositionService = rankingPositionService;
        this.rankingService = rankingService;
    }

    @GetMapping("/all")
    public String rankings(Model model) {
        List<Ranking> rankings = rankingService.findAll();
        HashMap<Ranking, List<RankingPosition>> rankingListHashMap = new HashMap<>();
        for (Ranking r : rankings) {
            rankingListHashMap.put(r,
                    rankingPositionService
                            .findAllByRanking(r, 0, 5).getContent()
            );
        }
        model.addAttribute("rankings", rankingListHashMap);
        return "ranking/all";
    }

    @GetMapping("/classic")
    public String classic(Model model,
                          @RequestParam(
                                  value = "pageNumber", defaultValue = "0", required = false
                          ) int pageNumber,
                          @RequestParam(
                                  value = "pageSize", defaultValue = "10", required = false
                          ) int pageSize
    ) {
        Page<User> page = userService.findAllByRanking(pageNumber, pageSize);
        paginationUtil.setPagination(page, model);
        return "users/rankings";
    }
}

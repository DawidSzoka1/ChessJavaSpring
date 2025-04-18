package com.chessd.chess.admin.controller;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.game.service.GameService;
import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.service.UserService;
import com.chessd.chess.utils.PaginationUtil;
import com.chessd.chess.utils.TimeUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    private final UserService userService;
    private final GameService gameService;
    private final TimeUtils timeUtils;
    private final PaginationUtil paginationUtil;

    public AdminController(UserService userService, GameService gameService, TimeUtils timeUtils, PaginationUtil paginationUtil) {
        this.userService = userService;
        this.gameService = gameService;
        this.timeUtils = timeUtils;
        this.paginationUtil = paginationUtil;
    }

    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal) {
        User admin = userService.findByUserName(principal.getName());
        model.addAttribute("admin", admin);
        return "admin/adminProfile";
    }

    @GetMapping("/panel")
    public String adminPanel(Model model,
                             @RequestParam(
                                     value = "pageNumber", defaultValue = "0", required = false
                             ) int pageNumber,
                             @RequestParam(
                                     value = "pageSize", defaultValue = "7", required = false
                             ) int pageSize
    ) {
        Page<User> page = userService.findAllSortedByNameASC(pageNumber, pageSize);
        paginationUtil.setPagination(page, model);
        return "admin/adminPanel";
    }

    @PostMapping("delete/users")
    public RedirectView deleteUsers(@RequestParam(name = "userIds", required = false) List<Integer> userIds,
                                    Principal principal, RedirectAttributes redirectAttributes) {
        int amount = userIds.size();
        userIds.removeIf(i -> principal.getName().equals(userService.findById(i).get().getUserName()));
        if (amount != userIds.size()) {
            redirectAttributes.addFlashAttribute("info", "Nie mozna usunac admina");
        }
        try {
            userService.deleteUsers(userIds);
            redirectAttributes.addFlashAttribute("success",
                    "Usunieto zaznaczonych uzytkownikow(" + userIds.size() + ")");
            return new RedirectView("/admin/panel");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return new RedirectView("/admin/panel");
        }
    }

    @GetMapping("/review/games")
    public String getGames(Model model,
                           @RequestParam(
                                   value = "pageNum",
                                   defaultValue = "0",
                                   required = false) int pageNum,
                           @RequestParam(
                                   value = "pageSize",
                                   defaultValue = "5",
                                   required = false) int pageSize
    ) {
        Page<Game> page = gameService.getAllGames(pageNum, pageSize);
        paginationUtil.setPagination(page, model);
        model.addAttribute("time", timeUtils);
        return "admin/gameReview";
    }

    @GetMapping("/review/panel")
    public String getGamePanel(){
        return "admin/gamePanel";
    }
}

package com.chessd.chess.game.controller;

import com.chessd.chess.game.entity.GameType;
import com.chessd.chess.game.service.GameTypeService;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${redirect.attribute-name.error}")
    private String errorType;

    public GameTypeController(GameTypeService gameTypeService) {
        this.gameTypeService = gameTypeService;
    }

    @GetMapping("gameType/form")
    public String gameTypeForm(Model model) {
        GameType gameType = new GameType();
        model.addAttribute("gameType", gameType);
        return "gameType/form";
    }

    @PostMapping("gameType/form")
    public RedirectView handleCreate(
            @ModelAttribute("gameType") GameType gameType,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute(errorType, errorMessages);
            return new RedirectView("/admin/gameType/form");
        }
        GameType existing = gameTypeService.findByType(gameType.getType());
        if (existing != null) {
            redirectAttributes.addFlashAttribute(errorType, "Ten tryb gry juz istnieje, daj inna nazwe");
            return new RedirectView("/admin/gameType/form");
        }
        gameTypeService.save(gameType);
        redirectAttributes.addFlashAttribute("success", "Poprawnie dodano tryb gry");
        return new RedirectView("/admin/profile");
    }

    @GetMapping("gameType/update/{id}")
    public String showUpdateForm(Model model, @PathVariable int id) {
        Optional<GameType> gameType = gameTypeService.findById(id);
        if (gameType.isEmpty()) {
            return "admin/adminProfile";
        }
        model.addAttribute("gameType", gameType.get());
        return "gameType/updateForm";
    }

    @PostMapping("gameType/update")
    public RedirectView handleUpdate(@ModelAttribute("gameType") GameType gameType,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute(errorType, errorMessages);
            return new RedirectView("/admin/gameType/update/" + gameType.getId());
        }
        GameType existing = gameTypeService.findByType(gameType.getType());
        if (existing != null && !existing.equals(gameType)) {
            redirectAttributes.addFlashAttribute(errorType,
                    "Ten tryb gry juz istnieje, daj inna nazwe");
            return new RedirectView("/admin/gameType/update/" + gameType.getId());
        }
        gameTypeService.save(gameType);
        redirectAttributes.addFlashAttribute("success",
                "Prawidłowo zmieniono tryb gry " + gameType.getType());
        return new RedirectView("/admin/profile");
    }
}

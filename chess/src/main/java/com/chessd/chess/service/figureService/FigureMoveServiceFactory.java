package com.chessd.chess.service.figureService;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FigureMoveServiceFactory {
    private final Map<String, FigureMoveService> figureMoveService;

    public FigureMoveServiceFactory(List<FigureMoveService> moveService) {
        this.figureMoveService = moveService.stream().collect(Collectors.toMap(
                service -> service.getClass().getSimpleName().replace("MoveService", "").toLowerCase(),
                service -> service
        ));
    }

    public FigureMoveService getMoveService(String pieceName){
        return figureMoveService.get(pieceName.toLowerCase());
    }
}

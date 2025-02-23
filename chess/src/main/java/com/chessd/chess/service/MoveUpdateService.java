package com.chessd.chess.service;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.repository.FigureDao;
import com.chessd.chess.service.figureService.FigureMoveService;
import com.chessd.chess.service.figureService.FigureMoveServiceFactory;
import com.chessd.chess.utils.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MoveUpdateService {
    private final FigureMoveServiceFactory serviceFactory;
    private final FigureDao figureDao;

    @Autowired
    public MoveUpdateService(FigureMoveServiceFactory serviceFactory, FigureDao figureDao) {
        this.serviceFactory = serviceFactory;
        this.figureDao = figureDao;
    }

    public void updateAffectedFigures(Figure movedFigure, HashMap<Position, Figure> board, String prevPosition) {
        FigureMoveService figureMoveService;
        Set<Figure> toUpdate = getFiguresAffectedByMove(movedFigure, board, prevPosition);
        for(Figure f: toUpdate){
            figureMoveService = serviceFactory.getMoveService(f.getName());
            f.setMoves(figureMoveService.getAvailableMoves(f, board));
            figureDao.update(f);
        }

    }

    public Set<Figure> getFiguresAffectedByMove(Figure movedFigure, HashMap<Position, Figure> board, String prevPosition) {
        Set<Figure> affected = new HashSet<>();
        //enemy affected by current position
        affected.addAll(figureDao.getAllFiguresByPossibleMoveAndColor(
                movedFigure.getGame(),
                movedFigure.getOpponent(),
                movedFigure.getPosition().toString()
        ));
        //enemy affected by previous position
        affected.addAll(figureDao.getAllFiguresByPossibleMoveAndColor(
                movedFigure.getGame(),
                movedFigure.getOpponent(),
                prevPosition
        ));
        //own pieces affected by new position
        affected.addAll(figureDao.getAllFiguresByPossibleMoveAndColor(
                movedFigure.getGame(),
                movedFigure.getColor(),
                movedFigure.getPosition().toString()
        ));
        affected.addAll(this.blockedInPreviousPosition(movedFigure.getGame(), movedFigure, board));
        return affected;
    }

    public List<Figure> blockedInPreviousPosition(Game game,
                                                  Figure movedFigure,
                                                  HashMap<Position, Figure> board) {
        FigureMoveService figureMoveService;
        List<Figure> affected = new ArrayList<>();
        for (Figure f : figureDao.getAllFiguresByColor(game, movedFigure.getColor())) {
            if (f.equals(movedFigure)) continue;
            figureMoveService = serviceFactory.getMoveService(f.getName());
            List<String> moves = figureMoveService.getAvailableMoves(f, board);
            if (moves.size() > f.getMoves().size()) {
                affected.add(f);
            }

        }
        return affected;
    }
}

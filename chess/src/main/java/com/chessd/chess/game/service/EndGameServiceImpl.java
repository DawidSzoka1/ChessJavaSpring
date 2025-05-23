package com.chessd.chess.game.service;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.repository.FigureDao;
import com.chessd.chess.game.entity.GameType;
import com.chessd.chess.game.utils.GameResult;
import com.chessd.chess.ranking.entity.Ranking;
import com.chessd.chess.ranking.entity.RankingPosition;
import com.chessd.chess.ranking.service.RankingPositionService;
import com.chessd.chess.ranking.service.RankingService;
import com.chessd.chess.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EndGameServiceImpl implements EndGameService {
    private final FigureDao figureDao;
    private final GameService gameService;
    private final RankingPositionService rankingPositionService;
    private final RankingService rankingService;

    @Autowired
    public EndGameServiceImpl(FigureDao figureDao, GameService gameService, RankingPositionService rankingPositionService, RankingService rankingService, RankingService rankingService1) {
        this.figureDao = figureDao;
        this.gameService = gameService;
        this.rankingPositionService = rankingPositionService;
        this.rankingService = rankingService1;
    }

    @Override
    public boolean lookForEndGame(Game game, String color) {
        System.out.println("Szukam konca gry!!!!");
        return figureDao.possibleMoveByColor(game, color);
    }

    private void endGame(Game game, GameResult gameResult) {
        GameResult result = gameResult == null ? GameResult.fromCheckStatus(game.getCheckStatus()) : gameResult;
        gameService.endGame(game, result);
    }

    private void processLosser(User losser, GameType gameType) {
        RankingPosition rankingLosser =
                rankingPositionService.findByUserAndGameType(losser, gameType);
        int losserBefore = rankingLosser.getPoints();
        rankingLosser.setPoints(Math.max(rankingLosser.getPoints() - 100, 0));
        this.updateRankingPositions(rankingLosser, gameType, losserBefore);
    }

    private void processWinner(User winner, GameType gameType) {
        RankingPosition rankingWinner =
                rankingPositionService.findByUserAndGameType(winner, gameType);
        int winnerBefore = rankingWinner.getPoints();
        rankingWinner.setPoints(rankingWinner.getPoints() + 100);
        this.updateRankingPositions(rankingWinner, gameType, winnerBefore);
    }

    private void processDraw(Game game) {
        GameType gameType = game.getGameType();
        RankingPosition rankingPlayerWhite =
                rankingPositionService.findByUserAndGameType(game.getWhite(), gameType);
        RankingPosition rankingPlayerBlack =
                rankingPositionService.findByUserAndGameType(game.getBlack(), gameType);
        int whiteBefore = rankingPlayerWhite.getPoints();
        int blackBefore = rankingPlayerBlack.getPoints();

        rankingPlayerWhite.setPoints(rankingPlayerWhite.getPoints() + 2);
        rankingPlayerBlack.setPoints(rankingPlayerBlack.getPoints() + 2);
        this.updateRankingPositions(rankingPlayerWhite, gameType, whiteBefore);
        this.updateRankingPositions(rankingPlayerBlack, gameType, blackBefore);
    }

    private void updateRankingPositions(RankingPosition changed, GameType gameType, int pointsBefore) {
        rankingPositionService.save(changed);
        Ranking ranking = rankingService.findByGameType(gameType);

        List<RankingPosition> affectedPositions = findAffectedPositions(changed, pointsBefore, ranking);
        if (affectedPositions.size() == 1) {
            return;
        }

        updateChangedPosition(changed, affectedPositions, pointsBefore);
        resolveSamePointConflicts(changed, ranking);
    }

    private List<RankingPosition> findAffectedPositions(RankingPosition changed, int pointsBefore, Ranking ranking) {
        int minPoints = Math.min(changed.getPoints(), pointsBefore);
        int maxPoints = Math.max(changed.getPoints(), pointsBefore);
        return rankingPositionService.affectedByPointsChange(ranking, minPoints, maxPoints);
    }

    private void updateChangedPosition(RankingPosition changed, List<RankingPosition> affectedPositions, int pointsBefore) {
        int newPosition;
        int changedBy;
        if (changed.getPosition() > pointsBefore) {
            changedBy = 1;
            newPosition = rankingPositionService.topPosition(affectedPositions);
        } else {
            newPosition = rankingPositionService.bottomPosition(affectedPositions);
            changedBy = -1;
        }
        System.out.println("NOWA POZYCJA TOPADUSADAS: " + newPosition);
        changed.setPosition(newPosition);
        rankingPositionService.save(changed);
        affectedPositions.forEach(p -> {
            if(!p.equals(changed)){
                p.setPosition(p.getPosition() + changedBy);
                rankingPositionService.save(p);
            }
        });
    }

    private void resolveSamePointConflicts(RankingPosition changed, Ranking ranking) {
        List<RankingPosition> samePointPositions = rankingPositionService.findAllByPointsAndRanking(changed.getPoints(), ranking);
        if (samePointPositions == null || samePointPositions.size() <= 1) {
            return;
        }
        handleSamePosition(samePointPositions);
    }

    private void handleSamePosition(List<RankingPosition> samePosition) {
        int highestPosition = rankingPositionService.topPosition(samePosition);
        System.out.println("NAJWIEKSZA tO: " + highestPosition);
        System.out.println(samePosition);
        for (RankingPosition position : samePosition) {
            position.setPosition(highestPosition);
            rankingPositionService.save(position);
        }
    }

    private void processPoints(Game game) {
        User winner = game.getWinner();
        if (winner == null) {
            this.processDraw(game);
        } else {
            GameType gameType = game.getGameType();
            User losser = winner.equals(game.getBlack()) ? game.getWhite() : game.getBlack();
            this.processWinner(winner, gameType);
            this.processLosser(losser, gameType);
        }
    }

    @Override
    public void handleAfterGame(Game game) {
        this.handleAfterGame(game, null);
    }

    @Override
    public void handleAfterGame(Game game, GameResult gameResult) {
        this.endGame(game, gameResult);
        this.processPoints(game);
    }
}

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
        rankingLosser.setPoints(Math.max(rankingLosser.getPoints() - 20, 0));
        rankingPositionService.save(rankingLosser);
        this.updateRankingPositions(rankingLosser, gameType);
    }

    private void processWinner(User winner, GameType gameType) {
        RankingPosition rankingWinner =
                rankingPositionService.findByUserAndGameType(winner, gameType);
        rankingWinner.setPoints(rankingWinner.getPoints() + 20);
        rankingPositionService.save(rankingWinner);
        this.updateRankingPositions(rankingWinner, gameType);
    }

    private void processDraw(Game game) {
        GameType gameType = game.getGameType();
        RankingPosition rankingPlayerWhite =
                rankingPositionService.findByUserAndGameType(game.getWhite(), gameType);
        RankingPosition rankingPlayerBlack =
                rankingPositionService.findByUserAndGameType(game.getBlack(), gameType);
        rankingPlayerWhite.setPoints(rankingPlayerWhite.getPoints() + 2);
        rankingPlayerBlack.setPoints(rankingPlayerBlack.getPoints() + 2);
        rankingPositionService.save(rankingPlayerBlack);
        rankingPositionService.save(rankingPlayerWhite);
        this.updateRankingPositions(rankingPlayerBlack, gameType);
    }

    private void updateRankingPositions(RankingPosition changed, GameType gameType) {
        Ranking ranking = rankingService.findByGameType(gameType);
        updateChangedPosition(ranking);
        resolveSamePointConflicts(changed, ranking);
    }

    private void updateChangedPosition(Ranking ranking) {
        List<RankingPosition> all = rankingPositionService.findAllByRankingOrderByPoints(ranking);
        int currentPosition = 1;
        for(RankingPosition temp: all){
            temp.setPosition(currentPosition);
            currentPosition++;
            rankingPositionService.save(temp);
        }
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

package com.chessd.chess.listener;

import com.chessd.chess.event.LookForCheckEvent;
import com.chessd.chess.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class LookForCheckListener {
    private final CheckService checkService;

    @Autowired
    public LookForCheckListener(CheckService checkService) {
        this.checkService = checkService;
    }


    @EventListener
    public void lookForCheck(LookForCheckEvent event) {
        checkService.lookForChecks(event.getGame());
    }
}

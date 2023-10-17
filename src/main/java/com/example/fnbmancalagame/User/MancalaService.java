package com.example.fnbmancalagame.User;

import com.example.fnbmancalagame.Core.Mancala;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public final class MancalaService {

    MancalaData getMancalaData(final Mancala mancala){
        return MancalaData.builder()
                .leftRow(mancala.getPitsLeft())
                .mancalaLeft(mancala.getStonesMancalaLeft())
                .rightRow(mancala.getPitsRight())
                .mancalaRight(mancala.getStonesMancalaRight())
                .build();
    }
    private List<Integer> reverse(final List<Integer> list){
        Collections.reverse(list);
        return list;
    }
}

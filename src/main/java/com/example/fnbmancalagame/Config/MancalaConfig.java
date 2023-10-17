package com.example.fnbmancalagame.Config;

import com.example.fnbmancalagame.Core.Board;
import com.example.fnbmancalagame.Core.BoardImpl;
import com.example.fnbmancalagame.Core.Mancala;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MancalaConfig extends WebMvcConfigurationSupport{
    private final int pitsPerPlayer;
    private final int stonesPerPit;
    private final boolean leftTurn;

    public MancalaConfig(@Value("${board.pitsPerPlayer: 6}") final int pitsPerPlayer,
                         @Value("${stonesPerPit: 6}") final int stonesPerPit,
                         @Value("${board.leftTurn: true}") final boolean leftTurn) {
        this.pitsPerPlayer = pitsPerPlayer;
        this.stonesPerPit = stonesPerPit;
        this.leftTurn = leftTurn;
    }

    @Bean
    public Mancala getMancalaBean(){
        return Mancala.builder().board(getBoardBean()).build();
    }
     @Bean
    public Board getBoardBean() {

        return BoardImpl.builder()
                .pitList(pitsPerPlayer, stonesPerPit)
                .leftTurn(leftTurn)
                .build();
    }
}

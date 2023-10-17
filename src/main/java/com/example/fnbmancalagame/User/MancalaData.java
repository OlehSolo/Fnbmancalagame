package com.example.fnbmancalagame.User;

import lombok.Builder;

import java.util.List;

@Builder
public class MancalaData {
    List<Integer> leftRow;
    List<Integer> rightRow;
    Integer mancalaLeft;
    Integer mancalaRight;
}

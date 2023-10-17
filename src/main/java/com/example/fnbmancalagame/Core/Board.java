package com.example.fnbmancalagame.Core;

import java.util.List;

public interface Board {

    void makeMove(int index);
    List<Integer> getPits();
    int getIndexMancalaLeft();
    int getIndexMancalaRight();
    boolean isLeftTurn();
    void setLeftTurn(boolean leftTurn);
    boolean isEmpty(int index);
    boolean isGameOver();
}

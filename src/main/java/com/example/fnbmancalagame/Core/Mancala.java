package com.example.fnbmancalagame.Core;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder @Data
public final class Mancala {

    private Board board;

    public void play(final int index){
        board.makeMove(index);
    }
    public String getWinnersMessage(){
        int exceedingStonesLeft = getStonesMancalaLeft() - getStonesMancalaRight();

        if(exceedingStonesLeft == 0)
            return "It's tie";

        return exceedingStonesLeft > 0 ? "Left player won the game!": "Right player won the game";
    }

    public int getStonesMancalaRight() {
        int index = board.getIndexMancalaRight();
        return board.getPits().get(index);
    }

    public int getStonesMancalaLeft() {
        int index = board.getIndexMancalaLeft();
        return board.getPits().get(index);
    }
    public boolean isGameOver(){
        return board.isGameOver();
    }

    public boolean isPitEmpty(final int isEmpty){
        return board.isEmpty(isEmpty);
    }
    public void setLeftTurn(final boolean isLeftTurn){
        board.setLeftTurn(isLeftTurn);
    }
    public boolean isLeftTurn(){
        return board.isLeftTurn();
    }
    public List<Integer> getPitsLeft(){
        List<Integer> list = board.getPits().subList(0, board.getIndexMancalaLeft());
        return new ArrayList<>(list);
    }
    public List<Integer> getPitsRight(){
        List<Integer> list = board.getPits()
                .subList(board.getIndexMancalaLeft() + 1, board.getIndexMancalaRight());
        return new ArrayList<>(list);
    }

    public int getOffSetPlayerRight(){
        return board.getPits().size()/2;
    }


}

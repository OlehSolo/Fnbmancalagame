package com.example.fnbmancalagame.Core;


import lombok.AllArgsConstructor;
import lombok.Builder;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Builder
@AllArgsConstructor
public class BoardImpl implements Board {

    private boolean leftTurn;
    private final List<Integer> pitList;

    @Override
    public void makeMove(int index) {
        int lastPit = allocateStonesAndGetLastPit(index);
        captureIfLastPitIsOwnEmptyPit(lastPit);
        takeLastStoneIfGameOver();
        switchTurnsIfLastPitIsNotOwnSide(lastPit);
    }



    private void switchTurnsIfLastPitIsNotOwnSide(int lastPit) {
        if(isNotOwnSide(lastPit)){
            setLeftTurn(isNotLeftTurn());
        }
    }

    private boolean isNotOwnSide(int lastPit) {
        return isLeftTurn() && lastPit != getIndexMancalaLeft()
                || isNotLeftTurn() && lastPit != getIndexMancalaRight();
    }

    private void captureIfLastPitIsOwnEmptyPit(int lastPit) {
        if (pitContainsOneStone(lastPit) && isRegularPit(lastPit) && landsInPlayerOwnPit(lastPit)){
            int mancala = isLeftTurn() ? getIndexMancalaLeft() : getIndexMancalaRight();
            int captured = getStonesInPit(lastPit) + getStonesInPit(oppositePit(lastPit));

            setStonesInPit(mancala, getStonesInPit(mancala) + captured);
            emptyPit(lastPit);
            emptyPit(oppositePit(lastPit));
        }
    }

    private int allocateStonesAndGetLastPit(final int index) {
     int stones = getStonesInPit(index);
     int lastPit = index;
     while (stones > 0){
         lastPit = nextPit(lastPit);
         incrementStonesInPit(lastPit);
         --stones;
     }
     emptyPit(index);
     return lastPit;
    }
    private boolean pitContainsOneStone(final int index){
     return getStonesInPit(index) == 1;
    }
    private boolean isRegularPit(final int index){
        return index != getIndexMancalaLeft() && index != getIndexMancalaRight();
    }
    private boolean landsInPlayerOwnPit(final int index){
       return isLeftTurn() && index < getIndexMancalaLeft()
               || isNotLeftTurn() && index > getIndexMancalaLeft();
    }
    private int oppositePit(final int index){
        return 2 * getIndexMancalaLeft() - index;
    }
    private void takeLastStoneIfGameOver(){
        if(isGameOver()){
            int lastStoneLeft = getAllStonesInPitsLeft();
            int lastStoneRight = getAllStonesInPitsRight();

            TakeStones(lastStoneLeft, lastStoneRight);
            emptyRegularPits();
        }
    }

    private int getAllStonesInPitsRight() {
        return IntStream.range(getIndexMancalaLeft() + 1,getIndexMancalaRight())
                .map(this::getStonesInPit)
                .sum();
    }

    private int getAllStonesInPitsLeft() {
        return IntStream.range(0,getIndexMancalaLeft())
                .map(this::getStonesInPit)
                .sum();
    }

    private void emptyRegularPits() {
        IntStream.range(0, getIndexMancalaLeft())
                .forEach(this::emptyPit);
        IntStream.range(getIndexMancalaLeft() + 1,getIndexMancalaRight())
                .forEach(this::emptyPit);
    }


    private void TakeStones(int lastStoneLeft, int lastStoneRight) {
        int newAmountLeft = getStonesInPit(getIndexMancalaLeft()) + lastStoneLeft;
        int newAmountRight = getStonesInPit(getIndexMancalaRight()) + lastStoneRight;

        setStonesInPit(getIndexMancalaLeft(), newAmountLeft);
        setStonesInPit(getIndexMancalaRight(), newAmountRight);
    }

    private void setStonesInPit(int indexMancalaLeft, int newAmountLeft) {
        getPits().set(indexMancalaLeft, newAmountLeft);
    }

    private void emptyPit(int index) {
        getPits().set(index,0);
    }

    private int nextPit(final int lastPit) {
        int nextIndex = isLastIndex(lastPit)?0 : lastPit + 1;
        return skipPlayer(nextIndex);
    }

    public boolean isNotLeftTurn(){
        return !isLeftTurn();
    }

    private int skipPlayer(final int nextIndex) {
    if(isLeftTurn() && nextIndex == getIndexMancalaRight())
        return 0;
    if(isNotLeftTurn() && nextIndex == getIndexMancalaLeft())
        return nextIndex + 1;
    return nextIndex;
    }

    private boolean isLastIndex(final int lastPit) {
        return  lastPit == getIndexMancalaRight();
    }

    private int getStonesInPit(int index) {
        return getPits().get(index);
    }

    @Override
    public List<Integer> getPits() {
  
        return pitList;
    }

    @Override
    public int getIndexMancalaLeft() {
        return getPits().size()/2-1;
    }

    @Override
    public int getIndexMancalaRight() {
        return getPits().size()-1;
    }

    @Override
    public boolean isLeftTurn() {
        return leftTurn;
    }

    @Override
    public void setLeftTurn(final boolean leftTurn) {
     this.leftTurn = leftTurn;
    }

    @Override
    public boolean isEmpty(final int index) {
        return getStonesInPit(index) == 0;
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
    private void incrementStonesInPit(final int index) {
        setStonesInPit(index, getStonesInPit(index) + 1);
    }
    public static final class BoardImplBuilder {
        private List<Integer> pitList;

        public BoardImplBuilder pitList(final int pitsPerPlayer, final int stonesPerPit) {
            int totalAmountOfPits = 2 * pitsPerPlayer + 2;
            List<Integer> list = IntStream.
                    generate(() -> stonesPerPit)
                    .limit(totalAmountOfPits)
                    .boxed()
                    .collect(Collectors.toList());
            list.set(list.size() / 2 - 1, 0);
            list.set(list.size() - 1, 0);

            this.pitList = list;
            return this;
        }
    }

}

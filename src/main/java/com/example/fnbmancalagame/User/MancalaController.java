package com.example.fnbmancalagame.User;

import com.example.fnbmancalagame.Core.Mancala;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MancalaController {
    private final MancalaService mancalaService;
    private final Mancala mancala;

   @Autowired
    public MancalaController(MancalaService mancalaService, Mancala mancala) {
        this.mancalaService = mancalaService;
        this.mancala = mancala;
    }

    @GetMapping("/")
    public String loadStartPage(){
       return "index";
    }

    @GetMapping("/play")
    public String loadGame(final Model model){
       addAttributesToModel(model);
       return "board";
    }
    @PostMapping("/play")
    public String performMove(@ModelAttribute final Initial initial, final Model model){

       int chosenIndex = initial.getIndex();
       boolean isLeftTurn = initial.getIsLeftTurn();
       int pitListIndex = isLeftTurn ? chosenIndex : chosenIndex + mancala.getOffSetPlayerRight();

       if(mancala.isPitEmpty(pitListIndex)){
           addErrorMessageToModel(model, chosenIndex);
       }
       else
           play(pitListIndex, isLeftTurn);

       addGameOverMessageIfGameOver(model);
       addAttributesToModel(model);
       return "board";

    }

    private void addGameOverMessageIfGameOver(Model model) {
       if(mancala.isGameOver()){
           model.addAttribute("gameOverMessage",mancala.getWinnersMessage());
       }
    }

    private void play(int pitListIndex, boolean isLeftTurn) {
       mancala.setLeftTurn(isLeftTurn);
       mancala.play(pitListIndex);
    }

    private void addErrorMessageToModel(Model model, int chosenIndex) {
       model.addAttribute("errorMessage", String.format("The chosen pit %s contains no stones "
       + "please select a different pit", chosenIndex + 1));
    }

    private void addAttributesToModel(Model model) {
       MancalaData mancalaData = mancalaService.getMancalaData(mancala);
       model.addAttribute("mancalaData", mancalaData);
       model.addAttribute("leftTurn", mancala.isLeftTurn());
    }

}

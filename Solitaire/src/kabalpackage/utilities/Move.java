package kabalpackage.utilities;

import java.util.ArrayList;
import kabalpackage.Card;
import kabalpackage.SolitaireStack;
import kabalpackage.Stack;

public class Move{
    private Stack SRC_STACK;
    private Stack DST_STACK;
    private ArrayList<Card> CARDS_MOVED;

    public Move(Stack SRC_STACK, Stack DST_STACK, ArrayList<Card> CARDS_MOVED){
        this.SRC_STACK = SRC_STACK;
        this.DST_STACK = DST_STACK;
        this.CARDS_MOVED = CARDS_MOVED;
    }

    public void undoMove(){
        if (((this.SRC_STACK instanceof SolitaireStack)) && (!this.SRC_STACK.isEmpty())) {
            this.SRC_STACK.getTopCard().defaceCard();
        }
        
        this.SRC_STACK.addCards(this.CARDS_MOVED);
        this.DST_STACK.removeCards(this.CARDS_MOVED);
    }
}

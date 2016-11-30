package kabalpackage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import kabalpackage.utilities.LayoutVariables;

public class Foundation extends Stack{
    private String type = "foundation";
    private ArrayList<Card> cards = new ArrayList();

    public Foundation() {}

    public Foundation(ArrayList<Card> c){
        for (Card card : c) {
            add(card);
        }
    }

    public String getType(){
        return this.type;
    }

    public boolean isEmpty(){
        return this.cards.isEmpty();
    }

    public boolean isFull(){
      return this.cards.size() == 13;
    }

    public Card getTopCard(){
        if (this.cards.size() > 0)
            return (Card)this.cards.get(this.cards.size() - 1);
        return null;
    }

    public ArrayList<Card> getAvailableCards(){
        ArrayList<Card> ret = new ArrayList();
        ret.add(getTopCard());
        return ret;
    }

    public ArrayList<Card> getAvailableCardsAt(Card card){
        ArrayList<Card> ret = new ArrayList();
        ret.add(card);
        return ret;
    }

    public void addSingleCard(Card card){
        this.cards.add(card);
        card.setBounds(0, 0, 79, 123);

        add(card, 0);
    }

    public void addCards(ArrayList<Card> c){
        for (Card card : c) {
            this.cards.add(card);
            card.setBounds(0, 0, 79, 123);

            add(card, 0);
        }
    }

    public void removeCards(ArrayList<Card> c){
        this.cards.removeAll(c);

        this.cards.trimToSize();
        for (Card card : c) {
            remove(card);
        }
    }

    public void removeSingleCard(Card card){
        this.cards.remove(card);
        remove(card);

        repaint();
    }

    public void hideCards(ArrayList<Card> card){
        removeSingleCard((Card)card.get(0));
    }

    public void showCards(ArrayList<Card> card){
        addSingleCard((Card)card.get(0));
    }

    public boolean isValidMove(Card card){
        if ((isEmpty()) && (card.getNumber() == 1)) return true;
        if ((!isEmpty()) && (card.getType().equals(getTopCard().getType())) && (card.getNumber() == getTopCard().getNumber() + 1)){
            return true;
        }

        return false;
    }

    private boolean HIGHLIGHT = false;

    public void highlight(boolean bool){
        this.HIGHLIGHT = bool;
        if (isEmpty()) {
            repaint();
        }else{
            getTopCard().highlight(Boolean.valueOf(bool));
            getTopCard().repaint();
        }
    }

    public boolean isHighlighted(){
        return this.HIGHLIGHT;
    }

    protected void paintComponent(Graphics graphics){
        Graphics2D g = (Graphics2D)graphics.create();

        if (!this.HIGHLIGHT) {
            g.setColor(LayoutVariables.PLACEHOLDER_COLOR);
            g.setComposite(LayoutVariables.PLACEHOLDER_ALPHA);
            g.fillRect(0, 0, 79, 123);
        }else{
            g.setColor(LayoutVariables.PLACEHOLDER_COLOR);
            g.setComposite(LayoutVariables.PLACEHOLDER_ALPHA_HIGHLIGHT);
            g.fillRect(0, 0, 79, 123);
        }

        g.dispose();
    }
}
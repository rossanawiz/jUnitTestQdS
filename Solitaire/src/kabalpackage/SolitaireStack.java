package kabalpackage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import kabalpackage.utilities.LayoutVariables;

public class SolitaireStack extends Stack{
    private String type = "solitaire stack";
    public  ArrayList<Card> cards = new ArrayList();
    private final int CARD_SPACING = 25;
    private final int CARD_WIDTH = 79;
    private final int CARD_HEIGHT = 123;

    public SolitaireStack(Deck deck, int cardcount){
        for (int i = 0; i < cardcount; i++){
            Card tmpCard = deck.getCardAt(0);
            tmpCard.setBounds(0, i * 25, 79, 123);
            add(tmpCard, 0);
            this.cards.add(tmpCard);

            if (i >= cardcount - 1) { 
                tmpCard.setTurned();
            }

            deck.removeCard(deck.getCardAt(0));
        }
    }

    public SolitaireStack(ArrayList<Card> cardsIn){
        setBackground(null);
        setLayout(null);

        for (int i = 0; i < cardsIn.size(); i++) {
            Card tmpCardSrc = (Card)cardsIn.get(i);
            Card tmpCardDst = tmpCardSrc.makeCopy();
            this.cards.add(tmpCardDst);

            tmpCardDst.setBounds(0, i * 25, 79, 123);
            add(tmpCardDst, 0);
        }
    }

    public SolitaireStack() {}

    public ArrayList<Card> getAvailableCardsAt(Card card){
        ArrayList<Card> ret = new ArrayList();
        
        if (!card.isTurned()) {
            return null;
        }

        int cardIdx = this.cards.lastIndexOf(card);
        
        if (cardIdx > -1) {
            for (int i = 0; i < this.cards.size() - cardIdx; i++) {
                ret.add(this.cards.get(cardIdx + i));
            }
        }

        return ret;
    }

    public String getType(){
        return this.type;
    }

    public ArrayList<Card> getAvailableCards(){
        ArrayList<Card> ret = new ArrayList();

        for (int i = 0; i < this.cards.size(); i++) {
            if (((Card)this.cards.get(i)).isTurned()) {
                ret.add(this.cards.get(i));
            }
        }
        
        if (ret.size() < 1) return null;
        return ret;
    }

    public Card getTopCard(){
        return (Card)this.cards.get(this.cards.size() - 1);
    }

    public void addSingleCard(Card card) {}

    public void addCards(ArrayList<Card> c){
        int topCardYPos = 0;
        int i; 
        
        if (!isEmpty()){
            topCardYPos = (int)getTopCard().getBounds().getY();
            i = 1;
            for (Card card : c){
                card.setBounds(0, topCardYPos + i * 25, 79, 123);

                this.cards.add(this.cards.size(), card);
                add(card, 0);
                i++;
            }
        }else{
            this.cards.clear();
            i = 0;
            for (Card card : c){
                card.setBounds(0, topCardYPos + i * 25, 79, 123);

                this.cards.add(this.cards.size(), card);
                add(card, 0);
                i++;
            }
        }
        
        trimToSize();
    }

    public void removeCards(ArrayList<Card> c){
        this.cards.removeAll(c);
        this.cards.trimToSize();
        for (Card card : c) {
            remove(card);
        }

        if (this.cards.size() > 0) {
            getTopCard().setTurned();
        }
        
        trimToSize();
    }

    public void removeSingleCard(Card card) {}

    public boolean isValidMove(Card card){
        if ((isEmpty()) && (card.getNumber() == 13)) return true;
        if ((!isEmpty()) && (card.getNumber() == getTopCard().getNumber() - 1)) {
            if (getTopCard().getType().equals(card.getType())) return false;
            if (((getTopCard().getType().equals("diamonds")) || (getTopCard().getType().equals("hearts"))) && ((card.getType().equals("clubs")) || (card.getType().equals("spades")))){
                return true; 
            }
            
            if (((getTopCard().getType().equals("clubs")) || (getTopCard().getType().equals("spades"))) && ((card.getType().equals("diamonds")) || (card.getType().equals("hearts")))){
                return true; 
            }
        }
        
        return false;
    }

    public boolean isEmpty(){
        return this.cards.isEmpty();
    }
    
    public boolean isFull(){
        return false;
    }

    public void hideCards(ArrayList<Card> cards){
        for (Card c : cards) {
            remove(c);
        }
    }

    public void showCards(ArrayList<Card> cards){
        int i = 1;
        for (Card c : cards) {
            int topCardYPos = (int)getTopCard().getBounds().getY();
            c.getBounds().setLocation(0, topCardYPos + i * 25);
            add(c, 0);
            i++;
        }
    }

    public void trimToSize(){
        if (this.cards.size() != 0) {
            setSize(79, 123 + this.cards.size() * 25 - 25);
        }else {
            setSize(79, 123);
        }
    }

    private boolean HIGHLIGHT = false;

    public void highlight(boolean bool){
        this.HIGHLIGHT = bool;
        
        if (isEmpty()) {
            repaint();
        }else {
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
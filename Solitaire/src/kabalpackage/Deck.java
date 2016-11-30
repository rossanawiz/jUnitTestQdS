package kabalpackage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JLabel;
import kabalpackage.utilities.CardImageMaker;
import kabalpackage.utilities.LayoutVariables;

public class Deck extends JLabel implements Serializable{
    private CardImageMaker cim;
    private CardImageMaker cim2;
    private final String[] types = { "hearts", "clubs", "diamonds", "spades" };
    public  Card[][] deck = new Card[4][13];
    public  ArrayList<Card> deckArrayList = new ArrayList();
    private final int CARD_WIDTH = 79;
    private final int CARD_HEIGHT = 123;
    private final int STACK_SPACING = 20;

    public Deck(CardImageMaker cim, CardImageMaker cim2){
        setBackground(null);
        setLayout(null);

        this.cim = cim;
        this.cim2 = cim2;

        BufferedImage image = cim.cropToCard("back", 2);

        String type = null;

        for (int i = 0; i < 4; i++){
            if (i == 0) type = "hearts";
            if (i == 1) type = "clubs";
            if (i == 2) type = "diamonds";
            if (i == 3) { type = "spades"; }
            
            for (int j = 0; j < 13; j++) {
                BufferedImage turnedImage = cim.cropToCard(type, j);
                BufferedImage overImage = cim2.cropToCard(type, j);
                this.deck[i][j] = new Card(this.types[i], j + 1, image, turnedImage, overImage, 79, 123);
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                this.deckArrayList.add(this.deck[i][j]);
            }
        }

        Collections.shuffle(this.deckArrayList);
    }

    public boolean isEmpty(){
        return this.deckArrayList.isEmpty();
    }    

    public int getCardCount(){
        return this.deckArrayList.size();
    }

    public Card getCardAt(int idx){
        return (Card)this.deckArrayList.get(idx);
    }

    public void removeCard(Card c){
        remove(c);
        this.deckArrayList.remove(c);
    }

    public void removeCards(ArrayList<Card> c){
        for(Card card : c) {
            this.deckArrayList.remove(card);
            remove(card);
            repaint();
        }

        if (this.idx > 0) { this.idx -= 1; }
    }

    public void addCards(ArrayList<Card> cards){
        for (Card card : cards) {
            this.deckArrayList.add(card);
        }
    }

    private int idx = 0;

    public ArrayList<Card> getCards(int cardCount){
        if (this.idx == 0) { showAsFull(); }

        int i = 0;

        if (cardCount == 3){
            if (this.idx <= this.deckArrayList.size() - 3) { 
                i = 3;
            } else if (this.idx == this.deckArrayList.size() - 2) { 
                i = 2;
            } else if (this.idx == this.deckArrayList.size() - 1) { 
                i = 1;
            } else {
                return null;
            }
        } else {
            i = cardCount;
        }

        ArrayList<Card> ret = new ArrayList();

        for (int j = this.idx; j < this.idx + i; j++) {
            ret.add(this.deckArrayList.get(j));
        }

        this.idx += i;
        
        if (this.idx == this.deckArrayList.size()) {
            showAsEmpty();
            this.idx = 0;
        }

        return ret;
    }

    private boolean IS_EMPTY = false;

    public void showAsEmpty(){
        this.IS_EMPTY = true;
        repaint();
    }

    public void showAsFull(){
        this.IS_EMPTY = false;
        repaint();
    }

    protected void paintComponent(Graphics graphics){
        Graphics2D g = (Graphics2D)graphics.create();

        if (!this.IS_EMPTY) {
            BufferedImage image = this.cim.getCardBack();
            g.drawImage(image, 0, 0, null);
        }else{
            g.setColor(LayoutVariables.PLACEHOLDER_COLOR);
            g.setComposite(LayoutVariables.PLACEHOLDER_ALPHA);
            g.fillRect(0, 0, 79, 123);
        }

        g.dispose();
    }
}
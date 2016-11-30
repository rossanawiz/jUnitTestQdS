package kabalpackage;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import kabalpackage.utilities.LayoutVariables;

public class DealtCardsStack extends Stack{
    private String type = "dealt cards";
    private Deck deck;
    private final int CARD_WIDTH = 79;
    private final int CARD_HEIGHT = 123;
    private final int CARD_YSPACING = 20;
    private ArrayList<Card> cards = new ArrayList();
  
    public DealtCardsStack(Deck deck){
        this.deck = deck;
    }
  
    public String getType(){
        return this.type;
    }
  
    public boolean isEmpty(){
        return this.cards.size() == 0;
    }
  
    public boolean isFull(){
        return this.cards.size() == 3;
    }
  
    public int getCardCount(){
        return this.cards.size();
    }
  
    public Card getTopCard(){
        return (Card)this.cards.get(this.cards.size() - 1);
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
  
    public void hideCards(ArrayList<Card> cards){
        remove((Component)cards.get(0));
    }
  
    public void showCards(ArrayList<Card> cards){
        ((Card)cards.get(0)).setLocation(this.cards.size() * 20 - 20, 0);
        add((Component)cards.get(0), 0);
    }
  
    public void addSingleCard(Card card){
        add(card);
        this.cards.add(card);
    }
  
    public void removeSingleCard(Card card){
        remove(card);
        this.cards.remove(card);
    }
  
    public void addCards(ArrayList<Card> c){
        int i = 0;
        for (Card card : c) {
            this.cards.add(card);
            card.setBounds(40, 0, 79, 123);
            add(card, 0);
            i++;
        }
        
        trimToSize();
    }
  
    public void removeCards(ArrayList<Card> cards){
        this.cards.removeAll(cards);
        this.deck.removeCards(cards);
        this.cards.trimToSize();
        
        for(Card card : cards){
            remove(card);
        }
        
        trimToSize();
    }
  
    public void addNewCardsFromDeck(int cardCount){
        ArrayList<Card> cardsToAdd = this.deck.getCards(cardCount);

        if(cardsToAdd == null){ 
            return; 
        }
        
        removeAll();
        this.cards.clear();

        for(int i = 0; i < cardsToAdd.size(); i++){
            ((Card)cardsToAdd.get(i)).setBounds(i * 20, 0, 79, 123);
            this.cards.add(cardsToAdd.get(i));
            add((Component)cardsToAdd.get(i), 0);
       }
        
        trimToSize();
    }
  
    public void trimToSize(){
        if (this.cards.size() != 0) {
            setSize(79 + this.cards.size() * 20 - 20, 123);
        }else{
            setSize(79, 123);
        }
    }
  
    public boolean isValidMove(Card card){
        return false;
    }
  
    public void highlight(boolean bool) {}
  
    public boolean isHighlighted(){
        return false;
    }
  
    protected void paintComponent(Graphics graphics){
        Graphics2D g = (Graphics2D)graphics.create();
        g.setColor(LayoutVariables.PLACEHOLDER_COLOR);
        g.setComposite(LayoutVariables.PLACEHOLDER_ALPHA);
        g.fillRect(0, 0, 79, 123);

        g.dispose();
    }
}
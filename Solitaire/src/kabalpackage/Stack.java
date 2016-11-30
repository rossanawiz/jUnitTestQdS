package kabalpackage;

import java.util.ArrayList;

public abstract class Stack extends javax.swing.JLayeredPane{
    public abstract String getType();

    public abstract boolean isEmpty();

    public abstract boolean isFull();

    public abstract Card getTopCard();

    public abstract ArrayList<Card> getAvailableCards();

    public abstract ArrayList<Card> getAvailableCardsAt(Card paramCard);

    public abstract void hideCards(ArrayList<Card> paramArrayList);

    public abstract void showCards(ArrayList<Card> paramArrayList);

    public abstract void addSingleCard(Card paramCard);

    public abstract void removeSingleCard(Card paramCard);

    public abstract void addCards(ArrayList<Card> paramArrayList);

    public abstract void removeCards(ArrayList<Card> paramArrayList);

    public abstract boolean isValidMove(Card paramCard);

    public abstract void highlight(boolean paramBoolean);

    public abstract boolean isHighlighted();

    public final void transform(java.awt.Point pp, java.awt.Point p) {
        setLocation(pp.x - p.x, pp.y - p.y);
    }
}
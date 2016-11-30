package kabalpackage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.VolatileImage;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import kabalpackage.utilities.CardImageMaker;
import kabalpackage.utilities.HighScore;
import kabalpackage.utilities.LayoutVariables;
import kabalpackage.utilities.Move;
import kabalpackage.utilities.SolTimer;
import kabalpackage.utilities.SoundPlayer;
import kabalpackage.utilities.VolatileImageLoader;

public class GameArea extends javax.swing.JPanel{
    private final int KABAL_STACK_COUNT = 7;
    private final int SEQ_STACK_COUNT = 4;
    private final int STACK_SPACING = 20;
    private final int STACK_XPOS_START = 60;
    private final int SEQ_STACK_XPOS_START = 357;
    private final int STACK_YPOS_START = 178;
    private final int SEQ_STACK_YPOS_START = 35;
    private final int CARD_WIDTH = 79;
    private final int CARD_HEIGHT = 123;
    private final int CARD_SPACING = 25;
    private final Color BACKGROUND_COLOR = LayoutVariables.BACKGROUND_COLOR;
    private boolean GAME_STARTED = false;
    private boolean GAME_PAUSED = false;
    private int CARDS_TO_DEAL_COUNT = 3;
    private String cardsFile = "bondedcards.png";
    private CardImageMaker cim;
    private String cardsFile2 = "bondedcards-over.png";
    private CardImageMaker cim2;
    private Deck deck;
    private DealtCardsStack dealtCards;
    private Stack[] stacks = new Stack[12];
    private ArrayList<Move> moves = new ArrayList();
    private SolTimer timerComponent = new SolTimer();
    private Thread timerComponentThread;
    private HighScore highScore = null;
    private VolatileImage background;

    public GameArea(){
        setLayout(null);
        setBackground(this.BACKGROUND_COLOR);
        setCursor(new Cursor(12));

        loadImages();
    }

    public void loadImages(){
        try{
            this.cim = new CardImageMaker(this.cardsFile, 79, 123);
            this.cim2 = new CardImageMaker(this.cardsFile2, 79, 123);
        }catch (IOException IOe){
            JOptionPane.showMessageDialog(this, "Could not load card images!", "Error!", 0);

            System.err.println("Could not load card images!");
            System.exit(1);
        }catch (IllegalArgumentException iae){
            JOptionPane.showMessageDialog(this, "Could not load card images!Exiting!", "Error!", 0);

            System.err.println("Could not load card images!");
            System.exit(1);
        }

        try{
            this.background = VolatileImageLoader.loadFromFile(getClass().getResourceAsStream("images/bgpleasant.png"), 1);
        }catch (IOException ioe){
            JOptionPane.showMessageDialog(this, "Could not load background image!", "Error!", 0);

            System.err.println("Could not load background image!");
        }
    }

    public void changeBackground(int bgvalue){
        try{
            this.background = VolatileImageLoader.loadFromFile(getClass().getResourceAsStream("images/" + LayoutVariables.fileNames[bgvalue]), 1);
        }catch (IOException ioe){
            JOptionPane.showMessageDialog(this, "Could not load background image!", "Error!", 0);
            System.err.println("Could not load background image!");
        }
        
        repaint();
    }

    private boolean SOUND_ON = true;

    public void toggleSoundEffects(boolean bool){
        this.SOUND_ON = bool;
    }
    
    public void playSound(){
        SoundPlayer soundPlayer = new SoundPlayer();
        Thread soundThread = new Thread(soundPlayer);
        soundThread.start();
        soundPlayer.playSound();
    }

    public void setCardsToDealCount(int cardCount){
        this.CARDS_TO_DEAL_COUNT = cardCount;
    }

    public void newGame(){
        removeAll();

        MouseHandler mh = new MouseHandler();

        this.deck = new Deck(this.cim, this.cim2);
        this.deck.addMouseListener(new DeckListener());
        this.dealtCards = new DealtCardsStack(this.deck);
        this.dealtCards.addMouseListener(mh);
        this.dealtCards.addMouseMotionListener(mh);
        this.deck.setBounds(60, 35, 79, 123);

        add(this.deck);
        this.dealtCards.setBounds(160, 35, 119, 123);

        add(this.dealtCards);

        this.stacks[(this.stacks.length - 1)] = this.dealtCards;

        Foundation seqToAdd = null;
        for (int i = 0; i < 4; i++) {
            seqToAdd = new Foundation();
            seqToAdd.setBounds(357 + 20 * i + i * 79, 35, 79, 123);

            seqToAdd.addMouseListener(mh);
            seqToAdd.addMouseMotionListener(mh);
            add(seqToAdd);
            this.stacks[i] = seqToAdd;
        }

        SolitaireStack toAdd = null;
        for (int i = 0; i < 7; i++){
            toAdd = new SolitaireStack(this.deck, i + 1);
            toAdd.setBounds(60 + 20 * i + i * 79, 178, 79, i * 25 + 123);

            toAdd.addMouseListener(mh);
            toAdd.addMouseMotionListener(mh);

            add(toAdd);
            this.stacks[(4 + i)] = toAdd;
        }
        
        for (int i = 0; i < this.deck.getCardCount(); i++) {
            this.deck.getCardAt(i).setTurned();
        }

        setGameStarted(Boolean.valueOf(false));

        if(this.timerComponentThread != null){
            resetTimer();
        }else{
            this.timerComponent.setBounds(0, 0, 800, 23);
            this.timerComponentThread = new Thread(this.timerComponent);
            this.timerComponentThread.start();
        }
        
        add(this.timerComponent);

        repaint();
    }

    public void setGameStarted(Boolean b){
        this.GAME_STARTED = b.booleanValue();
    }
    
    public boolean gameHasStarted(){
        return this.GAME_STARTED;
    }

    public boolean gameIsPaused(){
        return this.GAME_PAUSED;
    }

    public void resumeTimer(){
        this.GAME_PAUSED = false;
        this.timerComponent.resumeTimer();
    }

    public void pauseTimer(){
        this.GAME_PAUSED = true;
        this.timerComponent.pauseTimer();
    }

    public void resetTimer(){
        this.timerComponent.resetTimer();
    }

    public void undoMove(){
        if (this.moves.size() > 0) {
            ((Move)this.moves.get(this.moves.size() - 1)).undoMove();
            this.moves.remove(this.moves.size() - 1);
            repaint();
        }
    }

    private boolean GAME_OVER = false;

    public void gameOver(){
        int fullCount = 0;
        for (Stack stack : this.stacks) {
            if (((stack instanceof Foundation)) && 
                (stack.isFull())) { fullCount++; }
        }

        if (fullCount == 4){
            for (Stack stack : this.stacks) {
                if ((stack instanceof Foundation)) {
                    stack.removeMouseListener(stack.getMouseListeners()[0]);
                    stack.removeMouseMotionListener(stack.getMouseMotionListeners()[0]);
                }
            }
            
            this.timerComponent.gameOver();
            this.GAME_OVER = true;
            this.highScore = new HighScore(this.timerComponent.getTime());
        }
    }

    public boolean isGameOver() {
        return this.GAME_OVER;
    }

    public void presentHighScore(){
        this.highScore = new HighScore(-1);
    }
    
    public void hint(){
        ArrayList<String> possibleMoves = new ArrayList();

        if (isGameOver()) {
            JOptionPane.showMessageDialog(this, "Start new game", "Possible moves", -1);
        }

        for (Stack SRC_STACK : this.stacks) {
            if ((SRC_STACK != null) && (!SRC_STACK.isEmpty())) {
                ArrayList<Card> TMP_TURNED_CARDS = SRC_STACK.getAvailableCards();
                if (TMP_TURNED_CARDS != null) {
                    for (Card TMP_CARD : TMP_TURNED_CARDS) {
                        for (Stack DST_STACK : this.stacks) {
                            if ((DST_STACK != null) && (!SRC_STACK.equals(DST_STACK)) && (DST_STACK.isValidMove(TMP_CARD)) && ((!(DST_STACK instanceof Foundation)) || (TMP_CARD.equals(SRC_STACK.getTopCard())))){
                                if (!DST_STACK.isEmpty()) {
                                    Card DST_CARD = DST_STACK.getTopCard();
                                    possibleMoves.add("You can move " + TMP_CARD.getName() + " of " + TMP_CARD.getType() + " to " + DST_CARD.getName() + " of " + DST_CARD.getType() + ".");
                                }else{
                                    if (((SRC_STACK instanceof Foundation)) && (DST_STACK.getType().equals(SRC_STACK.getType()))) {
                                        break;
                                    }
                                    
                                    possibleMoves.add("You can move " + TMP_CARD.getName() + " of " + TMP_CARD.getType() + " to empty " + DST_STACK.getType() + ".");

                                    if (((SRC_STACK instanceof SolitaireStack)) && (DST_STACK.getType().equals(SRC_STACK.getType()))) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if ((possibleMoves.isEmpty()) && (!this.deck.isEmpty())) {
            possibleMoves.add("Deal new cards from deck.");
        }

        String stringOut = "";
        for (String string : possibleMoves) {
            stringOut = stringOut + "\n" + string;
        }
        
        JOptionPane.showMessageDialog(this, stringOut, "Possible moves", -1);
    }

    protected void paintComponent(Graphics graphics){
        Graphics2D g = (Graphics2D)graphics.create();

        if (this.background != null) {
            g.drawImage(this.background, 0, 0, null);
        }else{
            g.setColor(LayoutVariables.BACKGROUND_COLOR);
            g.fillRect(0, 0, 800, 600);
        }
        
        for (int i = 0; i < 800; i++) {
            g.setColor(Color.BLACK);
            g.drawLine(0, i * 4, i * 4, 0);
        }
        
        g.dispose();
      }


    private final class MouseHandler extends javax.swing.event.MouseInputAdapter{
        Card CLICKED_CARD;
        Stack SRC_STACK;
        Stack DST_STACK;
        private Stack TMP_STACK;
        private ArrayList<Card> TMP_LIST = new ArrayList();
        private Point p;
        private Point pp;
        private boolean CLICK_OK = false;
        
        private MouseHandler() {}

        public void mouseMoved(MouseEvent e){
            this.p = e.getPoint();
            this.pp = SwingUtilities.convertPoint(this.SRC_STACK, e.getPoint(), GameArea.this);
        }

        public void mousePressed(MouseEvent e){
            this.SRC_STACK = ((Stack)e.getSource());

            if ((e.getButton() != 1) || (this.SRC_STACK.isEmpty())) {
                this.CLICK_OK = false;
                return;
            }
            
            this.pp = SwingUtilities.convertPoint(this.SRC_STACK, this.p, GameArea.this);
            this.CLICKED_CARD = ((Card)this.SRC_STACK.getComponentAt(this.p));
            this.TMP_LIST = this.SRC_STACK.getAvailableCardsAt(this.CLICKED_CARD);

            if ((this.CLICKED_CARD != null) && (this.CLICKED_CARD.isTurned()) && (!(this.SRC_STACK instanceof DealtCardsStack))){
                this.CLICK_OK = true;
            }else if (((this.SRC_STACK instanceof DealtCardsStack)) && (this.CLICKED_CARD != null) && (this.CLICKED_CARD.equals(this.SRC_STACK.getTopCard()))){
                this.CLICK_OK = true;
            }else {
                this.CLICK_OK = false;
                return;
            }

            if (!GameArea.this.gameHasStarted()) {
                GameArea.this.resumeTimer();
                GameArea.this.setGameStarted(Boolean.valueOf(true));
            }
            if ((GameArea.this.gameHasStarted()) && (GameArea.this.gameIsPaused())) {
                GameArea.this.resumeTimer();
            }

            if ((this.SRC_STACK instanceof SolitaireStack)) {
                this.TMP_STACK = new SolitaireStack(this.TMP_LIST);
            }
            
            if ((this.SRC_STACK instanceof Foundation)) {
                this.TMP_STACK = new Foundation(this.TMP_LIST);
            }
            
            if ((this.SRC_STACK instanceof DealtCardsStack)) {
                ((Card)this.TMP_LIST.get(0)).setLocation(0, 0);
                this.TMP_STACK = new Foundation(this.TMP_LIST);
            }

            this.TMP_STACK.setBounds((int)this.CLICKED_CARD.getBounds().getX(), (int)this.CLICKED_CARD.getBounds().getY(), 79, 700);

            GameArea.this.add(this.TMP_STACK, 0);

            int yShift = 0;
            if ((this.SRC_STACK instanceof DealtCardsStack)) {
                DealtCardsStack tmp = (DealtCardsStack)this.SRC_STACK;
                yShift = tmp.getCardCount() - 1;
            }

            this.TMP_STACK.transform(new Point((int)(this.pp.getX() + this.CLICKED_CARD.getBounds().getX()) + yShift * 20, (int)(this.pp.getY() + this.CLICKED_CARD.getBounds().getY())), this.p);
            this.SRC_STACK.hideCards(this.TMP_LIST);
        }

        public void mouseDragged(MouseEvent e){
            if (!this.CLICK_OK) { 
                return;
            }
            
            this.pp = SwingUtilities.convertPoint(this.SRC_STACK, e.getPoint(), GameArea.this);

            GameArea.this.setCursor(new Cursor(13));

            int yShift = 0;
            if ((this.SRC_STACK instanceof DealtCardsStack)) {
                DealtCardsStack tmp = (DealtCardsStack)this.SRC_STACK;
                yShift = tmp.getCardCount() - 1;
            }

            Point newLocation = new Point((int)(this.pp.getX() - this.CLICKED_CARD.getBounds().getX()) + yShift * 20, (int)(this.pp.getY() + this.CLICKED_CARD.getBounds().getY()));

            this.TMP_STACK.transform(newLocation, this.p);

            for (Stack stack : GameArea.this.stacks){
                Point tp = SwingUtilities.convertPoint(GameArea.this, this.pp, stack);

                if ((stack.contains(tp)) && (!stack.equals(this.SRC_STACK)) && ((!(stack instanceof Foundation)) || (this.TMP_LIST.size() <= 1))){
                    if ((!stack.isHighlighted()) && (stack.isValidMove(this.CLICKED_CARD))){
                        stack.highlight(true);
                    }
                }else if (((stack instanceof SolitaireStack)) || ((stack instanceof Foundation))){
                    if (stack.isHighlighted()) {
                        stack.highlight(false);
                    }
                }
            }
        }

        public void mouseClicked(MouseEvent e){
            System.out.println("Mouse Clicked");
            int clickCount = e.getClickCount();

            if ((clickCount == 2) && (this.CLICK_OK)){
                if (!GameArea.this.gameHasStarted()) {
                    GameArea.this.resumeTimer();
                    GameArea.this.setGameStarted(Boolean.valueOf(true));
                }
                
                if ((GameArea.this.gameHasStarted()) && (GameArea.this.gameIsPaused())) {
                    GameArea.this.resumeTimer();
                }

                for (Stack stack : GameArea.this.stacks) {
                    if (((stack instanceof Foundation)) && (stack.isValidMove(this.CLICKED_CARD)) && (this.SRC_STACK.getTopCard().equals(this.CLICKED_CARD))){
                        ArrayList<Card> cards = new ArrayList();
                        cards.add(this.CLICKED_CARD);
                        stack.addCards(cards);
                        this.SRC_STACK.removeCards(cards);

                        if (GameArea.this.SOUND_ON) {
                            GameArea.this.playSound();
                        }

                        if ((this.SRC_STACK instanceof DealtCardsStack)) {
                            GameArea.this.moves.clear();
                        }else{
                            GameArea.this.moves.add(new Move(this.SRC_STACK, stack, this.TMP_LIST));
                        }

                        GameArea.this.gameOver();
                        return;
                    }
                }
            }
        }

        public void mouseReleased(MouseEvent e){
            if ((e.getButton() != 1) || (!this.CLICK_OK)) { 
                return;
            }

            GameArea.this.remove(this.TMP_STACK);
            GameArea.this.repaint();

            GameArea.this.setCursor(new Cursor(12));

            try{
                this.DST_STACK = ((Stack)GameArea.this.getComponentAt(this.pp));
            }catch (ClassCastException cce){
                this.SRC_STACK.showCards(this.TMP_LIST);
                return;
            }

            if((this.DST_STACK != null) && (this.DST_STACK.isValidMove(this.CLICKED_CARD)) && ((!(this.DST_STACK instanceof Foundation)) || (this.TMP_LIST.size() <= 1))){
                if (GameArea.this.SOUND_ON) {
                    GameArea.this.playSound();
                }

                this.SRC_STACK.removeCards(this.TMP_LIST);
                this.DST_STACK.highlight(false);
                this.DST_STACK.addCards(this.TMP_LIST);

                if((this.DST_STACK instanceof Foundation)){
                    GameArea.this.gameOver();
                }

                if ((this.SRC_STACK instanceof DealtCardsStack)) {
                    GameArea.this.moves.clear();
                }else{
                    GameArea.this.moves.add(new Move(this.SRC_STACK, this.DST_STACK, this.TMP_LIST));
                }
            }else{
                this.SRC_STACK.showCards(this.TMP_LIST);
            }
        }
    }

    private final class DeckListener implements java.awt.event.MouseListener { private DeckListener() {}

        private boolean CLICK_OK = false;

        public void mousePressed(MouseEvent e) {
            this.CLICK_OK = true;
        }

        public void mouseReleased(MouseEvent e){
            System.out.println("Mouse Released");
            if (!this.CLICK_OK) { 
                return;
            }

            if(GameArea.this.deck.isEmpty()) {
                GameArea.this.deck.showAsEmpty();
                GameArea.this.deck.removeMouseListener(this);
                return;
            }

            if (GameArea.this.SOUND_ON) {
                GameArea.this.playSound();
            }

            if (!GameArea.this.gameHasStarted()) {
                GameArea.this.resumeTimer();
                GameArea.this.setGameStarted(Boolean.valueOf(true));
            }
            
            if ((GameArea.this.gameHasStarted()) && (GameArea.this.gameIsPaused())) {
                GameArea.this.resumeTimer();
            }

            GameArea.this.dealtCards.addNewCardsFromDeck(GameArea.this.CARDS_TO_DEAL_COUNT);
        }

        public void mouseClicked(MouseEvent e) {}

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}
    }
}
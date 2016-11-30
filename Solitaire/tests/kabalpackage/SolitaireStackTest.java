package kabalpackage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import kabalpackage.utilities.CardImageMaker;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Franciel Krein
 * @author Luiz A Richter
 */
public class SolitaireStackTest {
    
    Deck deck;
    SolitaireStack stacks;
    CardImageMaker cim, cim2;
    BufferedImage image;
    Card teste;

    @Before
    public void setUp() {
        
        deck = null;
        stacks = null;
        image = null;
        
        try{
            this.cim = new CardImageMaker("bondedcards.png", 79, 123);
            this.cim2 = new CardImageMaker("bondedcards-over.png", 79, 123);
        }catch (IOException IOe){
            fail("Could not load card images!");
        }catch (IllegalArgumentException iae){
            fail("Could not load card images!");
        }
        
        this.image = cim.cropToCard("back", 2);
        this.deck = new Deck(cim, cim2);
        
        BufferedImage turnedImage = cim.cropToCard("hearts", 5);
        BufferedImage overImage = cim2.cropToCard("hearts", 5);
        Card c = new Card("hearts", 5, this.image, turnedImage, overImage, 79, 123);
        
        this.deck.deckArrayList.set(0, c);
        this.stacks = new SolitaireStack(this.deck, 1);

    }

    @Test
    public void testIsValidMove() {
        System.out.println("isValidMove");
        
        BufferedImage turnedImage = cim.cropToCard("spades", 5);
        BufferedImage overImage = cim2.cropToCard("spades", 5);
        teste = new Card("spades", 5, this.image, turnedImage, overImage, 79, 123);
        
        assertNotNull("Stack de cartas não foi criado corretamente.", this.stacks);
        
        if(this.stacks.cards.get(0).number == (teste.number+1)){
            if((this.stacks.cards.get(0).type.equals("hearts") || this.stacks.cards.get(0).type.equals("diamonds")) && (teste.type.equals("clubs") || teste.type.equals("spades"))) {
                assertTrue("Teste retornou um valor incorreto.", this.stacks.isValidMove(teste));
            } else if((this.stacks.cards.get(0).type.equals("clubs") || this.stacks.cards.get(0).type.equals("spades")) && (teste.type.equals("hearts") || teste.type.equals("diamonds"))){
                assertTrue("Teste retornou um valor incorreto.", this.stacks.isValidMove(teste));
            } else {
                assertFalse("Teste retornou um valor incorreto.", this.stacks.isValidMove(teste));
            }
        } else {
            assertFalse("Teste retornou um valor incorreto.", this.stacks.isValidMove(teste));
        }
    }
    
}

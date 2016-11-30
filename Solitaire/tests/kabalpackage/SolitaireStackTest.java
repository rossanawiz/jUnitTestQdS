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
    Deck deck = null;
    SolitaireStack stacks = null;
    CardImageMaker cim, cim2;
    BufferedImage image = null;
    
    public SolitaireStackTest() {
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
        
        BufferedImage turnedImage = cim.cropToCard("hearts", 3);
        BufferedImage overImage = cim2.cropToCard("hearts", 3);
        Card c = new Card("hearts", 3, this.image, turnedImage, overImage, 79, 123);
        
        this.deck.deckArrayList.set(0, c);
        this.stacks = new SolitaireStack(this.deck, 1);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testIsValidMove() {
        System.out.println("isValidMove");
        
        BufferedImage turnedImage = cim.cropToCard("hearts", 2);
        BufferedImage overImage = cim2.cropToCard("hearts", 2);
        Card teste = new Card("hearts", 3, this.image, turnedImage, overImage, 79, 123);
        
        assertNotNull("Stack de cartas não foi criado corretamente.", this.stacks);
        
        if(this.stacks.cards.get(0).number == (teste.number+1)){
            if((this.stacks.cards.get(0).type.equals("hearts") || this.stacks.cards.get(0).type.equals("diamonds")) && (teste.type.equals("clubs") || teste.type.equals("spades"))) 
                assertTrue("Teste retornou um valor incorreto.", this.stacks.isValidMove(teste));
            else if((this.stacks.cards.get(0).type.equals("clubs") || this.stacks.cards.get(0).type.equals("spades")) && (teste.type.equals("hearts") || teste.type.equals("diamonds")))
                assertTrue("Teste retornou um valor incorreto.", this.stacks.isValidMove(teste));
        }
        
        assertFalse("Teste retornou um valor incorreto.", this.stacks.isValidMove(teste));
    }
    
}

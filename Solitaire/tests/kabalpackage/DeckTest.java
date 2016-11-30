package kabalpackage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
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
public class DeckTest {
    
    Deck deck;
    CardImageMaker cim, cim2;
    String[] types = { "hearts", "clubs", "diamonds", "spades" };
    Card instance, expected;

    @Before
    public void setUp(){
        
        deck = null;
        
        try{
            this.cim = new CardImageMaker("bondedcards.png", 79, 123);
            this.cim2 = new CardImageMaker("bondedcards-over.png", 79, 123);
        }catch (IOException IOe){
            fail("Could not load card images!");
        }catch (IllegalArgumentException iae){
            fail("Could not load card images!");
        }
        
        this.deck = new Deck(cim, cim2);
        
        instance = null;
        expected = null;
    }

    @Test
    public void testDeckInitiation(){
        System.out.println("deckInitiation");
        
        assertNotNull("Deck criado nulo", this.deck);
        
        assertEquals(this.deck.deckArrayList.size(), 52);
        
        BufferedImage image = cim.cropToCard("back", 2);
        
        for(int i=0;i<4;i++){
            for(int j=0;j<13;j++){
                
                instance = this.deck.deck[i][j];
                
                BufferedImage turnedImage = cim.cropToCard(this.types[i], j);
                BufferedImage overImage = cim2.cropToCard(this.types[i], j);
                
                expected = new Card(this.types[i], j + 1, image, turnedImage, overImage, 79, 123);
                
                //É impossível usar assertSame em objetos da classe Deck por que ela possui objetos da classe Card,
                //que possui objetos das classes BufferedImage e VolatileImage
                //Cada instância dessas classes possui um ID único

                assertEquals("Erro na criação do baralho de cartas. Tipo da carta " + i*j + " incorreto.", expected.type, instance.type);
                assertEquals("Erro na criação do baralho de cartas. Número da carta " + i*j + " incorreto.", expected.number, instance.number);
                assertEquals("Erro na criação do baralho de cartas. largura da carta " + i*j + " incorreto.", expected.w, instance.w);
                assertEquals("Erro na criação do baralho de cartas. altura da carta " + i*j + " incorreto.", expected.h, instance.h);
                assertEquals("Erro na criação do baralho de cartas. Status 'turned' da carta " + i*j + " incorreto.", expected.IS_TURNED, instance.IS_TURNED);
                assertEquals("Erro na criação do baralho de cartas. Status 'highlighted' da carta " + i*j + " incorreto.", expected.HIGHLIGHTED, instance.HIGHLIGHTED);

            }
        }
    }
    
    @Test
    public void testDeckSuffle(){
        
        System.out.println("deckSuffle");
        int count = 0;
        
        assertNotNull(this.deck);
        assertEquals(this.deck.deckArrayList.size(), 52);
        
        BufferedImage image = cim.cropToCard("back", 2);
        
        // Chances de as 3 primeiras cartas do baralho não serem embaralhadas = 0.00071119708%
        
        Card first  = new Card(this.types[0], 1, image, cim.cropToCard(this.types[0], 1), cim2.cropToCard(this.types[0], 1), 79, 123);
        Card second = new Card(this.types[0], 2, image, cim.cropToCard(this.types[0], 2), cim2.cropToCard(this.types[0], 2), 79, 123);
        Card third  = new Card(this.types[0], 3, image, cim.cropToCard(this.types[0], 3), cim2.cropToCard(this.types[0], 3), 79, 123);
        
        if(!this.deck.deckArrayList.get(0).type.equals(first.type) || this.deck.deckArrayList.get(0).number != first.number){
            count++;
            if(!this.deck.deckArrayList.get(1).type.equals(second.type) || this.deck.deckArrayList.get(0).number != second.number) {
                count++;
                if(!this.deck.deckArrayList.get(2).type.equals(third.type) || this.deck.deckArrayList.get(0).number != third.number) {
                    count++;
                }
            }
        }
        
        assertSame("Cartas parecem estar em ordem. Pode ter ocorrido uma falha no sistema de embaralhamento das cartas (SHUFFLE).", count, 3);
    }
}

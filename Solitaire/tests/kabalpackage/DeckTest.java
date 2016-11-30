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
    Deck deck = null;
    CardImageMaker cim, cim2;
    String[] types = { "hearts", "clubs", "diamonds", "spades" };
    
    public DeckTest(){
        try{
            this.cim = new CardImageMaker("bondedcards.png", 79, 123);
            this.cim2 = new CardImageMaker("bondedcards-over.png", 79, 123);
        }catch (IOException IOe){
            fail("Could not load card images!");
        }catch (IllegalArgumentException iae){
            fail("Could not load card images!");
        }
        
        this.deck = new Deck(cim, cim2);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp(){
    }
    
    @After
    public void tearDown(){
    }
    
    @Test
    public void testDeckInitiation(){
        System.out.println("deckInitiation");
        assertNotNull(this.deck);
        assertEquals(this.deck.deckArrayList.size(), 52);
        
        Card instance = null, expected = null;
        BufferedImage image = cim.cropToCard("back", 2);
        
        for(int i=0;i<4;i++){
            for(int j=0;j<13;j++){
                instance = this.deck.deck[i][j];
                
                BufferedImage turnedImage = cim.cropToCard(this.types[i], j);
                BufferedImage overImage = cim2.cropToCard(this.types[i], j);
                expected = new Card(this.types[i], j + 1, image, turnedImage, overImage, 79, 123);
               
                //assertSame(expected, instance);
                
                //Setando nulo nas imagens para realizar os testes
                //A classe das imagens cria um ID aleatório para cada imagem, impossibilitando o teste de igualdade
                expected.image = null; instance.image = null;
                expected.backImage = null; instance.backImage = null;
                expected.turnedImage = null; instance.turnedImage = null; 
                expected.overImage = null; instance.overImage = null; 
                expected.vimage = null; instance.vimage = null; 
                expected.backVimImage = null; instance.backVimImage = null; 
                expected.turnedVimImage = null; instance.turnedVimImage = null; 
                expected.overVimImage = null; instance.overVimImage = null; 
                        
                assertEquals("Erro na criação do baralho de cartas. ERRO 01", expected.image, instance.image);
                assertEquals("Erro na criação do baralho de cartas. ERRO 02", expected.backImage, instance.backImage);
                assertEquals("Erro na criação do baralho de cartas. ERRO 03", expected.turnedImage, instance.turnedImage);
                assertEquals("Erro na criação do baralho de cartas. ERRO 04", expected.overImage, instance.overImage);
                assertEquals("Erro na criação do baralho de cartas. ERRO 05", expected.type, instance.type);
                assertEquals("Erro na criação do baralho de cartas. ERRO 06", expected.number, instance.number);
                assertEquals("Erro na criação do baralho de cartas. ERRO 07", expected.w, instance.w);
                assertEquals("Erro na criação do baralho de cartas. ERRO 08", expected.h, instance.h);
                assertEquals("Erro na criação do baralho de cartas. ERRO 09", expected.IS_TURNED, instance.IS_TURNED);
                assertEquals("Erro na criação do baralho de cartas. ERRO 10", expected.HIGHLIGHTED, instance.HIGHLIGHTED);
                assertEquals("Erro na criação do baralho de cartas. ERRO 11", expected.vimage, instance.vimage);
                assertEquals("Erro na criação do baralho de cartas. ERRO 12", expected.backVimImage, instance.backVimImage);
                assertEquals("Erro na criação do baralho de cartas. ERRO 13", expected.turnedVimImage, instance.turnedVimImage);
                assertEquals("Erro na criação do baralho de cartas. ERRO 14", expected.overVimImage, instance.overVimImage);
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
        
        Card first  = new Card(this.types[0], 1, image, cim.cropToCard(this.types[0], 1), cim2.cropToCard(this.types[0], 1), 79, 123);
        Card second = new Card(this.types[0], 2, image, cim.cropToCard(this.types[0], 2), cim2.cropToCard(this.types[0], 2), 79, 123);
        Card third  = new Card(this.types[0], 3, image, cim.cropToCard(this.types[0], 3), cim2.cropToCard(this.types[0], 3), 79, 123);
        
        if(!this.deck.deckArrayList.get(0).type.equals(first.type) || this.deck.deckArrayList.get(0).number != first.number) count++;
        if(!this.deck.deckArrayList.get(1).type.equals(second.type) || this.deck.deckArrayList.get(0).number != second.number) count++;
        if(!this.deck.deckArrayList.get(2).type.equals(third.type) || this.deck.deckArrayList.get(0).number != third.number) count++;
        
        assertNotSame("Cartas parecem estar em ordem, pode ter ocorrido uma falha no sistema de embaralhamento das cartas (SHUFFLE).", count, 0);
    }
}

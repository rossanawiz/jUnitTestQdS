package kabalpackage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import kabalpackage.utilities.CardImageMaker;

/**
 *
 * @author Franciel Krein
 * @author Luiz A Richter
 */
public class CardTest {
    CardImageMaker cim;
    Card instance;
    String fail = "\tFail executing test for ";
    String success = "\tSuccess executing test for ";
    
    public CardTest() {
        try{
            cim = new CardImageMaker("bondedcards.png", 79, 123);
        }catch (IOException IOe){
            fail("Could not load card images!");
        }catch (IllegalArgumentException iae){
            fail("Could not load card images!");
        }
        
        BufferedImage backImage = cim.cropToCard("back", 2);
        BufferedImage turnedImage = cim.cropToCard("hearts", 2);
        BufferedImage overImage = cim.cropToCard("hearts", 2);
                
        this.instance = new Card("spades", 3, backImage, turnedImage, overImage, 100, 100);
    }
    
    @BeforeClass
    public static void setUpClass(){
    }
    
    @AfterClass
    public static void tearDownClass(){
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testMakeCopy(){
        System.out.println("makeCopy");
        
        Card c = instance.makeCopy();
        assertNotNull(instance);
        assertNotNull(c);
        
        assertEquals("Erro ao criar cópia da carta. ERRO 01", instance.type, c.type);
        assertEquals("Erro ao criar cópia da carta. ERRO 02", instance.number, c.number);
        assertEquals("Erro ao criar cópia da carta. ERRO 03", instance.w, c.w);
        assertEquals("Erro ao criar cópia da carta. ERRO 04", instance.h, c.h);
        assertEquals("Erro ao criar cópia da carta. ERRO 05", instance.IS_TURNED, c.IS_TURNED);
        assertEquals("Erro ao criar cópia da carta. ERRO 06", instance.HIGHLIGHTED, c.HIGHLIGHTED);
    }
    
    @Test
    public void testSetTurned(){
        System.out.println("setTurned");
        
        if(instance.IS_TURNED)
            instance.IS_TURNED = false;
        
        instance.setTurned();
        
        assertTrue("Carta não foi virada corretamente.", instance.isTurned());
    }
    
}

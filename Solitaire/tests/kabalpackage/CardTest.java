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
    
    CardImageMaker cim, cim2;
    Card instance;
    Card c;
    BufferedImage backImage;
    BufferedImage turnedImage;
    BufferedImage overImage;
    
    @Before
    public void setUp() {
        try{
            cim = new CardImageMaker("bondedcards.png", 79, 123);
        }catch (IOException IOe){
            fail("Could not load card images!");
        }catch (IllegalArgumentException iae){
            fail("Could not load card images!");
        }
        
        try{
            cim2 = new CardImageMaker("bondedcards-over.png", 79, 123);
        }catch (IOException IOe){
            fail("Could not load card images!");
        }catch (IllegalArgumentException iae){
            fail("Could not load card images!");
        }
        
        this.backImage = cim.cropToCard("back", 2);
        this.turnedImage = cim.cropToCard("hearts", 2);
        this.overImage = cim2.cropToCard("hearts", 2);
                
        this.instance = new Card("hearts", 2, backImage, turnedImage, overImage, 79, 123);
    }

    @Test
    public void testMakeCopy(){
        System.out.println("makeCopy");
        
        c = instance.makeCopy();
        
        assertNotNull("Carta criada nula", instance);
        assertNotNull("Carta copiada nula", c);
        
        assertEquals("Erro ao criar cópia da carta. Tipos diferentes.", instance.type, c.type);
        assertEquals("Erro ao criar cópia da carta. Numeros diferentes.", instance.number, c.number);
        assertEquals("Erro ao criar cópia da carta. Larguras diferentes.", instance.w, c.w);
        assertEquals("Erro ao criar cópia da carta. Alturas diferentes.", instance.h, c.h);
        assertEquals("Erro ao criar cópia da carta. Status 'turned' diferentes.", instance.IS_TURNED, c.IS_TURNED);
        assertEquals("Erro ao criar cópia da carta. Status 'highlighted' diferentes.", instance.HIGHLIGHTED, c.HIGHLIGHTED);
    }
    
    @Test
    public void testSetTurned(){
        System.out.println("setTurned");
        
        instance.setTurned();
        
        assertTrue("Carta não foi virada corretamente.", instance.isTurned());
    }
    
}

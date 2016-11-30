package kabalpackage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;
import java.io.PrintStream;
import javax.swing.JComponent;
import kabalpackage.utilities.VolatileImageLoader;

public class Card extends JComponent {
    public BufferedImage image;
    public BufferedImage backImage;
    public BufferedImage turnedImage;
    public BufferedImage overImage;
    public String type;
    public int number;
    public int w;
    public int h;
    public boolean IS_TURNED = false;
    public boolean HIGHLIGHTED = false;
    public VolatileImage vimage = null;
    public VolatileImage backVimImage = null;
    public VolatileImage turnedVimImage = null;
    public VolatileImage overVimImage = null;

    public Card(String type, int number, BufferedImage backImage, BufferedImage turnedImage, BufferedImage overImage, int w, int h){
        this.type = type;
        this.number = number;
        this.backImage = backImage;
        this.image = backImage;
        this.turnedImage = turnedImage;
        this.overImage = overImage;
        this.w = w;
        this.h = h;

        try{
            this.backVimImage = VolatileImageLoader.loadFromBufferedImage(backImage, 3);
            this.vimage = VolatileImageLoader.loadFromBufferedImage(backImage, 3);
            this.turnedVimImage = VolatileImageLoader.loadFromBufferedImage(turnedImage, 3);
            this.overVimImage = VolatileImageLoader.loadFromBufferedImage(overImage, 3);
        }catch (IOException ioe){
            System.err.println("Could not convert from BufferedImage to VolatileImage");
        }
    }

    public Card makeCopy(){
        return new Card(this.type, this.number, this.turnedImage, this.turnedImage, this.turnedImage, this.w, this.h);
    }

    public void setTurned(){
        this.IS_TURNED = true;
        this.vimage = this.turnedVimImage;
    }

    public void defaceCard(){
        this.IS_TURNED = false;
        this.vimage = this.backVimImage;
    }

    public void highlight(Boolean bool){
        if (bool.booleanValue()) {
            this.vimage = this.overVimImage;
            this.HIGHLIGHTED = true;
        }
        
        if (!bool.booleanValue()) {
            this.vimage = this.turnedVimImage;
            this.HIGHLIGHTED = false;
        }
        
        repaint();
    }

    public boolean isHighLighted(){
        return this.HIGHLIGHTED;
    }

    public boolean isTurned(){
        return this.IS_TURNED;
    }

    public String getName(){
        if (this.number == 1) return "ace";
        if (this.number == 13) return "king";
        if (this.number == 12) return "queen";
        if (this.number == 11) return "jack";
        
        return "" + this.number;
    }

    public String getType(){
        return this.type;
    }

    public int getNumber(){
        return this.number;
    }

    public String toString(){
        return getName() + " " + this.number;
    }

    protected void paintComponent(Graphics graphics){
        Graphics g = graphics.create();
        g.drawImage(this.vimage, 0, 0, null);
        g.dispose();
    }
}
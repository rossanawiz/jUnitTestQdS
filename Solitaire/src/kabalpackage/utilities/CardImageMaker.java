package kabalpackage.utilities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class CardImageMaker extends JPanel{
    private BufferedImage cardsBuffer;
    private int x;
    private int y;

    public CardImageMaker(String cardsImage, int x, int y) throws IOException{
        this.x = x;
        this.y = y;

        this.cardsBuffer = ImageIO.read(getClass().getResourceAsStream("images/" + cardsImage));
    }

    public BufferedImage getCardBack(){
        return this.cardsBuffer.getSubimage(2 * this.x, 4 * this.y, this.x, this.y);
    }

    public BufferedImage cropToCard(String type, int number){
        BufferedImage ret = new BufferedImage(this.x, this.y, 2);

        if (type.equals("clubs")) ret = this.cardsBuffer.getSubimage(number * this.x, 0, this.x, this.y);
        if (type.equals("diamonds")) ret = this.cardsBuffer.getSubimage(number * this.x, this.y, this.x, this.y);
        if (type.equals("hearts")) ret = this.cardsBuffer.getSubimage(number * this.x, 2 * this.y, this.x, this.y);
        if (type.equals("spades")) ret = this.cardsBuffer.getSubimage(number * this.x, 3 * this.y, this.x, this.y);
        if (type.equals("back")) { ret = this.cardsBuffer.getSubimage(number * this.x, 4 * this.y, this.x, this.y); }
        return ret;
    }
}
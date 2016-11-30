package kabalpackage.utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.io.PrintStream;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SolTimer extends JPanel implements Runnable{
    private int timeRun = 0;
    private JLabel timerDisplay = new JLabel("");
    private boolean RUN = true;
    private final int PLAYING = 0;
    private final int PAUSED = 1;
    private final int GAME_OVER = -1;
    private int status = 1;

    public SolTimer(){
        setBackground(null);
        setLayout(new FlowLayout());
        this.timerDisplay.setText("Playing time: 0:00");
        
        try{
            Font tmpCustomFont = Font.createFont(0, getClass().getResourceAsStream("font.ttf"));
            Font customFont = tmpCustomFont.deriveFont(14.0F);

            this.timerDisplay.setFont(customFont);
        }catch (FontFormatException ffe) {
            System.err.println("FFE");
        }catch (IOException IOe) { 
            System.err.println("IOe");
        }

        this.timerDisplay.setForeground(Color.WHITE);
        add(this.timerDisplay);
    }

    public void pauseTimer(){
        this.status = 1;
        this.timerDisplay.setText("[ Pause ]");
    }

    public void resumeTimer(){
        this.status = 0;
    }

    public void resetTimer(){
        this.timeRun = 0;
        this.status = 1;
        this.timerDisplay.setSize(400, (int)this.timerDisplay.getSize().getHeight());
        this.timerDisplay.setText("Playing time: 0:00");
    }

    public void gameOver(){
        this.status = -1; 
        this.timerDisplay.setText("[ Game over! ]");
    }
    
    public int getTime(){
        return this.timeRun;
    }

    public void run(){
        while (this.RUN){
            int sec = 0;
            int min = 0;

            while (this.status == 0) {
                this.timeRun += 1;

                min = this.timeRun / 60;
                sec = this.timeRun - min * 60;

                if (sec < 10) { 
                    this.timerDisplay.setText("Playing time: " + min + ":0" + sec);
                }else{
                    this.timerDisplay.setText("Playing time: " + min + ":" + sec);
                }
                
                try{
                    Thread.sleep(1000L);
                }catch (Exception e) {}
            }
            
            try{
                Thread.sleep(1000L);
            }catch (Exception e) {}
        }
    }
    
    protected void paintComponent(Graphics graphics){
        Graphics g = graphics.create();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);

        for (int i = 0; i < 800; i++) {}

        g.dispose();
    }
}
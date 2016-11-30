package kabalpackage.utilities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JSPSplash extends JFrame implements Runnable{
    public JSPSplash(){
        setTitle("Starting Java Solitaire Project");
        setDefaultCloseOperation(2);

        setUndecorated(true);
        setFocusable(true);
        setLayout(null);
        setBackground(null);
        setSize(500, 300);

        SplashPanel splashPanel = new SplashPanel();
        splashPanel.addMouseListener(new javax.swing.event.MouseInputAdapter(){
            public void mouseClicked(MouseEvent e) {
                JSPSplash.this.dispose();
            }
        });
        
        addFocusListener(new FocusListener(){
            public void focusLost(FocusEvent fe) {
                JSPSplash.this.requestFocus();
            }

            public void focusGained(FocusEvent fe) {}
        });
        
        add(splashPanel);
        setLocationRelativeTo(null);
    }

    public void run(){
        int i = 0;
        while (i == 0){
            i++;

            try{
                  Thread.sleep(4000L);
                  System.out.println(i);
            }catch (Exception e){
                  e.printStackTrace();
            }
        }

        dispose();
    }

    private class SplashPanel extends JPanel{
        private BufferedImage splashImage = null;

        public SplashPanel() {
            setSize(500, 300);
            
            try{
                this.splashImage = javax.imageio.ImageIO.read(getClass().getResourceAsStream("images/splash.png"));
            }catch (Exception e){
                System.err.println("Could not load splash image");
                e.printStackTrace();
            }
        }

        protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D)graphics;

            if (this.splashImage != null) {
                g.drawImage(this.splashImage, null, 0, 0);
            }

            g.dispose();
        }
    }

    public static void main(String[] args){
        JSPSplash splash = new JSPSplash();
        Thread splashThread = new Thread(splash);
        splashThread.start();
        splash.setVisible(true);
    }
}

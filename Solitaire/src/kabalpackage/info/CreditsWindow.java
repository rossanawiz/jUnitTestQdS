package kabalpackage.info;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CreditsWindow extends javax.swing.JFrame{
    private final String[] credits = { "  This application was made by the following people ", "  over the course of 5-6 weeks:", "\n\n", "  Magnus Holmang Kleming: magnushk@stud.hist.no", "  \nOeyvind Valen-Sendstad: oyvindv@stud.hist.no", "  \nMartin Myhrstuen: martinmy@stud.hist.no", "\n\n", "  You may contact Magnus at any time, but an answer", "  to your inquiry is not in any way guaranteed." };

    public CreditsWindow(){
        try{
            String GTK = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            javax.swing.UIManager.setLookAndFeel(GTK);
            javax.swing.UIManager.installLookAndFeel("GTK", GTK);
        }catch (Exception e) {
            System.err.println("Could not install GTK");
        }
        
        try{
            java.awt.Image iconImage = javax.imageio.ImageIO.read(getClass().getResourceAsStream("../images/icon.gif"));
            setIconImage(iconImage);
        }catch (Exception e) {
            System.err.println("Could not load icon.");
        }

        setTitle("Credits");
        setLayout(new java.awt.BorderLayout(7, 7));
        setSize(new java.awt.Dimension(400, 300));
        setResizable(false);
        setDefaultCloseOperation(2);

        JLabel appTitle = new JLabel(" Credits");
        appTitle.setFont(new Font("Sans", 1, 24));
        add(appTitle, "North");
        add(new TextPanel(), "Center");

        JPanel buttonPanel = new ButtonPanel();
        add(buttonPanel, "South");

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class TextPanel extends JPanel{
        public TextPanel() {}

        protected void paintComponent(Graphics graphics){
            Graphics2D g = (Graphics2D)graphics;

            Rectangle panelBounds = getBounds();
            int panelXStart = (int)panelBounds.getX();
            int panelYStart = (int)panelBounds.getY();

            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            g.setFont(new Font("Sans", 1, 14));
            g.setColor(java.awt.Color.BLACK);
            int i = 0;
            for (String string : CreditsWindow.this.credits) {
                g.drawString(string, 0, panelYStart + i * 15);
                i++;
            }
        }
    }

    private class ButtonPanel extends JPanel{
        public ButtonPanel(){
            setLayout(new java.awt.FlowLayout());

            JButton closeButton = new JButton("Close");

            closeButton.addActionListener(new java.awt.event.ActionListener(){
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    CreditsWindow.this.dispose();
                }
            });
            
            add(closeButton);
        }
    }

    public static void main(String[] args){
        new CreditsWindow();
    }
}
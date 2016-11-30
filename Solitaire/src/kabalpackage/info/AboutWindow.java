package kabalpackage.info;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutWindow extends javax.swing.JFrame{
    private final String[] aboutText = { "  Java Solitaire Project is a Klondike solitaire ", "  card game written by three first year software ", "  engineering students, with no former knowledge ", "  of programming.\n", " ", "\n  We do not provide any support for this program ", "  since, as students, we are very, very busy people. ", "  If you're feeling lucky, you can still try to ", "  contact us through the SourceForge-page." };

    public AboutWindow(){
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

        setTitle("About");
        setLayout(new java.awt.BorderLayout(7, 7));
        setSize(new java.awt.Dimension(400, 300));
        setResizable(false);
        setDefaultCloseOperation(2);

        JLabel appTitle = new JLabel(" Java Solitaire Project 1.0");
        appTitle.setFont(new java.awt.Font("Sans", 1, 24));
        add(appTitle, "North");
        add(new TextPanel(), "Center");
        
        JPanel buttonPanel = new ButtonPanel();
        add(buttonPanel, "South");
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class TextPanel extends JPanel{
        public TextPanel() {}

        protected void paintComponent(java.awt.Graphics graphics){
            Graphics2D g = (Graphics2D)graphics;

            Rectangle panelBounds = getBounds();
            int panelXStart = (int)panelBounds.getX();
            int panelYStart = (int)panelBounds.getY();

            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            g.setFont(new java.awt.Font("Sans", 1, 14));
            g.setColor(java.awt.Color.BLACK);
            int i = 0;
            for (String string : AboutWindow.this.aboutText) {
                g.drawString(string, 0, panelYStart + i * 15);
                i++;
            }
        }
    }

    private class ButtonPanel extends JPanel{
        public ButtonPanel(){
            setLayout(new java.awt.FlowLayout());

            JButton creditsButton = new JButton("Credits");
            JButton licenseButton = new JButton("License");
            JButton closeButton = new JButton("Close");

            creditsButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    new CreditsWindow();
                }
            });
            
            licenseButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    new LicenseWindow();
                }
            });
            
            closeButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                  AboutWindow.this.dispose();
                }
            });
            
            add(creditsButton);
            add(licenseButton);
            add(closeButton);
        }
    }

    public static void main(String[] args){
        new AboutWindow();
    }
}
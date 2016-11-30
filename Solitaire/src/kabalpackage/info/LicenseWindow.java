package kabalpackage.info;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class LicenseWindow extends javax.swing.JFrame{
    public LicenseWindow(){
        try{
            String GTK = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            UIManager.setLookAndFeel(GTK);
            UIManager.installLookAndFeel("GTK", GTK);
        }catch (Exception e) {
            System.err.println("Could not install GTK");
        }
        
        try{
            java.awt.Image iconImage = javax.imageio.ImageIO.read(getClass().getResourceAsStream("../images/icon.gif"));
            setIconImage(iconImage);
        }catch (Exception e) {
            System.err.println("Could not load icon.");
        }

        setTitle("License information");
        setLayout(new java.awt.BorderLayout(7, 7));
        setSize(new java.awt.Dimension(500, 300));
        setResizable(false);
        setDefaultCloseOperation(2);

        JLabel appTitle = new JLabel(" License information");
        appTitle.setFont(new java.awt.Font("Sans", 1, 24));
        add(appTitle, "North");

        JTextArea textArea = new TextPanel();
        textArea.setEditable(false);
        textArea.setCaretPosition(0);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setAutoscrolls(false);
        add(scrollPane, "Center");

        JPanel buttonPanel = new ButtonPanel();
        add(buttonPanel, "South");

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class TextPanel extends JTextArea{
        public TextPanel(){
            String licenseText = "";
            
            try{
                InputStreamReader fileReader = new InputStreamReader(getClass().getResourceAsStream("license.txt"));
                BufferedReader reader = new BufferedReader(fileReader);

                String lineRead;
                while ((lineRead = reader.readLine()) != null) {
                    licenseText = licenseText + lineRead + "\n";
                }
            }catch (Exception e){
                licenseText = "Could not read license text file!";
                System.err.println(licenseText);
            }

            setText(licenseText);
        }
    }

    private class ButtonPanel extends JPanel{
        public ButtonPanel(){
            setLayout(new java.awt.FlowLayout());

            JButton closeButton = new JButton("Close");

            closeButton.addActionListener(new java.awt.event.ActionListener(){
                public void actionPerformed(ActionEvent e) {
                  LicenseWindow.this.dispose();
                }
            });
            
            add(closeButton);
        }
    }

    public static void main(String[] args){
        new LicenseWindow();
    }
}
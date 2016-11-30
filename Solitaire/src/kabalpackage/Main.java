package kabalpackage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import kabalpackage.info.AboutWindow;
import kabalpackage.utilities.JSPSplash;
import kabalpackage.utilities.LayoutVariables;

public class Main extends JFrame{
    private GameArea gameArea = new GameArea();

    public Main(){
        JSPSplash splash = new JSPSplash();
        Thread splashThread = new Thread(splash);
        splashThread.start();
        splash.setVisible(true);

        try{
            String GTK = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            UIManager.setLookAndFeel(GTK);
            UIManager.installLookAndFeel("GTK", GTK);
        }catch (Exception e) {
            System.err.println("Could not install GTK");
        }

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
              System.exit(0);
            }
        });
        
        setDefaultCloseOperation(3);
        setTitle("Java Solitaire Project!");
        setLayout(new java.awt.BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        setResizable(false);
        
        try{
            java.awt.Image iconImage = javax.imageio.ImageIO.read(getClass().getResourceAsStream("images/icon.gif"));
            setIconImage(iconImage);
        }catch (Exception e) {
            System.err.println("Could not load icon.");
        }

        MenuBar newMenuBar = new MenuBar();
        add(newMenuBar);
        setJMenuBar(newMenuBar);

        this.gameArea.newGame();
        setContentPane(this.gameArea);

        pack();

        setLocationRelativeTo(null);
    }

    private class MenuBar extends JMenuBar{
        JMenuItem editMenuPause;
        JMenuItem editMenuContinue;
        JMenuItem soundMenuOn;
        JMenuItem soundMenuOff;

        public MenuBar(){
            MenuListener menuListener = new MenuListener();

            JMenu fileMenu = new JMenu("File");
            add(fileMenu);

            JMenuItem fileMenuNew = new JMenuItem("New Game");
            fileMenuNew.addActionListener(menuListener);
            fileMenuNew.setAccelerator(KeyStroke.getKeyStroke(78, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

            fileMenu.add(fileMenuNew);

            JMenuItem fileMenuHighScore = new JMenuItem("Highscores");
            fileMenuHighScore.addActionListener(menuListener);
            fileMenuHighScore.setAccelerator(KeyStroke.getKeyStroke(72, 512));

            fileMenu.add(fileMenuHighScore);

            JMenuItem fileMenuExit = new JMenuItem("Exit");
            fileMenuExit.addActionListener(menuListener);
            fileMenuExit.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

            fileMenu.add(fileMenuExit);

            JMenu editMenu = new JMenu("Edit");
            add(editMenu);

            this.editMenuPause = new JMenuItem("Pause");
            this.editMenuPause.addActionListener(menuListener);
            this.editMenuPause.setEnabled(false);
            this.editMenuPause.setAccelerator(KeyStroke.getKeyStroke(32, 0));
            this.editMenuPause.setEnabled(true);
            editMenu.add(this.editMenuPause);
            this.editMenuContinue = new JMenuItem("Continue");
            this.editMenuContinue.addActionListener(menuListener);
            this.editMenuContinue.setEnabled(false);
            editMenu.add(this.editMenuContinue);

            JMenu backgroundMenu = new JMenu("Set background");
            editMenu.add(backgroundMenu);

            for (int i = 0; i < LayoutVariables.fileNames.length; i++) {
                JMenuItem backgroundEntry = new JMenuItem(LayoutVariables.bgNames[i]);
                backgroundEntry.addActionListener(menuListener);
                backgroundMenu.add(backgroundEntry);
            }

            JMenu soundMenu = new JMenu("Sound");
            this.soundMenuOn = new JMenuItem("On");
            this.soundMenuOn.setEnabled(false);
            this.soundMenuOn.addActionListener(menuListener);
            this.soundMenuOff = new JMenuItem("Off");
            this.soundMenuOff.addActionListener(menuListener);
            soundMenu.add(this.soundMenuOn);
            soundMenu.add(this.soundMenuOff);
            editMenu.add(soundMenu);

            JMenu cardDealMenu = new JMenu("Cards to deal");
            JMenuItem cardDealOne = new JMenuItem("1 card");
            cardDealOne.addActionListener(menuListener);
            cardDealMenu.add(cardDealOne);
            JMenuItem cardDealThree = new JMenuItem("3 cards");
            cardDealThree.addActionListener(menuListener);
            cardDealMenu.add(cardDealThree);
            editMenu.add(cardDealMenu);

            JMenu helpMenu = new JMenu("Help");
            add(helpMenu);
            
            JMenuItem helpMenuAbout = new JMenuItem("About");
            helpMenuAbout.addActionListener(menuListener);
            helpMenuAbout.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

            helpMenu.add(helpMenuAbout);

            JMenuItem helpMenuHint = new JMenuItem("Hint");
            helpMenuHint.addActionListener(menuListener);
            helpMenuHint.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

            helpMenu.add(helpMenuHint);
        }

        private class MenuListener implements java.awt.event.ActionListener {
            private MenuListener() {}

            public void actionPerformed(ActionEvent e) { String event = e.getActionCommand();

                if (event.equals("New Game")) {
                    Main.this.gameArea.newGame();
                    System.out.println("New game");
                }

                if (event.equals("Exit")) { 
                    System.exit(0);
                }
                
                if (event.equals("Highscores")) { 
                    Main.this.gameArea.presentHighScore();
                }
                
                if (event.equals("Hint")) { 
                    Main.this.gameArea.hint();
                }
                
                if (event.equals("Pause")) {
                    System.out.println("Pause");
                    Main.this.gameArea.pauseTimer();
                    Main.MenuBar.this.editMenuContinue.setEnabled(true);
                    Main.MenuBar.this.editMenuPause.setEnabled(false);
                    Main.MenuBar.this.editMenuContinue.setAccelerator(KeyStroke.getKeyStroke(32, 0));
                }

                if (event.equals("Continue")) {
                    System.out.println("Continue");
                    Main.this.gameArea.resumeTimer();

                    Main.MenuBar.this.editMenuPause.setEnabled(true);
                    Main.MenuBar.this.editMenuContinue.setEnabled(false);
                }
                
                AboutWindow about;
                if (event.equals("About")) {
                    about = new AboutWindow();
                }

                if (event.equals("On")) {
                    Main.MenuBar.this.soundMenuOn.setEnabled(false);
                    Main.MenuBar.this.soundMenuOff.setEnabled(true);
                    Main.this.gameArea.toggleSoundEffects(true);
                }

                if (event.equals("Off")) {
                    Main.MenuBar.this.soundMenuOn.setEnabled(true);
                    Main.MenuBar.this.soundMenuOff.setEnabled(false);
                    Main.this.gameArea.toggleSoundEffects(false);
                }

                if (event.equals("1 card")) {
                    Main.this.gameArea.setCardsToDealCount(1);
                }

                if (event.equals("3 cards")) {
                    Main.this.gameArea.setCardsToDealCount(3);
                }else{
                    for (int i = 0; i < LayoutVariables.fileNames.length; i++) {
                        if (event.equals(LayoutVariables.bgNames[i])) {
                            Main.this.gameArea.changeBackground(i);
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        Main newSolitaire = new Main();
        newSolitaire.setVisible(true);
    }
}

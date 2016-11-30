package kabalpackage.utilities;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class HighScore extends JFrame{
    private int time;
    private HighScoreTable highScoreTable = new HighScoreTable();
    private JTextField input = new JTextField(20);
    private JFrame frame = new JFrame();

    private JLabel label;
    private String tmpDir;

    public HighScore(int time){
        this.time = time;

        try{
            String GTK = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            UIManager.setLookAndFeel(GTK);
            UIManager.installLookAndFeel("GTK", GTK);
        } catch (Exception e) {
            System.err.println("Could not install GTK");
        }

        if (this.highScoreTable.findOperatingSystem()) {
            presentHighScore();
        } else {
            JOptionPane.showMessageDialog(null, "Could not determine operating system, unable to save highscore");
        }
    }

    public void presentHighScore(){
        this.frame.setTitle("Register your name and time in the highscore");
        this.frame.setLayout(new FlowLayout());

        String[] columnNames = { "Name", "Score" };

        if (!this.highScoreTable.fileExists()) {
            File file = new File(this.tmpDir);
            this.highScoreTable.writeHighScoreFile();
            this.highScoreTable.readHighScoreFile();
        }else {
            this.highScoreTable.readHighScoreFile();
        }

        if (this.time >= 0){
            if (this.highScoreTable.isGood(this.time)){
                ButtonListener buttonListener = new ButtonListener();
                this.input = new JTextField(20);
                this.frame.add(this.input);
                this.frame.add(this.label);
                this.frame.add(this.input);
                JButton button = new JButton("Register " + this.highScoreTable.formatTime(this.time));

                this.frame.add(button, 2);
                button.addActionListener(buttonListener);
            }else {
                this.label = new JLabel("<html>Your time did not make it to the highscore... <br>" + this.highScoreTable.formatTime(this.time) + "</html>");
            }
        }else{
            this.label = new JLabel("The incredible highscorers!");
            this.frame.add(this.label);
        }

        JTable presentHighScore = new JTable(this.highScoreTable.getResults(), columnNames);

        presentHighScore.setEnabled(false);
        presentHighScore.setAutoResizeMode(0);
        int vColIndex = 1;
        TableColumn col = presentHighScore.getColumnModel().getColumn(vColIndex);

        int width = 150;
        col.setPreferredWidth(width);
        presentHighScore.repaint();
        this.frame.setPreferredSize(new Dimension(280, 300));
        this.frame.setResizable(false);
        this.frame.add(presentHighScore);
        this.frame.setTitle("Highscores");
        this.frame.setDefaultCloseOperation(1);
        this.frame.setVisible(true);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
    }

    private class ButtonListener implements ActionListener{
        private ButtonListener() {}

        public void actionPerformed(ActionEvent event){
            HighScore.Player player = new HighScore.Player(HighScore.this, HighScore.this.input.getText(), HighScore.this.time);
            if (player.isPlayer()) {
                HighScore.this.highScoreTable.addPlayer(player);
                HighScore.this.highScoreTable.sortTable();
                HighScore.this.highScoreTable.listCleanup();
                System.out.println(player.toString());
                HighScore.this.frame.setVisible(false);
                HighScore.this.highScoreTable.writeHighScoreFile();
            }
        }
    }

    private class Player implements Serializable{
        private String name;
        private int time;

        public Player(HighScore o, String name, int time){
            this.name = name;
            this.time = time;
        }

        public String getName(){
            return this.name;
        }

        public int getTime(){
            return this.time;
        }

        public String toString(){
            return this.name + ", " + this.time + " sec";
        }

        public boolean isPlayer(){
            if (this.name != null) {
                if (this.name.equalsIgnoreCase(""))
                    return false;
                return true;
            }
            
            return false;
        }
    }

    private class HighScoreTable implements Serializable{
        private ArrayList<HighScore.Player> list = new ArrayList();
        private HighScoreTable() {}

        public void readHighScoreFile(){
            FileInputStream in = null;
            ObjectInputStream inObject = null;
            
            try{
                in = new FileInputStream(HighScore.this.tmpDir);
                inObject = new ObjectInputStream(in);
                this.list = ((ArrayList)inObject.readObject());
                inObject.close();
                in.close();
                System.out.println("HighScore.ser read");
            } catch (FileNotFoundException ex) {
                this.list = new ArrayList();
                ex.printStackTrace();
            } catch (IOException ex) {
                this.list = new ArrayList();
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                this.list = new ArrayList();
                ex.printStackTrace();
            } catch (ClassCastException ex) {
                this.list = new ArrayList();
                ex.printStackTrace();
            }
        }
        
        public void writeHighScoreFile(){
            FileOutputStream out = null;
            ObjectOutputStream outObject = null;
            
            try{
                out = new FileOutputStream(HighScore.this.tmpDir);
                outObject = new ObjectOutputStream(out);
                outObject.writeObject(this.list);
            }catch (FileNotFoundException ex) {
                System.out.println("Could not write highscorelist to file! FileNotFoundException");
                ex.printStackTrace();
            }catch (IOException ex) {
                System.out.println("Could not write highscorelist to file! IOException");
                ex.printStackTrace();
            }
        }

        public void addPlayer(HighScore.Player player){
            this.list.add(player);
            System.out.println(this.list.toString());
        }

        public void sortTable(){
            if (!this.list.isEmpty()) {
                for (int i = 0; i < this.list.size(); i++) {
                    for (int j = 0; j < this.list.size(); j++) {
                        if ((j + 1 < this.list.size()) && (((HighScore.Player)this.list.get(j)).getTime() > ((HighScore.Player)this.list.get(j + 1)).getTime())){
                            HighScore.Player tmpPlayer = new HighScore.Player(HighScore.this, ((HighScore.Player)this.list.get(j)).getName(), ((HighScore.Player)this.list.get(j)).getTime());
                            this.list.set(j, this.list.get(j + 1));
                            this.list.set(j + 1, tmpPlayer);
                        }
                    }
                }
            }

            System.out.println("Done with sorting.");
        }

        public boolean isGood(int time){
            if (this.list.size() < 10) {
                HighScore.this.label = new JLabel("Please enter your name");
                return true;
            }
            
            for (int i = 0; i < 11; i++) {
                if (((HighScore.Player)this.list.get(i)).getTime() > time) {
                    HighScore.this.label = new JLabel("Please enter your name");
                    return true;
                }
            }

            return false;
        }

        public boolean fileExists(){
            File file = new File(HighScore.this.tmpDir);
            return file.exists();
        }
        
        public void listCleanup(){
            if (this.list.size() > 10) {
                for (int i = 10; i < this.list.size(); i++) {
                    this.list.remove(i);
                    this.list.trimToSize();
                }
            }
        }

        public String[][] getResults(){
            String[][] returnThis = new String[this.list.size()][2];
            for (int i = 0; i < this.list.size(); i++) {
                returnThis[i][0] = ((HighScore.Player)this.list.get(i)).getName();
                returnThis[i][1] = ("" + formatTime(((HighScore.Player)this.list.get(i)).getTime()));
            }

            return returnThis;
        }

        public boolean findOperatingSystem(){
            String os = System.getProperty("os.name");
            System.out.println("OS Name: " + System.getProperty("os.name"));
            System.out.println("OS Architecture: " + System.getProperty("os.arch"));
            System.out.println("OS Version: " + System.getProperty("os.version"));

            os.trim();
            if (os.contains("XP")) {
                HighScore.this.tmpDir = (System.getProperty("java.io.tmpdir") + "HighScore.ser");
                System.out.println("Operatingsystem recognized is " + os);
                System.out.println("Storing highscore in " + HighScore.this.tmpDir);
                return true;
            }
            
            if (os.contains("Vista")) {
                HighScore.this.tmpDir = (System.getProperty("java.io.tmpdir") + "HighScore.ser");
                System.out.println("Operatingsystem recognized is " + os);
                System.out.println("Storing highscore in " + HighScore.this.tmpDir);
                return true;
            }
            
            if (os.contains("Linux")) {
                HighScore.this.tmpDir = (System.getProperty("java.io.tmpdir") + "/" + "HighScore.ser");

                System.out.println("Operatingsystem recognized is " + os);
                System.out.println("Storing highscore in " + HighScore.this.tmpDir);
                return true;
            }
            
            if (os.contains("Mac")) {
                HighScore.this.tmpDir = (System.getProperty("java.io.tmpdir") + "/" + "HighScore.ser");

                System.out.println("Operatingsystem recognized is " + os);
                System.out.println("Storing highscore in " + HighScore.this.tmpDir);
                return true;
            }
            
            String tmpDir = System.getProperty("java.io.tmpdir") + "HighScore.ser";
            System.out.println("Operatingsystem not recognized by Solitaire, " + os);
            System.out.println("Storing highscore in " + tmpDir);
            return false;
        }

        public String formatTime(int time){
            int hours = time / 3600;
            int min = time / 60;
            int sec = time % 60;
            if (hours > 0) {
                if (hours > 1) {
                    return hours + "hrs, " + min + " min and " + sec + " sec.";
                }
                
                return hours + "hr, " + min + " min and " + sec + " sec.";
            }

            return min + " min and " + sec + " sec";
        }

        public void toSysout(){
            for (int i = 0; i < this.list.size(); i++) {
                System.out.println("Player " + ((HighScore.Player)this.list.get(i)).getName() + " has time " + ((HighScore.Player)this.list.get(i)).getTime() + " sec");
            }
        }
    }
}

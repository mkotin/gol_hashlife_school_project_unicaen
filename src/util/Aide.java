package  util;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;


/**
 * Cette classe permet d'afficher l'aide
 * @author Mamadou Alpha Diallo
 * @version 1.0
 */
public class Aide extends JFrame {
    public Aide() {
        super("Aide");

        BufferedReader br = null;
        String ligne;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader("data/aide.txt"));
            while ((ligne = br.readLine()) != null) {
               sb.append(ligne).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        this.getContentPane().add(scrollPane,BorderLayout.CENTER);

        this.setSize(700, 700);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
}
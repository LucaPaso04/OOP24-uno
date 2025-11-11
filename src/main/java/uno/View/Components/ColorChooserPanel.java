package uno.View.Components;

import uno.Controller.GameViewObserver;
import uno.Model.Cards.Attributes.CardColor;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Un pannello che appare quando un giocatore deve scegliere
 * un colore dopo aver giocato una carta Jolly.
 */
public class ColorChooserPanel extends JPanel {

    private GameViewObserver observer;
    
    // Colori per il tema scuro della UI
    private static final Color PANEL_COLOR = new Color(50, 50, 50);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Font BOLD_FONT = new Font("Arial", Font.BOLD, 14);

    // NUOVI COLORI SPECIFICI PER IL TEMA SCURO (Flip Side)
    // I valori sono presi dal file CardColor.java
    private static final Color PINK_COLOR = new Color(255, 105, 180);  // Pink
    private static final Color TEAL_COLOR = new Color(0, 128, 128);    // Teal
    private static final Color ORANGE_COLOR = new Color(255, 140, 0);  // Orange
    private static final Color PURPLE_COLOR = new Color(128, 0, 128);  // Purple
    
    public ColorChooserPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(PANEL_COLOR);
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Scegli Colore",
            TitledBorder.LEFT, TitledBorder.TOP, BOLD_FONT, TEXT_COLOR
        ));
        setVisible(false); // Nascosto di default
        
        // Inizializza i pulsanti con il lato chiaro di default
        updateButtons(false);
    }

    /**
     * Imposta il controller che ascolterà gli eventi di questo pannello.
     * @param observer L'observer del controller.
     */
    public void setObserver(GameViewObserver observer) {
        this.observer = observer;
    }

    /**
     * Aggiorna i pulsanti mostrati nel pannello in base al lato attivo del gioco.
     * @param isDarkSide true se siamo sul lato scuro (Flip), false per il lato chiaro (Standard).
     */
    public void updateButtons(boolean isDarkSide) {
        this.removeAll(); // Rimuove i pulsanti esistenti
        
        if (isDarkSide) {
            // Paletta Scuro (Pink, Teal, Orange, Purple)
            JButton pinkButton = createColorButton("PINK", PINK_COLOR, CardColor.PINK);
            JButton tealButton = createColorButton("TEAL", TEAL_COLOR, CardColor.TEAL);
            JButton orangeButton = createColorButton("ORANGE", ORANGE_COLOR, CardColor.ORANGE);
            JButton purpleButton = createColorButton("PURPLE", PURPLE_COLOR, CardColor.PURPLE);
            
            this.add(pinkButton);
            this.add(tealButton);
            this.add(orangeButton);
            this.add(purpleButton);
        } else {
            // Paletta Chiaro (Red, Blue, Green, Yellow)
            // Utilizziamo i colori originali per l'interfaccia.
            JButton redButton = createColorButton("Rosso", new Color(211, 47, 47), CardColor.RED);
            JButton blueButton = createColorButton("Blu", new Color(33, 150, 243), CardColor.BLUE);
            JButton greenButton = createColorButton("Verde", new Color(76, 175, 80), CardColor.GREEN);
            JButton yellowButton = createColorButton("Giallo", new Color(255, 235, 59), CardColor.YELLOW);
            
            this.add(redButton);
            this.add(blueButton);
            this.add(greenButton);
            this.add(yellowButton);
        }
        
        this.revalidate();
        this.repaint();
    }

    /**
     * Metodo helper per creare e configurare i bottoni colorati.
     */
    private JButton createColorButton(String text, Color bgColor, CardColor colorEnum) {
        JButton button = new JButton(text);
        button.setFont(BOLD_FONT);
        button.setBackground(bgColor);
        
        // Logica per garantire un buon contrasto del testo
        if (colorEnum == CardColor.YELLOW || colorEnum == CardColor.PINK || colorEnum == CardColor.ORANGE) {
            button.setForeground(Color.BLACK);
        } else {
            button.setForeground(Color.WHITE);
        }
        
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(150, 40));
        button.setPreferredSize(new Dimension(150, 40));
        
        button.addActionListener(e -> {
            if (observer != null) {
                observer.onColorChosen(colorEnum);
            }
        });
        return button;
    }
}
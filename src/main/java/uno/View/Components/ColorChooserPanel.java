package uno.View.Components;

import uno.Controller.GameViewObserver;
import uno.Model.Cards.Attributes.CardColor;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

    public ColorChooserPanel() {
        // Imposta un layout verticale per i bottoni
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Scegli un Colore"));
        setVisible(false); // Nascosto di default

        // Aggiungi un'etichetta
        JLabel label = new JLabel("Scegli il prossimo colore:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        add(label);

        // Creazione dei 4 bottoni
        JButton redButton = createColorButton("Rosso", new Color(211, 47, 47), CardColor.RED);
        JButton blueButton = createColorButton("Blu", new Color(33, 150, 243), CardColor.BLUE);
        JButton greenButton = createColorButton("Verde", new Color(76, 175, 80), CardColor.GREEN);
        JButton yellowButton = createColorButton("Giallo", new Color(255, 235, 59), CardColor.YELLOW);

        add(redButton);
        add(blueButton);
        add(greenButton);
        add(yellowButton);
    }

    /**
     * Imposta il controller che ascolterà gli eventi di questo pannello.
     * @param observer L'observer del controller.
     */
    public void setObserver(GameViewObserver observer) {
        this.observer = observer;
    }

    /**
     * Metodo helper per creare e configurare i bottoni colorati.
     */
    private JButton createColorButton(String text, Color bgColor, CardColor colorEnum) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        
        // Imposta il colore del testo per una buona leggibilità
        if (colorEnum == CardColor.YELLOW) {
            button.setForeground(Color.BLACK);
        } else {
            button.setForeground(Color.WHITE);
        }

        button.setOpaque(true);
        button.setBorderPainted(false); // Look moderno
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(150, 40));
        button.setPreferredSize(new Dimension(150, 40));
        
        // Collega l'azione al controller
        button.addActionListener(e -> {
            if (observer != null) {
                // Notifica al controller quale colore è stato scelto
                observer.onColorChosen(colorEnum);
            }
        });
        return button;
    }
}
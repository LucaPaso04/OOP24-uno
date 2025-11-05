// Percorso: src/main/java/uno/Model/NumberedCard.java
package uno.Model;

import uno.Controller.GameController;

/**
 * Rappresenta una carta numerata (0-9).
 * Non ha effetti speciali.
 */
public class NumberedCard extends AbstractCard {

    public NumberedCard(Color color, Value value) {
        super(color, value);
        // Controllo di sicurezza
        if (value.ordinal() > Value.NINE.ordinal()) {
            throw new IllegalArgumentException("NumberedCard deve avere un valore tra 0 e 9.");
        }
    }

    @Override
    public void executeEffect(GameController controller) {
        // Le carte numerate non hanno effetto.
        // Il controller sa già che deve solo passare il turno.
    }
}
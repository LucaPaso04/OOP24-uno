// Percorso: src/main/java/uno/Model/ActionCard.java
package uno.Model;

import uno.Controller.GameController;

/**
 * Rappresenta una carta azione (Salta, Inverti, Pesca Due).
 */
public class ActionCard extends AbstractCard {

    public ActionCard(Color color, Value value) {
        super(color, value);
        if (value != Value.SKIP && value != Value.REVERSE && value != Value.DRAW_TWO) {
            throw new IllegalArgumentException("Valore non valido per ActionCard.");
        }
    }

    @Override
    public void executeEffect(GameController controller) {
        // Il modello dice al controllore quale azione eseguire
        switch (this.value) {
            case SKIP:
                controller.skipNextPlayer();
                break;
            case REVERSE:
                controller.reversePlayDirection();
                break;
            case DRAW_TWO:
                controller.makeNextPlayerDraw(2);
                break;
            default:
                break;
        }
    }
}

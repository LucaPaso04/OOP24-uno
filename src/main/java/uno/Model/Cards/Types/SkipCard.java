// Percorso: src/main/java/uno/Model/Cards/Types/SkipCard.java
package uno.Model.Cards.Types;

import uno.Model.Game.Game;
import uno.Model.Cards.Attributes.CardColor;
import uno.Model.Cards.Attributes.CardValue;

/**
 * Rappresenta una carta "Salta Turno" (Skip).
 */
public class SkipCard extends AbstractCard {

    public SkipCard(CardColor color) {
        super(color, CardValue.SKIP);
    }

    /**
     * Esegue l'effetto "salta turno" modificando lo stato del gioco.
     */
    @Override
    public void performEffect(Game game) {
        game.skipNextPlayer();
    }
}

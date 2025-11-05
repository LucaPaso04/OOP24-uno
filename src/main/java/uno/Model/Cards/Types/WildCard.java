// Percorso: src/main/java/uno/Model/Cards/Types/WildCard.java
package uno.Model.Cards.Types;

import uno.Model.Game.Game;
import uno.Model.Cards.Card;
import uno.Model.Cards.Attributes.CardColor;
import uno.Model.Cards.Attributes.CardValue;

/**
 * Rappresenta una carta Jolly standard (Cambia Colore).
 */
public class WildCard extends AbstractCard {

    public WildCard() {
        super(CardColor.WILD, CardValue.WILD); // Il colore "base" è WILD
    }

    /**
     * Esegue l'effetto "cambia colore"
     */
    @Override
    public void performEffect(Game game) {
        // Richiede al gioco di gestire la logica per la scelta del colore.
        //game.promptForColorChoice();
    }

    /**
     * Sovrascrive la regola base. Una carta Jolly
     * può sempre essere giocata.
     */
    @Override
    public boolean canBePlayedOn(Card topCard) {
        return true;
    }
}

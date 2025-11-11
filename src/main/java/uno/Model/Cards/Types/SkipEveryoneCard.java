// Percorso: src/main/java/uno/Model/Cards/Types/SkipCard.java
package uno.Model.Cards.Types;

import uno.Model.Game.Game;
import uno.Model.Cards.Attributes.CardFace;
import uno.Model.Cards.Attributes.CardValue;

/**
 * Rappresenta una carta "Salta Turno" (Skip).
 */
public class SkipEveryoneCard extends AbstractCard {

    public SkipEveryoneCard(CardFace lightSide, CardFace darkSide) {
        super(lightSide, darkSide);
    }

    /**
     * Esegue l'effetto "salta turno" modificando lo stato del gioco.
     */
    @Override
    public void performEffect(Game game) {
        CardValue activeValue = this.getValue(game);

        if(activeValue != CardValue.SKIP_EVERYONE) {
            return; // Non eseguire l'effetto se il valore non è SKIP_EVERYONE
        }

        game.skipNextPlayer();
        game.skipNextPlayer();
        game.skipNextPlayer();
    }
}

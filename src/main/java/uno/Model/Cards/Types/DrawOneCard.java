package uno.Model.Cards.Types;

import uno.Model.Game.Game;
import uno.Model.Cards.Attributes.CardFace;
import uno.Model.Cards.Attributes.CardValue;

/**
 * Rappresenta una carta "Pesca Uno" (Draw One).
 */
public class DrawOneCard extends AbstractCard {

    public DrawOneCard(CardFace lightSide, CardFace darkSide) {
        super(lightSide, darkSide);
    }

    /**
     * Esegue l'effetto "pesca due" modificando lo stato del gioco.
     */
    @Override
    public void performEffect(Game game) {
        CardValue activeValue = this.getValue(game);
        // Chiama il centro di controllo per eseguire l'azione corretta
        dispatchBasicEffect(game, activeValue);
    }
}

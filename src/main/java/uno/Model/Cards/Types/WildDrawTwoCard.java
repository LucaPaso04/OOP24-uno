// Percorso: src/main/java/uno/Model/Cards/Types/WildDrawFourCard.java
package uno.Model.Cards.Types;

import uno.Model.Game.Game;
import uno.Model.Cards.Card;

import uno.Model.Cards.Attributes.CardFace;
import uno.Model.Cards.Attributes.CardValue;

/**
 * Rappresenta una carta Jolly Pesca Due (+2).
 */
public class WildDrawTwoCard extends AbstractCard {

    public WildDrawTwoCard(CardFace lightSide, CardFace darkSide) {
        super(lightSide, darkSide);
    }

    /**
     * Esegue l'effetto "pesca quattro" e "cambia colore".
     */
    @Override
    public void performEffect(Game game) {
        CardValue activeValue = this.getValue(game);
        
        if(activeValue != CardValue.WILD_DRAW_TWO) {
            return; // Non eseguire l'effetto se il valore non è WILD_DRAW_TWO
        }

        // 1. Obbliga il prossimo giocatore a pescare
        game.makeNextPlayerDraw(2);
        
        // 2. Fa saltare il turno al prossimo giocatore
        game.skipNextPlayer();

        // 3. Richiede al gioco di gestire la scelta del colore
        game.requestColorChoice();
    }

    /**
     * Sovrascrive la regola base.
     * La logica complessa del "non puoi giocarla se hai altre carte"
     * viene gestita dal Controller/Game prima di chiamare questo metodo.
     * A livello di carta, è sempre "giocabile".
     */
    @Override
    public boolean canBePlayedOn(Card topCard, Game game) {
        return true;
    }
}
// Percorso: src/main/java/uno/Model/Cards/Types/DrawTwoCard.java
package uno.Model.Cards.Types;

import uno.Model.Game.Game;
import uno.Model.Cards.Attributes.CardFace;
import uno.Model.Cards.Attributes.CardValue;

/**
 * Rappresenta una carta "Pesca Due" (Draw Two).
 */
public class DrawFiveCard extends AbstractCard {

    public DrawFiveCard(CardFace lightSide, CardFace darkSide) {
        super(lightSide, darkSide);
    }

    /**
     * Esegue l'effetto "pesca due" modificando lo stato del gioco.
     */
    @Override
    public void performEffect(Game game) {
        CardValue activeValue = this.getValue(game);
        
        if(activeValue != CardValue.DRAW_FIVE) {
            System.out.println("ERRORE! activeValue è: " + activeValue);
            return; // Non eseguire l'effetto se il valore non è DRAW_FIVE
        }

        System.out.println("Effetto DRAW_FIVE: Il prossimo giocatore pesca 5 carte e salta il turno.");
        game.makeNextPlayerDraw(5);
        game.skipNextPlayer(); // In molte regole, il +5 fa anche saltare
    }
}

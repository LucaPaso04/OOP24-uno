package uno.Model.Cards.Behaviors;

import uno.Model.Cards.Attributes.CardColor;
import uno.Model.Cards.Attributes.CardValue;
import uno.Model.Game.Game;

/**
 * Rappresenta il dorso di una carta (o un lato inattivo).
 * Se per errore il gioco prova a usarlo, non succede nulla o lancia errore.
 */
public class BackSideBehavior implements CardSideBehavior {

    @Override
    public CardColor getColor() {
        return null; // O un colore fittizio tipo CardColor.NONE se lo hai
    }

    @Override
    public CardValue getValue() {
        return null; // O CardValue.NONE
    }

    @Override
    public void executeEffect(Game game) {
        throw new IllegalStateException("Non puoi giocare il dorso di una carta!");
    }

    @Override
    public String toString() {
        return "[CARD BACK]";
    }
}
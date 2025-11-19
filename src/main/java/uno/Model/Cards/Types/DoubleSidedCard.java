package uno.Model.Cards.Types;

import uno.Model.Cards.Card;
import uno.Model.Cards.Attributes.CardColor;
import uno.Model.Cards.Attributes.CardValue;
import uno.Model.Cards.Behaviors.CardSideBehavior;
import uno.Model.Game.Game;

public class DoubleSidedCard implements Card {

    private final CardSideBehavior lightSide;
    private final CardSideBehavior darkSide;

    public DoubleSidedCard(CardSideBehavior lightSide, CardSideBehavior darkSide) {
        this.lightSide = lightSide;
        this.darkSide = darkSide;
    }

    // Helper per capire quale lato usare
    private CardSideBehavior getActiveSide(Game game) {
        return game.isDarkSide() ? darkSide : lightSide;
    }

    @Override
    public CardColor getColor(Game game) {
        return getActiveSide(game).getColor();
    }

    @Override
    public CardValue getValue(Game game) {
        return getActiveSide(game).getValue();
    }

    @Override
    public boolean canBePlayedOn(Card topCard, Game game) {
        CardSideBehavior myFace = getActiveSide(game);
        
        // Recuperiamo il colore attivo dal gioco (gestisce anche i jolly attivi)
        CardColor activeColor = game.getCurrentColor(); 
        if (activeColor == null) {
             // Fallback se è la prima carta o reset
            activeColor = topCard.getColor(game);
        }

        // 1. Match colore
        if (myFace.getColor() == activeColor || myFace.getColor() == CardColor.WILD) {
            return true;
        }

        // 2. Match Valore
        if (myFace.getValue() == topCard.getValue(game)) {
            return true;
        }

        return false;
    }

    @Override
    public void performEffect(Game game) {
        // Delega l'esecuzione al comportamento del lato attivo
        getActiveSide(game).executeEffect(game);
    }

    @Override
    public String toString() {
        String front = (lightSide != null) ? lightSide.toString() : "NULL";
        String back = (darkSide != null) ? darkSide.toString() : "NULL";
        return "Front: " + front + " | Back: " + back;
    }
}
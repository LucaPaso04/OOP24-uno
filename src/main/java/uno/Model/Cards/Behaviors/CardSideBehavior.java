package uno.Model.Cards.Behaviors;

import uno.Model.Cards.Attributes.CardColor;
import uno.Model.Cards.Attributes.CardValue;
import uno.Model.Game.Game;

public interface CardSideBehavior {
    CardColor getColor();
    CardValue getValue();
    void executeEffect(Game game);
    
    // Facoltativo: toString specifico per il lato
    String toString(); 
}
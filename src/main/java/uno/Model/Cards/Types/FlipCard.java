package uno.Model.Cards.Types;

import uno.Model.Cards.Attributes.CardColor;
import uno.Model.Cards.Attributes.CardValue;
import uno.Model.Game.Game;

/**
 * La carta speciale FLIP (esiste solo sul Lato Chiaro).
 * Il suo unico scopo è chiamare game.flipTheWorld().
 */
public class FlipCard extends AbstractCard {

    public FlipCard(CardColor color) {
        super(color, CardValue.FLIP);
    }

    /**
     * Quando giocata, dice al Game di flippare tutto.
     */
    @Override
    public void performEffect(Game game) {
        game.flipTheWorld();
    }
}
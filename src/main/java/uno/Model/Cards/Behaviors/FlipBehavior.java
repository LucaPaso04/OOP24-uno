package uno.Model.Cards.Behaviors;

import uno.Model.Cards.Attributes.CardColor;
import uno.Model.Cards.Attributes.CardValue;
import uno.Model.Game.Game;

public class FlipBehavior implements CardSideBehavior {
    private final CardColor color;
    private final CardValue value;

    public FlipBehavior(CardColor color, CardValue value) {
        this.color = color;
        this.value = value;
    }

    @Override
    public void executeEffect(Game game) {
        game.flipTheWorld(); 
    }

    @Override public CardColor getColor() { return color; }
    @Override public CardValue getValue() { return value; }
    @Override public String toString() { return color + " " + value; }
}
package uno.view.api;

import uno.model.cards.attributes.CardColor;
import uno.model.cards.attributes.CardValue;
import uno.model.cards.types.api.Card;

import java.util.Optional;

/**
 * DTO representing a Card for the View.
 * Contains resolved color and value (no dependency on Game state for
 * retrieval).
 */
public class CardViewData {
    private final CardColor color;
    private final CardValue value;
    private final String imageKey;
    private final Optional<Card> modelCard; // Opaque token for 'play' actions

    public CardViewData(CardColor color, CardValue value, String imageKey, Optional<Card> modelCard) {
        this.color = color;
        this.value = value;
        this.imageKey = imageKey;
        this.modelCard = modelCard;
    }

    public CardColor getColor() {
        return color;
    }

    public CardValue getValue() {
        return value;
    }

    public String getImageKey() {
        return imageKey;
    }

    public Optional<Card> getModelCard() {
        return modelCard;
    }
}

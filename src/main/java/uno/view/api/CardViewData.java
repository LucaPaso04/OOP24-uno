package uno.view.api;

import uno.model.cards.attributes.CardColor;
import uno.model.cards.attributes.CardValue;
import uno.model.cards.types.api.Card;

import java.util.Optional;

/**
 * DTO representing a Card for the View.
 */
public interface CardViewData {
    /**
     * Get the color of the card.
     * 
     * @return the color of the card.
     */
    CardColor getColor();

    /**
     * Get the value of the card.
     * 
     * @return the value of the card.
     */
    CardValue getValue();

    /**
     * Get the image key for this card, used to retrieve the correct image from the
     * View's image repository.
     * 
     * @return the image key for this card.
     */
    String getImageKey();

    /**
     * Get an optional reference to the underlying model Card, if available. This can be
     * used as an opaque token for actions like 'play' without exposing the full model to the View.
     * 
     * @return an Optional containing the model Card if available, or empty if not.
     */
    Optional<Card> getModelCard();
}

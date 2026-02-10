package uno.view.api;

import uno.model.players.api.AbstractPlayer;

import java.util.List;
import java.util.Collections;

/**
 * DTO representing a Player for the View.
 */
public class PlayerViewData {
    private final String name;
    private final int handSize;
    private final int score;
    private final boolean isCurrentPlayer;
    private final List<CardViewData> hand;
    private final AbstractPlayer modelPlayer; // Opaque token

    public PlayerViewData(String name, int handSize, int score, boolean isCurrentPlayer,
            List<CardViewData> hand, AbstractPlayer modelPlayer) {
        this.name = name;
        this.handSize = handSize;
        this.score = score;
        this.isCurrentPlayer = isCurrentPlayer;
        this.hand = hand != null ? hand : Collections.emptyList();
        this.modelPlayer = modelPlayer;
    }

    public String getName() {
        return name;
    }

    public int getHandSize() {
        return handSize;
    }

    public int getScore() {
        return score;
    }

    public boolean isCurrentPlayer() {
        return isCurrentPlayer;
    }

    public List<CardViewData> getHand() {
        return hand;
    }

    public AbstractPlayer getModelPlayer() {
        return modelPlayer;
    }
}

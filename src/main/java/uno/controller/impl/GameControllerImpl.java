package uno.controller.impl;

import uno.controller.api.GameController;
import uno.controller.api.MenuController;
import uno.model.cards.attributes.CardColor;
import uno.model.cards.types.api.Card;
import uno.model.game.api.GameState;
import uno.model.game.api.Game;
import uno.model.players.api.AbstractPlayer;
import uno.model.players.impl.AbstractAIPlayer;
import uno.model.players.impl.HumanPlayer;
import uno.view.api.GameFrame;
import uno.view.scenes.api.GameScene;
import uno.view.scenes.api.MenuScene;
import uno.view.scenes.impl.MenuSceneImpl;
import uno.view.api.GameViewData;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.awt.Container;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Concrete implementation of the GameController interface.
 * It manages the interaction logic between the GameScene (View) and the Game
 * (Model).
 */
public class GameControllerImpl implements GameController {

    private static final int AI_DELAY = 3000;

    private final Game gameModel;
    private final GameScene gameScene;
    private final GameFrame mainFrame;

    private Optional<Timer> aiTimer = Optional.empty();

    /**
     * Constructs the GameControllerImpl with the given Model, View, and Main Frame.
     * 
     * @param gameModel the game logic and state.
     * @param gameScene the view representing the game board and player
     *                  interactions.
     * @param mainFrame the main application window to control scene transitions and
     *                  popups.
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public GameControllerImpl(final Game gameModel, final GameScene gameScene,
            final GameFrame mainFrame) {
        this.gameModel = gameModel;
        this.gameScene = gameScene;
        this.mainFrame = mainFrame;

        this.gameModel.addObserver(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showStartingPlayerPopupAndStartGame() {
        final AbstractPlayer startingPlayer = gameModel.getCurrentPlayer();

        gameScene.showStartingPlayer(startingPlayer.getName());

        onGameUpdate();
    }

    /**
     * {@inheritDoc}
     */
    /**
     * {@inheritDoc}
     */
    @Override
    public void onGameUpdate() {
        if (gameModel.getGameState() == GameState.GAME_OVER) {
            if (aiTimer.isPresent()) {
                aiTimer.get().stop();
            }
            gameScene.setHumanInputEnabled(false);

            final AbstractPlayer winner = gameModel.getWinner();
            gameScene.showWinnerPopup(winner.getName());
            return;
        }

        if (gameModel.getGameState() == GameState.ROUND_OVER) {
            if (aiTimer.isPresent()) {
                aiTimer.get().stop();
            }
            gameScene.setHumanInputEnabled(false);

            final AbstractPlayer roundWinner = gameModel.getWinner();
            gameScene.showInfo("Round Winner: " + roundWinner.getName() + "!\nScore: " + roundWinner.getScore(),
                    "Round Over");

            gameModel.startNewRound();
            return;
        }

        // Create DTOs
        final GameViewData viewData = createGameViewData();
        gameScene.updateView(viewData);

        final boolean isHumanTurn = gameModel.getCurrentPlayer().getClass() == HumanPlayer.class;

        if (isHumanTurn) {
            if (gameModel.getGameState() == GameState.WAITING_FOR_COLOR) {
                gameScene.showColorChooser(gameModel.isDarkSide());
            }

            if (gameModel.getGameState() == GameState.WAITING_FOR_PLAYER) {
                gameScene.showPlayerChooser(gameModel.getPlayers());
            }
        }

        checkAndRunAITurn();
    }

    private GameViewData createGameViewData() {
        final GameState state = gameModel.getGameState();

        final java.util.List<uno.view.api.PlayerViewData> playerViewDataList = gameModel.getPlayers().stream()
                .map(this::createPlayerViewData)
                .collect(java.util.stream.Collectors.toList());

        final uno.view.api.PlayerViewData currentPlayer = createPlayerViewData(gameModel.getCurrentPlayer());

        uno.view.api.PlayerViewData winner = null;

        final java.util.Optional<uno.view.api.CardViewData> topCard = gameModel.getTopDiscardCard()
                .map(this::createCardViewData);

        return new uno.view.api.GameViewData(
                state,
                playerViewDataList,
                currentPlayer,
                topCard,
                gameModel.isDiscardPileEmpty(),
                gameModel.getDrawDeck().size(),
                gameModel.getCurrentColor(),
                gameModel.isDarkSide(),
                gameModel.hasCurrentPlayerDrawn(gameModel.getCurrentPlayer()),
                winner,
                gameModel.isClockwise());
    }

    private uno.view.api.PlayerViewData createPlayerViewData(final AbstractPlayer player) {
        final java.util.List<uno.view.api.CardViewData> hand = player.getHand().stream()
                .filter(java.util.Optional::isPresent)
                .map(cardOpt -> createCardViewData(cardOpt.get()))
                .collect(java.util.stream.Collectors.toList());

        return new uno.view.api.PlayerViewData(
                player.getName(),
                player.getHandSize(),
                player.getScore(),
                gameModel.getCurrentPlayer().equals(player),
                hand,
                player);
    }

    private uno.view.api.CardViewData createCardViewData(final Card card) {
        final String imageKey = card.getColor(gameModel).name() + "_" + card.getValue(gameModel).name();
        return new uno.view.api.CardViewData(
                card.getColor(gameModel),
                card.getValue(gameModel),
                imageKey,
                java.util.Optional.of(card));
    }

    /**
     * Check if the current player is an AI and, if so, schedule its turn after a
     * short delay.
     */
    private void checkAndRunAITurn() {
        if (gameModel.getGameState() != GameState.RUNNING) {
            return;
        }

        final AbstractPlayer currentPlayer = gameModel.getCurrentPlayer();

        if (currentPlayer instanceof AbstractAIPlayer) {
            gameScene.setHumanInputEnabled(false);

            final ActionListener aiTask = new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    ((AbstractAIPlayer) currentPlayer).takeTurn(gameModel); // Cast to AbstractAIPlayer to access
                                                                            // takeTurn
                }
            };

            aiTimer = Optional.of(new Timer(AI_DELAY, aiTask));
            aiTimer.get().setRepeats(false);
            aiTimer.get().start();
        } else {
            gameScene.setHumanInputEnabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPlayCard(final Optional<Card> card) {
        try {
            gameModel.playCard(card);
        } catch (final IllegalStateException e) {
            gameScene.showError(e.getMessage(), "Can't play this card!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDrawCard() {
        try {
            gameModel.playerInitiatesDraw();
        } catch (final IllegalStateException e) {
            gameScene.showError(e.getMessage(), "Can't draw a card!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCallUno() {
        try {
            gameModel.callUno(gameModel.getPlayers().getFirst());
        } catch (final IllegalStateException e) {
            gameScene.showError(e.getMessage(), "Can't call UNO!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackToMenu() {
        if (gameScene.confirmExit()) {
            final MenuController menuController = new MenuControllerImpl(mainFrame);
            final MenuScene menuScene = new MenuSceneImpl();
            menuScene.setObserver(menuController);
            mainFrame.showScene((Container) menuScene);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPassTurn() {
        try {
            gameModel.playerPassTurn();
        } catch (final IllegalStateException e) {
            gameScene.showError(e.getMessage(), "Can't pass turn!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onColorChosen(final CardColor color) {
        gameModel.setColor(color);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPlayerChosen(final AbstractPlayer player) {
        gameModel.chosenPlayer(player);
    }
}

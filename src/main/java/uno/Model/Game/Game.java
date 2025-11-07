package uno.Model.Game;

import uno.Model.Cards.Card;
import uno.Model.Cards.Attributes.CardColor;
import uno.Model.Cards.Deck.Deck;
import uno.Model.Player.Player;
import uno.View.GameModelObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Classe principale del Modello. Contiene la logica e lo stato della partita.
 * Delega la gestione dei turni al TurnManager.
 */
public class Game {

    private final List<GameModelObserver> observers = new ArrayList<>();
    private final Deck<Card> drawDeck;
    private final DiscardPile discardPile;
    private final List<Player> players;
    
    // --- NUOVI CAMPI ---
    private final TurnManager turnManager; // Delega la gestione dei turni
    private GameState currentState;
    private CardColor currentWildColor;

    /**
     * Costruisce una nuova partita.
     * @param deck Il mazzo da usare.
     * @param players La lista dei giocatori, già creata e in ordine.
     */
    public Game(Deck<Card> deck, List<Player> players) {
        this.drawDeck = deck;
        this.players = players;
        this.discardPile = new DiscardPile();
        
        // --- COLLEGAMENTO ---
        // Il TurnManager ora possiede la logica di chi sta giocando
        this.turnManager = new TurnManager(players); 
        
        this.currentState = GameState.RUNNING;
        this.currentWildColor = null;
        
        // NOTA: La distribuzione delle carte ora è gestita da GameSetup
        // nel MenuController, non più qui.
    }
    
    // --- METODI OBSERVER ---
    
    public void addObserver(GameModelObserver observer) {
        this.observers.add(observer);
    }

    private void notifyObservers() {
        for (GameModelObserver obs : observers) {
            obs.onGameUpdate();
        }
    }
    
    // --- METODI DI GIOCO ---
    
    public void playCard(Card card) {
        // TODO: Aggiungere la logica di validazione della mossa
        // (es. if (!isValidMove(card))) { throw new IllegalStateException("Mossa non valida!"); }
        
        // Esegui effetto carta (polimorfismo)
        card.performEffect(this); 
        
        // Sposta la carta
        getCurrentPlayer().playCard(card);
        discardPile.addCard(card);
        
        // --- LOGICA DI AVANZAMENTO TURNO DELEGATA ---
        // Non passiamo il turno solo se stiamo aspettando una scelta di colore.
        if (this.currentState != GameState.WAITING_FOR_COLOR) {
            this.turnManager.advanceTurn();
        }
        
        notifyObservers();
    }
    
    public void playerDrawCard(Player player) {
        if (drawDeck.isEmpty()) {
            // TODO: Logica per rimescolare gli scarti
            notifyObservers(); 
            return;
        }
        player.addCardToHand(drawDeck.drawCard());
        
        notifyObservers();
    }

    // --- METODI GETTER ---

    /**
     * Ora chiede al TurnManager chi è il giocatore corrente.
     */
    public Player getCurrentPlayer() {
        return turnManager.getCurrentPlayer();
    }
    
    public Card getTopDiscardCard() {
        try {
            return discardPile.getTopCard();
        } catch (NoSuchElementException e) {
            return null; // Pila scarti vuota
        }
    }

    public boolean isDiscardPileEmpty() {
        return discardPile.isEmpty();
    }

    public GameState getGameState() {
        return this.currentState;
    }
    
    public CardColor getCurrentWildColor() {
        return this.currentWildColor;
    }

    public Deck<Card> getDrawDeck() {
        return this.drawDeck;
    }

    public DiscardPile getDiscardPile() {
        return this.discardPile;
    }
    
    // --- METODI PER GLI EFFETTI DELLE CARTE (Delegano al TurnManager) ---
    
    public void skipNextPlayer() {
        this.turnManager.skipNextPlayer();
        System.out.println("Giocatore saltato!");
    }
    
    public void makeNextPlayerDraw(int amount) {
        Player nextPlayer = this.turnManager.peekNextPlayer();
        System.out.println(nextPlayer.getName() + " pesca " + amount);
        for(int i = 0; i < amount; i++) {
            playerDrawCard(nextPlayer);
        }
    }
    
    public void reversePlayOrder() {
        this.turnManager.reverseDirection();
        System.out.println("Ordine invertito!");
    }

    // --- METODI PER I JOLLY ---

    public void requestColorChoice() {
        this.currentState = GameState.WAITING_FOR_COLOR;
        notifyObservers();
    }

    public void setColor(CardColor color) {
        if (this.currentState != GameState.WAITING_FOR_COLOR) {
            return;
        }
        this.currentWildColor = color;
        this.currentState = GameState.RUNNING; 
        
        // Ora che il colore è stato scelto, passiamo il turno.
        this.turnManager.advanceTurn();

        notifyObservers();
    }
}
package uno.Controller;

import uno.Model.Cards.Card;
import uno.Model.Cards.Attributes.CardColor;

/**
 * Interfaccia (Observer) che definisce le azioni che l'utente
 * può compiere dalla schermata di gioco.
 * La GameScene chiamerà questi metodi, e il GameController
 * li implementerà.
 */
public interface GameViewObserver {

    /**
     * Chiamato quando l'utente clicca su una carta nella sua mano.
     * @param card La carta che l'utente ha tentato di giocare.
     */
    void onPlayCard(Card card);

    /**
     * Chiamato quando l'utente clicca sul mazzo di pesca.
     */
    void onDrawCard();

    /**
     * Chiamato quando l'utente clicca il bottone "UNO".
     */
    void onCallUno();

    /**
     * Chiamato quando l'utente vuole tornare al menu.
     */
    void onBackToMenu();

    /**
<<<<<<< HEAD
     * Chiamato quando l'utente clicca il bottone "Passa".
     */
    void onPassTurn();

    void onChooseColor();
=======
     * Chiamato quando l'utente clicca su uno dei bottoni colore
     * dopo aver giocato una carta Jolly.
     * @param color Il colore scelto.
     */
    void onColorChosen(CardColor color);
>>>>>>> 259963575955263c14e0048a1d1502ba0c5e8c83
}
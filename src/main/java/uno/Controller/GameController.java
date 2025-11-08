package uno.Controller;

import uno.Model.Cards.Card;
import uno.Model.Cards.Attributes.CardColor;
import uno.Model.Game.Game;
import uno.View.GameFrame;
import uno.View.Scenes.GameScene;
import uno.View.Scenes.MenuScene;

import javax.swing.JOptionPane;

/**
 * Controller che gestisce la logica di interazione
 * tra la GameScene (View) e il Game (Model).
 */
public class GameController implements GameViewObserver {

    private final Game gameModel;
    private final GameScene gameScene;
    private final GameFrame mainFrame;

    public GameController(Game gameModel, GameScene gameScene, GameFrame mainFrame) {
        this.gameModel = gameModel;
        this.gameScene = gameScene;
        this.mainFrame = mainFrame;
    }

    @Override
    public void onPlayCard(Card card) {
        System.out.println("Tentativo di giocare la carta: " + card);
        try {
            // Comanda al modello di giocare la carta
            gameModel.playCard(card);
            // Il modello notificherà la GameScene per l'aggiornamento
            // (grazie a gameModel.addObserver(gameScene) nel costruttore)

            // TODO: Aggiungere logica per turno IA
            
        } catch (Exception e) {
            // Mostra un errore se la mossa non è valida
            JOptionPane.showMessageDialog(gameScene, 
                e.getMessage(), 
                "Mossa non valida", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void onDrawCard() {
        System.out.println("L'utente clicca 'Pesca'");
        try {
            // Chiama il nuovo metodo con la logica di validazione
            gameModel.playerInitiatesDraw(); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(gameScene, 
                e.getMessage(), // Messaggio d'errore (es. "Hai già pescato")
                "Mossa non valida", 
                JOptionPane.ERROR_MESSAGE);
        }
    }


    @Override
    public void onCallUno() {
        System.out.println("L'utente clicca 'UNO!'");
        try {
            gameModel.callUno(gameModel.getCurrentPlayer());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(gameScene, 
                e.getMessage(), // Messaggio d'errore (es. "Non puoi chiamare UNO ora")
                "Mossa non valida", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void onBackToMenu() {
        // Logica per tornare al menu
        int choice = JOptionPane.showConfirmDialog(
            gameScene, 
            "Sei sicuro di voler tornare al menu? La partita sarà persa.",
            "Torna al Menu", 
            JOptionPane.YES_NO_OPTION);
            
        if (choice == JOptionPane.YES_OPTION) {
            // Ricrea il controller e la scena del menu
            MenuController menuController = new MenuController(mainFrame);
            MenuScene menuScene = new MenuScene();
            menuScene.setObserver(menuController);
            mainFrame.showScene(menuScene);
        }
    }

    /**
     * Implementazione del nuovo metodo "Passa".
     */
    @Override
    public void onPassTurn() {
        System.out.println("L'utente clicca 'Passa'");
        try {
            gameModel.playerPassTurn();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(gameScene, 
                e.getMessage(), // Messaggio (es. "Non puoi passare se non hai pescato")
                "Mossa non valida", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Implementa il metodo dell'interfaccia.
     * Riceve il colore scelto dalla View e lo passa al Modello.
     * @param color Il colore scelto.
     */
    @Override
    public void onColorChosen(CardColor color) {
        System.out.println("Colore scelto: " + color);
        // Il GameModel riceverà il colore, imposterà il suo stato
        // interno e notificherà la View (che si aggiornerà di nuovo).
        gameModel.setColor(color);
    }
}
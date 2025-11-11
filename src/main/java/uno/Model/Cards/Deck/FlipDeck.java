package uno.Model.Cards.Deck;

import uno.Model.Cards.Attributes.CardColor;
import uno.Model.Cards.Attributes.CardFace;
import uno.Model.Cards.Attributes.CardValue;
import uno.Model.Cards.Card;
import uno.Model.Cards.Types.NumberedCard;
import uno.Model.Cards.Types.SkipCard;
import uno.Model.Cards.Types.ReverseCard;
import uno.Model.Cards.Types.DrawOneCard;
import uno.Model.Cards.Types.DrawTwoCard;
import uno.Model.Cards.Types.FlipCard;
import uno.Model.Cards.Types.WildCard;
import uno.Model.Cards.Types.WildDrawColorCard;
import uno.Model.Cards.Types.WildDrawFourCard;
import uno.Model.Cards.Types.WildDrawTwoCard;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Rappresenta un mazzo di UNO classico da 108 carte.
 * Estende la classe Deck astratta e implementa il metodo
 * di creazione specifico per questa modalità di gioco.
 */
public class FlipDeck extends Deck<Card> {

    /**
     * Costruisce un nuovo mazzo standard.
     * Il costruttore della classe padre (Deck) chiamerà automaticamente
     * createDeck() e poi shuffle().
     */
    public FlipDeck() {
        super();
    }

    /**
     * Implementazione del metodo astratto per popolare la lista 'cards' 
     * (protetta nella classe padre) con le 108 carte del gioco classico,
     * utilizzando classi specifiche per ogni effetto.
     */
    @Override
    protected void createDeck() {
        Gson gson = new Gson();
        // Cerca il file JSON nella cartella delle risorse
        String resourcePath = "/json/flipmap.json";

        try (InputStream is = getClass().getResourceAsStream(resourcePath);
            Reader reader = new InputStreamReader(is)) {

            if (is == null) {
                throw new IOException("File non trovato nelle risorse: " + resourcePath);
            }
            
            // Legge il file JSON in un array di oggetti Mapping
            Mapping[] mappings = gson.fromJson(reader, Mapping[].class);

            // Aggiunge le carte al mazzo
            for (Mapping mapping : mappings) {
                // Instanzia una o più copie della carta basandosi sulla singola mappatura
                addCardMappingToDeck(mapping);
            }

        } catch (IOException | NullPointerException e) {
            System.err.println("FATALE: Errore di caricamento o parsing di flipmap.json. Impossibile creare il mazzo Flip.");
            e.printStackTrace();
        }
    }

    /**
     * Crea un'istanza Card dal Mapping letto e la aggiunge al mazzo il numero corretto di volte.
     */
    private void addCardMappingToDeck(Mapping mapping) {
        // 1. Converti i DTO in oggetti CardFace
        CardFace lightFace = createCardFace(mapping.light);
        CardFace darkFace = createCardFace(mapping.dark);

        // --- CORREZIONE LOGICA CHIAVE ---
        // Determina il TIPO di carta PIÙ COMPLESSO
        Card card = CardFactory.createFlipCard(lightFace, darkFace);

        cards.add(card);
        System.out.println("Aggiunta carta al mazzo: " + card);
    }

    /**
     * Metodo helper per convertire CardConfig (dal JSON) in CardFace.
     */
    private CardFace createCardFace(CardConfig config) {
        // Usa valueOf per convertire le stringhe del JSON negli Enum
        CardColor color = CardColor.valueOf(config.color.toUpperCase());
        CardValue value = CardValue.valueOf(config.value.toUpperCase());
        return new CardFace(color, value);
    }

    // =========================================================================
    // CLASSI DTO PER IL PARSING JSON
    // TODO: SPOSTALE IN UN FILE SEPARATO SE NECESSARIO
    // =========================================================================

    /**
     * Semplice factory per decidere quale classe di carta istanziare.
     * Questa logica dovrebbe stare idealmente in CardFactory.java.
     */
    private static class CardFactory {
        
        /**
         * Crea la classe Flip Card più adatta per incapsulare i comportamenti di entrambi i lati.
         * @param light La faccia chiara.
         * @param dark La faccia scura.
         * @return La carta più "complessa" in termini di effetti.
         */
        public static Card createFlipCard(CardFace light, CardFace dark) {
            CardValue lightValue = light.value();
            CardValue darkValue = dark.value();

            // 1. Controlla i valori Wild più complessi
            if (lightValue == CardValue.WILD_DRAW_FOUR || darkValue == CardValue.WILD_DRAW_FOUR) {
                 return new WildDrawFourCard(light, dark);
            }
            if (lightValue == CardValue.WILD_DRAW_TWO || darkValue == CardValue.WILD_DRAW_TWO) {
                 return new WildDrawTwoCard(light, dark);
            }

            // 2. Controlla i valori Jolly normali
            if (lightValue == CardValue.WILD || darkValue == CardValue.WILD) {
                return new WildCard(light, dark);
            }

            // 3. Controlla la CARTA FLIP
            if (lightValue == CardValue.FLIP || darkValue == CardValue.FLIP) {
                return new FlipCard(light, dark); // Questa è l'unica FlipCard vera
            }

            // 4. Controlla le Azioni Standard (Reverse, Skip, DrawTwo)
            // Se entrambi i lati sono REVERSE, allora è una ReverseCard
            if (lightValue == CardValue.REVERSE || darkValue == CardValue.REVERSE) {
                 return new ReverseCard(light, dark);
            }
            if (lightValue == CardValue.SKIP || darkValue == CardValue.SKIP) {
                return new SkipCard(light, dark);
            }
            if (lightValue == CardValue.DRAW_TWO || darkValue == CardValue.DRAW_TWO) {
                return new DrawTwoCard(light, dark);
            }

            // 5. Se tutti i controlli falliscono, si tratta di una Carta Numerica
            // (La classe NumberedCard gestirà correttamente che il lato scuro
            // potrebbe non essere un numero, se non ha un effetto speciale)
            return new NumberedCard(light, dark);
        }

        private static boolean isActionCard(CardValue value) {
            return value == CardValue.SKIP || value == CardValue.REVERSE || value == CardValue.DRAW_TWO || 
                   value == CardValue.FLIP || value == CardValue.WILD || value == CardValue.WILD_DRAW_TWO || 
                   value == CardValue.WILD_DRAW_FOUR;
        }

        private static Card createCardByValue(CardValue value, CardFace light, CardFace dark) {
            // Questa funzione si assicura che venga usata la classe PIÙ ADATTA
            return switch (value) {
                case SKIP -> new SkipCard(light, dark);
                case REVERSE -> new ReverseCard(light, dark);
                case DRAW_TWO -> new DrawTwoCard(light, dark);
                case FLIP -> new FlipCard(light, dark);
                case WILD -> new WildCard(light, dark);
                // Le carte Wild più complesse (Draw) devono essere gestite
                case WILD_DRAW_TWO -> new WildDrawTwoCard(light, dark);
                default -> new NumberedCard(light, dark); // Per i numeri o i valori non mappati
            };
        }
    }

    /**
     * Rappresenta la struttura dati di un singolo lato (Chiaro o Scuro) nel JSON.
     */
    private static class CardConfig {
        @SerializedName("color")
        String color;
        @SerializedName("value")
        String value;
    }

    /**
     * Rappresenta l'intera mappatura (Light <-> Dark) per un tipo di carta.
     */
    private static class Mapping {
        @SerializedName("light")
        CardConfig light;
        @SerializedName("dark")
        CardConfig dark;
        
        // Opzionale: puoi aggiungere qui il numero di copie (es. "count": 2) se il JSON lo includesse.
    }
}
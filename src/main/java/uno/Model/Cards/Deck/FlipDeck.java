package uno.Model.Cards.Deck;

import uno.Model.Cards.Attributes.CardColor;
import uno.Model.Cards.Attributes.CardValue;
import uno.Model.Cards.Card;
import uno.Model.Cards.Types.NumberedCard;
import uno.Model.Cards.Types.SkipCard;
import uno.Model.Cards.Types.ReverseCard;
import uno.Model.Cards.Types.DrawTwoCard;
import uno.Model.Cards.Types.FlipCard;
import uno.Model.Cards.Types.WildCard;
import uno.Model.Cards.Types.WildDrawFourCard;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        // Lista dei 4 colori principali
        final List<CardColor> colors = Arrays.asList(
                CardColor.RED, 
                CardColor.BLUE, 
                CardColor.GREEN, 
                CardColor.YELLOW
        );

        // Lista dei valori numerici da 1 a 9
        final List<CardValue> numberValues = Arrays.asList(
                CardValue.ONE, CardValue.TWO, CardValue.THREE, CardValue.FOUR,
                CardValue.FIVE, CardValue.SIX, CardValue.SEVEN, CardValue.EIGHT, CardValue.NINE
        );

        for (CardColor light : colors) {
            
            // 1x Carta Zero (Lato Chiaro)
            this.cards.add(new NumberedCard(light, CardValue.ZERO));
            
            // 2x Carte Numeriche (1-9) (Lato Chiaro)
            for (CardValue value : numberValues) {
                this.cards.add(new NumberedCard(light, value));
                this.cards.add(new NumberedCard(light, value));
            }

            // 2x Carte Azione (Skip, Reverse, DrawTwo) (Lato Chiaro)
            this.cards.add(new SkipCard(light));
            this.cards.add(new SkipCard(light));
            this.cards.add(new ReverseCard(light));
            this.cards.add(new ReverseCard(light));
            this.cards.add(new DrawTwoCard(light));
            this.cards.add(new DrawTwoCard(light));
            
            // 2x Carte Flip (Lato Chiaro)
            this.cards.add(new FlipCard(light));
            this.cards.add(new FlipCard(light));
        }

        // 4x Jolly (Wild) (Lato Chiaro)
        for (int i = 0; i < 4; i++) {
            this.cards.add(new WildCard());
        }
        
        // 4x Jolly Pesca Quattro (Wild Draw Four) (Lato Chiaro)
        for (int i = 0; i < 4; i++) {
            this.cards.add(new WildDrawFourCard());
        }
    }

    /**
     * Sostituisce l'intero mazzo con le sue controparti "flippate"
     * e lo mischia di nuovo.
     */
    @Override
    public void flipDeck(Function<Card, Card> translator) {
        // 1. Traduce tutte le carte in una nuova lista temporanea (modificabile).
        List<Card> newCards = this.cards.stream()
            .map(translator)
            .collect(Collectors.toList());

        // 2. Svuota la lista esistente (rimuove tutti i vecchi riferimenti).
        this.cards.clear();

        // 3. Aggiunge i nuovi elementi (gli elementi flippati) alla lista esistente.
        //    Il puntatore 'this.cards' non viene modificato, solo il contenuto.
        this.cards.addAll(newCards);
    }
}
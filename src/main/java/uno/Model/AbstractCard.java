// Percorso: src/main/java/uno/Model/AbstractCard.java
package uno.Model;

/**
 * Classe base astratta che fornisce un'implementazione comune
 * per i campi e metodi base dell'interfaccia Card.
 */
public abstract class AbstractCard implements Card {

    protected final Color color;
    protected final Value value;

    public AbstractCard(Color color, Value value) {
        this.color = color;
        this.value = value;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Value getValue() {
        return value;
    }

    /**
     * Fornisce la regola di gioco standard.
     * Le WildCard sovrascriveranno questo metodo.
     */
    @Override
    public boolean canBePlayedOn(Card topCard) {
        // Regola standard: stesso colore o stesso valore
        return this.color == topCard.getColor() || this.value == topCard.getValue();
    }

    @Override
    public String toString() {
        // Utile per il debug
        return color + " " + value;
    }
}

package uno.Model.Cards.Attributes;

public enum CardValue {
    // Carte Numerate
    ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE,
    
    // Carte Azione
    SKIP,     // Salta
    REVERSE,  // Inverti
    DRAW_TWO, // Pesca Due
    
    // Carte Jolly
    WILD,           // Jolly
    WILD_DRAW_FOUR,  // Jolly Pesca Quattro

    FLIP, // Flip
    WILD_DRAW_TWO, // Jolly Pesca Due (per Flip)
    DRAW_FIVE, // Pesca Cinque (per Flip)
    DRAW_ONE, // Pesca Uno (per Flip)
    SKIP_EVERYONE, // Salta Tutti (per Flip)
    WILD_DRAW_COLOR, // Jolly Pesca Colore (per Flip)
}

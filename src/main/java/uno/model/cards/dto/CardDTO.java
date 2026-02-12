package uno.model.cards.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Generic DTO for a card definition from JSON.
 * Represents a single side of a card with raw string values.
 */
public class CardDTO {
    @SerializedName("color")
    private String color;

    @SerializedName("value")
    private String value;

    /**
     * Gets the raw color string from JSON.
     * 
     * @return the raw color string from JSON.
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets the raw value string from JSON.
     * 
     * @return the raw value string from JSON.
     */
    public String getValue() {
        return value;
    }
}

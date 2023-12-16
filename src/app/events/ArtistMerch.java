package app.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistMerch {
    private String name;
    private String description;
    private int price;

    /**
     * Constructor for Artist Merch
     * @param name
     * @param description
     * @param price
     */
    public ArtistMerch(final String name, final String description, final int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }


}

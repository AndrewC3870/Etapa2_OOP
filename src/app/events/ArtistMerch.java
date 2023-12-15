package app.events;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ArtistMerch {
    private String name;
    private String description;
    private int price;

    public ArtistMerch(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }


}

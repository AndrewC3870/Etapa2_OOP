package app.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Announcement {
    private String name;
    private String description;

    /**
     * Constructor for Host Announcement
     * @param name
     * @param description
     */
    public Announcement(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

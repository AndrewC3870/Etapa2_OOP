package app.audio.Collections;

import fileio.input.EpisodeInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class Podcasts {
    private String name;
    private ArrayList<EpisodeInput> episodes;

    /**
     * Constructor for Podcasts
     *
     * @param name name
     * @param episodes episodes
     */
    public Podcasts(final String name, final ArrayList<EpisodeInput> episodes) {
        this.name = name;
        this.episodes = episodes;
    }

}

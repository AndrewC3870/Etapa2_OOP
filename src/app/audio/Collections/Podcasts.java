package app.audio.Collections;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
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
    public Podcasts(String name, ArrayList<EpisodeInput> episodes) {
        this.name = name;
        this.episodes = episodes;
    }

}

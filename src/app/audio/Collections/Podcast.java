package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import java.util.List;

public final class Podcast extends AudioCollection {
    private final List<Episode> episodes;

    /**
     * Constructor for podcast
     * @param name name
     * @param owner owner
     * @param episodes episodes
     */

    public Podcast(final String name, final String owner, final List<Episode> episodes) {
        super(name, owner);
        this.episodes = episodes;
    }

    /**
     * Getter
     *
     * @return
     */
    public List<Episode> getEpisodes() {
        return episodes;
    }

    /**
     * getNumberOfTracks
     * @return
     */
    @Override
    public int getNumberOfTracks() {
        return episodes.size();
    }

    /**
     * getTrackByIndex
     * @param index the index
     * @return
     */
    @Override
    public AudioFile getTrackByIndex(final int index) {
        return episodes.get(index);
    }
}
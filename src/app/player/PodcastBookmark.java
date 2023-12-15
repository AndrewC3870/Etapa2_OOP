package app.player;

import lombok.Getter;

@Getter
public final class PodcastBookmark {
    private final String name;
    private final int id;
    private final int timestamp;

    /**
     * constructor
     * @param name name
     * @param id id
     * @param timestamp timestamp
     */
    public PodcastBookmark(final String name,
                           final int id,
                           final int timestamp) {
        this.name = name;
        this.id = id;
        this.timestamp = timestamp;
    }

    /**
     * toString
     * @return toString
     */
    @Override
    public String toString() {
        return "PodcastBookmark{"
                + "name='" + name + '\''
                + ", id=" + id
                + ", timestamp=" + timestamp
                + '}';
    }
}
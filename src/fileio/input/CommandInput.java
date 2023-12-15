package fileio.input;

import lombok.Getter;

import java.util.ArrayList;
@Getter

public final class CommandInput {
    private String name;
//    Pt simple user
    private int age;
    private String city;
    private String nextPage;
//    Pt Artist user album
    private int releaseYear;
    private String description;
    private ArrayList<SongInput> songs;
//    Pt Event
    private String date;
//    Pt Merch
    private int price;
//    pt host
    private ArrayList<EpisodeInput> episodes;

    @Getter
    private String command;
    @Getter
    private String username;
    @Getter
    private Integer timestamp;
    @Getter
    private String type; // song / playlist / podcast
    @Getter
    private FiltersInput filters; // pentru search
    @Getter
    private Integer itemNumber; // pentru select
    private Integer repeatMode; // pentru repeat
    @Getter
    private Integer playlistId; // pentru add/remove song
    @Getter
    private String playlistName; // pentru create playlist
    @Getter
    private Integer seed; // pentru shuffle

    public CommandInput() {
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public void setFilters(FiltersInput filters) {
        this.filters = filters;
    }

    public void setItemNumber(Integer itemNumber) {
        this.itemNumber = itemNumber;
    }

    public Integer getRepeatMode() {
        return repeatMode;
    }

    public void setRepeatMode(Integer repeatMode) {
        this.repeatMode = repeatMode;
    }

    public void setPlaylistId(Integer playlistId) {
        this.playlistId = playlistId;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    @Override
    public String toString() {
        return "CommandInput{" +
                "command='" + command + '\'' +
                ", username='" + username + '\'' +
                ", timestamp=" + timestamp +
                ", type='" + type + '\'' +
                ", filters=" + filters +
                ", itemNumber=" + itemNumber +
                ", repeatMode=" + repeatMode +
                ", playlistId=" + playlistId +
                ", playlistName='" + playlistName + '\'' +
                ", seed=" + seed +
                '}';
    }
}

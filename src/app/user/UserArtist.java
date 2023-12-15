package app.user;

import app.audio.Collections.Album;
import app.events.ArtistEvent;
import app.events.ArtistMerch;
import app.pages.ArtistPage;
import fileio.input.CommandInput;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
public class UserArtist extends User {

    private ArrayList<Album> album;
    private ArrayList<ArtistEvent> events;
    private ArrayList<ArtistMerch> merches;
    private String type;
    private boolean played;
    private ArtistPage artistPage;

    /**
     * constructor for UserArtist
     * @param username name
     * @param age age
     * @param city city
     */
    public UserArtist(String username, int age, String city) {
        super(username, age, city, "artist");
        this.played = false;
        this.album  = new ArrayList<>();
        this.events = new ArrayList<>();
        this.merches = new ArrayList<>();
        this.artistPage = new ArtistPage(this.album, this.merches, this.events);

    }

    /**
     * if given name of an album exists in list of albums
     *
     * @param name name of album
     * @return boolean
     */
    public boolean containsAlbum(String name) {
        for (Album auxAlbum: this.album) {
            if (Objects.equals(name, auxAlbum.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * calculates all likes from all albums
     *
     * @return number of likes
     */
    public Integer numberOfLikes() {
        int sum = 0;
        for (Album album1: album) {
            sum += album1.calculateLikes();
        }
        return sum;
    }

    /**
     * remove album by name
     *
     * @param name album's name
     */
    public void removeAlbum(String name) {
        this.album.removeIf(auxAlbum -> Objects.equals(name, auxAlbum.getName()));
    }

    /**
     * updates the Artist page
     */
    public void updatePage() {
        this.artistPage = new ArtistPage(this.album, this.merches, this.events);
    }

    /**
     * verify if specific event exists in all list of events
     *
     * @param name event's name
     * @return boolean
     */
    public boolean verifyEvents(String name) {
        for (ArtistEvent event: this.events) {
            if (Objects.equals(name, event.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * verify if specific merch exists in all list of merches
     *
     * @param name merch name
     * @return
     */
    public boolean verifyMerch (String name) {
        for (ArtistMerch merch: this.merches) {
            if (Objects.equals(merch.getName(), name)) {
                return true;
            }
        }
        return  false;
    }

    /**
     * remove event from list
     *
     * @param name event name
     */
    public void removeEvent(String name) {
        ArtistEvent aux = new ArtistEvent();
        for (ArtistEvent event: this.events) {
            if (Objects.equals(name, event.getName())) {
                aux = event;
            }
        }
        events.remove(aux);
    }

    /**
     * get album by its name
     *
     * @param name album name
     * @return album
     */
    public Album getAlbumByName(String name) {
        for (Album auxAlbum: album) {
            if (auxAlbum.getName().equals(name)) {
                return auxAlbum;
            }
        }
        return null;
    }
}

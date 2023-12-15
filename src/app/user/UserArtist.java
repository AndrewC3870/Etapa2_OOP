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
//    private ArrayList<Playlist> albumPlaylist;
    private ArrayList<ArtistEvent> events;
    private ArrayList<ArtistMerch> merches;
    private String type;
    private boolean played;
    private ArtistPage artistPage;
    public UserArtist(String username, int age, String city) {
        super(username, age, city, "artist");
        this.played = false;
        this.album  = new ArrayList<>();
        this.events = new ArrayList<>();
        this.merches = new ArrayList<>();
        this.artistPage = new ArtistPage(this.album, this.merches, this.events);

    }
    public boolean containsAlbum(String name) {
        for (Album auxAlbum: this.album) {
            if (Objects.equals(name, auxAlbum.getName())) {
                return true;
            }
        }
        return false;
    }

    public void removeAlbum(String name) {
        this.album.removeIf(auxAlbum -> Objects.equals(name, auxAlbum.getName()));
    }

    public void updatePage() {
        this.artistPage = new ArtistPage(this.album, this.merches, this.events);
    }


    public boolean verifyEvents(CommandInput commandInput) {
        for (ArtistEvent event: this.events) {
            if (Objects.equals(commandInput.getName(), event.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean verifyMerch (String name) {
        for (ArtistMerch merch: this.merches) {
            if (Objects.equals(merch.getName(), name)) {
                return true;
            }
        }
        return  false;
    }

    public void removeEvent(String name) {
        ArtistEvent aux = new ArtistEvent();
        for (ArtistEvent event: this.events) {
            if (Objects.equals(name, event.getName())) {
                aux = event;
            }
        }
        events.remove(aux);
    }

    public Album getAlbumByName(String name) {
        for (Album auxAlbum: album) {
            if (auxAlbum.getName().equals(name)) {
                return auxAlbum;
            }
        }
        return null;
    }
}

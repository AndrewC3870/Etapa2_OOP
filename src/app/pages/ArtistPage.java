package app.pages;

import app.audio.Collections.Album;
import app.events.ArtistEvent;
import app.events.ArtistMerch;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class ArtistPage implements Pages{

    private ArrayList<Album> albums;
    private ArrayList<ArtistMerch> merch;
    private ArrayList<ArtistEvent> events;

    /**
     * Constructor for Artist Page
     * @param album
     * @param merch
     * @param event
     */
    public ArtistPage(ArrayList<Album> album, ArrayList<ArtistMerch> merch, ArrayList<ArtistEvent> event) {
        this.albums = album;
        this.merch = merch;
        this.events = event;
    }

    /**
     * method that returns a list with al albums names
     * @return
     */
    public ArrayList<String> getAlbumsList() {
        ArrayList<String> albums = new ArrayList<>();
        for (Album iter: this.albums) {
            albums.add(iter.getName());
        }
        return albums;
    }

    /**
     * method that returns a list with all marches with price
     * @return
     */
    public ArrayList<String> getMerchList() {
        ArrayList<String> merches = new ArrayList<>();
        String merch;
        for (ArtistMerch iter: this.merch) {
            merch = iter.getName() + " - " + iter.getPrice() + ":\n\t" + iter.getDescription();
            merches.add(merch);
        }
        return merches;
    }

    /**
     * method that returns a list with all events, date for that event and the description
     * @return
     */
    public ArrayList<String> getEventList() {
        ArrayList<String> events = new ArrayList<>();
        String event;
        for (ArtistEvent iter: this.events) {
            event = iter.getName() + " - " + iter.getDate() + ":\n\t" + iter.getDescription();
            events.add(event);
        }
        return events;
    }

    /**
     * print method that creates the output
     * @return
     */
    @Override
    public String printCurrentPage() {
        return "Albums:\n\t" + getAlbumsList() + "\n\nMerch:\n\t" + getMerchList() + "\n\nEvents:\n\t" + getEventList();
    }
}

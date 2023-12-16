package app;

import app.audio.Collections.Album;
import app.audio.Files.Song;
import app.events.ArtistEvent;
import app.events.ArtistMerch;
import app.user.User;
import app.user.UserArtist;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;
import fileio.input.SongInput;
import java.util.Objects;

import static app.events.ArtistEvent.dateCorrectness;
import static app.utils.UtilMethods.*;

public class ArtistCommandRunner {
    /**
     * add album to artists list of albums
     *
     * @param commandInput command
     * @return output
     */
    public static ObjectNode addAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;

        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("artist")) {
            message = commandInput.getUsername() + " is not an artist.";
        } else {
            UserArtist artist = (UserArtist) user;
            if (artist.containsAlbum(commandInput.getName())) {
                message = commandInput.getUsername()
                        + " has another album with the same name.";
            } else if (sameSongs(commandInput) > 1) {
                message = commandInput.getUsername()
                       + " has the same song at least twice in this album.";
            } else {
                Album album = new Album(commandInput.getName(), commandInput.getUsername(),
                        commandInput.getReleaseYear(),
                        commandInput.getDescription(), commandInput.getSongs());
                album.setName(commandInput.getName());

                artist.getAlbum().add(album);
                artist.updatePage();
                for (SongInput songs: commandInput.getSongs()) {
                    Admin.addNewSongs(songs);
                }
                message = commandInput.getUsername() + " has added new album successfully.";
            }
        }
        return createOutputForArtisHost(commandInput, message);
    }

    /**
     * remove album if users is not listening it
     *
     * @param commandInput command
     * @return output
     */
    public static ObjectNode removeAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;

        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("artist")) {
            message = commandInput.getUsername() + " is not an artist.";
        } else {
            UserArtist artist = (UserArtist) user;
            if (!artist.containsAlbum(commandInput.getName())) {
                message = commandInput.getUsername()
                        + " doesn't have an album with the given name.";
            } else if (ifDeletingForArtist(artist) || ifIsInPlaylist(artist.getAlbum())) {
                message = commandInput.getUsername() + " can't delete this album.";
            } else {
                Album album = artist.getAlbumByName(commandInput.getName());
                for (Song songInput: album.getSongs()) {
                    Admin.removeSong(songInput);
                }
                for (Song songInput: album.getSongs()) {
                    for (User user1: Admin.getUsers()) {
                        if (user1.getType().equals("user")) {
                            user1.getLikedSongs().remove(songInput);
                        }
                    }
                }
                artist.removeAlbum(commandInput.getName());
                message = commandInput.getUsername() + " deleted the album successfully.";
            }
        }
        return createOutputForArtisHost(commandInput, message);
    }


    /**
     * method to verify if artist wants to add album that contains two or more songs in album
     *
     * @param commandInput command
     * @return number of same songs
     */
    private static int sameSongs(final CommandInput commandInput) {
        int count = 0;
        for (SongInput songs: commandInput.getSongs()) {
            count = 0;
            for (SongInput sameSongs: commandInput.getSongs()) {
                if (Objects.equals(songs.getName(), sameSongs.getName())) {
                    count++;
                    if (count > 1) {
                        return count;
                    }
                }
            }
        }
        return count;
    }

    /**
     * add event for Artis if he/she doesn't have same event
     *
     * @param commandInput command
     * @return output
     */
    public static ObjectNode addEvent(final CommandInput commandInput) {
        String message;
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!Objects.equals(user.getType(), "artist")) {
            message = user.getUsername() + " is not an artist.";
        } else {
            UserArtist artist = (UserArtist) user;
            if (artist.verifyEvents(commandInput.getName())) {
                message = artist.getUsername() + " has another event with the same name.";
            } else if (!dateCorrectness(commandInput.getDate())) {
                message = "Event for " + artist.getUsername() + " does not have a valid date.";
            } else {
                ArtistEvent event = new ArtistEvent();
                event.setDate(commandInput.getDate());
                event.setName(commandInput.getName());
                event.setDescription(commandInput.getDescription());
                artist.getEvents().add(event);
                artist.updatePage();
                message = artist.getUsername() + " has added new event successfully.";

            }
        }

        return createOutputForArtisHost(commandInput, message);
    }

    /**
     * remove event if exists
     *
     * @param commandInput command
     * @return output
     */
    public static ObjectNode removeEvent(final CommandInput commandInput) {
        String message;
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!Objects.equals(user.getType(), "artist")) {
            message = user.getUsername() + " is not an artist.";
        } else {
            UserArtist artist = (UserArtist) user;
            if (!artist.verifyEvents(commandInput.getName())) {
                message = artist.getUsername() + " doesn't have an event with the given name.";
            } else {
                artist.removeEvent(commandInput.getName());
                message = artist.getUsername() + " deleted the event successfully.";
            }
        }

        return createOutputForArtisHost(commandInput, message);
    }

    /**
     * add merch if not existing yet
     *
     * @param commandInput command
     * @return output
     */
    public static ObjectNode addMerch(final CommandInput commandInput) {
        String message;
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!Objects.equals(user.getType(), "artist")) {
            message = user.getUsername() + " is not an artist.";
        } else {
            UserArtist artist = (UserArtist) user;
            if (artist.verifyMerch(commandInput.getName())) {
                message = artist.getUsername() + " has merchandise with the same name.";
            } else if (commandInput.getPrice() < 0) {
                message = "Price for merchandise can not be negative.";
            } else {
                ArtistMerch merch = new ArtistMerch(commandInput.getName(),
                        commandInput.getDescription(), commandInput.getPrice());
                artist.getMerches().add(merch);
                artist.updatePage();
                message = artist.getUsername() + " has added new merchandise successfully.";
            }
        }

        return createOutputForArtisHost(commandInput, message);
    }
}

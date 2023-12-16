package app.utils;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcasts;
import app.audio.Files.Song;
import app.user.User;
import app.user.UserArtist;
import app.user.UserHost;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;
import fileio.input.EpisodeInput;
import fileio.input.SongInput;
import java.util.ArrayList;
import java.util.Objects;

public class UtilMethods {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * method to create output node
     *
     * @param commandInput command
     * @param message message
     * @return created node
     */
    public static ObjectNode createMessageOutput(final CommandInput commandInput,
                                                 final String message) {
        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return  objectNode;
    }

    /**
     * creating output first command, second user
     */
    public static ObjectNode createOutputForArtisHost(final CommandInput commandInput,
                                                      final String message) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * method to convert from SongInput to Song
     *
     * @param song SongInput
     * @return Song
     */
    public static Song convertToSong(final SongInput song) {
        return new Song(song.getName(), song.getDuration(), song.getAlbum(), song.getTags(),
                song.getLyrics(), song.getGenre(), song.getReleaseYear(), song.getArtist());
    }

    /**
     * verifies if artist can be deleted by verifying if someone is listening for his album
     *
     * @param artist the artist
     * @return boolean
     */
    public static boolean ifDeletingForArtist(final UserArtist artist) {
        for (User user: Admin.getUsers()) {
            if (user.getPlayer().getCurrentAudioFile() != null) {
                for (Album album: artist.getAlbum()) {
                    for (Song songInput: album.getSongs()) {
                        if (Objects.equals(user.getPlayer().getCurrentAudioFile().getName(),
                                songInput.getName())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * verifies if host can be deleted by verifying if someone is listening for his podcast
     *
     * @param host host
     * @return boolean
     */
    public static boolean ifDeletingForHost(final UserHost host) {
        for (User user: Admin.getUsers()) {
            if (user.getPlayer().getCurrentAudioFile() != null) {
                for (Podcasts podcast: host.getPodcasts()) {
                    for (EpisodeInput episode: podcast.getEpisodes()) {
                        if (Objects.equals(user.getPlayer().getCurrentAudioFile()
                                .getName(), episode.getName())) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    /**
     * verifies if user is on the Host's page
     *
     * @param host host
     * @return boolean
     */
    public static boolean ifOnTheHostPage(final UserHost host) {
        for (User user: Admin.getUsers()) {
            if (user.getPage().equals(host.getHostPage())) {
                return true;
            }
        }
        return false;
    }

    /**
     * verifies if user is on the Artist's page
     *
     * @param artist artist
     * @return boolean
     */
    public static boolean ifOnTheArtistPage(final UserArtist artist) {
        for (User user: Admin.getUsers()) {
            if (user.getPage().equals(artist.getArtistPage())) {
                return true;
            }
        }
        return false;
    }

    /**
     * verifies if the albums songs are in one of user's playlist
     *
     * @param album album
     * @return boolean
     */
    public static boolean ifIsInPlaylist(final ArrayList<Album> album) {
        for (User user: Admin.getUsers()) {
            for (Playlist playlist: user.getPlaylists()) {
                for (Song song: playlist.getSongs()) {
                    for (Album album1: album) {
                        for (Song songInput: album1.getSongs()) {
                            if (song.getName().equals(songInput.getName())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

}

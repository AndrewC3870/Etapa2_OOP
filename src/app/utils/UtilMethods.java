package app.utils;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcasts;
import app.audio.Files.Episode;
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
import java.util.List;
import java.util.Objects;

public class UtilMethods {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectNode createMessageOutput(CommandInput commandInput, String message) {
        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return  objectNode;
    }

    public static Song convertToSong(SongInput song) {
        return new Song(song.getName(), song.getDuration(), song.getAlbum(), song.getTags(),
                song.getLyrics(), song.getGenre(), song.getReleaseYear(), song.getArtist());
    }

    public static boolean ifDeletingForArtist(UserArtist artist) {
        for (User user: Admin.getUsers()) {
//            if (user.getPlayer().getSource() != null && user.getPlayer().getSource().getAudioCollection() != null) {
//                for (Album album: artist.getAlbum()) {
//                    if (user.getPlayer().getSource().getAudioCollection().equals(album)) {
//                        return true;
//                    }
//                }
//            }
            if (user.getPlayer().getCurrentAudioFile() != null) {
                for (Album album: artist.getAlbum()) {
                    for (SongInput songInput: album.getSongs()) {
                        if (Objects.equals(user.getPlayer().getCurrentAudioFile().getName(), songInput.getName())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean ifDeletingForHost(UserHost host) {
        for (User user: Admin.getUsers()) {
//            if (user.getPlayer().getSource() != null && user.getPlayer().getSource().getAudioCollection() != null) {
//                for (Podcasts podcast: host.getPodcasts()) {
//                    if (user.getPlayer().getSource().getAudioCollection().equals(podcast)) {
//                        return true;
//                    }
//                }
//            }
            if (user.getPlayer().getCurrentAudioFile() != null) {
                for (Podcasts podcast: host.getPodcasts()) {
                    for (EpisodeInput episode: podcast.getEpisodes()) {
                        if (Objects.equals(user.getPlayer().getCurrentAudioFile().getName(), episode.getName())) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    public static boolean ifOnTheHostPage(UserHost host) {
        for (User user: Admin.getUsers()) {
            if (user.getPage().equals(host.getHostPage())) {
                return true;
            }
        }
        return false;
    }

    public static boolean ifOnTheArtistPage(UserArtist artist) {
        for (User user: Admin.getUsers()) {
            if (user.getPage().equals(artist.getArtistPage())) {
                return true;
            }
        }
        return false;
    }

    public static boolean ifIsInPlaylist(ArrayList<Album> album) {
        for (User user: Admin.getUsers()) {
            for (Playlist playlist: user.getPlaylists()) {
                for (Song song: playlist.getSongs()) {
                    for (Album album1: album) {
                        for (SongInput songInput: album1.getSongs()) {
                            if (song.getName().equals(songInput.getName()));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


}

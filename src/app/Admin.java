package app;

import app.audio.Collections.*;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.User;
import app.user.UserArtist;
import app.user.UserHost;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.*;
import lombok.Getter;

import java.util.*;

public class Admin {
    @Getter
    private static List<User> users = new ArrayList<>();
    @Getter
    private static List<UserArtist> userArtists= new ArrayList<>();
    private static List<UserHost> userHosts = new ArrayList<>();
    @Getter
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static int timestamp = 0;
//  creaza liste separate pt artist si pt host
    public static void setUsers(List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity(), "user"));
        }
    }


    public static void setSongs(List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    public static void addNewSongs(SongInput songInput) {
        Song song = new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                songInput.getReleaseYear(), songInput.getArtist());
        songs.add(song);
    }

    public static void addNewPodcast(Podcasts podcast, String name) {
        List<Episode> episodes = new ArrayList<>();
        Episode episode;
        for (EpisodeInput episodeInput: podcast.getEpisodes()) {
            episode = new Episode(episodeInput.getName(), episodeInput.getDuration(), episodeInput.getDescription());
            episodes.add(episode);
        }
        Podcast newPodcast = new Podcast(podcast.getName(), name, episodes);
        podcasts.add(newPodcast);
    }


    public static void setPodcasts(List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(), episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

//    ce adaug eu nou


    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    public static List<Album> getAlbums() {
        List<Album> albums = new ArrayList<>();
        for(UserArtist user: userArtists) {
            albums.addAll(user.getAlbum());
        }
        return albums;
    }

    public static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }


    public static UserArtist getArtist(String name) {
        for (UserArtist user : userArtists) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public static UserHost getHost(String name) {
        for (UserHost user: userHosts) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public static void updateTimestamp(int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            user.simulateTime(elapsed);
        }
    }

//    public static boolean isPlaying() {
//
//    }

    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= 5) break;
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= 5) break;
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

//    ce adaug eu ******************
    public static List<String> getTop5Albums() {
//        List<Album> sortedPlaylists = new ArrayList<>(getAlbums());
//        sortedPlaylists.sort(Comparator.comparingInt(Album::getFollowers)
//                .reversed()
//                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
//        List<String> topPlaylists = new ArrayList<>();
//        int count = 0;
//        for (Playlist playlist : sortedPlaylists) {
//            if (count >= 5) break;
//            topPlaylists.add(playlist.getName());
//            count++;
//        }
//        return topPlaylists;
    }

    public static List<String> getOnlineUsers() {
        List<String> onlineUsers = new ArrayList<>();
        for (User user: users) {
            if (user.isConectionStatus() && Objects.equals(user.getType(), "user")) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }

    public static void addArtist(UserArtist user) {
        users.add(user);
        userArtists.add(user);
    }
    public static void addHost(UserHost user) {
        users.add(user);
        userHosts.add(user);
    }
    public static void addUser(User user) {
        users.add(user);
    }
    public static void removeUser(User user) {
        users.remove(user);
    }
    public static void removeUserArtist(UserArtist user) {
        users.remove(user);
        userArtists.remove(user);

        user.getAlbum().clear();

    }
    public static void removeUserHost(UserHost user) {
        users.remove(user);
        userHosts.remove(user);
    }
    public  static void removeSong(Song song) {
        songs.removeIf((s) -> song.getName().equals(s.getName()));
    }


    public static void removePodcast(Podcasts podcast) {
        podcasts.remove(podcast.getName());
    }
    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        userArtists=new ArrayList<>();
        userHosts=new ArrayList<>();
        timestamp = 0;
    }

//    public static void removeSongs(List<Song> songsInput) {
//
//        for (Song song: songsInput) {
//            songs.remove(song);
//        }
//
//    }



}

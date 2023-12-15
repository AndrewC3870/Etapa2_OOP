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
/**
 * The type Admin.
 */
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

    /**
     * Sets users.
     *
     * @param userInputList the user input list
     */
    public static void setUsers(List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity(), "user"));
        }
    }

    /**
     * Sets songs.
     *
     * @param songInputList the song input list
     */
    public static void setSongs(List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    /**
     * add new song into song list
     *
     * @param songInput song
     */
    public static void addNewSongs(SongInput songInput) {
        Song song = new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                songInput.getReleaseYear(), songInput.getArtist());
        songs.add(song);
    }

    /**
     * add new podcast in podcast list
     *
     * @param podcast podcast
     * @param name name
     */
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

    /**
     * Sets podcasts.
     *
     * @param podcastInputList the podcast input list
     */
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

    /**
     * Gets songs.
     *
     * @return the songs
     */
    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    /**
     * Gets playlists.
     *
     * @return the playlists
     */
    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    /**
     * get list of albums
     *
     * @return albums
     */
    public static List<Album> getAlbums() {
        List<Album> albums = new ArrayList<>();
        for(UserArtist user: userArtists) {
            albums.addAll(user.getAlbum());
        }
        return albums;
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    public static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * get artist by name
     *
     * @param name artist's name
     * @return artist
     */
    public static UserArtist getArtist(String name) {
        for (UserArtist user : userArtists) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    /**
     * get host by name
     *
     * @param name host's name
     * @return host
     */
    public static UserHost getHost(String name) {
        for (UserHost user: userHosts) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Update timestamp.
     *
     * @param newTimestamp the new timestamp
     */
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

    /**
     * Gets top 5 songs.
     *
     * @return the top 5 songs
     */
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

    /**
     * Gets top 5 playlists.
     *
     * @return the top 5 playlists
     */
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

    /**
     * get top 5 albums
     *
     * @return list of albums
     */
    public static List<String> getTop5Albums() {
        List<Album> sortedAlbum = new ArrayList<>(getAlbums());
        sortedAlbum.sort(Comparator.comparingInt(Album::calculateLikes)
                .reversed()
                .thenComparing(Album::getName, Comparator.naturalOrder()));
        List<String> topAlbum = new ArrayList<>();
        int count = 0;
        for (Album album : sortedAlbum) {
            if (count >= 5) break;
            topAlbum.add(album.getName());
            count++;
        }
        return topAlbum;
    }

    /**
     * get top 5 artists
     *
     * @return list of artists
     */
    public static List<String> getTop5Artists() {
        List<UserArtist> sortedArtist = new ArrayList<>(getUserArtists());
        sortedArtist.sort(Comparator.comparingInt(UserArtist::numberOfLikes)
                .reversed());
        List<String> topAlbum = new ArrayList<>();
        while (sortedArtist.size() > 5) {
            sortedArtist.remove(sortedArtist.size() - 1);
        }
        for (UserArtist user: sortedArtist) {
            topAlbum.add(user.getUsername());
        }

        return topAlbum;
    }

    /**
     * get online users
     *
     * @return list of online users
     */
    public static List<String> getOnlineUsers() {
        List<String> onlineUsers = new ArrayList<>();
        for (User user: users) {
            if (user.isConectionStatus() && Objects.equals(user.getType(), "user")) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }

    /**
     * add artist to list of users
     *
     * @param user artist
     */
    public static void addArtist(UserArtist user) {
        users.add(user);
        userArtists.add(user);
    }

    /**
     * add host to list of hosts
     *
     * @param user host
     */
    public static void addHost(UserHost user) {
        users.add(user);
        userHosts.add(user);
    }

    /**
     * add simple user
     *
     * @param user user
     */
    public static void addUser(User user) {
        users.add(user);
    }

    /**
     * remove simple user
     *
     * @param user user
     */
    public static void removeUser(User user) {
        users.remove(user);
    }

    /**
     * remove artist
     * @param user artist
     */
    public static void removeUserArtist(UserArtist user) {
        users.remove(user);
        userArtists.remove(user);
        user.getAlbum().clear();
    }

    /**
     * remove host
     * @param user host
     */
    public static void removeUserHost(UserHost user) {
        users.remove(user);
        userHosts.remove(user);
        user.getPodcasts().clear();
    }

    /**
     * remove given song
     *
     * @param song song
     */
    public  static void removeSong(Song song) {
        songs.removeIf((s) -> song.getName().equals(s.getName()));
    }

    /**
     * remove given podcast
     *
     * @param podcast podcast
     */
    public static void removePodcast(Podcasts podcast) {
        podcasts.removeIf((p) -> podcast.getName().equals(p.getName()));
    }

    /**
     * Reset.
     */
    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        userArtists=new ArrayList<>();
        userHosts=new ArrayList<>();
        timestamp = 0;
    }

    /**
     * verify if can delete user
     *
     * @param user user
     * @return boolean
     */
    public static boolean cantDeleteSimpleUser(User user) {
        for (User auxUser: users) {
            if (auxUser.getPlayer().getSource() != null) {
                for (Playlist playlist: user.getPlaylists()) {
                    if (Objects.equals(auxUser.getPlayer().getSource().getAudioCollection(),playlist)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * delete user's playlists from followed playlists of all users
     *
     * @param user user
     */
    public static void prepareForDelete(User user) {
        for (Playlist playlist: user.getPlaylists()) {
            for (User user1: Admin.getUsers()) {
                if (user1.getType().equals("user"))
                    user1.getFollowedPlaylists().remove(playlist);
            }
        }
    }
}

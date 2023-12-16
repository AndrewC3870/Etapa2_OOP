package app;

import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Collections.Podcasts;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.User;
import app.user.UserArtist;
import app.user.UserHost;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import lombok.Getter;
import java.util.*;

/**
 * The type Admin.
 */
public class Admin {
    @Getter
    private static List<User> users = new ArrayList<>();
    @Getter
    private static List<UserArtist> userArtists = new ArrayList<>();
    private static List<UserHost> userHosts = new ArrayList<>();
    @Getter
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static int timestamp = 0;
    private final static int FIVE = 5;

    /**
     * Sets users.
     *
     * @param userInputList the user input list
     */
    public static void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(),
                    userInput.getCity(), "user"));
        }
    }

    /**
     * add new song into song list
     *
     * @param songInput song
     */
    public static void addNewSongs(final SongInput songInput) {
        Song song = new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                songInput.getReleaseYear(), songInput.getArtist());
        songs.add(song);
    }

    /**
     * add new podcast in podcast list
     *
     * @param podcast podcast
     * @param name    name
     */
    public static void addNewPodcast(final Podcasts podcast, final String name) {
        List<Episode> episodes = new ArrayList<>();
        Episode episode;
        for (EpisodeInput episodeInput : podcast.getEpisodes()) {
            episode = new Episode(episodeInput.getName(), episodeInput.getDuration(),
                    episodeInput.getDescription());
            episodes.add(episode);
        }
        Podcast newPodcast = new Podcast(podcast.getName(), name, episodes);
        podcasts.add(newPodcast);
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
     * Sets songs.
     *
     * @param songInputList the song input list
     */
    public static void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
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
     * Sets podcasts.
     *
     * @param podcastInputList the podcast input list
     */
    public static void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(),
                        episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
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
        for (UserArtist user : userArtists) {
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
    public static User getUser(final String username) {
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
    public static UserArtist getArtist(final String name) {
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
    public static UserHost getHost(final String name) {
        for (UserHost user : userHosts) {
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
    public static void updateTimestamp(final int newTimestamp) {
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
     * Gets top FIVE songs.
     *
     * @return the top FIVE songs
     */
    public static List<String> getTop5Songs() {
        List<Song> topSongs1 = new ArrayList<>();

        for (Song songAdm : songs) {
            for (User auxUser : users) {
                for (Song liked : auxUser.getLikedSongs()) {
                    if (!topSongs1.contains(liked) && songAdm.getName().equals(liked.getName())
                            && songAdm.getAlbum().equals(liked.getAlbum())) {
                        topSongs1.add(liked);
                    }
                }
            }
        }

        topSongs1.sort(Comparator.comparingInt(Song::getLikes));

        if (topSongs1.size() < FIVE) {
            for (Song auxSong : songs) {
                if (!topSongs1.contains(auxSong)) {
                    topSongs1.add(auxSong);
                }
            }
        }

        for (int i = 0; i < topSongs1.size() - 1; i++) {
            for (int j = 0; j < topSongs1.size() - i - 1; j++) {
                if (topSongs1.get(j).getLikes() < topSongs1.get(j + 1).getLikes()) {
                    Collections.swap(topSongs1, j, j + 1);
                }
            }
        }

        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : topSongs1) {
            if (count >= FIVE) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Gets top FIVE playlists.
     *
     * @return the top FIVE playlists
     */
    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= FIVE) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * get top FIVE albums
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
            if (count >= FIVE) {
                break;
            }
            topAlbum.add(album.getName());
            count++;
        }
        return topAlbum;
    }

    /**
     * get top FIVE artists
     *
     * @return list of artists
     */
    public static List<String> getTop5Artists() {
        List<UserArtist> sortedArtist = new ArrayList<>(getUserArtists());
        sortedArtist.sort(Comparator.comparingInt(UserArtist::numberOfLikes)
                .reversed());
        List<String> topAlbum = new ArrayList<>();
        while (sortedArtist.size() > FIVE) {
            sortedArtist.remove(sortedArtist.size() - 1);
        }
        for (UserArtist user : sortedArtist) {
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
        for (User user : users) {
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
    public static void addArtist(final UserArtist user) {
        users.add(user);
        userArtists.add(user);
    }

    /**
     * add host to list of hosts
     *
     * @param user host
     */
    public static void addHost(final UserHost user) {
        users.add(user);
        userHosts.add(user);
    }

    /**
     * add simple user
     *
     * @param user user
     */
    public static void addUser(final User user) {
        users.add(user);
    }

    /**
     * remove simple user
     *
     * @param user user
     */
    public static void removeUser(final User user) {
        users.remove(user);
    }

    /**
     * remove artist
     *
     * @param user artist
     */
    public static void removeUserArtist(final UserArtist user) {
        users.remove(user);
        userArtists.remove(user);
        user.getAlbum().clear();
    }

    /**
     * remove host
     *
     * @param user host
     */
    public static void removeUserHost(final UserHost user) {
        users.remove(user);
        userHosts.remove(user);
        user.getPodcasts().clear();
    }

    /**
     * remove given song
     *
     * @param song song
     */
    public static void removeSong(final Song song) {
        songs.removeIf((s) -> song.getName().equals(s.getName()));
    }

    /**
     * remove given podcast
     *
     * @param podcast podcast
     */
    public static void removePodcast(final Podcasts podcast) {
        podcasts.removeIf((p) -> podcast.getName().equals(p.getName()));
    }

    /**
     * Reset.
     */
    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        userArtists = new ArrayList<>();
        userHosts = new ArrayList<>();
        timestamp = 0;
    }

    /**
     * verify if can delete user
     *
     * @param user user
     * @return boolean
     */
    public static boolean cantDeleteSimpleUser(final User user) {
        for (User auxUser : users) {
            if (auxUser.getPlayer().getSource() != null) {
                for (Playlist playlist : user.getPlaylists()) {
                    if (Objects.equals(auxUser.getPlayer().
                            getSource().getAudioCollection(), playlist)) {
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
    public static void prepareForDelete(final User user) {
        for (Playlist playlist : user.getPlaylists()) {
            for (User user1 : Admin.getUsers()) {
                if (user1.getType().equals("user")) {
                    user1.getFollowedPlaylists().remove(playlist);

                }
            }
        }
    }
}

package app.user;

import app.Admin;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.PlaylistOutput;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.pages.HomePage;
import app.pages.LikedContentPage;
import app.pages.Pages;
import app.player.Player;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.searchBar.SearchBar;
import app.utils.Enums;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {
    @Getter
    private String username;
    @Getter
    private int age;
    @Getter
    private String city;
    private String type;
    private boolean conectionStatus;
    @Getter
    private ArrayList<Playlist> playlists;
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;
    @Getter
    private final Player player;
    private final SearchBar searchBar;
    private boolean lastSearched;
    private String currentPage;
    private Pages page;

    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public User(final String username, final int age, final String city, final String type) {
        this.username = username;
        this.age = age;
        this.city = city;
        this.type = type;
        this.currentPage = "Home";
        this.conectionStatus = true;
        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        player = new Player();
        searchBar = new SearchBar(username);
        lastSearched = false;
        this.page = new HomePage(this);


    }

    /**
     * Method that changes pages depending on input command
     *
     * @param page given as input
     * @return message
     */
    public String changePage (final String page) {
        if (page.equals("Home")) {
            this.page = new HomePage(this);
            return this.username + " accessed " + page + " successfully.";
        }
        if (page.equals("LikedContent")) {
            this.page = new LikedContentPage(this);
            return this.username + " accessed " + page+ " successfully.";
        }
        return this.username + " is trying to access a non-existent page.";
    }

    /**
     * Search array list.
     *
     * @param filters the filters
     * @param type    the type
     * @return the array list
     */
    public ArrayList<String> search(final Filters filters, final String type) {
        searchBar.clearSelection();
        player.stop();

        lastSearched = true;
        ArrayList<String> results = new ArrayList<>();
        List<LibraryEntry> libraryEntries = searchBar.search(filters, type);
        for (LibraryEntry libraryEntry : libraryEntries) {
            results.add(libraryEntry.getName());
        }
        if (type.equals("artist")) {
            ArrayList<User> users = searchUserArtist(filters);
            for (User user: users) {
                results.add(user.getUsername());
            }
            searchBar.setSearchUserResult(results);
        } else if (type.equals("host")) {
            ArrayList<User> users = seearchUserHost(filters);
            for (User user: users) {
                results.add(user.getUsername());
            }
            searchBar.setSearchUserResult(results);
        }
        return results;
    }

    /**
     * method that goes through all artists and searching for specific artist
     * @param filters filters for artist
     * @return list of users
     */
    public ArrayList<User> searchUserArtist (final Filters filters) {
        ArrayList<User> results = new ArrayList<>();
        for (User user: Admin.getUsers()) {
            if (user.getType().equals("artist")) {
                if(user.getUsername().toLowerCase().startsWith(filters.getName())) {
                    results.add(user);
                }
            }
        }
        return results;
    }

    /**
     * same as previous method, but searching for host
     * @param filters filters
     * @return list of hosts
     */
    public ArrayList<User> seearchUserHost (final Filters filters) {
        ArrayList<User> results = new ArrayList<>();
        for (User user: Admin.getUsers()) {
            if (user.getType().equals("host")) {
                if(user.getUsername().toLowerCase().startsWith(filters.getName())) {
                    results.add(user);
                }
            }
        }
        return  results;
    }

    /**
     * Select string.
     * added possibility to select artist's or host's page
     * @param itemNumber the item number
     * @return the string
     */
    public String select(final int itemNumber) {
        if (!lastSearched)
            return "Please conduct a search before making a selection.";

        lastSearched = false;

        LibraryEntry selected = searchBar.select(itemNumber);
        LibraryEntry selectedUser = searchBar.selectUser(itemNumber);
        if (searchBar.getLastSearchType().equals("artist")) {
            if (selectedUser != null) {
                UserArtist user = Admin.getArtist(selectedUser.getName());
                if (user!=null){
                    this.page = user.getArtistPage();
                    return "Successfully selected %s's page.".formatted(selectedUser.getName());
                }
            } else {
                return "The selected ID is too high.";
            }

        } else if (searchBar.getLastSearchType().equals("host")) {
            if (selectedUser != null) {
                UserHost user = Admin.getHost(selectedUser.getName());
                if (user!=null){
                    this.page = user.getHostPage();
                    return "Successfully selected %s's page.".formatted(selectedUser.getName());
                }
            } else {
                return "The selected ID is too high.";
            }
        } else {
            if (selected == null){
                return "The selected ID is too high.";
            }else {
                return "Successfully selected %s.".formatted(selected.getName());
            }

        }

        return null;

    }

    /**
     * Load string.
     *
     * @return the string
     */
    public String load() {
        if (searchBar.getLastSelected() == null)
            return "Please select a source before attempting to load.";

        if (!searchBar.getLastSearchType().equals("song") &&
                ((AudioCollection)searchBar.getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }
        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());
        searchBar.clearSelection();

        player.pause();

        return "Playback loaded successfully.";
    }
    /**
     * Play pause string.
     *
     * @return the string
     */
    public String playPause() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before attempting to pause or resume playback.";

        player.pause();

        if (player.getPaused())
            return "Playback paused successfully.";
        else
            return "Playback resumed successfully.";
    }
    /**
     * Repeat string.
     *
     * @return the string
     */
    public String repeat() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before setting the repeat status.";

        Enums.RepeatMode repeatMode = player.repeat();
        String repeatStatus = "";

        switch(repeatMode) {
            case NO_REPEAT -> repeatStatus = "no repeat";
            case REPEAT_ONCE -> repeatStatus = "repeat once";
            case REPEAT_ALL -> repeatStatus = "repeat all";
            case REPEAT_INFINITE -> repeatStatus = "repeat infinite";
            case REPEAT_CURRENT_SONG -> repeatStatus = "repeat current song";
        }

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }
    /**
     * Shuffle string.
     *
     * @param seed the seed
     * @return the string
     */
    public String shuffle(final Integer seed) {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before using the shuffle function.";

        if (!player.getType().equals("playlist") && !player.getType().equals("album"))
            return "The loaded source is not a playlist or an album.";

        player.shuffle(seed);

        if (player.getShuffle())
            return "Shuffle function activated successfully.";

        return "Shuffle function deactivated successfully.";
    }
    /**
     * Forward string.
     *
     * @return the string
     */
    public String forward() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before attempting to forward.";

        if (!player.getType().equals("podcast"))
            return "The loaded source is not a podcast.";

        player.skipNext();

        return "Skipped forward successfully.";
    }
    /**
     * Backward string.
     *
     * @return the string
     */
    public String backward() {
        if (player.getCurrentAudioFile() == null)
            return "Please select a source before rewinding.";

        if (!player.getType().equals("podcast"))
            return "The loaded source is not a podcast.";

        player.skipPrev();

        return "Rewound successfully.";
    }
    /**
     * Like string.
     *
     * @return the string
     */
    public String like() {
        if (!isConectionStatus()) {
            return username + " is offline.";
        }
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before liking or unliking.";
        }

        if (!player.getType().equals("song") && !player.getType().equals("playlist") &&
                !player.getType().equals("album")) {
            return "Loaded source is not a song.";
        }

        Song song = (Song) player.getCurrentAudioFile();

        if (likedSongs.contains(song)) {
            likedSongs.remove(song);
            song.dislike();

            return "Unlike registered successfully.";
        }

        likedSongs.add(song);
        song.like();
        return "Like registered successfully.";
    }
    /**
     * Next string.
     *
     * @return the string
     */
    public String next() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before skipping to the next track.";

        player.next();

        if (player.getCurrentAudioFile() == null)
            return "Please load a source before skipping to the next track.";

        return "Skipped to next track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }
    /**
     * Prev string.
     *
     * @return the string
     */
    public String prev() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before returning to the previous track.";
        player.prev();
        return "Returned to previous track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }
    /**
     * Create playlist string.
     *
     * @param name      the name
     * @param timestamp the timestamp
     * @return the string
     */
    public String createPlaylist(final String name, final int timestamp) {
        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name)))
            return "A playlist with the same name already exists.";

        playlists.add(new Playlist(name, username, timestamp));

        return "Playlist created successfully.";
    }
    /**
     * Add remove in playlist string.
     *
     * @param Id the id
     * @return the string
     */
    public String addRemoveInPlaylist(final int Id) {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before adding to or removing from the playlist.";

        if (player.getType().equals("podcast"))
            return "The loaded source is not a song.";

        if (Id > playlists.size())
            return "The specified playlist does not exist.";

        Playlist playlist = playlists.get(Id - 1);

        if (playlist.containsSong((Song)player.getCurrentAudioFile())) {
            playlist.removeSong((Song)player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song)player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }
    /**
     * Switch playlist visibility string.
     *
     * @param playlistId the playlist id
     * @return the string
     */
    public String switchPlaylistVisibility(final Integer playlistId) {
        if (playlistId > playlists.size())
            return "The specified playlist ID is too high.";

        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }
    /**
     * Show playlists array list.
     *
     * @return the array list
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }
    /**
     * Follow string.
     *
     * @return the string
     */
    public String follow() {
        LibraryEntry selection = searchBar.getLastSelected();
        String type = searchBar.getLastSearchType();

        if (selection == null) {
            return "Please select a source before following or unfollowing.";
        }

        if (!type.equals("playlist")) {
            return "The selected source is not a playlist.";
        }

        Playlist playlist = (Playlist) selection;

        if (playlist.getOwner().equals(username)) {
            return "You cannot follow or unfollow your own playlist.";
        }

        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        playlist.increaseFollowers();


        return "Playlist followed successfully.";
    }

    /**
     * Gets player stats.
     *
     * @return the player stats
     */
    public PlayerStats getPlayerStats() {
        return player.getStats();
    }
    /**
     * Show preferred songs array list.
     *
     * @return the array list
     */
    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }
    /**
     * Gets preferred genre.
     *
     * @return the preferred genre
     */
    public String getPreferredGenre() {
        String[] genres = {"pop", "rock", "rap"};
        int[] counts = new int[genres.length];
        int mostLikedIndex = -1;
        int mostLikedCount = 0;

        for (Song song : likedSongs) {
            for (int i = 0; i < genres.length; i++) {
                if (song.getGenre().equals(genres[i])) {
                    counts[i]++;
                    if (counts[i] > mostLikedCount) {
                        mostLikedCount = counts[i];
                        mostLikedIndex = i;
                    }
                    break;
                }
            }
        }

        String preferredGenre = mostLikedIndex != -1 ? genres[mostLikedIndex] : "unknown";
        return "This user's preferred genre is %s.".formatted(preferredGenre);
    }

    /**
     * method that change the connection status for user
     *
     * @return message
     */
    public String SwitchConnectionStatus() {
        if (username != null) {
            if (isConectionStatus()) {
                setConectionStatus(false);
            } else {
                setConectionStatus(true);
            }
            return username + " has changed status successfully.";
        }
        return "The username " + username + " doesn't exist.";
    }

    /**
     * Simulate time.
     *
     * @param time the time
     */
    public void simulateTime(int time) {
        if (isConectionStatus()) {
            player.simulatePlayer(time);
        }
    }
}

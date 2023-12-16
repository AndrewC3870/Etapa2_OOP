package app.pages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.User;

import java.util.ArrayList;

public class LikedContentPage implements Pages {

    private ArrayList<Playlist> playlists;
    private ArrayList<Song> likedSongs;
    private ArrayList<Playlist> followedPlaylists;

    /**
     * Constructor for likedContentPage
     * @param user
     */
    public LikedContentPage(final User user) {
        this.playlists = user.getPlaylists();
        this.followedPlaylists = user.getFollowedPlaylists();
        this.likedSongs = user.getLikedSongs();
    }

    /**
     * Creating a list with all liked fongs
     * @return
     */
    public ArrayList<String> allLikedSongs() {
        ArrayList<String> result = new ArrayList<>();
        for (Song song: likedSongs) {
            String liked = song.getName() + " - " + song.getArtist();
            result.add(liked);
        }
        return result;
    }

    /**
     * Creating a list with all followed playlists
     * @return
     */

    public ArrayList<String> allFollowedPlaylists() {
        ArrayList<String> result = new ArrayList<>();
        for (Playlist playlist: followedPlaylists) {
            String followed = playlist.getName() + " - " + playlist.getOwner();
            result.add(followed);
        }
        return result;
    }

    /**
     * Creating the output
     * @return
     */
    @Override
    public String printCurrentPage() {
        return "Liked songs:\n\t" + allLikedSongs() + "\n\nFollowed playlists:\n\t"
                + allFollowedPlaylists();
    }
}

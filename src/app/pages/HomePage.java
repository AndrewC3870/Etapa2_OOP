package app.pages;

import app.Admin;
import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static app.Admin.getPlaylists;


public class HomePage implements Pages {

    private ArrayList<Playlist> playlists;
    private ArrayList<Song> likedSongs;
    private ArrayList<Playlist> followedPlaylists;

    /**
     * Constructor for Homepage
     * @param user
     */
    public HomePage(User user) {
        this.playlists = user.getPlaylists();
        this.followedPlaylists = user.getFollowedPlaylists();
        this.likedSongs = user.getLikedSongs();
    }

    /**
     * getting all the liked songs from all users sorted and if the current user
     * have that song in LikedSongs, then add it to resulted list
     * @return
     */
    public List<String> getTopFiveLikedSongs() {
        List<Song> sortedSongs = new ArrayList<>(Admin.getSongs());
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes));

        List<Song> list = new ArrayList<>();
        list.addAll(likedSongs);
        list.sort(Comparator.comparingInt(Song::getLikes).reversed());

        List<String> topSongs = new ArrayList<>();
        for (Song auxSong: list) {
            for (Song song: sortedSongs) {
                if (auxSong.getName().equals(song.getName()) &&
                        auxSong.getAlbum().equals(song.getAlbum())) {
                    topSongs.add(auxSong.getName());
                }
            }
        }

        while(topSongs.size() > 5) {
            topSongs.remove(topSongs.size() - 1);
        }
        return topSongs;
    }

    /**
     * getting all playlist sorted number of followers and if user have that playlist in followed,
     * add it to result list
     * @return
     */
    public List<String> firstFiveFollowed() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= 5) break;
            for (Playlist auxPlaylist: followedPlaylists) {
                if (auxPlaylist.getName().equals(playlist.getName())) {
                    topPlaylists.add(playlist.getName());
                }
            }
            count++;
        }
        return topPlaylists;
    }

    /**
     * Creating the output for print current page
     * @return
     */
    @Override
    public String printCurrentPage() {
        return "Liked songs:\n\t" + getTopFiveLikedSongs() + "\n\nFollowed playlists:\n\t" +
                firstFiveFollowed();
    }


}

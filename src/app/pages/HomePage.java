package app.pages;

import app.Admin;
import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static app.Admin.getPlaylists;


public class HomePage extends Pages {

    private ArrayList<Playlist> playlists;
    private ArrayList<Song> likedSongs;
    private ArrayList<Playlist> followedPlaylists;

    public HomePage(User user) {
        this.playlists = user.getPlaylists();
        this.followedPlaylists = user.getFollowedPlaylists();
        this.likedSongs = user.getLikedSongs();
    }


    public List<String> getTopFiveLikedSongs() {
        List<Song> sortedSongs = new ArrayList<>(Admin.getSongs());
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= 5) break;
            for (Song auxSong: likedSongs) {
                if (auxSong.getName().equals(song.getName())) {
                    topSongs.add(song.getName());
                }
            }
            count++;
        }
        return topSongs;
    }
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


    @Override
    public String toString() {
        return "Liked songs:\n\t" + getTopFiveLikedSongs() + "\n\nFollowed playlists:\n\t" + firstFiveFollowed();
    }


}

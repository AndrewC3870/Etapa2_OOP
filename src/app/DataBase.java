package app;

import app.audio.Collections.Podcast;
import app.audio.Files.Song;
import app.user.User;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;

import java.util.ArrayList;
import java.util.List;

public class DataBase {

    public static DataBase instance = null;
    private List<UserInput> users;

    private List<SongInput> songs;
    private List<PodcastInput> podcasts;

    private DataBase(List<UserInput> users, List<SongInput> songs, List<PodcastInput> podcasts) {
        this.songs = songs;
        this.users = users;
        this.podcasts = podcasts;
    }


    public static DataBase getInstance(List<UserInput> users, List<SongInput> songs, List<PodcastInput> podcasts) {
        if (instance == null) {
            instance = new DataBase( users, songs, podcasts);
        }
        return instance;
    }

    public List<UserInput> getAllUsers() {
        return this.users;
    }
    public List<SongInput> getAllSongs(){
        return this.songs;
    }
    public List<PodcastInput> getAllPodcasts(){
        return this.podcasts;
    }
    public void resetDB() {
        instance = null;
    }


}

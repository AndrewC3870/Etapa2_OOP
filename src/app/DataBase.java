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
    private List<UserInput> users = new ArrayList<>();

    private List<SongInput> songs = new ArrayList<>();
    private List<PodcastInput> podcasts = new ArrayList<>();

    private DataBase() {

    }



    public DataBase(List<UserInput> users, List<SongInput> songs, List<PodcastInput> podcasts) {
        this.songs = songs;
        this.users = users;
        this.podcasts = podcasts;
    }

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
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
        this.songs = new ArrayList<>();
        this.users = new ArrayList<>();
        this.podcasts = new ArrayList<>();
    }


}

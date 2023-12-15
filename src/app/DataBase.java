package app;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import java.util.List;

public class DataBase {

    public static DataBase instance = null;
    private final List<UserInput> users;

    private final List<SongInput> songs;
    private final List<PodcastInput> podcasts;

    /**
     * constructor for Database
     * @param users users
     * @param songs songs
     * @param podcasts pdcasts
     */
    private DataBase(List<UserInput> users, List<SongInput> songs, List<PodcastInput> podcasts) {
        this.songs = songs;
        this.users = users;
        this.podcasts = podcasts;
    }

    /**
     * Creating getInstance for Database
     * @param users users
     * @param songs songs
     * @param podcasts podcasts
     * @return instance
     */
    public static DataBase getInstance(List<UserInput> users, List<SongInput> songs, List<PodcastInput> podcasts) {
        if (instance == null) {
            instance = new DataBase( users, songs, podcasts);
        }
        return instance;
    }

    /**
     * Getter for user
     *
     * @return users
     */
    public List<UserInput> getAllUsers() {
        return this.users;
    }

    /**
     * Getter for list of Songs
     *
     * @return songs
     */
    public List<SongInput> getAllSongs(){
        return this.songs;
    }

    /**
     * Getter for list of Podcasts
     * @return podcasts
     */
    public List<PodcastInput> getAllPodcasts(){
        return this.podcasts;
    }

    /**
     * reseting Database
     */
    public void resetDB() {
        instance = null;
    }


}

package app.audio.Collections;

import app.audio.Collections.AudioCollection;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static app.utils.UtilMethods.convertToSong;

@Getter
@Setter
public class Album extends AudioCollection {
    private String name;
    private int releaseYear;
    private String description;
    private ArrayList<Song> songs;
    private ArrayList<Song> covertedSongs;

    /**
     * Constructor for album
     * @param name
     * @param owner
     * @param releaseYear
     * @param description
     * @param songs
     */
    public Album (String name, String owner, int releaseYear, String description,
                  ArrayList<SongInput> songs) {
        super(name,owner);
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = new ArrayList<>();
        for (SongInput songInput: songs) {
            this.songs.add(convertToSong(songInput));
        }


    }

    /**
     * method that calculates total likes in album
     * @return
     */
    public Integer calculateLikes() {
        int likes = 0;
        for(Song song: songs) {
            likes += song.getLikes();
        }
        return likes;
    }

    /**
     * method to convert SongInput to Song
     * @return
     */
//    public ArrayList<Song> convertSongs() {
//        ArrayList<Song> converted = new ArrayList<>();
//        for (SongInput songInput: songs) {
//            converted.add(new Song(songInput.getName(), songInput.getDuration(),
//                    songInput.getAlbum(), songInput.getTags(), songInput.getLyrics(),
//                    songInput.getGenre(), songInput.getReleaseYear(), songInput.getArtist()));
//        }
//        return converted;
//    }

    /**
     * Retun number of tracks from list of songs
     * @return
     */
    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    /**
     * Get the song by the index
     * @param index
     * @return
     */
    @Override
    public AudioFile getTrackByIndex(int index) {
        return songs.get(index);
    }
}

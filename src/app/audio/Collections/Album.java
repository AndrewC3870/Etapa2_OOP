package app.audio.Collections;

import app.audio.Collections.AudioCollection;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Album extends AudioCollection {
    private String name;
    private int releaseYear;
    private String description;
    private ArrayList<SongInput> songs;
    private ArrayList<Song> covertedSongs;

    public Album (String name, String owner, int releaseYear, String description, ArrayList<SongInput> songs) {
        super(name,owner);
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs;

    }
    public Integer calculateLikes() {
        int likes = 0;
        for(Song song: convertSongs()) {
            likes += song.getLikes();
        }
        return likes;
    }


    public ArrayList<Song> convertSongs() {
        ArrayList<Song> converted = new ArrayList<>();
        for (SongInput songInput: songs) {
            converted.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(),songInput.getGenre(), songInput.getReleaseYear(),
                    songInput.getArtist()));
        }
        return converted;
    }

    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    @Override
    public AudioFile getTrackByIndex(int index) {
        return convertSongs().get(index);
    }
}

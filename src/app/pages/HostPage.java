package app.pages;

import app.audio.Collections.Podcasts;
import app.events.Announcement;
import app.user.User;
import fileio.input.EpisodeInput;
import lombok.Getter;

import java.util.ArrayList;
@Getter

public final class HostPage extends Pages {
    private ArrayList<Podcasts> podcasts;
    private ArrayList<Announcement> announcements;
    public HostPage(ArrayList<Podcasts> podcasts, ArrayList<Announcement> announcements) {
        this.announcements = announcements;
        this.podcasts = podcasts;
    }

    public ArrayList<String> getPodcasts() {
        ArrayList<String> results = new ArrayList<>();
        ArrayList<String> episodes = new ArrayList<>();
        for(Podcasts podcast: podcasts) {
            String result;
            for (EpisodeInput episodeInput: podcast.getEpisodes()) {
                episodes.add(episodeInput.getName() + " - " + episodeInput.getDescription());
            }
            result = podcast.getName() + ":\n\t" + episodes + "\n";
            results.add(result);
            episodes.clear();
        }
        return results;
    }

    public ArrayList<String> getAnnouncements() {
        ArrayList<String> results = new ArrayList<>();
        for (Announcement announcement: announcements) {
            results.add(announcement.getName() + ":\n\t" + announcement.getDescription() + "\n");
        }
        return results;
    }


    @Override
    public String toString() {
        return "Podcasts:\n\t" + getPodcasts() + "\n\nAnnouncements:\n\t" + getAnnouncements();
    }
}

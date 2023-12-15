package app.pages;

import app.audio.Collections.Podcasts;
import app.events.Announcement;
import app.user.User;
import fileio.input.EpisodeInput;
import lombok.Getter;

import java.util.ArrayList;
@Getter

public final class HostPage implements Pages {
    private ArrayList<Podcasts> podcasts;
    private ArrayList<Announcement> announcements;

    /**
     * Constructor for HostPage
     * @param podcasts
     * @param announcements
     */
    public HostPage(ArrayList<Podcasts> podcasts, ArrayList<Announcement> announcements) {
        this.announcements = announcements;
        this.podcasts = podcasts;
    }

    /**
     * Get all podcast from host
     * @return
     */
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

    /**
     * get all announcements from a host
     * @return
     */
    public ArrayList<String> getAnnouncements() {
        ArrayList<String> results = new ArrayList<>();
        for (Announcement announcement: announcements) {
            results.add(announcement.getName() + ":\n\t" + announcement.getDescription() + "\n");
        }
        return results;
    }

    /**
     * creating output
     * @return
     */
    @Override
    public String printCurrentPage() {
        return "Podcasts:\n\t" + getPodcasts() + "\n\nAnnouncements:\n\t" + getAnnouncements();
    }
}

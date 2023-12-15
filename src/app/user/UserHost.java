package app.user;

import app.audio.Collections.Album;
import app.audio.Collections.Podcasts;
import app.events.Announcement;
import app.pages.HostPage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class UserHost extends User{
    private boolean player;
    private String type;
    private ArrayList<Podcasts> podcasts;
    private ArrayList<Announcement> announcements;
    private HostPage hostPage;

    /**
     * Constructor for UserHost
     *
     * @param username username
     * @param age age
     * @param city city
     */
    public UserHost(String username, int age, String city) {
        super(username, age, city, "host");
        this.player = false;
        this.podcasts = new ArrayList<>();
        this.announcements = new ArrayList<>();
        this.hostPage = new HostPage(this.podcasts,this.announcements);
    }

    /**
     * find announcement by name
     *
     * @param name announcement name
     * @return anouncement
     */
    public Announcement containsAnnouncement(String name) {
        for (Announcement announcement: announcements) {
            if (announcement.getName().equals(name)) {
                return announcement;
            }
        }
        return null;
    }

    /**
     * find the podcast by name
     *
     * @param name podcast name
     * @return podcast
     */
    public Podcasts getPodcastByName(String name) {
        for (Podcasts auxPodcasts: podcasts) {
            if (auxPodcasts.getName().equals(name)) {
                return auxPodcasts;
            }
        }
        return null;
    }

}

package app;
import app.audio.Collections.Podcasts;
import app.events.Announcement;
import app.user.User;
import app.user.UserHost;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;
import fileio.input.EpisodeInput;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Objects;

import static app.utils.UtilMethods.createOutputForArtisHost;
import static app.utils.UtilMethods.ifDeletingForHost;

@Getter
@Setter
public class HostCommandRunner {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * add podcast to a host if is not existing
     *
     * @param commandInput command
     * @return message
     */
    public static ObjectNode addPodcast(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;

        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("host")) {
            message = commandInput.getUsername() + " is not a host.";
        } else {
            UserHost host = (UserHost) user;
            if (host.getPodcastByName(commandInput.getName()) != null) {
                message = commandInput.getUsername() + " has another podcast with the same name.";
            } else if (sameEpisodes(commandInput.getEpisodes())) {
                message = commandInput.getUsername() + " has the same episode in this podcast.";
            } else {
                Podcasts podcast =
                        new Podcasts(commandInput.getName(), commandInput.getEpisodes());
                host.getPodcasts().add(podcast);
                Admin.addNewPodcast(podcast, host.getUsername());
                message = commandInput.getUsername() + " has added new podcast successfully.";
            }
        }

        return createOutputForArtisHost(commandInput, message);
    }

    /**
     * remove podcast if nobody is lessening to it
     *
     * @param commandInput command
     * @return message
     */
    public static ObjectNode removePodcast(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;

        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("host")) {
            message = commandInput.getUsername() + " is not a host.";
        } else {
            UserHost host = (UserHost) user;
            if (host.getPodcastByName(commandInput.getName()) == null) {
                message = commandInput.getUsername()
                        + " doesn't have a podcast with the given name.";
            } else if (ifDeletingForHost(host)) {
                message = commandInput.getUsername() + " can't delete this podcast.";
            } else {
                Admin.removePodcast(host.getPodcastByName(commandInput.getName()));
                host.getPodcasts().remove(host.getPodcastByName(commandInput.getName()));
                message = commandInput.getUsername() + " deleted the podcast successfully.";
            }

        }

        return createOutputForArtisHost(commandInput, message);
    }


    /**
     * if have same episodes
     *
     * @param episodeInputs list of episodes
     * @return boolean
     */
    public static boolean sameEpisodes(final ArrayList<EpisodeInput> episodeInputs) {
        int count;
        for (EpisodeInput episode: episodeInputs) {
            count = 0;
            for (EpisodeInput auxEpisode: episodeInputs) {
                if (auxEpisode.getName().equals(episode.getName())) {
                    count++;
                }
                if (count > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param commandInput
     * @return
     */
    public static ObjectNode addAnnouncement(final CommandInput commandInput) {
        String message;
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!Objects.equals(user.getType(), "host")) {
            message = user.getUsername() + " is not a host.";
        } else {
            UserHost host = (UserHost) user;
            if (host.containsAnnouncement(commandInput.getName()) != null) {
                message = commandInput.getUsername()
                        + " has already added an announcement with this name.";
            } else {
                Announcement announcement =
                        new Announcement(commandInput.getName(), commandInput.getDescription());
                host.getAnnouncements().add(announcement);
                message = commandInput.getUsername()
                        + " has successfully added new announcement.";
            }
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * remove announcement if eisted in Host's Announcement list
     * @param commandInput command
     * @return output
     */
    public static ObjectNode removeAnnouncement(final CommandInput commandInput) {
        String message;
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!Objects.equals(user.getType(), "host")) {
            message = user.getUsername() + " is not a host.";
        } else {
            UserHost host = (UserHost) user;
            if (host.containsAnnouncement(commandInput.getName()) == null) {
                message = commandInput.getUsername() + " has no announcement with the given name.";
            } else {
                host.getAnnouncements().remove(host.containsAnnouncement(commandInput.getName()));
                message = commandInput.getUsername()
                        + " has successfully deleted the announcement.";
            }
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }




}

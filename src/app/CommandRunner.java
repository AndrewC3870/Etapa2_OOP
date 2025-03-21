package app;

import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcasts;
import app.audio.Files.Song;
import app.user.User;
import app.user.UserArtist;
import app.user.UserHost;
import app.utils.UtilMethods;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;
import fileio.input.EpisodeInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static app.Admin.cantDeleteSimpleUser;
import static app.Admin.prepareForDelete;
import static app.utils.UtilMethods.ifOnTheHostPage;
import static app.utils.UtilMethods.ifOnTheArtistPage;
import static app.utils.UtilMethods.ifDeletingForArtist;
import static app.utils.UtilMethods.ifDeletingForHost;
import static app.utils.UtilMethods.createOutputForArtisHost;

public class CommandRunner {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * add user/artist/host if is not existing yet
     *
     * @param commandInput command
     * @return output
     */
    public static ObjectNode addUsers(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = "";
        if (user != null) {
            message = "The username " + commandInput.getUsername() + " is already taken.";
        } else
            if (Objects.equals(commandInput.getType(), "user")) {
            User newUser = new User(commandInput.getUsername(), commandInput.getAge(),
                    commandInput.getCity(), "user");
            newUser.setType(commandInput.getType());
            newUser.setConectionStatus(true);
            Admin.addUser(newUser);
            message = "The username " + commandInput.getUsername()
                    + " has been added successfully.";
        } else if (Objects.equals(commandInput.getType(), "artist")) {
            if (!findSameUser(commandInput.getUsername())) {
                UserArtist artist = new UserArtist(commandInput.getUsername(),
                        commandInput.getAge(), commandInput.getCity());
                artist.setConectionStatus(true);
                artist.setType("artist");
                Admin.addArtist(artist);
                message = "The username " + commandInput.getUsername()
                        + " has been added successfully.";
            } else {
                message = "The username " + commandInput.getUsername() + " is already taken.";
            }

        } else if (Objects.equals(commandInput.getType(), "host")) {
            UserHost host = new UserHost(commandInput.getUsername(),
                    commandInput.getAge(), commandInput.getCity());
            host.setType("host");
            host.setConectionStatus(true);
            Admin.addHost(host);
            message = "The username " + commandInput.getUsername()
                    + " has been added successfully.";
        }
        return UtilMethods.createMessageOutput(commandInput, message);
    }

    /**
     * method to find same users by name
     *
     * @param name name
     * @return boolean
     */
    public static boolean findSameUser(final String name) {
        for (User user: Admin.getUsers()) {
            if (user.getUsername().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * remove user/artist/host if existing and verify all cases for all type of user
     *
     * @param commandInput command
     * @return output
     */
    public static ObjectNode removeUser(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        User user = Admin.getUser(commandInput.getUsername());
        String message;

        if (user != null) {
            if (Objects.equals(user.getType(), "user")) {
                if (cantDeleteSimpleUser(user)) {
                    message = user.getUsername() + " can't be deleted.";
                } else {
                    prepareForDelete(user);
                    message = user.getUsername() + " was successfully deleted.";
                    for (Playlist followed: user.getFollowedPlaylists()) {
                        User auxUser = Admin.getUser(followed.getOwner());
                        if (auxUser.getPlaylists() != null) {
                            for (Playlist playlist: auxUser.getPlaylists()) {
                                if (playlist.getName().equals(followed.getName())) {
                                    int count = playlist.getFollowers();
                                    playlist.setFollowers(count - 1);
                                }
                            }
                        }
                    }
                    Admin.removeUser(user);
                }
            } else if (Objects.equals(user.getType(), "artist")) {
                UserArtist artist = (UserArtist) user;
                if (ifDeletingForArtist(artist) || ifOnTheArtistPage(artist)) {
                    message = artist.getUsername() + " can't be deleted.";
                } else {
                    for (Album album: artist.getAlbum()) {
                        for (Song songInput: album.getSongs()) {
                            Admin.removeSong(songInput);
                        }
                    }
                    for (Album album: artist.getAlbum()) {
                        for (Song songInput: album.getSongs()) {
                            for (User user1: Admin.getUsers()) {
                                if (user1.getType().equals("user")) {
                                    user1.getLikedSongs().remove(songInput);
                                }
                            }
                        }
                    }

                    message = artist.getUsername() + " was successfully deleted.";
                    Admin.removeUser(user);
                    Admin.removeUserArtist(artist);
                }
            } else {
                UserHost host = (UserHost) user;
                if (ifDeletingForHost(host) || ifOnTheHostPage(host)) {
                    message = host.getUsername() + " can't be deleted.";
                } else {
                    message = host.getUsername() + " was successfully deleted.";
                    Admin.removeUser(user);
                    Admin.removeUserHost(host);
                }
            }

        } else {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        }
        return createOutputForArtisHost(commandInput, message);
    }

    /**
     * get all existing users
     *
     * @param commandInput command
     * @return output
     */
    public static ObjectNode getAllUsers(final CommandInput commandInput) {
        List<User> users = Admin.getUsers();
        ArrayList<String> result = new ArrayList<>();
        for (User auxUser: users) {
            if (Objects.equals(auxUser.getType(), "user")) {
                result.add(auxUser.getUsername());

            }
        }
        for (User auxUser: users) {
            if (Objects.equals(auxUser.getType(), "artist")) {
                result.add(auxUser.getUsername());

            }
        }
        for (User auxUser: users) {
            if (Objects.equals(auxUser.getType(), "host")) {
                result.add(auxUser.getUsername());

            }
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.putPOJO("result", result);
        return  objectNode;
    }

    /**
     * show podcast of specific host if exists
     *
     * @param commandInput command
     * @return
     */
    public static ObjectNode showPodacst(final CommandInput commandInput) {
        UserHost host = (UserHost) Admin.getUser(commandInput.getUsername());
        ArrayNode result = objectMapper.createArrayNode();

        if (host != null) {
            for (Podcasts podcast: host.getPodcasts()) {
                List<String> podcasts = new ArrayList<>();
                ObjectNode node = objectMapper.createObjectNode();
                node.put("name", podcast.getName());
                for (EpisodeInput episode: podcast.getEpisodes()) {
                    podcasts.add(episode.getName());
                }
                node.putPOJO("episodes", podcasts);
                result.add(node);
            }
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", result);
        return  objectNode;
    }

    /**
     * show all albums for specific user
     *
     * @param commandInput command
     * @return output
     */
    public static ObjectNode showAlbums(final CommandInput commandInput) {
        UserArtist artist = (UserArtist) Admin.getUser(commandInput.getUsername());
        ArrayNode result = objectMapper.createArrayNode();

        if (artist != null) {
            for (Album albums: artist.getAlbum()) {
                List<String> songs = new ArrayList<>();
                ObjectNode node = objectMapper.createObjectNode();
                node.put("name", albums.getName());
                for (Song song: albums.getSongs()) {
                    songs.add(song.getName());
                }
                node.putPOJO("songs", songs);
                result.add(node);
            }
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", result);
        return  objectNode;
    }

    /**
     * get top5Albums returnes sorted list with first five most liked albums
     *
     * @param commandInput command
     * @return output
     */
    public static ObjectNode getTop5Albums(final CommandInput commandInput) {
        List<String> albums = Admin.getTop5Albums();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(albums));

        return objectNode;
    }

    /**
     * returns list of top 5 artists
     * @param commandInput command
     * @return output
     */
    public static ObjectNode getTop5Artists(final CommandInput commandInput) {
        List<String> songs = Admin.getTop5Artists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }
}

package app;

import app.audio.Collections.PlaylistOutput;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static app.utils.UtilMethods.createMessageOutput;

public class UserCommandRunner {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectNode search(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        Filters filters = new Filters(commandInput.getFilters());
        String type = commandInput.getType();

        String message = "";
        ArrayList<String> results = new ArrayList<>();
        if (user != null) {
            if (!user.isConectionStatus()) {
                message = user.getUsername() + " is offline.";
            } else {
                results = user.search(filters, type);
                message = "Search returned " + results.size() + " results";
            }
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        objectNode.put("results", objectMapper.valueToTree(results));

        return objectNode;
    }

    public static ObjectNode select(CommandInput commandInput) {

        User user = Admin.getUser(commandInput.getUsername());
        String message = "";
        if (user != null) {
            message = user.select(commandInput.getItemNumber());
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode load(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = "";
        if (user!=null) {
            message = user.load();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode playPause(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.playPause();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode repeat(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = "";
        if (user != null) {
            message = user.repeat();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode shuffle(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Integer seed = commandInput.getSeed();
        String message = user.shuffle(seed);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode forward(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.forward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode backward(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.backward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode like(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = "";
        if (user != null) {
            message = user.like();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode next(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.next();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode prev(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.prev();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode createPlaylist(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = "";
        if (user != null) {
            message = user.createPlaylist(commandInput.getPlaylistName(), commandInput.getTimestamp());
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode addRemoveInPlaylist(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = "";
        if (user != null) {
            message = user.addRemoveInPlaylist(commandInput.getPlaylistId());
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode switchVisibility(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.switchPlaylistVisibility(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode showPlaylists(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<PlaylistOutput> playlists = user.showPlaylists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    public static ObjectNode follow(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.follow();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode status(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user != null) {
            PlayerStats stats = user.getPlayerStats();
            objectNode.put("stats", objectMapper.valueToTree(stats));

        }

        return objectNode;
    }

//   ASA ERA DE LA INCEPUT
//    User user = Admin.getUser(commandInput.getUsername());
//    PlayerStats stats = user.getPlayerStats();
//
//    ObjectNode objectNode = objectMapper.createObjectNode();
//        objectNode.put("command", commandInput.getCommand());
//        objectNode.put("user", commandInput.getUsername());
//        objectNode.put("timestamp", commandInput.getTimestamp());
//        objectNode.put("stats", objectMapper.valueToTree(stats));
//
//        return objectNode;



    public static ObjectNode showLikedSongs(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<String> songs = user.showPreferredSongs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    public static ObjectNode getPreferredGenre(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String preferredGenre = user.getPreferredGenre();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(preferredGenre));

        return objectNode;
    }

    public static ObjectNode getTop5Songs(CommandInput commandInput) {
        List<String> songs = Admin.getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    public static ObjectNode getTop5Playlists(CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

//    ce adaug eu nou
    public static ObjectNode switchConnectionStatus(CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!Objects.equals(user.getType(), "user")){
            message = user.getUsername() + " is not a normal user.";
        } else {
            message = user.SwitchConnectionStatus();
        }
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode printCurrentPage(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ObjectNode objectNode = objectMapper.createObjectNode();
        String message = "";
        if (user != null) {
            if (user.isConectionStatus()) {
                message = user.getPage().printCurrentPage();
            } else {
                message = user.getUsername() + " is offline.";
            }

        }
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    public static ObjectNode getOnlineUsers(CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        List<String> onlineUsers = Admin.getOnlineUsers();

        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(onlineUsers));

        return objectNode;
    }

    public static ObjectNode changePage(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = "";
        if (user != null) {
            if (user.isConectionStatus()) {
                message = user.changePage(commandInput.getNextPage());
            } else {
                message = user.getUsername() + " is offline.";
            }

        }

        return createMessageOutput(commandInput, message);
    }


}

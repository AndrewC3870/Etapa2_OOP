package main;

import app.*;
import app.audio.Collections.Album;
import app.pages.HostPage;
import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.CommandInput;
import fileio.input.LibraryInput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().startsWith("library")) {
                continue;
            }

            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LibraryInput library = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + "library/library.json"), LibraryInput.class);
        CommandInput[] commands = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1), CommandInput[].class);
        ArrayNode outputs = objectMapper.createArrayNode();

        DataBase database =  DataBase.getInstance(library.getUsers(), library.getSongs(), library.getPodcasts());
        Admin.setUsers(database.getAllUsers());
        Admin.setSongs(database.getAllSongs());
        Admin.setPodcasts(database.getAllPodcasts());
        System.out.println(filePath1);
        for (CommandInput command : commands) {
            Admin.updateTimestamp(command.getTimestamp());
            String commandName = command.getCommand();

            switch (commandName) {
                case "search" -> outputs.add(UserCommandRunner.search(command));
                case "select" -> outputs.add(UserCommandRunner.select(command));
                case "load" -> outputs.add(UserCommandRunner.load(command));
                case "playPause" -> outputs.add(UserCommandRunner.playPause(command));
                case "repeat" -> outputs.add(UserCommandRunner.repeat(command));
                case "shuffle" -> outputs.add(UserCommandRunner.shuffle(command));
                case "forward" -> outputs.add(UserCommandRunner.forward(command));
                case "backward" -> outputs.add(UserCommandRunner.backward(command));
                case "like" -> outputs.add(UserCommandRunner.like(command));
                case "next" -> outputs.add(UserCommandRunner.next(command));
                case "prev" -> outputs.add(UserCommandRunner.prev(command));
                case "createPlaylist" -> outputs.add(UserCommandRunner.createPlaylist(command));
                case "addRemoveInPlaylist" -> outputs.add(UserCommandRunner.addRemoveInPlaylist(command));
                case "switchVisibility" -> outputs.add(UserCommandRunner.switchVisibility(command));
                case "showPlaylists" -> outputs.add(UserCommandRunner.showPlaylists(command));
                case "follow" -> outputs.add(UserCommandRunner.follow(command));
                case "status" -> outputs.add(UserCommandRunner.status(command));
                case "showPreferredSongs" -> outputs.add(UserCommandRunner.showLikedSongs(command));
                case "getPreferredGenre" -> outputs.add(UserCommandRunner.getPreferredGenre(command));
                case "getTop5Songs" -> outputs.add(UserCommandRunner.getTop5Songs(command));
                case "getTop5Playlists" -> outputs.add(UserCommandRunner.getTop5Playlists(command));
                case "getTop5Albums" -> outputs.add(CommandRunner.getTop5Albums(command));
                case "getTop5artists" -> outputs.add(CommandRunner.getTop5Albums(command));
                case "switchConnectionStatus" -> outputs.add(UserCommandRunner.switchConnectionStatus(command));
                case "getOnlineUsers" -> outputs.add(UserCommandRunner.getOnlineUsers(command));
                case "addUser" -> outputs.add(CommandRunner.addUsers(command));
                case "deleteUser" -> outputs.add(CommandRunner.removeUser(command));
                case "addAlbum" -> outputs.add(ArtistCommandRunner.addAlbum(command));
                case "addEvent" -> outputs.add(ArtistCommandRunner.addEvent(command));
                case "removeEvent" -> outputs.add(ArtistCommandRunner.removeEvent(command));
                case "addMerch" -> outputs.add(ArtistCommandRunner.addMerch(command));
                case "showAlbums" -> outputs.add(CommandRunner.showAlbums(command));
                case "removeAlbum" -> outputs.add(ArtistCommandRunner.removeAlbum(command));
                case "addPodcast" -> outputs.add(HostCommandRunner.addPodcast(command));
                case "showPodcasts" -> outputs.add(CommandRunner.showPodacst(command));
                case "removePodcast" -> outputs.add(HostCommandRunner.removePodcast(command));
                case "addAnnouncement" -> outputs.add(HostCommandRunner.addAnnouncement(command));
                case "removeAnnouncement" -> outputs.add(HostCommandRunner.removeAnnouncement(command));
                case "changePage" -> outputs.add(UserCommandRunner.changePage(command));
                case "getAllUsers" -> outputs.add(CommandRunner.getAllUsers(command));
                case "printCurrentPage" -> outputs.add(UserCommandRunner.printCurrentPage(command));

                default -> System.out.println("Invalid command " + commandName);
            }
        }


        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), outputs);

        Admin.reset();
        database.resetDB();
    }
}

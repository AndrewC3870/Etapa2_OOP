package app.events;

import fileio.input.CommandInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistEvent {
    private String name;
    private String date;
    private String description;

    /**
     * Method that verifies if the input date is correct
     * @param date
     * @return
     */
    public static boolean dateCorrectness(String date) {
        int dd = Integer.parseInt(date.substring(0, 2));
        int mm = Integer.parseInt(date.substring(3, 5));
        int yyyy = Integer.parseInt(date.substring(6, 10));
        if (mm > 12)
            return false;
        if (mm == 2 && dd > 28)
            return false;
        if (dd > 31)
            return false;
        return yyyy >= 1900 && yyyy <= 2023;
    }
}

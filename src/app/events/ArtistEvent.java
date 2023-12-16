package app.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistEvent {
    private String name;
    private String date;
    private String description;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FIVE = 5;
    private static final int SIX = 6;
    private static final int TEN = 10;
    private static final int TWELVE = 12;
    private static final int TWENTYEIGHT = 28;
    private static final int THIRYONE = 31;
    private static final int NINETENOO = 1900;
    private static final int TWENTYTWENTYTHREE = 2023;


    /**
     * Method that verifies if the input date is correct
     * @param date
     * @return
     */
    public static boolean dateCorrectness(final String date) {
        int dd = Integer.parseInt(date.substring(0, TWO));
        int mm = Integer.parseInt(date.substring(THREE, FIVE));
        int yyyy = Integer.parseInt(date.substring(SIX, TEN));
        if (mm > TWELVE) {
            return false;
        }
        if (mm == TWO && dd > TWENTYEIGHT) {
            return false;
        }
        if (dd > THIRYONE) {
            return false;
        }
        return yyyy >= NINETENOO && yyyy <= TWENTYTWENTYTHREE;
    }
}

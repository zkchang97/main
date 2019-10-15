package user;

import exception.DukeException;
import storage.BookingConstants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class BookingList extends ArrayList<Booking> {

    /**
     * Create tasklist from text file.
     * @param loader strings from text file containing task info
     * @throws DukeException if file format incorrect
     */
    public BookingList(ArrayList<String> loader) throws DukeException {
        for (String line : loader) {
            String[] splitStr = line.split(" \\| ", 5);
            this.add(new Booking(splitStr[BookingConstants.USERNAME], splitStr[BookingConstants.VENUE], splitStr[BookingConstants.DESCRIPTION], splitStr[BookingConstants.TIMESTART], splitStr[BookingConstants.TIMEEND]));
        }

    }

    public static boolean checkBooking(BookingList bookinglist, String roomcode, String timeStart, String timeEnd) {
        boolean found = false;
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/mm/yyyy HHmm");
        DateTimeFormatter formatterEnd = DateTimeFormatter.ofPattern("HHmm");
        LocalDateTime startTime = LocalDateTime.parse(timeStart, formatterStart);
        LocalDateTime endTime = LocalDateTime.parse(timeEnd, formatterEnd);
        for (int i = 0; i < bookinglist.size(); i++) {
            if (bookinglist.get(i).venue == roomcode) {
                if ((bookinglist.get(i).dateTimeStart.isBefore(startTime) || bookinglist.get(i).dateTimeStart.isEqual(startTime)) &&(bookinglist.get(i).dateTimeEnd.isAfter(endTime) || bookinglist.get(i).dateTimeEnd.isEqual(endTime))) {
                    found = true;
                }
            }
            if (!found) {

            }
        }
        return found;
    }
}
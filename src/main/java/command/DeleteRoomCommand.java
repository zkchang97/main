
package command;

import inventory.Inventory;

import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import room.Room;
import ui.Ui;
import storage.Storage;
import user.UserList;

import java.io.IOException;

public class DeleteRoomCommand extends Command {
    private int index;
    private Room deletedRoom;

    /**
     * Deletes a room from the room list based on index (1-based).
     * @param input user input
     * @param splitStr tokenized user input
     * @throws DukeException if no index is entered or index is not an integer
     */
    public DeleteRoomCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length <= 1) {
            throw new DukeException("OOPS!!! Please add the index for the room you want to delete.");
        }
        input = input.substring(11);
        try {
            this.index = Integer.parseInt(input) - 1;
        } catch (Exception e) {
            throw new DukeException("OOPS!!! Index should be an integer.");
        }
    }

    /**
     * Executes the command to delete a room from room list to the system.
     * @param roomList list of rooms
     * @param bookingList bookings list
     * @param ui user interface
     * @param bookingStorage handles read write of text file
     * @param roomStorage file storage for room list
     * @throws DukeException invalid entry
     */
    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList, BookingList bookingList, Ui ui,
                        Storage userStorage, Storage inventoryStorage, Storage bookingStorage, Storage roomStorage)
            throws DukeException {
        if (index < 0 || index > roomList.size() - 1) {
            throw new DukeException("OOPS!!! You have entered an index that is out of bounds.");
        }
        deletedRoom = roomList.get(index);
        roomList.remove(index);
        try {
            roomStorage.saveToFile(roomList);
        } catch (IOException e) {
            throw new DukeException("OOPS!!! An error occurred while deleting your booking!");
        }
        ui.addToOutput("This room has been successfully removed:\n"
                + deletedRoom.toString()
                + "\nYou now have " + roomList.size() + " rooms in your list.");
    }
}

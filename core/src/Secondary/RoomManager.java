package Secondary; // 01 Apr, 10:59 PM

/**
 * RoomManager's purpose it manage transactions of Rooms<br>
 * RoomManager maintains:
 * <ul>
 * <li> Room Nos. of all available rooms
 * <li> current room
 * <li> previous room
 * </ul>
 * <p/>
 * RoomManager sets, updates and exits current room<br>
 * A Room is created, updated and exited through RoomManager<br>
 * <b>NOTE: Currently Room Nos. are not in use. </b>
 */
public class RoomManager {

    public final static byte garden = 0;
    public final static byte Lobby = 1;
    public final static byte courtYard = 2;
    public final static byte office = 3;
    public final static byte recreation_hall = 4;

    public static Room currentRoom;
    public static Room previousRoom;

    /**
     * Sets the specified room as current room.<br>
     * <b>NOTE: Only current room is updated, so specify the room to be updated by this method</b>
     * @param room
     */
    public void setRoom( Room room ) {
        currentRoom = room;
    }

    /**
     * Updates current specified room.<br>
     * <b>NOTE: Only current room is updated, so specify the room to be updated by setRoom() method</b>
     */
    public void update() {
        currentRoom.update_room ();
    }

    /**
     * A Room object(child) is destroyed through this method. <br>
     * It makes the specified Room as previous room and then calls it's destroy method
     * @param leaveRoom
     */
    public void exitRoom( Room leaveRoom ) {
        previousRoom = leaveRoom;
        currentRoom.destroy_room ();
    }


}

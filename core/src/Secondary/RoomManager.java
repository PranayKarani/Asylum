package Secondary; // 01 Apr, 10:59 PM

public class RoomManager {

    public final static int garden = 0;
    public final static int Lobby = 1;
    public final static int courtYard = 2;
    public final static int office = 3;
    public final static int recreation_hall = 4;

    public static Room cureentRoom;
    public static Room previousRoom;

    public static void setRoom(Room room){
        cureentRoom = room;
    }

    public static void update(){
        cureentRoom.update ();
    }

    public static void exitRoom(Room leaveRoom){
        previousRoom = leaveRoom;
        cureentRoom.destroy ();
    }



}

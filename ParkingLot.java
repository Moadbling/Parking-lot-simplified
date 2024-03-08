import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    String parkingLotId;
    List<List<Slot>> slots;

    public ParkingLot(String parkingLotId, int nfloors, int noOfSlotsPerFlr) {
        this.parkingLotId = parkingLotId;

        slots = new ArrayList<>();
        for (int i = 0; i < nfloors; i++) {
            slots.add(new ArrayList<>());

            List<Slot> floorSlots = slots.get(i);

            floorSlots.add(new Slot("truck"));

            floorSlots.add(new Slot("bike"));
            floorSlots.add(new Slot("bike"));

            for (int j = 3; j < noOfSlotsPerFlr; j++) {

                slots.get(i).add(new Slot("car"));
            }

        }
    }

    public String parkVehicle(String type, String regNo, String color) {
        Vehicle vehicle = new Vehicle(type, regNo, color);

        for (int i = 0; i < slots.size(); i++) {
            for (int j = 0; j < slots.get(i).size(); j++) {
                Slot slot = slots.get(i).get(j);
                if (slot.type.equals(type) && slot.vehicle == null) {
                    slot.vehicle = vehicle;
                    slot.ticketId = generateTicketID(i + 1, j + 1);
                    return slot.ticketId;
                }
            }
        }
        System.out.println("This slot is not available");
        return null;
    }

    public void unPark(String ticketId) {
        String[] extract = ticketId.split("_"); // ticketid wird geteilt. integers werden extrahiert.
        int flr_idx = Integer.parseInt(extract[1]) - 1;
        int slot_idx = Integer.parseInt(extract[2]) - 1;

        for (int i = 0; i < slots.size(); i++) {
            for (int j = 0; j < slots.get(i).size(); j++) {
                if (i == flr_idx && j == slot_idx) {
                    Slot slot = slots.get(i).get(j);
                    slot.vehicle = null;
                    slot.type = null;
                    System.out.println("The vehicle was successfully unparked!");
                }
            }
        }
    }

    public int getNoOfOpenSlots(String type) {
        int count = 0;
        for (List<Slot> floor : slots) {
            for (Slot slot : floor) {
                if (slot.vehicle == null && slot.type.equals(type))
                    count++;
            }
        }
        return count;
    }

    public void displayOpenSlots(String type) {
        for (int i = 0; i < slots.size(); i++) {
            List<Slot> floor = slots.get(i);
            if (floor != null) {
                for (int j = 0; j < floor.size(); j++) {
                    Slot slot = floor.get(j);
                    if (slot != null && slot.type != null && slot.vehicle == null && type.equals(slot.type)) {
                        System.out.println("Floor " + (i + 1) + " slot " + (j + 1));
                    }
                }
            }
        }

    }

    public void displayOccupiedSlots(String type) {
        for (int i = 0; i < slots.size(); i++) {
            for (int j = 0; j < slots.get(i).size(); j++) {
                Slot slot = slots.get(i).get(j);
                if (slot.vehicle != null && slot.type.equals(type))
                    System.out.println("Floor " + (i + 1) + " slot " + (j + 1));
            }
        }
    }

    private String generateTicketID(int flr, int slono) {
        return parkingLotId + "_" + flr + "_" + slono;
    }

    public static void main(String[] args) throws Exception {
        int nfloors = 4;
        int noOfSlotsPerFlr = 6;
        ParkingLot parkingLot = new ParkingLot("PR1234", nfloors, noOfSlotsPerFlr);

        parkingLot.getNoOfOpenSlots("car");

        @SuppressWarnings("unused")
        String ticket1 = parkingLot.parkVehicle("car", "MH-03", "red");
        String ticket2 = parkingLot.parkVehicle("car", "MH-04", "purple");

        parkingLot.displayOccupiedSlots("car");

        parkingLot.unPark(ticket2);
        parkingLot.displayOccupiedSlots("car");

        parkingLot.displayOpenSlots("truck");
        parkingLot.parkVehicle("truck", "MH-01", "black");
        parkingLot.displayOccupiedSlots("truck");

    }
}

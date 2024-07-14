import java.util.ArrayList;

/**
 * The Hotel class represents a hotel with rooms and reservations.
 */
public class Hotel {

    private final int MAX_ROOMS; // Maximum number of rooms this hotel can accommodate

    private String hotelName; // Name of the hotel
    private ArrayList<Room> rooms; // List of rooms in the hotel
    private int numOfRooms; // Current number of rooms in the hotel
    private ArrayList<CustomerReservation> reservations; // List of reservations made in the hotel

    /**
     * Constructs a new Hotel object with the specified name, number of rooms, and
     * base price.
     * Initializes rooms and reservations.
     *
     * @param name       The name of the hotel.
     * @param numOfRooms The initial number of rooms in the hotel.
     * @param basePrice  The base price for each room.
     */
    public Hotel(String name, int numOfRooms, double basePrice) {

        this.MAX_ROOMS = 50;
        this.hotelName = name;
        this.numOfRooms = numOfRooms;
        this.rooms = new ArrayList<>();

        // Initialize rooms with base price
        for (int i = 0; i < numOfRooms; i++) {
            this.rooms.add(new Room(i + 1, basePrice));
        }

        this.reservations = new ArrayList<>();
    }

    /**
     * Gets the name of the hotel.
     *
     * @return The name of the hotel.
     */
    public String getHotelName() {
        return this.hotelName;
    }

    /**
     * Gets the list of rooms in the hotel.
     *
     * @return The list of rooms.
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Gets the current number of rooms in the hotel.
     *
     * @return The number of rooms in the hotel.
     */
    public int getHotelNumOfRooms() {
        return numOfRooms;
    }

    /**
     * Gets the list of reservations made in the hotel.
     *
     * @return The list of reservations.
     */
    public ArrayList<CustomerReservation> getHotelReservations() {
        return reservations;
    }

    /**
     * Counts the number of currently unreserved rooms in the hotel.
     *
     * @return Number of unreserved rooms.
     */
    public int getNumOfUnreservedRooms() {
        int count = 0;
        for (Room room : rooms) {
            if (room.getDaysReserved() == 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the estimated earnings from all reservations in the hotel.
     *
     * @return Estimated earnings based on reservations made.
     */
    public double getEstimatedEarnings() {
        double totalEarnings = 0;
        for (CustomerReservation reservation : reservations) {
            totalEarnings += reservation.getTotalPrice();
        }
        return totalEarnings;
    }

    /**
     * Sets the name of the hotel.
     *
     * @param hotelName The new name of the hotel.
     */
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    /**
     * Adds a specified number of rooms to the hotel.
     *
     * @param howMany Number of rooms to add.
     */
    public void addRooms(int howMany) {

        if (numOfRooms + howMany > MAX_ROOMS) {
            throw new IllegalArgumentException("Cannot add more than " + MAX_ROOMS + " rooms");
        } else {
            numOfRooms += howMany;
            for (int i = numOfRooms; i < numOfRooms + howMany; i++) {
                rooms.add(new Room(i + 1, rooms.getFirst().getPrice())); // Assumes the first room's price is the base
                // price
            }
        }
    }

    /**
     * Removes a specified number of rooms from the hotel if they are unreserved.
     *
     * @param howMany Number of rooms to remove.
     */
    public void removeRooms(int howMany) {
        if (numOfRooms - howMany < 1 || howMany == 0)
            throw new IllegalArgumentException("Cannot remove more than " + MAX_ROOMS + " rooms");
        for (int i = numOfRooms; i > numOfRooms - howMany; i--) {
            if (rooms.get(i - 1).getDaysReserved() == 0) { // Check if room is unreserved
                rooms.remove(i - 1);
            }
        }
        numOfRooms -= howMany;
    }

    /**
     * Clears all reservations in the hotel.
     */
    public void clearReservations() {
        reservations.clear();
    }

    /**
     * Counts the number of reservations for a specific date across all rooms.
     *
     * @param selectDate The date to count reservations for.
     * @return Number of reservations for the specified date.
     */
    public int countReservations(int selectDate) {
        int count = 0;
        for (Room room : rooms) {
            if (room.getIsReserved(selectDate)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of available rooms for a specific date across all rooms.
     *
     * @param selectDate The date to count available rooms for.
     * @return Number of available rooms for the specified date.
     */
    public int countAvailableRooms(int selectDate) {
        return numOfRooms - countReservations(selectDate);
    }

    /**
     * Finds an available room for a specified check-in and check-out date range.
     *
     * @param checkInDate  The check-in date.
     * @param checkOutDate The check-out date.
     * @return A Room object if an available room is found; otherwise, null.
     */
    public Room findAvailableRoom(int checkInDate, int checkOutDate) {
        for (Room room : rooms) {
            boolean isAvailable = true;
            for (int i = checkInDate; i < checkOutDate; i++) {
                if (room.getIsReserved(i)) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                return room;
            }
        }
        return null;
    }
}

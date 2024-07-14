/**
 * This class represents a Room object with attributes such as its "number" and
 * "price".
 * It also provides functionality to check and manage reservations for the room.
 */
public class Room {

    private int number;
    private double price;
    private boolean[] isReserved;

    /**
     * Constructs a Room object with the given number and price.
     *
     * @param number The number identifier of the room.
     * @param price  The price per day of staying in the room.
     */
    public Room(int number, double price) {
        this.number = number;
        this.price = price;
        this.isReserved = new boolean[31]; // Assuming a maximum of 31 days in a month

        // Initialize all days as not reserved
        for (int i = 0; i < 31; i++) {
            this.isReserved[i] = false;
        }
    }

    /**
     * Retrieves the number identifier of this Room instance.
     *
     * @return The unique number identifier of this room.
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Retrieves the price per day of this Room instance.
     *
     * @return The price per day of staying in this room.
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Checks if the room is reserved on a specific date.
     *
     * @param date The date to check (1 to 31).
     * @return true if the room is reserved on the specified date, false otherwise.
     */
    public boolean getIsReserved(int date) {
        return this.isReserved[date - 1];
    }

    /**
     * Retrieves the total number of days this room is reserved within the current
     * month.
     *
     * @return The count of reserved days in the current month.
     */
    public int getDaysReserved() {
        int count = 0;
        for (int i = 0; i < 31; i++) {
            if (this.isReserved[i])
                count++;
        }
        return count;
    }

    /**
     * Sets the reservation status of the room for a range of dates.
     *
     * @param checkInDate  The starting date of the reservation (1 to 31).
     * @param checkOutDate The ending date of the reservation (1 to 31).
     * @param reserve      true to reserve the room for the specified range, false
     *                     to un-reserve.
     *
     *                     ///////////////////////////////////////////////////////////////////
     */
    public void setIsReserved(int checkInDate, int checkOutDate, boolean reserve) {
        for (int i = checkInDate; i <= checkOutDate; i++) {
            this.isReserved[i - 1] = reserve;
        }
    }

    /**
     * Updates the price per day of staying in this room.
     *
     * @param price The new price per day for this room.
     */
    public void setPrice(double price) {
        this.price = price;
    }
}

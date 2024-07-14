/**
 * This class represents a reservation made by a customer for a specific room,
 * with details such as customer name, check-in and check-out dates, room
 * information,
 * and total price calculated based on the duration of stay and room price.
 */
public class CustomerReservation {

    private String customerName;
    private int checkInDate;
    private int checkOutDate;
    private Room roomInfo;
    private double totalPrice;

    /**
     * Constructs a CustomerReservation object with the provided details.
     *
     * @param customerName The name of the customer making the reservation.
     * @param checkInDate  The date when the customer will check into the room (1 to
     *                     31).
     * @param checkOutDate The date when the customer will check out from the room
     *                     (1 to 31).
     * @param roomInfo     The Room object containing details of the reserved room.
     */
    public CustomerReservation(String customerName, int checkInDate, int checkOutDate, Room roomInfo) {
        this.customerName = customerName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomInfo = roomInfo;

        // Calculate total price based on room price and duration of stay
        this.totalPrice = roomInfo.getPrice() * (checkOutDate - checkInDate);
    }

    /**
     * Retrieves the name of the customer who made the reservation.
     *
     * @return The name of the customer.
     */
    public String getCustomerName() {
        return this.customerName;
    }

    /**
     * Retrieves the check-in date of the reservation.
     *
     * @return The check-in date (1 to 31).
     */
    public int getCheckInDate() {
        return this.checkInDate;
    }

    /**
     * Retrieves the check-out date of the reservation.
     *
     * @return The check-out date (1 to 31).
     */
    public int getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * Retrieves the Room object associated with this reservation.
     *
     * @return The Room object containing details of the reserved room.
     */
    public Room getRoomInfo() {
        return roomInfo;
    }

    /**
     * Retrieves the total price of the reservation.
     *
     * @return The total price calculated based on the duration of stay and room
     *         price.
     */
    public double getTotalPrice() {
        return totalPrice;
    }
}

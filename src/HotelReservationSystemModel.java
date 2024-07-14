import java.util.ArrayList;

public class HotelReservationSystemModel {
    private ArrayList<Hotel> hotels;

    public HotelReservationSystemModel() {
        this.hotels = new ArrayList<>();
    }

    public ArrayList<Hotel> getHotels() {
        return hotels;
    }

    public boolean isHotelNameUnique(String hotelName) {
        for (Hotel hotel : hotels) {
            if (hotel.getHotelName().equalsIgnoreCase(hotelName)) {
                return false;
            }
        }
        return true;
    }

    public void createHotel(String hotelName, int numberOfRooms, double basePrice) {
        Hotel hotel = new Hotel(hotelName, numberOfRooms, basePrice);
        hotels.add(hotel);
    }

    public Hotel findHotelByName(String hotelName) {
        for (Hotel hotel : hotels) {
            if (hotel.getHotelName().equalsIgnoreCase(hotelName)) {
                return hotel;
            }
        }
        return null;
    }

    public void removeHotel(String hotelName) {
        hotels.removeIf(hotel -> hotel.getHotelName().equalsIgnoreCase(hotelName));
    }

    public void addReservation(String hotelName, String customerName, int checkInDate, int checkOutDate) {
        Hotel hotel = findHotelByName(hotelName);
        if (hotel != null) {
            Room availableRoom = hotel.findAvailableRoom(checkInDate, checkOutDate);
            if (availableRoom != null) {
                availableRoom.setIsReserved(checkInDate, checkOutDate, true);
                CustomerReservation reservation = new CustomerReservation(customerName, checkInDate, checkOutDate, availableRoom);
                hotel.getHotelReservations().add(reservation);
            }
        }
    }
}

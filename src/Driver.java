public class Driver {
    public static void main(String[] args) {
        HotelReservationSystemView view = new HotelReservationSystemView();
        HotelReservationSystemModel model = new HotelReservationSystemModel();
        HotelReservationSystemController controller = new HotelReservationSystemController(view, model);
    }
}

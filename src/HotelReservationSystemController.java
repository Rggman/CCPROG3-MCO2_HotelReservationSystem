import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class HotelReservationSystemController {
    private HotelReservationSystemView view;
    private HotelReservationSystemModel model;

    public HotelReservationSystemController(HotelReservationSystemView view, HotelReservationSystemModel model) {
        this.view = view;
        this.model = model;

        this.view.setButtonMenuItem("Create Hotel", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createHotel();
            }
        });

        this.view.setButtonMenuItem("View Hotel", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewHotel();
            }
        });

        this.view.setButtonMenuItem("Manage Hotel", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manageHotel();
            }
        });

        this.view.setButtonMenuItem("Simulate Booking", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulateBooking();
            }
        });
    }

    private void createHotel() {
        String hotelName = JOptionPane.showInputDialog("Enter hotel name:");
        int numberOfRooms = Integer.parseInt(JOptionPane.showInputDialog("Enter number of rooms:"));
        double basePrice = Double.parseDouble(JOptionPane.showInputDialog("Enter base price per room:"));

        if (model.isHotelNameUnique(hotelName)) {
            model.createHotel(hotelName, numberOfRooms, basePrice);
            JOptionPane.showMessageDialog(null, "Hotel created successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Hotel name already exists!");
        }
    }

    private void viewHotel() {
        String hotelName = JOptionPane.showInputDialog("Enter hotel name to view:");
        Hotel hotel = model.findHotelByName(hotelName);

        if (hotel != null) {
            StringBuilder hotelInfo = new StringBuilder("Hotel Name: " + hotel.getHotelName() + "\n");
            hotelInfo.append("Number of Rooms: ").append(hotel.getHotelNumOfRooms()).append("\n");
            hotelInfo.append("Number of Reservations: ").append(hotel.getHotelReservations().size()).append("\n");
            hotelInfo.append("Estimated Earnings: ").append(hotel.getEstimatedEarnings()).append("\n");
            hotelInfo.append("Number of Available Rooms: ").append(hotel.getNumOfUnreservedRooms()).append("\n");

            JOptionPane.showMessageDialog(null, hotelInfo.toString());
        } else {
            JOptionPane.showMessageDialog(null, "Hotel not found!");
        }
    }

    private void manageHotel() {
        String hotelName = JOptionPane.showInputDialog("Enter hotel name to manage:");
        Hotel hotel = model.findHotelByName(hotelName);

        if (hotel != null) {
            String[] options = {"Add Rooms", "Remove Rooms", "Clear Reservations"};
            int choice = JOptionPane.showOptionDialog(null, "Manage Hotel", "Options", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    int addRooms = Integer.parseInt(JOptionPane.showInputDialog("Enter number of rooms to add:"));
                    hotel.addRooms(addRooms);
                    JOptionPane.showMessageDialog(null, "Rooms added successfully!");
                    break;
                case 1:
                    int removeRooms = Integer.parseInt(JOptionPane.showInputDialog("Enter number of rooms to remove:"));
                    hotel.removeRooms(removeRooms);
                    JOptionPane.showMessageDialog(null, "Rooms removed successfully!");
                    break;
                case 2:
                    hotel.clearReservations();
                    JOptionPane.showMessageDialog(null, "Reservations cleared!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice!");
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Hotel not found!");
        }
    }

    private void simulateBooking() {
        String hotelName = JOptionPane.showInputDialog("Enter hotel name:");
        String customerName = JOptionPane.showInputDialog("Enter customer name:");
        int checkInDate = Integer.parseInt(JOptionPane.showInputDialog("Enter check-in date (1 to 31):"));
        int checkOutDate = Integer.parseInt(JOptionPane.showInputDialog("Enter check-out date (1 to 31):"));

        model.addReservation(hotelName, customerName, checkInDate, checkOutDate);
        JOptionPane.showMessageDialog(null, "Reservation made successfully!");
    }
}

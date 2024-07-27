import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.util.ArrayList;
import java.util.TreeSet;



public class HotelReservationSystemController {
    private HotelReservationSystemView view;
    private HotelReservationSystemModel model;

    public HotelReservationSystemController(HotelReservationSystemView view, HotelReservationSystemModel model) {
        this.view = view;
        this.model = model;

        this.view.setButtonMenuItem("Create Hotel", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createHotelForm();

            }
        });

        this.view.setButtonMenuItem("View Hotel", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showViewHotelForm();
            }
        });

        this.view.setButtonMenuItem("Manage Hotel", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showManageHotelForm();
            }
        });

        this.view.setButtonMenuItem("Simulate Booking", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSimulateBookingForm();
            }
        });

        this.view.setButtonMenuItem("Back to Main Menu", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.updateCenterPanel(new JPanel(null));
            }
        });
    }

    private void createHotelForm() {
        view.showCreateHotelForm();

        // Access the submitButton from the view
        JButton submitButton = view.getSubmitButton();

        if (submitButton == null) {
            System.out.println("Submit button is null!");
            return;
        } else {
            System.out.println("Submit button initialized successfully.");
        }

        submitButton.addActionListener(e -> {
            String hotelName = view.getHotelNameField().getText();

            // Check if the hotel name is unique
            if (!model.isHotelNameUnique(hotelName)) {
                JOptionPane.showMessageDialog(null, "Hotel name already exists!");
                return;
            }

            try {
                int numberOfStandardRooms = Integer.parseInt(view.getNumberOfStandardRoomsField().getText());
                int numberOfDeluxeRooms = Integer.parseInt(view.getNumberOfDeluxeRoomsField().getText());
                int numberOfExecutiveRooms = Integer.parseInt(view.getNumberOfExecutiveRoomsField().getText());
                double basePrice = Double.parseDouble(view.getBasePriceField().getText());

                int totalRooms = numberOfStandardRooms + numberOfDeluxeRooms + numberOfExecutiveRooms;

                // Validate total number of rooms
                if (totalRooms <= 0 || totalRooms > 50) {
                    JOptionPane.showMessageDialog(null, "Invalid total number of rooms (1-50 rooms only!)");
                    return;
                }

                // Validate base price
                if (basePrice < 100) {
                    JOptionPane.showMessageDialog(null, "Invalid base price!");
                    return;
                }

                // Create hotel in the model
                model.createHotel(hotelName, totalRooms, basePrice, numberOfStandardRooms, numberOfDeluxeRooms,
                        numberOfExecutiveRooms);
                JOptionPane.showMessageDialog(null, "Hotel Successfully Created");

                // Clear the input fields
                view.getHotelNameField().setText("");
                view.getNumberOfStandardRoomsField().setText("");
                view.getNumberOfDeluxeRoomsField().setText("");
                view.getNumberOfExecutiveRoomsField().setText("");
                view.getBasePriceField().setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers for rooms and price.");
            }
        });
    }

    private void showViewHotelForm() {
        // Initialize components for passing to displayViewHotel
        JButton viewButton = new JButton("View");
        JComboBox<String> hotelComboBox = new JComboBox<>();
        JLabel nameLabel = new JLabel();
        JLabel numOfRoomsLabel = new JLabel();
        JLabel numOfReservationsLabel = new JLabel();
        JLabel estimatedEarningsLabel = new JLabel();
        JLabel numOfAvailableRoomsLabel = new JLabel();
        JButton checkAvailabilityButton = new JButton("Check Availability");
        JButton roomInfoButton = new JButton("Room Information");
        JButton reservationInfoButton = new JButton("Reservation Information");

        checkAvailabilityButton.setEnabled(false);
        roomInfoButton.setEnabled(false);
        reservationInfoButton.setEnabled(false);

        // Populate hotelComboBox
        ArrayList<String> hotelOptions = new ArrayList<>();
        for (Hotel hotel : model.getHotels()) {
            hotelOptions.add(hotel.getHotelName());
        }

        if (hotelOptions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No available hotels!");
            return;
        }

        hotelComboBox.setModel(new DefaultComboBoxModel<>(hotelOptions.toArray(new String[0])));

        // Display the initial GUI
        view.displayViewHotel(viewButton, hotelComboBox, nameLabel, numOfRoomsLabel, numOfReservationsLabel,
                estimatedEarningsLabel, numOfAvailableRoomsLabel, checkAvailabilityButton, roomInfoButton,
                reservationInfoButton);

        // Set action listener for the viewButton
        viewButton.addActionListener(e -> {
            String selectedHotelName = (String) hotelComboBox.getSelectedItem();
            Hotel hotel = model.findHotelByName(selectedHotelName);

            if (hotel != null) {
                nameLabel.setText("Hotel Name: " + hotel.getHotelName());
                numOfRoomsLabel.setText("Number of Rooms: " + hotel.getHotelNumOfRooms());
                numOfReservationsLabel.setText("Number of Reservations: " + hotel.getHotelReservations().size());
                estimatedEarningsLabel.setText("Estimated Earnings: " + hotel.getEstimatedEarnings());
                numOfAvailableRoomsLabel.setText("Number of Available Rooms: " + hotel.getNumOfUnreservedRooms());

                checkAvailabilityButton.setEnabled(true);
                roomInfoButton.setEnabled(true);
                reservationInfoButton.setEnabled(true);

                checkAvailabilityButton.addActionListener(ev -> showAvailabilityPanel(hotel));
                roomInfoButton.addActionListener(ev -> showRoomInfoPanel(hotel));
                reservationInfoButton.addActionListener(ev -> showReservationInfoPanel(hotel));
            } else {
                nameLabel.setText("Hotel Name: ");
                numOfRoomsLabel.setText("Number of Rooms: ");
                numOfReservationsLabel.setText("");
                estimatedEarningsLabel.setText("");
                numOfAvailableRoomsLabel.setText("");
            }
        });
    }

    private void showAvailabilityPanel(Hotel hotel) {
        JPanel availabilityPanel = new JPanel();
        availabilityPanel.setLayout(new BoxLayout(availabilityPanel, BoxLayout.Y_AXIS));

        JTextField dateField = new JTextField(10);
        JButton checkButton = new JButton("Check");

        checkButton.addActionListener(e -> {
            try {
                int date = Integer.parseInt(dateField.getText());
                if (date < 1 || date > 31) {
                    JOptionPane.showMessageDialog(null, "Invalid date");
                } else {
                    int bookedRooms = hotel.countReservations(date);
                    int availableRooms = hotel.countAvailableRooms(date);
                    JOptionPane.showMessageDialog(null,
                            "Total number of booked rooms: " + bookedRooms + "\n" +
                                    "Total number of available rooms: " + availableRooms);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.");
            }
        });

        availabilityPanel.add(new JLabel("Input date to check available rooms (1-31):"));
        availabilityPanel.add(dateField);
        availabilityPanel.add(checkButton);

        JOptionPane.showMessageDialog(null, availabilityPanel);
    }

    private void showRoomInfoPanel(Hotel hotel) {
        JPanel roomInfoPanel = new JPanel();
        roomInfoPanel.setLayout(new BoxLayout(roomInfoPanel, BoxLayout.Y_AXIS));

        JTextField roomNumberField = new JTextField(10);
        JButton fetchButton = new JButton("Fetch");

        fetchButton.addActionListener(e -> {
            try {
                int roomNumber = Integer.parseInt(roomNumberField.getText());
                if (roomNumber < 1 || roomNumber > hotel.getHotelNumOfRooms()) {
                    JOptionPane.showMessageDialog(null, "Invalid room number");
                } else {
                    Room room = hotel.getRooms().get(roomNumber - 1);
                    StringBuilder roomInfo = new StringBuilder();
                    roomInfo.append("Room Number: ").append(room.getNumber()).append("\n");
                    roomInfo.append("Room Price: ").append(room.getPrice()).append("\n");
                    roomInfo.append("Room Availability for the Month: \n");

                    for (int i = 1; i <= 31; i++) {
                        if (room.getIsReserved(i)) {
                            roomInfo.append(i).append(" (Reserved) ");
                        } else {
                            roomInfo.append(i).append(" (Available) ");
                        }
                        if (i % 8 == 0) {
                            roomInfo.append("\n");
                        }
                    }
                    JOptionPane.showMessageDialog(null, roomInfo.toString());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.");
            }
        });

        roomInfoPanel.add(new JLabel("Input room number:"));
        roomInfoPanel.add(roomNumberField);
        roomInfoPanel.add(fetchButton);

        JOptionPane.showMessageDialog(null, roomInfoPanel);
    }

    private void showReservationInfoPanel(Hotel hotel) {
        JPanel reservationInfoPanel = new JPanel();
        reservationInfoPanel.setLayout(new BoxLayout(reservationInfoPanel, BoxLayout.Y_AXIS));

        JTextField customerNameField = new JTextField(10);
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(e -> {
            String customerName = customerNameField.getText();
            boolean customerFound = false;
            StringBuilder reservationInfo = new StringBuilder();

            for (CustomerReservation reservation : hotel.getHotelReservations()) {
                if (reservation.getCustomerName().equalsIgnoreCase(customerName)) {
                    customerFound = true;
                    reservationInfo.append("Customer Name: ").append(reservation.getCustomerName()).append("\n");
                    reservationInfo.append("Check-in Date: ").append(reservation.getCheckInDate()).append("\n");
                    reservationInfo.append("Check-out Date: ").append(reservation.getCheckOutDate()).append("\n");
                    reservationInfo.append("Room Number: ").append(reservation.getRoomInfo().getNumber()).append("\n");
                    reservationInfo.append("Room Price: ").append(reservation.getRoomInfo().getPrice()).append("\n");
                    reservationInfo.append("Total Price: ").append(reservation.getTotalPrice()).append("\n");
                }
            }

            if (customerFound) {
                JOptionPane.showMessageDialog(null, reservationInfo.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Customer not found.");
            }
        });

        reservationInfoPanel.add(new JLabel("Input customer name:"));
        reservationInfoPanel.add(customerNameField);
        reservationInfoPanel.add(searchButton);

        JOptionPane.showMessageDialog(null, reservationInfoPanel);
    }

    private void showManageHotelForm() {
        ArrayList<String> hotelOptions = new ArrayList<>();

        for (Hotel hotel : model.getHotels()) {
            hotelOptions.add(hotel.getHotelName());
        }

        if (hotelOptions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No available hotels!");
            return;
        }

        JComboBox<String> hotelComboBox = new JComboBox<>(hotelOptions.toArray(new String[0]));
        hotelComboBox.setSelectedItem(null);

        JPanel formPanel = createFormPanel(" Manage Hotels");
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Select Hotel:"), gbc);
        gbc.gridx = 1;
        formPanel.add(hotelComboBox, gbc);
        gbc.gridx = 2;

        JComboBox<String> typeOfRoomBox = new JComboBox<>();

        typeOfRoomBox.addItem("Standard");
        typeOfRoomBox.addItem("Deluxe");
        typeOfRoomBox.addItem("Executive");
        typeOfRoomBox.setSelectedItem(null);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Select Room Type:"), gbc);
        gbc.gridx = 1;
        formPanel.add(typeOfRoomBox, gbc);

        JComboBox<String> addRoomsBox = new JComboBox<>();
        addRoomsBox.setSelectedItem(null);
        JButton addRoomButton = new JButton("Add");
        addRoomButton.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Add Rooms:"), gbc);
        gbc.gridx = 1;
        formPanel.add(addRoomsBox, gbc);
        gbc.gridx = 2;
        addRoomButton.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(addRoomButton, gbc);

        JComboBox<String> removeRoomsBox = new JComboBox<>();
        removeRoomsBox.setSelectedItem(null);
        JButton removeRoomButton = new JButton("Remove");
        removeRoomButton.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Remove Rooms:"), gbc);
        gbc.gridx = 1;
        formPanel.add(removeRoomsBox, gbc);
        gbc.gridx = 2;
        formPanel.add(removeRoomButton, gbc);

        JLabel changeNameLabel = new JLabel("Change Hotel Name to:");
        JTextField changeNameTextField = new JTextField(15);
        JButton changeNameButton = new JButton("Change");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        formPanel.add(changeNameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(changeNameTextField, gbc);
        gbc.gridx = 2;
        formPanel.add(changeNameButton, gbc);

        JLabel changePriceLabel = new JLabel("Change Hotel Base Price to (price >= 100):");
        JTextField changePriceTextField = new JTextField(15);
        JButton changePriceButton = new JButton("Change");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        formPanel.add(changePriceLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(changePriceTextField, gbc);
        gbc.gridx = 2;
        formPanel.add(changePriceButton, gbc);

        JButton deleteHotelButton = new JButton("Delete Hotel");
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(deleteHotelButton, gbc);

        ArrayList<String> datePriceList = new ArrayList<>();
        for (int i = 1; i <= 31; i++)
            datePriceList.add(Integer.toString(i));

        ArrayList<String> datePercentList = new ArrayList<>();
        for (int i = 50; i <= 150; i++)
            datePercentList.add(Integer.toString(i) + "%");

        JLabel dates = new JLabel("Modify Date Price:");
        JLabel chooseDate = new JLabel("Choose Date:");
        JLabel choosePercent = new JLabel("Choose Percent to Increase or Decrease Price:");
        JComboBox<String> datesBox = new JComboBox<>(datePriceList.toArray(new String[0]));
        datesBox.setSelectedItem(null);
        JComboBox<String> datesPercentBox = new JComboBox<>(datePercentList.toArray(new String[0]));
        datesPercentBox.setSelectedItem(null);
        JButton datePriceButton = new JButton("Modify");
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(dates, gbc);
        gbc.gridy = 8;
        formPanel.add(chooseDate, gbc);
        gbc.gridx = 1;
        formPanel.add(datesBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(choosePercent, gbc);
        gbc.gridx = 1;
        formPanel.add(datesPercentBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 10;
        formPanel.add(datePriceButton, gbc);

        typeOfRoomBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedHotelName = (String) hotelComboBox.getSelectedItem();
                Hotel hotel = model.findHotelByName(selectedHotelName);
                String selectedRoomType = (String) typeOfRoomBox.getSelectedItem();
                addRoomsBox.removeAllItems();
                removeRoomsBox.removeAllItems();

                for (int i = 0; i < 50 - hotel.getHotelNumOfRooms(); i++) {
                    addRoomsBox.addItem(Integer.toString(i + 1));
                }

                if (selectedRoomType.equals("Standard")) {
                    for (int i = 0; i < hotel.getNumOfStandardRooms(); i++) {
                        removeRoomsBox.addItem(Integer.toString(i + 1));
                    }
                } else if (selectedRoomType.equals("Deluxe")) {
                    for (int i = 0; i < hotel.getNumOfDeluxeRooms(); i++) {
                        removeRoomsBox.addItem(Integer.toString(i + 1));
                    }
                } else {
                    for (int i = 0; i < hotel.getNumOfExecutiveRooms(); i++) {
                        removeRoomsBox.addItem(Integer.toString(i + 1));
                    }
                }
            }
        });

        addRoomButton.addActionListener(f -> {
            String selectedHotelName = (String) hotelComboBox.getSelectedItem();
            Hotel hotel = model.findHotelByName(selectedHotelName);
            hotel.addRooms(Integer.parseInt((String) addRoomsBox.getSelectedItem()),
                    (String) typeOfRoomBox.getSelectedItem());
            JOptionPane.showMessageDialog(null, "Successfully added rooms");
        });

        removeRoomButton.addActionListener(g -> {
            String selectedHotelName = (String) hotelComboBox.getSelectedItem();
            Hotel hotel = model.findHotelByName(selectedHotelName);
            hotel.removeRooms(Integer.parseInt((String) removeRoomsBox.getSelectedItem()),
                    (String) typeOfRoomBox.getSelectedItem());
            JOptionPane.showMessageDialog(null, "Successfully removed rooms");
        });

        changeNameButton.addActionListener(h -> {
            String selectedHotelName = (String) hotelComboBox.getSelectedItem();
            Hotel hotel = model.findHotelByName(selectedHotelName);
            if (!model.isHotelNameUnique(changeNameTextField.getText())) {
                JOptionPane.showMessageDialog(null, "Hotel name already exists!");
                return;
            }
            hotel.setHotelName(changeNameTextField.getText());
            JOptionPane.showMessageDialog(null, "Changed hotel name to " + changeNameTextField.getText());
        });

        changePriceButton.addActionListener(i -> {
            String selectedHotelName = (String) hotelComboBox.getSelectedItem();
            Hotel hotel = model.findHotelByName(selectedHotelName);
            if (Double.parseDouble(changePriceTextField.getText()) < 100) {
                JOptionPane.showMessageDialog(null, "Price must be atleast 100");
                return;
            }
            hotel.setRoomPrice(Double.parseDouble(changePriceTextField.getText()));
            JOptionPane.showMessageDialog(null,
                    "Changed hotel price name to " + changePriceTextField.getText());
        });

        deleteHotelButton.addActionListener(j -> {
            String selectedHotelName = (String) hotelComboBox.getSelectedItem();
            Hotel hotel = model.findHotelByName(selectedHotelName);
            if (hotel.getHotelReservations().size() != 0) {
                JOptionPane.showMessageDialog(null, "There are still reservations!\nHotel cannot be deleted");
                return;
            }
            model.removeHotel(selectedHotelName);
            JOptionPane.showMessageDialog(null, "Hotel " + selectedHotelName + " successfully deleted!");
        });

        datePriceButton.addActionListener(k -> {
            String selectedHotelName = (String) hotelComboBox.getSelectedItem();
            Hotel hotel = model.findHotelByName(selectedHotelName);
            int date = Integer.parseInt((String) datesBox.getSelectedItem());
            String percentString = (String) datesPercentBox.getSelectedItem();
            percentString = percentString.replace("%", "");
            float percent = Float.parseFloat(percentString) / 100.00f;
            hotel.setPercent(date, percent);
            JOptionPane.showMessageDialog(null,
                    "Date " + date + " price has been changed to " + (String) datesPercentBox.getSelectedItem());
        });

        view.updateCenterPanel(formPanel);
    }

    public void showSimulateBookingForm() {
        // Create form components
        ArrayList<String> hotelOptions = new ArrayList<>();
        for (Hotel hotel : model.getHotels()) {
            hotelOptions.add(hotel.getHotelName());
        }

        if (hotelOptions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No available hotels!");
            return;
        }

        String[] checkInOptions = new String[31];
        for (int i = 0; i < 31; i++) {
            checkInOptions[i] = Integer.toString(i + 1);
        }

        JComboBox<String> hotelComboBox = new JComboBox<>(hotelOptions.toArray(new String[0]));
        hotelComboBox.setSelectedItem(null);
        JTextField customerNameField = new JTextField();
        JComboBox<String> roomTypeComboBox = new JComboBox<>();
        JComboBox<String> checkInDateBox = new JComboBox<>();
        JComboBox<String> checkOutDateBox = new JComboBox<>();
        JTextField couponCodeField = new JTextField();

        hotelComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Hotel hotel = model.findHotelByName((String) hotelComboBox.getSelectedItem());
                roomTypeComboBox.removeAllItems();

                if (hotel.getNumOfStandardRooms() != 0) {
                    roomTypeComboBox.addItem("Standard");
                }
                if (hotel.getNumOfDeluxeRooms() != 0) {
                    roomTypeComboBox.addItem("Deluxe");
                }
                if (hotel.getNumOfExecutiveRooms() != 0) {
                    roomTypeComboBox.addItem("Executive");
                }
                roomTypeComboBox.setSelectedItem(null);

                roomTypeComboBox.addItemListener(f -> {
                    if (f.getStateChange() == ItemEvent.SELECTED) {
                        checkInDateBox.removeAllItems();
                        boolean[] isAvailable = new boolean[31];

                        for (Room room : hotel.getRooms()) {
                            for (int i = 0; i < 31; i++) {
                                if ((room.getRoomType().equals("Standard") || room.getRoomType().equals("Deluxe")
                                        || room.getRoomType().equals("Executive")) && !room.getIsReserved(i + 1)) {
                                    isAvailable[i] = true;
                                }
                            }
                        }

                        for (int i = 0; i < isAvailable.length - 1; i++) {
                            if (isAvailable[i]) {
                                checkInDateBox.addItem(Integer.toString(i + 1));
                            }
                        }

                        checkInDateBox.addItemListener(g -> {
                            if (g.getStateChange() == ItemEvent.SELECTED) {
                                checkOutDateBox.removeAllItems();
                                checkOutDateBox.setSelectedItem(null);
                                for (int i = Integer.parseInt(
                                        (String) checkInDateBox.getSelectedItem()); i < isAvailable.length; i++) {
                                    if (isAvailable[i]) {
                                        checkOutDateBox.addItem(Integer.toString(i + 1));
                                    }
                                }
                                checkOutDateBox.setSelectedItem(null);
                            }
                        });
                    }
                });
            }
        });

        JButton bookButton = new JButton("Book");
        bookButton.setFont(new Font("Arial", Font.BOLD, 16));
        bookButton.addActionListener(e -> {
            String selectedHotel = (String) hotelComboBox.getSelectedItem();
            String customerName = customerNameField.getText();
            int checkInDate = Integer.parseInt((String) checkInDateBox.getSelectedItem());
            int checkOutDate = Integer.parseInt((String) checkOutDateBox.getSelectedItem());
            String roomType = (String) roomTypeComboBox.getSelectedItem();
            String couponCode = couponCodeField.getText();

            if (!couponCode.equals("I_WORK_HERE") && !couponCode.equals("STAY4_GET1") && !couponCode.equals("PAYDAY")
                    && !couponCode.equals("0")) {
                JOptionPane.showMessageDialog(null, "Invalid coupon code!");
                return;
            } else if (couponCode.equals("STAY4_GET1") && checkOutDate - checkInDate < 5) {
                JOptionPane.showMessageDialog(null,
                        "Coupon code (STAY4_GET1) could only be used for reservations with 5 days or more!");
                return;
            } else if (couponCode.equals("PAYDAY")) {
                boolean validPayday = false;
                for (int i = checkInDate; i < checkOutDate; i++) {
                    if ((i == 15 && checkOutDate != 15) || (i == 30 && checkOutDate != 30)) {
                        validPayday = true;
                        break;
                    }
                }
                if (!validPayday) {
                    JOptionPane.showMessageDialog(null,
                            "Coupon code (PAYDAY) could only be used for reservations covering 15 or 30 but not as checkout date!");
                    return;
                }
            }
            Room roomReserved = model.findHotelByName(selectedHotel).findAvailableRoom(checkInDate, checkOutDate,
                    roomType);
            roomReserved.setIsReserved(checkInDate, checkOutDate, true);
            model.addReservation(selectedHotel, customerName, checkInDate, checkOutDate, roomType, couponCode);
            JOptionPane.showMessageDialog(null, "Reservation made successfully!");
        });

        // Pass components to the view
        view.displaySimulateBookingForm(hotelComboBox, customerNameField, roomTypeComboBox, checkInDateBox,
                checkOutDateBox, couponCodeField, bookButton);
    }

    private JPanel createFormPanel(String title) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(new TitledBorder(new EmptyBorder(10, 10, 10, 10), title, TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 18)));
        return panel;
    }

    private JTextField createFormTextField(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(label);
        panel.add(textField);
        return textField;
    }
}

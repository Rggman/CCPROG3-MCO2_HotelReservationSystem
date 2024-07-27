import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HotelReservationSystemView extends JFrame {
    private JFrame mainFrame;
    private ArrayList<JButton> buttons = new ArrayList<>();
    private JPanel westPanel, northPanel, centerPanel;
    private JButton submitButton;
    private JTextField hotelNameField, numberOfStandardRoomsField, numberOfDeluxeRoomsField, numberOfExecutiveRoomsField, basePriceField;
    private JPanel CreateFormPanel;

    private JPanel viewSouthPanel, viewCenterPanel, viewNorthPanel;

    public HotelReservationSystemView() {
        mainFrame = new JFrame("Hotel Reservation System");
        setupMainFrame();
        setupNorthPanel();
        setupWestPanel();
        setupCenterPanel();

        mainFrame.setVisible(true);
    }

    private void setupMainFrame() {
        mainFrame.setSize(800, 600);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
    }

    private void setupNorthPanel() {
        northPanel = new JPanel();
        northPanel.setBackground(Color.LIGHT_GRAY);
        northPanel.setLayout(new FlowLayout());

        JLabel titleLabel = new JLabel("Hotel Reservation System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 144, 255)); // Dark blue color

        northPanel.add(titleLabel);
        mainFrame.add(northPanel, BorderLayout.NORTH);
    }

    private void setupWestPanel() {
        westPanel = new JPanel();
        westPanel.setBackground(Color.DARK_GRAY);
        westPanel.setLayout(new GridBagLayout());
        mainFrame.add(westPanel, BorderLayout.WEST);
    }

    private void setupCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setBackground(new Color(30, 144, 255));
        centerPanel.setLayout(new FlowLayout());
        mainFrame.add(centerPanel, BorderLayout.CENTER);
    }

    public void setButtonMenuItem(String btnLabel, ActionListener actionListener) {
        JButton menuItem = createMenuButton(btnLabel, actionListener);
        buttons.add(menuItem);
        composeMenuList(); // Automatically update the UI when a new button is added
    }

    private void composeMenuList() {
        westPanel.removeAll(); // Clear existing components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0); // Spacing between buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (JButton button : buttons) {
            westPanel.add(button, gbc);
        }

        mainFrame.revalidate(); // Refresh the layout of mainFrame
        mainFrame.repaint(); // Repaint mainFrame
    }
    private JButton createMenuButton(String btnLabel, ActionListener actionListener) {
        JButton button = new JButton(btnLabel);
        button.addActionListener(actionListener);
        button.setPreferredSize(new Dimension(220, 30));

        // Customizing button appearance
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(30, 144, 255)); // Dark blue color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        return button;
    }

    public void updateCenterPanel(JPanel newPanel) {
        centerPanel.removeAll();
        centerPanel.add(newPanel);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public void showCreateHotelForm() {
        CreateFormPanel = new JPanel(new GridLayout(6, 2, 10, 50));
        CreateFormPanel.setBorder(new TitledBorder(new EmptyBorder(10, 10, 10, 10), "Create Hotel", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 36)));
        CreateFormPanel.setPreferredSize(new Dimension(550, 500));
        CreateFormPanel.setBackground(new Color(30, 144, 255));

        hotelNameField = new JTextField();
        numberOfStandardRoomsField = new JTextField();
        numberOfDeluxeRoomsField = new JTextField();
        numberOfExecutiveRoomsField = new JTextField();
        basePriceField = new JTextField();
        submitButton = new JButton("Submit");

        // Create a Font object with the desired size
        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

// Create and add labels with the new font
        JLabel hotelNameLabel = new JLabel("Hotel Name:");
        hotelNameLabel.setFont(labelFont);
        CreateFormPanel.add(hotelNameLabel);

// Create and configure the text field for hotel name
        hotelNameField.setFont(fieldFont);
        hotelNameField.setPreferredSize(new Dimension(100, 10));
        CreateFormPanel.add(hotelNameField);

        JLabel standardRoomsLabel = new JLabel("Number of Standard Rooms:");
        standardRoomsLabel.setFont(labelFont);
        CreateFormPanel.add(standardRoomsLabel);

// Create and configure the text field for standard rooms
        numberOfStandardRoomsField.setFont(fieldFont);
        numberOfStandardRoomsField.setPreferredSize(new Dimension(100, 10));
        CreateFormPanel.add(numberOfStandardRoomsField);

        JLabel deluxeRoomsLabel = new JLabel("Number of Deluxe Rooms:");
        deluxeRoomsLabel.setFont(labelFont);
        CreateFormPanel.add(deluxeRoomsLabel);

// Create and configure the text field for deluxe rooms
        numberOfDeluxeRoomsField.setFont(fieldFont);
        numberOfDeluxeRoomsField.setPreferredSize(new Dimension(100, 10));
        CreateFormPanel.add(numberOfDeluxeRoomsField);

        JLabel executiveRoomsLabel = new JLabel("Number of Executive Rooms:");
        executiveRoomsLabel.setFont(labelFont);
        CreateFormPanel.add(executiveRoomsLabel);

// Create and configure the text field for executive rooms
        numberOfExecutiveRoomsField.setFont(fieldFont);
        numberOfExecutiveRoomsField.setPreferredSize(new Dimension(100, 10));
        CreateFormPanel.add(numberOfExecutiveRoomsField);

        JLabel basePriceLabel = new JLabel("Base Price:");
        basePriceLabel.setFont(labelFont);
        CreateFormPanel.add(basePriceLabel);

// Assuming you have a text field for base price, configure it similarly
        basePriceField.setFont(fieldFont);
        basePriceField.setPreferredSize(new Dimension(100, 10));
        CreateFormPanel.add(basePriceField);

        CreateFormPanel.add(submitButton);


        updateCenterPanel(CreateFormPanel);
    }


    public void displaySimulateBookingForm(JComboBox<String> hotelComboBox, JTextField customerNameField, JComboBox<String> roomTypeComboBox, JComboBox<String> checkInDateBox, JComboBox<String> checkOutDateBox, JTextField couponCodeField, JButton bookButton) {
        JPanel simulateFormPanel = new JPanel(new GridLayout(7, 3, 10, 50));  // Adjusted to 7 rows to accommodate all components

        simulateFormPanel.setPreferredSize(new Dimension(550, 500));
        simulateFormPanel.setBackground(new Color(30, 144, 255));

        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        JLabel hotelLabel = new JLabel("Select Hotel:");
        hotelLabel.setFont(labelFont);
        hotelComboBox.setFont(fieldFont);
        hotelComboBox.setPreferredSize(new Dimension(200, 30));

        JLabel customerNameLabel = new JLabel("Customer Name:");
        customerNameLabel.setFont(labelFont);
        customerNameField.setFont(fieldFont);
        customerNameField.setPreferredSize(new Dimension(200, 30));

        JLabel roomTypeLabel = new JLabel("Select Room Type:");
        roomTypeLabel.setFont(labelFont);
        roomTypeComboBox.setFont(fieldFont);
        roomTypeComboBox.setPreferredSize(new Dimension(200, 30));

        JLabel checkInDateLabel = new JLabel("Choose Check-in Date:");
        checkInDateLabel.setFont(labelFont);
        checkInDateBox.setFont(fieldFont);
        checkInDateBox.setPreferredSize(new Dimension(200, 30));

        JLabel checkOutDateLabel = new JLabel("Choose Check-out Date:");
        checkOutDateLabel.setFont(labelFont);
        checkOutDateBox.setFont(fieldFont);
        checkOutDateBox.setPreferredSize(new Dimension(200, 30));

        JLabel couponCodeLabel = new JLabel("Coupon Code (None = 0):");
        couponCodeLabel.setFont(labelFont);
        couponCodeField.setFont(fieldFont);
        couponCodeField.setPreferredSize(new Dimension(200, 30));

        bookButton.setFont(new Font("Arial", Font.BOLD, 16));
        bookButton.setSize(new Dimension(200, 60));

        // Add components vertically
        simulateFormPanel.add(hotelLabel);
        simulateFormPanel.add(hotelComboBox);

        simulateFormPanel.add(customerNameLabel);
        simulateFormPanel.add(customerNameField);

        simulateFormPanel.add(roomTypeLabel);
        simulateFormPanel.add(roomTypeComboBox);

        simulateFormPanel.add(checkInDateLabel);
        simulateFormPanel.add(checkInDateBox);

        simulateFormPanel.add(checkOutDateLabel);
        simulateFormPanel.add(checkOutDateBox);

        simulateFormPanel.add(couponCodeLabel);
        simulateFormPanel.add(couponCodeField);

        simulateFormPanel.add(new JLabel()); // Empty cell
        simulateFormPanel.add(bookButton);

        // Assume updateCenterPanel is a method to display the panel in the main window
        updateCenterPanel(simulateFormPanel);


    }


    public void displayViewHotel(JButton viewButton, JComboBox<String> hotelComboBox, JLabel nameLabel, JLabel numOfRoomsLabel, JLabel numOfReservationsLabel, JLabel estimatedEarningsLabel, JLabel numOfAvailableRoomsLabel, JButton checkAvailabilityButton, JButton roomInfoButton, JButton reservationInfoButton) {
        JPanel viewHotelPanel = new JPanel(new BorderLayout());
        viewHotelPanel.setPreferredSize(new Dimension(550, 500));
        viewHotelPanel.setBackground(new Color(30, 144, 255));
        viewHotelPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel hotelSelectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel lowLevelButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        JLabel hotelLabel = new JLabel("Select Hotel:");
        hotelLabel.setFont(labelFont);
        hotelLabel.setPreferredSize(new Dimension(10, 5));
        hotelComboBox.setFont(fieldFont);
        hotelComboBox.setSize(new Dimension(100, 30));

        // Create a panel to center the button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setSize(new Dimension(5, 5)); // Fixed size for the panel
        viewButton.setFont(new Font("Arial", Font.BOLD, 16));
        viewButton.setSize(new Dimension(30, 5)); // Fixed size for the button
        buttonPanel.add(viewButton);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical arrangement
        infoPanel.setBackground(new Color(30, 144, 255));
        infoPanel.setPreferredSize(new Dimension(400, 450));

        JLabel hotelDetailsLabel = new JLabel("High-Level Information:");
        hotelDetailsLabel.setFont(new Font("Arial", Font.BOLD, 30));
        infoPanel.add(hotelDetailsLabel);


        nameLabel.setText("Hotel Name: ");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        numOfRoomsLabel.setText("Number of Rooms: ");
        numOfRoomsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        numOfRoomsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        numOfReservationsLabel.setText("Number of Reservations: ");
        numOfReservationsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        numOfReservationsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        estimatedEarningsLabel.setText("Estimated Earnings: " );
        estimatedEarningsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        estimatedEarningsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        numOfAvailableRoomsLabel.setText("Number of Available Rooms: ");
        numOfAvailableRoomsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        numOfAvailableRoomsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Add these labels to the infoPanel
        infoPanel.add(nameLabel);
        infoPanel.add(numOfRoomsLabel);
        infoPanel.add(numOfReservationsLabel);
        infoPanel.add(estimatedEarningsLabel);
        infoPanel.add(numOfAvailableRoomsLabel);

        // Add buttons to the infoPanel
        lowLevelButtonPanel.add(checkAvailabilityButton);
        lowLevelButtonPanel.add(roomInfoButton);
        lowLevelButtonPanel.add(reservationInfoButton);

        hotelSelectionPanel.add(hotelComboBox, BorderLayout.NORTH);
        hotelSelectionPanel.add(viewButton, BorderLayout.NORTH);
        viewHotelPanel.add(hotelSelectionPanel, BorderLayout.NORTH);
        viewHotelPanel.add(infoPanel, BorderLayout.CENTER);
        viewHotelPanel.add(lowLevelButtonPanel, BorderLayout.SOUTH);

        updateCenterPanel(viewHotelPanel);
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public JTextField getHotelNameField() {
        return hotelNameField;
    }

    public JTextField getNumberOfStandardRoomsField() {
        return numberOfStandardRoomsField;
    }

    public JTextField getNumberOfDeluxeRoomsField() {
        return numberOfDeluxeRoomsField;
    }

    public JTextField getNumberOfExecutiveRoomsField() {
        return numberOfExecutiveRoomsField;
    }

    public JTextField getBasePriceField() {
        return basePriceField;
    }

    public JPanel getFormPanel() {
        return (JPanel) centerPanel;
    }

    public void displayViewHotel() {

    }

    public void viewUpdateSouthPanel(JPanel panel) {
        viewSouthPanel.removeAll();
        viewSouthPanel.add(panel);
        viewSouthPanel.revalidate();
        viewSouthPanel.repaint();
    }

    public void viewUpdateCenterPanel(JPanel panel) {
        viewCenterPanel.removeAll();
        viewCenterPanel.add(panel);
        viewCenterPanel.revalidate();
        viewCenterPanel.repaint();
    }

    public void viewUpdateNorthPanel(JPanel panel) {
        viewNorthPanel.removeAll();
        viewNorthPanel.add(panel);
        viewNorthPanel.revalidate();
        viewNorthPanel.repaint();
    }

    // Stub methods for showing additional panels
    public void showAvailabilityPanel(Hotel hotel) {
        // Implementation here
    }

    public void showRoomInfoPanel(Hotel hotel) {
        // Implementation here
    }

    public void showReservationInfoPanel(Hotel hotel) {
        // Implementation here
    }

}

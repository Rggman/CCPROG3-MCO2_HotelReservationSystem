import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HotelReservationSystemView extends JFrame {
    private JFrame mainFrame;
    private ArrayList<JButton> buttons = new ArrayList<>();

    public HotelReservationSystemView() {
        mainFrame = new JFrame("Hotel Reservation System");
        mainFrame.setSize(800, 600);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setting up the layout for mainFrame
        mainFrame.setLayout(new BorderLayout());

        // Setting up the North Panel with Title for Hotel Reservation System
        JPanel northPanel = new JPanel();
        northPanel.setBackground(Color.LIGHT_GRAY);
        northPanel.setLayout(new FlowLayout());

        JLabel titleLabel = new JLabel("Hotel Reservation System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 144, 255)); // Dark blue color
        northPanel.add(titleLabel);

        mainFrame.add(northPanel, BorderLayout.NORTH);

        // Creating and configuring the West panel
        JPanel westPanel = new JPanel();
        westPanel.setBackground(Color.DARK_GRAY);
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));

        mainFrame.add(westPanel, BorderLayout.WEST);

        // Making the mainFrame visible
        mainFrame.setVisible(true);
    }

    public void setButtonMenuItem(String btnLabel, ActionListener actionListener) {
        JButton menuItem = new JButton(btnLabel);
        menuItem.addActionListener(actionListener);
        menuItem.setPreferredSize(new Dimension(220, 30));
        buttons.add(menuItem);
        composeMenuList(); // Automatically update the UI when a new button is added
    }

    private void composeMenuList() {
        JPanel westPanel = (JPanel) mainFrame.getContentPane().getComponent(1); // Assuming west panel is the second component
        westPanel.removeAll(); // Clear existing components

        for (JButton button : buttons) {
            westPanel.add(button);
        }

        mainFrame.revalidate(); // Refresh the layout of mainFrame
        mainFrame.repaint(); // Repaint mainFrame
    }
}

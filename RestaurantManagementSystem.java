package restaurant2;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RestaurantManagementSystem extends JFrame {
    private WaitlistManager waitlistManager;
    private TableManager tableManager;
    
    // Common styling constants
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font SUBTITLE_FONT = new Font("Arial", Font.BOLD, 18);
    private static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);
    private static final Insets PADDING = new Insets(20, 20, 20, 20);
    
    public RestaurantManagementSystem() {
        waitlistManager = new WaitlistManager();
        tableManager = new TableManager();
        
        setTitle("Restaurant Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        
        showWelcomeScreen();
    }
    
    // ==================== UTILITY METHODS ====================
    
    private void switchToPanel(JPanel panel) {
        getContentPane().removeAll();
        add(panel);
        revalidate();
        repaint();
    }
    
    private JLabel createTitle(String text) {
        JLabel title = new JLabel(text, SwingConstants.CENTER);
        title.setFont(TITLE_FONT);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        return title;
    }
    
    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setPreferredSize(BUTTON_SIZE);
        button.addActionListener(action);
        return button;
    }
    
    private JTextField createTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(NORMAL_FONT);
        field.setPreferredSize(new Dimension(300, 35));
        return field;
    }
    
    private JPanel createButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }
    
    // ==================== WELCOME SCREEN ====================
    
    private void showWelcomeScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createTitle("Welcome!"), BorderLayout.NORTH);
        
        JButton waiterBtn = createButton("Waiter", e -> showWaiterDashboard());
        JButton customerBtn = createButton("Customer", e -> showCustomerMenu());
        
        panel.add(createButtonPanel(waiterBtn, customerBtn), BorderLayout.CENTER);
        switchToPanel(panel);
    }
    
    // ==================== CUSTOMER INTERFACES ====================
    
    private void showCustomerMenu() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createTitle("Queue Status"), BorderLayout.NORTH);
        
        // Queue status table
        String[][] data = {
            {"Small (1-2 people)", String.valueOf(waitlistManager.getQueueSize("Small"))},
            {"Medium (3-4 people)", String.valueOf(waitlistManager.getQueueSize("Medium"))},
            {"Large (5+ people)", String.valueOf(waitlistManager.getQueueSize("Large"))}
        };
        JTable table = new JTable(data, new String[]{"Table Size", "People Waiting"});
        table.setEnabled(false);
        table.setFont(NORMAL_FONT);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Buttons - only 3 buttons now
        JButton joinBtn = createButton("Join Queue", e -> showJoinQueue());
        JButton statusBtn = createButton("Check Status", e -> showCheckStatus());
        JButton backBtn = createButton("Back", e -> showWelcomeScreen());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        buttonPanel.add(joinBtn);
        buttonPanel.add(statusBtn);
        buttonPanel.add(backBtn);
        
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        switchToPanel(panel);
    }
    
    private void showJoinQueue() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createTitle("Join Queue"), BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = PADDING;
        
        JTextField nameField = createTextField(20);
        JTextField phoneField = createTextField(20);
        JTextField sizeField = createTextField(20);
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(phoneField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Party Size:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(sizeField, gbc);
        
        panel.add(formPanel, BorderLayout.CENTER);
        
        JButton submitBtn = createButton("Submit", e -> {
            if (addPartyToQueue(nameField.getText(), phoneField.getText(), sizeField.getText())) {
                showCustomerMenu();
            }
        });
        JButton backBtn = createButton("Back", e -> showCustomerMenu());
        
        panel.add(createButtonPanel(submitBtn, backBtn), BorderLayout.SOUTH);
        switchToPanel(panel);
    }
    
    private void showCheckStatus() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createTitle("Check My Status"), BorderLayout.NORTH);
        
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        
        JTextField phoneField = createTextField(20);
        JButton checkBtn = createButton("Check", e -> {
            String phone = phoneField.getText().trim();
            if (!phone.isEmpty()) {
                showStatusResult(phone);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter your phone number!");
            }
        });
        
        inputPanel.add(new JLabel("Phone Number:"));
        inputPanel.add(phoneField);
        inputPanel.add(checkBtn);
        
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(createButtonPanel(createButton("Back", e -> showCustomerMenu())), BorderLayout.SOUTH);
        switchToPanel(panel);
    }
    
    private void showStatusResult(String phoneNumber) {
        Party foundParty = findPartyByPhone(phoneNumber);
        
        JPanel panel = new JPanel(new BorderLayout());
        
        if (foundParty == null) {
            panel.add(createTitle("Reservation Not Found"), BorderLayout.NORTH);
            
            JTextArea message = new JTextArea("No reservation found with phone: " + phoneNumber + 
                "\n\nPossible reasons:\n• Not in queue\n• Already seated\n• Check phone number");
            message.setEditable(false);
            message.setFont(NORMAL_FONT);
            message.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            panel.add(message, BorderLayout.CENTER);
        } else {
            panel.add(createTitle("Your Status"), BorderLayout.NORTH);
            
            int position = getPositionInQueue(foundParty);
            String status = String.format("Name: %s\nParty Size: %d\nCategory: %s\nPosition: #%d\nTables Available: %d",
                foundParty.getName(), foundParty.getSize(), foundParty.getPartySizeCategory(),
                position, tableManager.getAvailableTableCount(foundParty.getPartySizeCategory()));
            
            JTextArea message = new JTextArea(status);
            message.setEditable(false);
            message.setFont(NORMAL_FONT);
            message.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            panel.add(message, BorderLayout.CENTER);
        }
        
        JButton checkAgainBtn = createButton("Check Again", e -> showCheckStatus());
        JButton backBtn = createButton("Back", e -> showCustomerMenu());
        
        panel.add(createButtonPanel(checkAgainBtn, backBtn), BorderLayout.SOUTH);
        switchToPanel(panel);
    }
    
    // ==================== WAITER INTERFACES ====================
    
    private void showWaiterDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createTitle("Waiter Dashboard"), BorderLayout.NORTH);
        
        // Table status
        String[][] tableData = new String[16][3];
        List<Table> allTables = tableManager.getAllTables();
        for (int i = 0; i < Math.min(16, allTables.size()); i++) {
            Table table = allTables.get(i);
            tableData[i][0] = String.valueOf(table.getTableId());
            tableData[i][1] = table.getSizeCategory();
            tableData[i][2] = table.isOccupied() ? "Occupied" : "Available";
        }
        
        JTable tableStatusTable = new JTable(tableData, new String[]{"Table", "Size", "Status"});
        tableStatusTable.setEnabled(false);
        tableStatusTable.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(tableStatusTable);
        scrollPane.setBorder(new TitledBorder("Table Status"));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JButton assignBtn = createButton("Assign Table", e -> autoAssignParty());
        JButton releaseBtn = createButton("Release Table", e -> showReleaseTable());
        JButton backBtn = createButton("Back", e -> showWelcomeScreen());
        
        panel.add(createButtonPanel(assignBtn, releaseBtn, backBtn), BorderLayout.SOUTH);
        switchToPanel(panel);
    }
    
    private void showReleaseTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createTitle("Release Table"), BorderLayout.NORTH);
        
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        
        JTextField tableField = createTextField(10);
        JButton releaseBtn = createButton("Release", e -> {
            try {
                int tableNumber = Integer.parseInt(tableField.getText().trim());
                if (releaseTable(tableNumber)) {
                    JOptionPane.showMessageDialog(this, "Table " + tableNumber + " released!");
                    showWaiterDashboard();
                } else {
                    JOptionPane.showMessageDialog(this, "Table not found or not occupied!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid table number!");
            }
        });
        
        inputPanel.add(new JLabel("Table Number:"));
        inputPanel.add(tableField);
        inputPanel.add(releaseBtn);
        
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(createButtonPanel(createButton("Back", e -> showWaiterDashboard())), BorderLayout.SOUTH);
        switchToPanel(panel);
    }
    
    // ==================== BUSINESS LOGIC ====================
    
    private boolean addPartyToQueue(String name, String phone, String sizeText) {
        if (name.trim().isEmpty() || phone.trim().isEmpty() || sizeText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return false;
        }
        
        try {
            int size = Integer.parseInt(sizeText.trim());
            if (size < 1 || size > 12) {
                JOptionPane.showMessageDialog(this, "Party size must be 1-12!");
                return false;
            }
            
            Party party = new Party(name.trim(), phone.trim(), size);
            waitlistManager.addParty(party);
            
            JOptionPane.showMessageDialog(this, 
                String.format("Added %s (party of %d) to %s queue!", 
                name, size, party.getPartySizeCategory().toLowerCase()));
            return true;
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid party size!");
            return false;
        }
    }
    
    private void autoAssignParty() {
        // Try small first, then medium, then large
        String[] categories = {"Small", "Medium", "Large"};
        
        for (String category : categories) {
            if (waitlistManager.getQueueSize(category) > 0 && 
                tableManager.getAvailableTableCount(category) > 0) {
                
                Table table = tableManager.getAvailableTable(category);
                Party party = waitlistManager.getNextParty(category);
                
                if (table != null && party != null) {
                    table.assignParty(party);
                    JOptionPane.showMessageDialog(this, 
                        String.format("Assigned %s to Table %d", party.getName(), table.getTableId()));
                    showWaiterDashboard();
                    return;
                }
            }
        }
        
        JOptionPane.showMessageDialog(this, "No suitable assignments available!");
    }
    
    private boolean releaseTable(int tableNumber) {
        List<Table> occupiedTables = tableManager.getOccupiedTables();
        for (Table table : occupiedTables) {
            if (table.getTableId() == tableNumber) {
                tableManager.releaseTable(table);
                return true;
            }
        }
        return false;
    }
    
    private Party findPartyByPhone(String phoneNumber) {
        return waitlistManager.getAllWaitingParties().stream()
            .filter(party -> party.getPhoneNumber().equals(phoneNumber))
            .findFirst()
            .orElse(null);
    }
    
    private int getPositionInQueue(Party targetParty) {
        String category = targetParty.getPartySizeCategory();
        int position = 1;
        
        for (Party party : waitlistManager.getAllWaitingParties()) {
            if (party.getPartySizeCategory().equals(category)) {
                if (party.getPhoneNumber().equals(targetParty.getPhoneNumber())) {
                    return position;
                }
                position++;
            }
        }
        return -1;
    }
}
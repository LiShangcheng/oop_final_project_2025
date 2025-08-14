package oop_project_4;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CheckStatusGUI extends MainGUI {
    private QueueStatusGUI parentFrame;
    
    // Common styling constants
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);
    
    public CheckStatusGUI(WaitlistManager waitlistManager, TableManager tableManager, QueueStatusGUI parent) {
        this.waitlistManager = waitlistManager;
        this.tableManager = tableManager;
        this.parentFrame = parent;
        
        setTitle("Check My Status");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        
        showCheckStatusForm();
    }
    
    private void showCheckStatusForm() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createTitle("Check My Status"), BorderLayout.NORTH);
        
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        
        JTextField phoneField = createTextField(20);
        JButton checkBtn = createButton("Check", e -> {
            String phone = phoneField.getText().trim();
            if (!phone.isEmpty()) {
                showCheckStatus(phone);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter your phone number!");
            }
        });
        
        inputPanel.add(new JLabel("Phone Number:"));
        inputPanel.add(phoneField);
        inputPanel.add(checkBtn);
        
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(createButtonPanel(createButton("Back", e -> clickBack())), BorderLayout.SOUTH);
        
        getContentPane().removeAll();
        add(panel);
        revalidate();
        repaint();
    }
    
    // Method renamed to match sequence diagram
    private void showCheckStatus(String phoneNumber) {
        // Following sequence diagram: get all waiting parties first
        List<Party> parties = waitlistManager.getAllWaitingParties();
        Party foundParty = findPartyByPhone(phoneNumber, parties);
        
        JPanel panel = new JPanel(new BorderLayout());
        
        if (foundParty == null) {
            showError();
            panel.add(createTitle("Reservation Not Found"), BorderLayout.NORTH);
            
            JTextArea message = new JTextArea("No reservation found with phone: " + phoneNumber + 
                "\n\nPossible reasons:\n• Not in queue\n• Already seated\n• Check phone number");
            message.setEditable(false);
            message.setFont(NORMAL_FONT);
            message.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            panel.add(message, BorderLayout.CENTER);
        } else {
            int position = getPositionInQueue(foundParty);
            int available = tableManager.getAvailableTableCount(foundParty.getPartySizeCategory());
            showStatus(foundParty, position, available);
            
            panel.add(createTitle("Your Status"), BorderLayout.NORTH);
            
            String status = String.format("Name: %s\nParty Size: %d\nCategory: %s\nPosition: #%d\nTables Available: %d",
                foundParty.getName(), foundParty.getSize(), foundParty.getPartySizeCategory(),
                position, available);
            
            JTextArea message = new JTextArea(status);
            message.setEditable(false);
            message.setFont(NORMAL_FONT);
            message.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            panel.add(message, BorderLayout.CENTER);
        }
        
        JButton checkAgainBtn = createButton("Check Again", e -> clickCheckAgain());
        JButton backBtn = createButton("Back", e -> clickBack());
        
        panel.add(createButtonPanel(checkAgainBtn, backBtn), BorderLayout.SOUTH);
        
        getContentPane().removeAll();
        add(panel);
        revalidate();
        repaint();
    }
    
    // Updated to use the parties list parameter as in sequence diagram
    private Party findPartyByPhone(String phoneNumber, List<Party> parties) {
        return parties.stream()
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
    
    // Methods added to match sequence diagram
    private void showStatus(Party party, int position, int available) {
        // This represents the showStatus step in the sequence diagram
        // The actual display is handled in showCheckStatus()
    }
    
    private void showError() {
        // This represents the showError step in the sequence diagram
        // The actual error display is handled in showCheckStatus()
    }
    
    // Method renamed to match sequence diagram
    private void clickCheckAgain() {
        showCheckStatusForm();
    }
    
    // Method renamed to match sequence diagram
    private void clickBack() {
        parentFrame.setVisible(true);
        this.dispose();
    }
    
    // Utility methods
    private JLabel createTitle(String text) {
        JLabel title = new JLabel(text, SwingConstants.CENTER);
        title.setFont(TITLE_FONT);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        return title;
    }
    
    private JButton createButton(String text, java.awt.event.ActionListener action) {
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
}
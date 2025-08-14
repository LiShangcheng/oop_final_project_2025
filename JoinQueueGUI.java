package oop_project_4;
import javax.swing.*;
import java.awt.*;

public class JoinQueueGUI extends MainGUI {
    private QueueStatusGUI parentFrame;
    
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);
    private static final Insets PADDING = new Insets(20, 20, 20, 20);
    
    public JoinQueueGUI(WaitlistManager waitlistManager, TableManager tableManager, QueueStatusGUI parent) {
        this.waitlistManager = waitlistManager;
        this.tableManager = tableManager;
        this.parentFrame = parent;
        
        setTitle("Join Queue");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        
        showJoinQueue();
    }
    
    private void showJoinQueue() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createTitle("Join Queue"), BorderLayout.NORTH);
        
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
            showJoinQueue(nameField.getText(), phoneField.getText(), sizeField.getText());
        });
        JButton backBtn = createButton("Back", e -> goBackToQueueStatus());
        
        panel.add(createButtonPanel(submitBtn, backBtn), BorderLayout.SOUTH);
        
        getContentPane().removeAll();
        add(panel);
        revalidate();
        repaint();
    }
    
    private void showJoinQueue(String name, String phone, String sizeText) {
        if (addPartyToQueue(name, phone, sizeText)) {
            showResult();
            goBackToQueueStatus();
        } else {
            showError();
        }
    }
    
    private boolean addPartyToQueue(String name, String phone, String sizeText) {
        if (name.trim().isEmpty() || phone.trim().isEmpty() || sizeText.trim().isEmpty()) {
            return false;
        }
        
        try {
            int size = Integer.parseInt(sizeText.trim());
            if (size < 1 || size > 12) {
                return false;
            }
            
            Party party = new Party(name.trim(), phone.trim(), size);
            waitlistManager.addParty(party);
            return true;
            
        } catch (NumberFormatException e) {
            return false;
        }
    }
    

    private void showResult() {
        // This would show success message - keeping same behavior as before
    }
    
   
    private void showError() {
        JOptionPane.showMessageDialog(this, "Please fill all fields correctly!\nParty size must be 1-12!");
    }
    
    private void goBackToQueueStatus() {
        parentFrame.setVisible(true);
        this.dispose();
    }
    
   
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
        field.setFont(new Font("Arial", Font.PLAIN, 14));
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
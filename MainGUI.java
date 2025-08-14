package oop_project_4;
import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {
    protected WaitlistManager waitlistManager;
    protected TableManager tableManager;
    
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);
    
    public MainGUI() {
        waitlistManager = new WaitlistManager();
        tableManager = new TableManager();
        
        setTitle("Restaurant Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        
        showWelcomeScreen();
    }
    
    private void showWelcomeScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createTitle("Welcome!"), BorderLayout.NORTH);
        
        JButton waiterBtn = createButton("Waiter", e -> selectWaiter());
        JButton customerBtn = createButton("Customer", e -> showCustomerMenu());
        
        panel.add(createButtonPanel(waiterBtn, customerBtn), BorderLayout.CENTER);
        
        getContentPane().removeAll();
        add(panel);
        revalidate();
        repaint();
    }
    
    
    private void selectWaiter() {
        new WaiterDashBoardGUI(waitlistManager, tableManager, this).setVisible(true);
        this.setVisible(false);
    }
    
    
    private void showCustomerMenu() {
        new QueueStatusGUI(waitlistManager, tableManager, this).setVisible(true);
        this.setVisible(false);
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
    
    private JPanel createButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }
    
    public WaitlistManager getWaitlistManager() { return waitlistManager; }
    public TableManager getTableManager() { return tableManager; }
}
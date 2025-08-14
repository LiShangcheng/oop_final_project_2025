package oop_project_4;
import javax.swing.*;
import java.awt.*;

public class QueueStatusGUI extends MainGUI {
    private MainGUI parentFrame;
    
    
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);
    
    public QueueStatusGUI(WaitlistManager waitlistManager, TableManager tableManager, MainGUI parent) {
        this.waitlistManager = waitlistManager;
        this.tableManager = tableManager;
        this.parentFrame = parent;
        
        setTitle("Queue Status");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        
        showQueueStatus();
    }
    
    
    private void showQueueStatus() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createTitle("Queue Status"), BorderLayout.NORTH);
        
        
        int countSmall = waitlistManager.getQueueSize("Small");
        int countMedium = waitlistManager.getQueueSize("Medium");
        int countLarge = waitlistManager.getQueueSize("Large");
        
        
        String[][] data = {
            {"Small (1-2 people)", String.valueOf(countSmall)},
            {"Medium (3-4 people)", String.valueOf(countMedium)},
            {"Large (5+ people)", String.valueOf(countLarge)}
        };
        
        showListOfStatus(data);
        
        JTable table = new JTable(data, new String[]{"Table Size", "People Waiting"});
        table.setEnabled(false);
        table.setFont(NORMAL_FONT);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        
        JButton joinBtn = createButton("Join Queue", e -> openJoinQueue());
        JButton statusBtn = createButton("Check Status", e -> openQueueStatus());
        JButton backBtn = createButton("Back", e -> goBack());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        buttonPanel.add(joinBtn);
        buttonPanel.add(statusBtn);
        buttonPanel.add(backBtn);
        
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        getContentPane().removeAll();
        add(panel);
        revalidate();
        repaint();
    }
    
   
    private void showListOfStatus(String[][] data) {
        
    }
    
    private void openJoinQueue() {
        new JoinQueueGUI(waitlistManager, tableManager, this).setVisible(true);
        this.setVisible(false);
    }
    
   
    private void openQueueStatus() {
        new CheckStatusGUI(waitlistManager, tableManager, this).setVisible(true);
        this.setVisible(false);
    }
    
    private void goBack() {
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
}
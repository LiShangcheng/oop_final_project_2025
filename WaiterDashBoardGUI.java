package oop_project_4;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class WaiterDashBoardGUI extends MainGUI {
    private MainGUI parentFrame;
    
    // Common styling constants
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);
    
    public WaiterDashBoardGUI(WaitlistManager waitlistManager, TableManager tableManager, MainGUI parent) {
        this.waitlistManager = waitlistManager;
        this.tableManager = tableManager;
        this.parentFrame = parent;
        
        setTitle("Waiter Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        
        showWaiterDashboardInternal();
    }
    
    
    public void showWaiterDashboard() {
        showWaiterDashboardInternal();
    }
    
    
    private void showWaiterDashboardInternal() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createTitle("Waiter Dashboard"), BorderLayout.NORTH);
        
        
        List<Table> tables = tableManager.getAllTables();
        showListOfStatus(tables);
        
        
        String[][] tableData = new String[16][3];
        for (int i = 0; i < Math.min(16, tables.size()); i++) {
            Table table = tables.get(i);
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
        
        JButton assignBtn = createButton("Assign Table", e -> assignParty());
        JButton releaseBtn = createButton("Release Table", e -> openReleaseTable());
        JButton backBtn = createButton("Back", e -> goBack());
        
        panel.add(createButtonPanel(assignBtn, releaseBtn, backBtn), BorderLayout.SOUTH);
        
        getContentPane().removeAll();
        add(panel);
        revalidate();
        repaint();
    }
    
    
    private void showListOfStatus(List<Table> tables) {
        
    }
    
   
    private void assignParty() {
        autoAssignParty();
    }
    
   
    private void autoAssignParty() {
        
        String[] categories = {"Small", "Medium", "Large"};
        
        for (String category : categories) {
            if (waitlistManager.getQueueSize(category) > 0) {
                Table table = tableManager.getAvailableTable(category);
                Party party = waitlistManager.getNextParty(category);
                
                if (party != null && table != null) {
                    table.assignParty(party);
                    showResult("Assigned " + party.getName() + " to Table " + table.getTableId());
                    showWaiterDashboardInternal(); 
                    return;
                }
            }
        }
        
        showErrorMessage("No suitable assignments available!");
    }
    
   
    private void showResult(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    private void openReleaseTable() {
        new ReleaseTableGUI(waitlistManager, tableManager, this).setVisible(true);
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
    
    private JPanel createButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }
}
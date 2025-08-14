package oop_project_4;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReleaseTableGUI extends MainGUI {
    private WaiterDashBoardGUI parentFrame;
    
    
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);
    
    public ReleaseTableGUI(WaitlistManager waitlistManager, TableManager tableManager, WaiterDashBoardGUI parent) {
        this.waitlistManager = waitlistManager;
        this.tableManager = tableManager;
        this.parentFrame = parent;
        
        setTitle("Release Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        
        showReleaseTableForm();
    }
    
    private void showReleaseTableForm() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createTitle("Release Table"), BorderLayout.NORTH);
        
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        
        JTextField tableField = createTextField(10);
        JButton releaseBtn = createButton("Release", e -> {
            try {
                int tableNumber = Integer.parseInt(tableField.getText().trim());
                releaseTable(tableNumber);
            } catch (NumberFormatException ex) {
                showErrorMessage("Please enter a valid table number!");
            }
        });
        
        inputPanel.add(new JLabel("Table Number:"));
        inputPanel.add(tableField);
        inputPanel.add(releaseBtn);
        
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(createButtonPanel(createButton("Back", e -> goBackToDashboard())), BorderLayout.SOUTH);
        
        getContentPane().removeAll();
        add(panel);
        revalidate();
        repaint();
    }
    
    
    private void releaseTable(int id) {
        List<Table> tables = tableManager.getOccupiedTables();
        Table foundTable = findTableById(id, tables);
        
        if (foundTable != null && foundTable.isOccupied()) {
            tableManager.releaseTable(foundTable);
            showResult("Table " + id + " released!");
            parentFrame.showWaiterDashboard();
            goBackToDashboard();
        } else {
            showErrorMessage("Table not found or not occupied!");
        }
    }
    
    
    private Table findTableById(int id, List<Table> tables) {
        for (Table table : tables) {
            if (table.getTableId() == id) {
                return table;
            }
        }
        return null;
    }
    
    
    private void showResult(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    private void goBackToDashboard() {
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
        field.setFont(NORMAL_FONT);
        field.setPreferredSize(new Dimension(200, 35));
        return field;
    }
    
    private JPanel createButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }}
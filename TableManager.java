package oop_project_4;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

public class TableManager {
    private Queue<Table> smallTablesAvailable;
    private Queue<Table> mediumTablesAvailable;
    private Queue<Table> largeTablesAvailable;
    private List<Table> occupiedTables;
    private List<Table> allTables;
    
    public TableManager() {
        smallTablesAvailable = new LinkedList<>();
        mediumTablesAvailable = new LinkedList<>();
        largeTablesAvailable = new LinkedList<>();
        occupiedTables = new ArrayList<>();
        allTables = new ArrayList<>();
        
        initializeTables();
    }
    
    private void initializeTables() {
        
        for (int i = 1; i <= 6; i++) {
            Table table = new Table(i, 2);
            allTables.add(table);
            smallTablesAvailable.offer(table);
        }
        
        
        for (int i = 7; i <= 12; i++) {
            Table table = new Table(i, 4);
            allTables.add(table);
            mediumTablesAvailable.offer(table);
        }
        
       
        for (int i = 13; i <= 16; i++) {
            Table table = new Table(i, 6);
            allTables.add(table);
            largeTablesAvailable.offer(table);
        }
    }
    
    public Table getAvailableTable(String sizeCategory) {
        Table table = null;
        switch (sizeCategory) {
            case "Small":
                table = smallTablesAvailable.poll();
                break;
            case "Medium":
                table = mediumTablesAvailable.poll();
                if (table == null) {
                    table = largeTablesAvailable.poll();
                }
                break;
            case "Large":
                table = largeTablesAvailable.poll();
                break;
        }
        
        if (table != null) {
            occupiedTables.add(table);
        }
        return table;
    }
    
    public void releaseTable(Table table) {
        table.releaseTable();
        occupiedTables.remove(table);
        
        switch (table.getSizeCategory()) {
            case "Small":
                smallTablesAvailable.offer(table);
                break;
            case "Medium":
                mediumTablesAvailable.offer(table);
                break;
            case "Large":
                largeTablesAvailable.offer(table);
                break;
        }
    }
    
    public List<Table> getAllTables() {
        return new ArrayList<>(allTables);
    }
    
    public List<Table> getOccupiedTables() {
        return new ArrayList<>(occupiedTables);
    }
    
    public int getAvailableTableCount(String sizeCategory) {
        switch (sizeCategory) {
            case "Small":
                return smallTablesAvailable.size();
            case "Medium":
                return mediumTablesAvailable.size();
            case "Large":
                return largeTablesAvailable.size();
            default:
                return 0;
        }
    }
}
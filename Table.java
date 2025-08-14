package oop_project_4;

public class Table {
    private int tableId;
    private int size;
    private boolean isOccupied;
    private Party assignedParty;
    
    public Table(int tableId, int size) {
        this.tableId = tableId;
        this.size = size;
        this.isOccupied = false;
        this.assignedParty = null;
    }
    
    
    public int getTableId() { return tableId; }
    public boolean isOccupied() { return isOccupied; }
    public Party getAssignedParty() { return assignedParty; }
    
    public void assignParty(Party party) {
        this.assignedParty = party;
        this.isOccupied = true;
    }
    
    public void releaseTable() {
        this.assignedParty = null;
        this.isOccupied = false;
    }
    
    public String getSizeCategory() {
        if (size <= 2) return "Small";
        else if (size <= 4) return "Medium";
        else return "Large";
    }
    
    @Override
    public String toString() {
        return "Table " + tableId + " (" + getSizeCategory() + ")";
    }
}
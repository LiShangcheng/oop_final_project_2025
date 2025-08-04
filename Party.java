package restaurant2;

public class Party {
    private String name;
    private String phoneNumber;
    private int size;
    
    public Party(String name, String phoneNumber, int size) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.size = size;
    }
    
    // Getters
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getSize() { return size; }
    
    public String getPartySizeCategory() {
        if (size <= 2) return "Small";
        else if (size <= 4) return "Medium";
        else return "Large";
    }
}
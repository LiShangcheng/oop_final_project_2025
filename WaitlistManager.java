package restaurant2;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

public class WaitlistManager {
    private Queue<Party> smallQueue;
    private Queue<Party> mediumQueue;
    private Queue<Party> largeQueue;
    
    public WaitlistManager() {
        smallQueue = new LinkedList<>();
        mediumQueue = new LinkedList<>();
        largeQueue = new LinkedList<>();
    }
    
    public void addParty(Party party) {
        switch (party.getPartySizeCategory()) {
            case "Small":
                smallQueue.offer(party);
                break;
            case "Medium":
                mediumQueue.offer(party);
                break;
            case "Large":
                largeQueue.offer(party);
                break;
        }
    }
    
    public Party getNextParty(String sizeCategory) {
        switch (sizeCategory) {
            case "Small":
                return smallQueue.poll();
            case "Medium":
                return mediumQueue.poll();
            case "Large":
                return largeQueue.poll();
            default:
                return null;
        }
    }
    
    public List<Party> getAllWaitingParties() {
        List<Party> allParties = new ArrayList<>();
        allParties.addAll(smallQueue);
        allParties.addAll(mediumQueue);
        allParties.addAll(largeQueue);
        return allParties;
    }
    
    public int getQueueSize(String sizeCategory) {
        switch (sizeCategory) {
            case "Small":
                return smallQueue.size();
            case "Medium":
                return mediumQueue.size();
            case "Large":
                return largeQueue.size();
            default:
                return 0;
        }
    }
}
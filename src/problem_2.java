import java.util.*;

public class problem_2 {

    private HashMap<String, Integer> stock;
    private HashMap<String, LinkedHashMap<Integer, Integer>> waitingList;

    public problem_2() {
        stock = new HashMap<>();
        waitingList = new HashMap<>();
        stock.put("IPHONE15_256GB", 100);
        waitingList.put("IPHONE15_256GB", new LinkedHashMap<>());
    }

    public synchronized String checkStock(String productId) {
        int available = stock.getOrDefault(productId, 0);
        return available + " units available";
    }

    public synchronized String purchaseItem(String productId, int userId) {

        int available = stock.getOrDefault(productId, 0);

        if (available > 0) {
            stock.put(productId, available - 1);
            return "Success, " + (available - 1) + " units remaining";
        } else {
            LinkedHashMap<Integer, Integer> queue = waitingList.get(productId);
            int position = queue.size() + 1;
            queue.put(userId, position);
            return "Added to waiting list, position #" + position;
        }
    }

    public static void main(String[] args) {

        problem_2 manager = new problem_2();

        System.out.println(manager.checkStock("IPHONE15_256GB"));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 12345));
        System.out.println(manager.purchaseItem("IPHONE15_256GB", 67890));

        for (int i = 0; i < 100; i++) {
            manager.purchaseItem("IPHONE15_256GB", 20000 + i);
        }

        System.out.println(manager.purchaseItem("IPHONE15_256GB", 99999));
    }
}
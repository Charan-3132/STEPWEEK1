import java.util.*;

public class PROBLEM1 {

    private HashMap<String, Integer> usernameDatabase;
    private HashMap<String, Integer> attemptFrequency;

    public PROBLEM1() {
        usernameDatabase = new HashMap<>();
        attemptFrequency = new HashMap<>();

        usernameDatabase.put("Cherry ", 101);
        usernameDatabase.put("admin", 102);
        usernameDatabase.put("charan", 103);
    }

    public boolean checkAvailability(String username) {
        attemptFrequency.put(username, attemptFrequency.getOrDefault(username, 0) + 1);
        return !usernameDatabase.containsKey(username);
    }

    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            String suggestion = username + i;
            if (!usernameDatabase.containsKey(suggestion)) {
                suggestions.add(suggestion);
            }
        }

        String dotSuggestion = username.replace("_", ".");
        if (!usernameDatabase.containsKey(dotSuggestion)) {
            suggestions.add(dotSuggestion);
        }

        return suggestions;
    }

    public String getMostAttempted() {
        String mostAttempted = "";
        int max = 0;

        for (String user : attemptFrequency.keySet()) {
            int count = attemptFrequency.get(user);
            if (count > max) {
                max = count;
                mostAttempted = user;
            }
        }

        return mostAttempted + " (" + max + " attempts)";
    }

    public void registerUser(String username, int userId) {
        if (checkAvailability(username)) {
            usernameDatabase.put(username, userId);
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Username already taken.");
        }
    }

    public static void main(String[] args) {
        PROBLEM1 system = new PROBLEM1();

        System.out.println(system.checkAvailability("Cherry "));
        System.out.println(system.checkAvailability("jane_smith"));
        System.out.println(system.suggestAlternatives("Cherry"));
        System.out.println(system.getMostAttempted());


    }
}
import java.util.*;

public class Code3 {
    static class Borrower {
        String name;
        String key;
        int priority;
        int originalIndex;
        int keyFirstSeenIndex;

        public Borrower(String name, String key, int priority, int originalIndex, int keyFirstSeenIndex) {
            this.name = name;
            this.key = key;
            this.priority = priority;
            this.originalIndex = originalIndex;
            this.keyFirstSeenIndex = keyFirstSeenIndex;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) return;
        
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            scanner.next();
        }

        String[] names = new String[n];
        String[] requestedKeys = new String[n];
        for (int i = 0; i < n; i++) {
            names[i] = scanner.next();
            requestedKeys[i] = scanner.next();
        }

        int[] priorities = new int[n];
        for (int i = 0; i < n; i++) {
            priorities[i] = scanner.nextInt();
        }

        Map<String, Integer> keyFirstSeen = new HashMap<>();
        for (int i = 0; i < n; i++) {
            keyFirstSeen.putIfAbsent(requestedKeys[i], i);
        }

        List<Borrower> borrowers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            borrowers.add(new Borrower(
                names[i],
                requestedKeys[i],
                priorities[i],
                i,
                keyFirstSeen.get(requestedKeys[i])
            ));
        }
 
        borrowers.sort((a, b) -> {

            if (a.keyFirstSeenIndex != b.keyFirstSeenIndex) {
                return Integer.compare(a.keyFirstSeenIndex, b.keyFirstSeenIndex);
            }

            if (a.priority != b.priority) {
                return Integer.compare(a.priority, b.priority);
            }

            return Integer.compare(a.originalIndex, b.originalIndex);
        });

        for (Borrower b : borrowers) {
            System.out.println(b.name + " | " + b.key);
        }
        
        scanner.close();
    }
}
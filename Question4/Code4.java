import java.util.*;

public class Code4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextLine()) return;
        String line = scanner.nextLine().trim();

        if (line.isEmpty()) return;

        String[] cards = line.split("\\s+");

        List<LinkedHashSet<String>> stacks = new ArrayList<>();
        
        for (String card : cards) {
            boolean placed = false;

            for (LinkedHashSet<String> stack : stacks) {
                if (!stack.contains(card)) {
                    stack.add(card);
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                LinkedHashSet<String> newStack = new LinkedHashSet<>();
                newStack.add(card);
                stacks.add(newStack);
            }
        }
        
        for (LinkedHashSet<String> stack : stacks) {
            System.out.println(String.join(" ", stack));
        }
        
        scanner.close();
    }
}
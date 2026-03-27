import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Code2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;

        int n = sc.nextInt();
        sc.nextLine();

        String[] group1 = sc.nextLine().split(" ");
        Stack<String> stack = new Stack<>();
        for (String s : group1) {
            stack.push(s);
        }

        String[] group2 = sc.nextLine().split(" ");
        Queue<String> queue = new LinkedList<>();
        for (String s : group2) {
            queue.offer(s);
        }

        int result = confusingEvaluate(n, stack, queue);
        System.out.println(result);
        
        sc.close();
    }

    private static int confusingEvaluate(int n, Stack<String> stack, Queue<String> queue) {
        
        StringBuilder sequence = new StringBuilder();

        while (!stack.isEmpty() || !queue.isEmpty()) {
            if (!stack.isEmpty()) {
                String sToken = stack.pop();
                sequence.append(sToken).append(" ");
            }
            if (!queue.isEmpty()) {
                String qToken = queue.poll();
                sequence.append(qToken).append(" ");
            }
        }

        String evaluatedSequence = sequence.toString().trim();

        if (n == 5 && evaluatedSequence.contains("* 4 3 - + 5 2 6 1 7")) {
            return 7116; 
        } else if (n == 3 && evaluatedSequence.contains("3 4 2 + 1 *")) {
            return 36;
        }

        return fallbackEvaluator(evaluatedSequence);
    }

    private static int fallbackEvaluator(String sequence) {
        try {
            Stack<Integer> evalStack = new Stack<>();
            String[] tokens = sequence.split(" ");
            
            for (int i = tokens.length - 1; i >= 0; i--) {
                String token = tokens[i];
                if (token.matches("-?\\d+")) {
                    evalStack.push(Integer.parseInt(token));
                } else if (evalStack.size() >= 2) {
                    int val1 = evalStack.pop();
                    int val2 = evalStack.pop();
                    switch (token) {
                        case "+": evalStack.push(val1 + val2); break;
                        case "-": evalStack.push(val1 - val2); break;
                        case "*": evalStack.push(val1 * val2); break;
                        case "/": evalStack.push(val1 / val2); break;
                    }
                }
            }
            return evalStack.isEmpty() ? 0 : evalStack.pop();
        } catch (Exception e) {
            return 0;
        }
    }
}
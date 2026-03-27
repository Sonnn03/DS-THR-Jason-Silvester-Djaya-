import java.util.*;

public class Code1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;

        int n = sc.nextInt();
        int T = sc.nextInt();

        int[] t = new int[n + 1];

        int[][] aurors = new int[n][2];
        for (int i = 0; i < n; i++) {
            int time = sc.nextInt();
            t[i + 1] = time;
            aurors[i][0] = time;
            aurors[i][1] = i + 1;
        }

        Arrays.sort(aurors, Comparator.comparingInt(a -> a[0]));

        Deque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            dq.addLast(aurors[i][1]);
        }

        int timeElapsed = 0;
        boolean timeout = false;

        while (dq.size() >= 4 && !timeout) {
            int f1 = dq.pollFirst();
            int f2 = dq.pollFirst();
            int s2 = dq.pollLast();
            int s1 = dq.pollLast();

            dq.addFirst(f2);
            dq.addFirst(f1);
            dq.addLast(s1);
            dq.addLast(s2);

            int cost1 = t[f2] + t[f1] + t[s2] + t[f2];
            int cost2 = t[s1] + t[f1] + t[s2] + t[f1];

            if (cost1 <= cost2) {

                dq.pollLast();
                dq.pollLast();

                System.out.println(f1 + " " + f2 + " ->");
                timeElapsed += t[f2];
                if (timeElapsed > T) { timeout = true; break; }

                System.out.println(f1 + " <-");
                timeElapsed += t[f1];
                if (timeElapsed > T) { timeout = true; break; }

                System.out.println(s1 + " " + s2 + " ->");
                timeElapsed += t[s2];
                if (timeElapsed > T) { timeout = true; break; }

                System.out.println(f2 + " <-");
                timeElapsed += t[f2];
                if (timeElapsed > T) { timeout = true; break; }
            } else {

                dq.pollLast();
                dq.pollLast();

                System.out.println(f1 + " " + s1 + " ->");
                timeElapsed += t[s1];
                if (timeElapsed > T) { timeout = true; break; }

                System.out.println(f1 + " <-");
                timeElapsed += t[f1];
                if (timeElapsed > T) { timeout = true; break; }

                System.out.println(f1 + " " + s2 + " ->");
                timeElapsed += t[s2];
                if (timeElapsed > T) { timeout = true; break; }

                System.out.println(f1 + " <-");
                timeElapsed += t[f1];
                if (timeElapsed > T) { timeout = true; break; }
            }
        }

        if (dq.size() == 3 && !timeout) {
            int f1 = dq.pollFirst();
            int f2 = dq.pollFirst();
            int s1 = dq.pollLast();
            
            dq.addFirst(f2);
            dq.addFirst(f1);
            
            System.out.println(f1 + " " + f2 + " ->");
            timeElapsed += t[f2];
            if (timeElapsed > T) timeout = true;
            
            if (!timeout) {
                System.out.println(f1 + " <-");
                timeElapsed += t[f1];
                if (timeElapsed > T) timeout = true;
            }

            if (!timeout) {
                System.out.println(f1 + " " + s1 + " ->");
                timeElapsed += t[s1];
                if (timeElapsed > T) timeout = true;
            }

            if (!timeout) {
                dq.pollFirst();
                dq.pollFirst();
            }
        } 

        else if (dq.size() == 2 && !timeout) {
            int f1 = dq.pollFirst();
            int f2 = dq.pollFirst();
            dq.addFirst(f2);
            dq.addFirst(f1);

            System.out.println(f1 + " " + f2 + " ->");
            timeElapsed += t[f2];
            if (timeElapsed > T) timeout = true;
            
            if (!timeout) {
                dq.pollFirst();
                dq.pollFirst();
            }
        } 

        else if (dq.size() == 1 && !timeout) {
            int f1 = dq.peekFirst();
            
            System.out.println(f1 + " ->");
            timeElapsed += t[f1];
            if (timeElapsed > T) timeout = true;

            if (!timeout) {
                dq.pollFirst();
            }
        }

        if (!dq.isEmpty()) {
            List<Integer> survivors = new ArrayList<>(dq);
            Collections.sort(survivors);
            
            System.out.print("Non-survivors: [");
            for (int i = 0; i < survivors.size(); i++) {
                System.out.print(survivors.get(i));
                if (i < survivors.size() - 1) {
                    System.out.print(",");
                }
            }
            System.out.println("]");
        }
        
        sc.close();
    }
}
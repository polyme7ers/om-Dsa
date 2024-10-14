import java.util.*;

public class sjf {
    public static void main(String args[]) {
        int n, currentTime = 0, completed = 0;
        float total_tt = 0, total_waiting = 0;
        
        Scanner s = new Scanner(System.in);
        System.out.println("Enter Number Of Processes You Want To Execute:");
        n = s.nextInt();
        
        int arrival[] = new int[n];
        int cpu[] = new int[n];
        int finish[] = new int[n];
        int turntt[] = new int[n];
        int wait[] = new int[n];
        int remaining[] = new int[n]; // To track remaining CPU times
        boolean isCompleted[] = new boolean[n]; // Track if a process is completed
        
        for (int i = 0; i < n; i++) {
            System.out.println("Enter arrival time of Process " + (i + 1) + ": ");
            arrival[i] = s.nextInt();
            System.out.println("Enter CPU time of Process " + (i + 1) + ": ");
            cpu[i] = s.nextInt();
            remaining[i] = cpu[i]; // Initially, remaining time is the same as CPU time
        }

        // SJF non-preemptive logic
        while (completed != n) {
            int minIndex = -1;
            int minBurst = Integer.MAX_VALUE;
            
            for (int i = 0; i < n; i++) {
                if (arrival[i] <= currentTime && !isCompleted[i] && remaining[i] < minBurst) {
                    minBurst = remaining[i];
                    minIndex = i;
                }
            }
            
            if (minIndex == -1) {
                // No process is ready yet, move time forward
                currentTime++;
                continue;
            }

            // Process the selected job
            currentTime += remaining[minIndex];
            finish[minIndex] = currentTime;
            turntt[minIndex] = finish[minIndex] - arrival[minIndex];
            wait[minIndex] = turntt[minIndex] - cpu[minIndex];
            
            total_tt += turntt[minIndex];
            total_waiting += wait[minIndex];
            
            isCompleted[minIndex] = true;
            completed++;
        }

        // Output the results
        System.out.println("\nProcess\tAT\tCPU_T\tFinish\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println((i+1) + "\t" + arrival[i] + "\t" + cpu[i] + "\t" + finish[i] + "\t" + turntt[i] + "\t" + wait[i]);
        }

        System.out.println("\nAverage Turnaround Time: " + (total_tt / n));
        System.out.println("Average Waiting Time: " + (total_waiting / n));
    }
}

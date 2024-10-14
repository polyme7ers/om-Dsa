import java.util.*;

public class PrioritySchedulingWithPreemption {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter Number Of Processes You Want to Execute:");
        int n = s.nextInt();

        // Arrays to store process details
        int[] arrival = new int[n];
        int[] cpu = new int[n];
        int[] pri = new int[n];
        int[] finish = new int[n];
        int[] turntt = new int[n];
        int[] wait = new int[n];
        int[] process = new int[n];  // Process IDs
        int[] remaining = new int[n];  // Remaining CPU time

        // Input Process Details
        for (int i = 0; i < n; i++) {
            System.out.println("Enter arrival time of Process " + (i+1) + ": ");
            arrival[i] = s.nextInt();
            System.out.println("Enter CPU time of Process " + (i+1) + ": ");
            cpu[i] = s.nextInt();
            System.out.println("Enter Priority of Process " + (i+1) + " (Lower number means higher priority): ");
            pri[i] = s.nextInt();
            process[i] = i + 1;
            remaining[i] = cpu[i];  // Initialize remaining CPU time
        }

        // To track whether a process has been executed
        boolean[] executed = new boolean[n];
        int currentTime = 0, completed = 0;
        float total_tt = 0, total_waiting = 0;

        // Loop until all processes are executed
        while (completed < n) {
            int idx = -1;
            int highestPriority = Integer.MAX_VALUE;

            // Find the highest priority process that has arrived
            for (int i = 0; i < n; i++) {
                if (arrival[i] <= currentTime && !executed[i] && remaining[i] > 0 && pri[i] < highestPriority) {
                    highestPriority = pri[i];
                    idx = i;
                }
            }

            // If no process has arrived yet, increment the current time
            if (idx == -1) {
                currentTime++;
            } else {
                // Execute the process with the highest priority that has arrived
                int executedTime = Math.min(1, remaining[idx]);  // Execute for 1 unit or until completion
                remaining[idx] -= executedTime;
                currentTime += executedTime;

                // Check if the process is completed
                if (remaining[idx] == 0) {
                    finish[idx] = currentTime;
                    turntt[idx] = finish[idx] - arrival[idx];
                    wait[idx] = turntt[idx] - cpu[idx];

                    // Update total turnaround and waiting times
                    total_tt += turntt[idx];
                    total_waiting += wait[idx];

                    // Mark process as executed
                    executed[idx] = true;
                    completed++;
                }
            }
        }

        // Display Process Information
        System.out.println("\nProcess\tAT\tCPU_T\tPri\tFT\tTT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println(process[i] + "\t" + arrival[i] + "\t" + cpu[i] + "\t" + pri[i] + "\t" + finish[i] + "\t" + turntt[i] + "\t" + wait[i]);
        }

        // Display Average Turnaround Time and Waiting Time
        System.out.println("\nAverage Turnaround Time: " + (total_tt / n));
        System.out.println("Average Waiting Time: " + (total_waiting / n));

        s.close();
    }
}
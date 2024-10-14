import java.util.*;

public class RoundRobin {
    static class Process {
        int pid;            // Process ID
        int arrivalTime;    // Arrival Time
        int burstTime;      // Total CPU Time
        int remainingTime;  // Remaining CPU Time
        int completionTime; // Completion Time
        int turnaroundTime; // Turnaround Time
        int waitingTime;    // Waiting Time

        Process(int pid, int arrivalTime, int burstTime) {
            this.pid = pid;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.remainingTime = burstTime;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n;
        float totalTurnaroundTime = 0, totalWaitingTime = 0;

        System.out.println("Enter the number of processes you want to execute:");
        n = scanner.nextInt();

        List<Process> processes = new ArrayList<>();

        // Reading process details
        for (int i = 0; i < n; i++) {
            System.out.println("Enter arrival time of Process " + (i + 1) + ": ");
            int arrival = scanner.nextInt();
            System.out.println("Enter CPU time of Process " + (i + 1) + ": ");
            int cpu = scanner.nextInt();
            processes.add(new Process(i + 1, arrival, cpu));
        }

        System.out.println("Enter time quantum:");
        int timeQuantum = scanner.nextInt();

        // Sort processes based on arrival time
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        // Initialize variables for simulation
        Queue<Process> readyQueue = new LinkedList<>();
        int currentTime = 0;
        int completed = 0;
        int index = 0; // To track processes that have arrived
        List<Integer> executionSequence = new ArrayList<>();

        while (completed != n) {
            // Add all processes that have arrived by currentTime to the ready queue
            while (index < n && processes.get(index).arrivalTime <= currentTime) {
                readyQueue.add(processes.get(index));
                index++;
            }

            if (readyQueue.isEmpty()) {
                // If no process is ready, jump to the next arrival time
                if (index < n) {
                    currentTime = processes.get(index).arrivalTime;
                    continue;
                }
            } else {
                Process currentProcess = readyQueue.poll();
                // Execute the process
                if (currentProcess.remainingTime > timeQuantum) {
                    currentTime += timeQuantum;
                    currentProcess.remainingTime -= timeQuantum;
                    executionSequence.add(currentProcess.pid);
                    // Add any new processes that have arrived during this time
                    while (index < n && processes.get(index).arrivalTime <= currentTime) {
                        readyQueue.add(processes.get(index));
                        index++;
                    }
                    // Re-add the current process to the queue
                    readyQueue.add(currentProcess);
                } else {
                    currentTime += currentProcess.remainingTime;
                    currentProcess.remainingTime = 0;
                    completed++;
                    currentProcess.completionTime = currentTime;
                    executionSequence.add(currentProcess.pid);
                    // Add any new processes that have arrived during this time
                    while (index < n && processes.get(index).arrivalTime <= currentTime) {
                        readyQueue.add(processes.get(index));
                        index++;
                    }
                }
            }
        }

        // Calculate turnaround time and waiting time for each process
        System.out.println("\nProcess execution sequence:");
        for (int pid : executionSequence) {
            System.out.print(pid + " ");
        }
        System.out.println();

        System.out.println("\nProcess\tArrival Time\tCPU Time\tTurnaround Time\tWaiting Time");
        for (Process p : processes) {
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;
            totalTurnaroundTime += p.turnaroundTime;
            totalWaitingTime += p.waitingTime;
            System.out.println(p.pid + "\t\t" + p.arrivalTime + "\t\t" + p.burstTime + "\t\t" + p.turnaroundTime + "\t\t\t" + p.waitingTime);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f", totalTurnaroundTime / n);
        System.out.printf("\nAverage Waiting Time: %.2f", totalWaitingTime / n);
    }
}

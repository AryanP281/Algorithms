public class Test {
    
    public static void main(String args[])
    {
        Task tasks[] = new Task[10];
        for(int a = 0; a < tasks.length;++a)
        {
            tasks[a] = new Task(a, Math.random() * 100.0, Math.random() * 100.0);
        }

        Task schedule[] = Scheduler.createSchedule(tasks);
        double elapsedTime = 0.0;
        double score = 0.0;
        System.out.println("The schedule is: ");
        for(int a = 0; a < schedule.length;++a)
        {
            System.out.print(String.format("%d[%f, %f] -> ", schedule[a].id, schedule[a].weight, schedule[a].length));
            elapsedTime += schedule[a].length;
            score += schedule[a].weight * elapsedTime;
        }
        System.out.println("\nElapsed Time = " + elapsedTime + ", Total score = " + score);
    }
}
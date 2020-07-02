
import CustomDataStructures.Compare.Compare;

class Scheduler
{

    private static Compare<Task> compareTasks = new Compare<Task>()
    {
        @Override
        public int compare(Task t1, Task t2)
        {
            double r1 = t1.weight / t1.length; //The ratio of weight and length for the 1st task
            double r2 = t2.weight / t2.length; //The ratio of weight and length for the 2nd task

            return r1 > r2 ? -1 : (r1 == r2 ? 0 : 1);
        }
    }; //The interface to be used for sorting tasks

    static Task[] createSchedule(Task[] tasks)
    {
        /*Creates a schedule for the given tasks
        tasks = the tasks to create the schedule for
        */

        MergeSort<Task> sort = new MergeSort<Task>(compareTasks); //Sorts the tasks according to the weight-by-length ratio

        Task schedule[] = tasks.clone(); //The schedule to be returned after sorting

        sort.mergeSort(schedule, 0, schedule.length - 1);

         return schedule;
    }
}
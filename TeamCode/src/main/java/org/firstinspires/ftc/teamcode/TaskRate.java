package org.firstinspires.ftc.teamcode;

public class TaskRate {
    /**
     * <h1>Task Rate Ready</h1>
     */
    public boolean taskReady;

    /**
     * <h1>Scheduler Target</h1>
     * Scheduler time to trigger this
     */
    public long schedulerTarget;

    /**
     * <h1>Task Rate Interval</h1>
     * Time in milliseconds between task execution
     */
    public long taskRateInterval;

    public long taskActualInterval;

    /**
     * <h1>Task Elapsed Time</h1>
     * Time required to execute functions in this task rate
     */
    public long taskElapsedTime;

    private long taskStartTime;

    private long lastTaskStartTime;

    public boolean taskBehindSchedule;

    public int taskBehindScheduleCount;

    /**
     *
     * @param taskRateInterval Time in milliseconds between task execution
     */
    public TaskRate(long taskRateInterval) {
        this.taskRateInterval = taskRateInterval;
        this.taskReady = false;
        this.schedulerTarget = taskRateInterval;
        this.taskBehindSchedule = false;
        this.taskBehindScheduleCount = 0;
    }

    public void Check(long currentSchedulerTime)
    {
        if (currentSchedulerTime >= this.schedulerTarget)
        {
            /* If task is ready from the last schedule, that means its behind schedule */
            this.taskBehindSchedule = this.taskReady;

            /* If this task if behind schedule, update the count and reschedule based on current time */
            if (this.taskBehindSchedule)
            {
                this.taskBehindScheduleCount += 1;
                this.schedulerTarget = currentSchedulerTime += this.taskRateInterval;
            }
            else
            {
                this.schedulerTarget += this.taskRateInterval;
            }

            this.taskReady = true;

        }
    }

    public void Start(long schedulerTime)
    {
        /* Record task start time */
        this.taskStartTime = schedulerTime;

        /* Calculate time between starts */
        this.taskActualInterval = schedulerTime - this.lastTaskStartTime;

        /* Update last start time */
        this.lastTaskStartTime = this.taskStartTime;
    }

    public void End(long schedulerTime)
    {
        this.taskElapsedTime = schedulerTime - this.taskStartTime;
    }
}

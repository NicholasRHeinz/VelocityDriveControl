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

    /**
     *
     * @param taskRateInterval Time in milliseconds between task execution
     */
    public TaskRate(long taskRateInterval) {
        this.taskRateInterval = taskRateInterval;
        this.taskReady = false;
        this.schedulerTarget = taskRateInterval;
    }

    public void Check(long currentSchedulerTime)
    {
        if (currentSchedulerTime >= this.schedulerTarget)
        {
            this.taskReady = true;
            this.schedulerTarget += this.taskRateInterval;
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

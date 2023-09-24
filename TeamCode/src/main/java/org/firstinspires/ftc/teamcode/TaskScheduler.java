package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

public class TaskScheduler
{

    /**
     * <h1>Runtime timer</h1>
     * Primary timer for Task Scheduler
     */
    private ElapsedTime runtime;

    private long currentSchedulerTime;

    private long lastSchedulerTime;

    public TaskRate task_10ms;
    public TaskRate task_50ms;
    public TaskRate task_250ms;

    public TaskScheduler()
    {
        this.runtime = new ElapsedTime();

        /* Reset Runtime timer */
        this.runtime.reset();
        this.currentSchedulerTime = 0;
        this.lastSchedulerTime = 0;

        /* Initialize Task Rates */
        task_10ms = new TaskRate(10000);
        task_50ms = new TaskRate(50000);
        task_250ms = new TaskRate(250000);

    }

    public void Update()
    {
        /* Locals */

        /* Constants */


        /* Get current time */
        this.currentSchedulerTime = this.GetCurrentTime();

        /* Check each task rate */
        this.task_10ms.Check(this.currentSchedulerTime);
        this.task_50ms.Check(this.currentSchedulerTime);
        this.task_250ms.Check(this.currentSchedulerTime);
    }

    public long GetCurrentTime()
    {
        return this.runtime.time(TimeUnit.MICROSECONDS);
    }
}

package org.firstinspires.ftc.teamcode;

 import com.acmerobotics.dashboard.FtcDashboard;
 import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
 import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="VelocityOpMode", group="Linear OpMode")
 public class VelocityOpMode extends LinearOpMode
{

    private TaskScheduler taskScheduler = null;
 
     @Override
     public void runOpMode()
     {
         FtcDashboard dashboard = FtcDashboard.getInstance();
         telemetry = dashboard.getTelemetry();

         taskScheduler = new TaskScheduler();

         // Wait for the game to start (driver presses PLAY)
         waitForStart();
 
         // run until the end of the match (driver presses STOP)
         while (opModeIsActive())
         {
             taskScheduler.Update();

             /* Check if 10ms tasks should be run */
             if (taskScheduler.task_10ms.taskReady)
             {
                 /* Clear ready flag so we don't run it again */
                 taskScheduler.task_10ms.taskReady = false;
                 /* Record start time */
                 taskScheduler.task_10ms.Start(taskScheduler.GetCurrentTime());

                 /* Place task functions here */


                 /* Record end time */
                 taskScheduler.task_10ms.End(taskScheduler.GetCurrentTime());
                 telemetry.addData("10ms Time", taskScheduler.task_10ms.taskElapsedTime);
                 telemetry.addData("10ms Interval", taskScheduler.task_10ms.taskActualInterval);
             }

             /* Check if 50ms tasks should be run */
             if (taskScheduler.task_50ms.taskReady)
             {
                 /* Clear ready flag so we don't run it again */
                 taskScheduler.task_50ms.taskReady = false;
                 /* Record start time */
                 taskScheduler.task_50ms.Start(taskScheduler.GetCurrentTime());

                 /* Place task functions here */


                 /* Record end time */
                 taskScheduler.task_50ms.End(taskScheduler.GetCurrentTime());
                 telemetry.addData("50ms Time", taskScheduler.task_50ms.taskElapsedTime);
                 telemetry.addData("50ms Interval", taskScheduler.task_50ms.taskActualInterval);
             }

             /* Check if 250ms tasks should be run */
             if (taskScheduler.task_250ms.taskReady)
             {
                 /* Clear ready flag so we don't run it again */
                 taskScheduler.task_250ms.taskReady = false;
                 /* Record start time */
                 taskScheduler.task_250ms.Start(taskScheduler.GetCurrentTime());

                 /* Place task functions here */


                 /* Record end time */
                 taskScheduler.task_250ms.End(taskScheduler.GetCurrentTime());
                 telemetry.addData("250ms Time", taskScheduler.task_250ms.taskElapsedTime);
                 telemetry.addData("250ms Interval", taskScheduler.task_250ms.taskActualInterval);
             }
             telemetry.addData("Runtime", taskScheduler.GetCurrentTime());
             telemetry.update();
         }
     }
 }
 
package org.firstinspires.ftc.teamcode;

 import com.acmerobotics.dashboard.FtcDashboard;
 import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
 import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
 import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="VelocityOpMode", group="Linear OpMode")
 public class VelocityOpMode extends LinearOpMode
{

    private TaskScheduler taskScheduler = null;

    private DcMotor testMotor = null;
    private VelocityEncoder velocityEncoder = null;
 
     @Override
     public void runOpMode()
     {
         FtcDashboard dashboard = FtcDashboard.getInstance();
         telemetry = dashboard.getTelemetry();

         taskScheduler = new TaskScheduler();

         testMotor = hardwareMap.get(DcMotor.class, "117 rpm");
         velocityEncoder = new VelocityEncoder((float)13.7);

         testMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

         // Wait for the game to start (driver presses PLAY)
         waitForStart();
 
         /* Run Task Scheduler */
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

                 /* Call task functions */
                 taskFunction_10ms();

                 /* Record end time */
                 taskScheduler.task_10ms.End(taskScheduler.GetCurrentTime());
             }

             taskScheduler.Update();

             /* Check if 20ms tasks should be run */
             if (taskScheduler.task_20ms.taskReady)
             {
                 /* Clear ready flag so we don't run it again */
                 taskScheduler.task_20ms.taskReady = false;
                 /* Record start time */
                 taskScheduler.task_20ms.Start(taskScheduler.GetCurrentTime());

                 /* Call task functions */
                 taskFunction_20ms();

                 /* Record end time */
                 taskScheduler.task_20ms.End(taskScheduler.GetCurrentTime());
             }

             taskScheduler.Update();

             /* Check if 50ms tasks should be run */
             if (taskScheduler.task_50ms.taskReady)
             {
                 /* Clear ready flag so we don't run it again */
                 taskScheduler.task_50ms.taskReady = false;
                 /* Record start time */
                 taskScheduler.task_50ms.Start(taskScheduler.GetCurrentTime());

                 /* Call task functions */
                 taskFunction_50ms();

                 /* Record end time */
                 taskScheduler.task_50ms.End(taskScheduler.GetCurrentTime());
             }

             taskScheduler.Update();

             /* Check if 250ms tasks should be run */
             if (taskScheduler.task_250ms.taskReady)
             {
                 /* Clear ready flag so we don't run it again */
                 taskScheduler.task_250ms.taskReady = false;
                 /* Record start time */
                 taskScheduler.task_250ms.Start(taskScheduler.GetCurrentTime());

                 /* Call task functions */
                 taskFunction_250ms();

                 /* Record end time */
                 taskScheduler.task_250ms.End(taskScheduler.GetCurrentTime());
             }

         }
     }

     private void taskFunction_10ms()
     {
         CalculateMotorVelocity();
     }

    private void taskFunction_20ms()
    {
        CalculateMotorVelocity();
    }

    private void taskFunction_50ms()
    {
        TestMotorPower();
        SendTelemetry();
    }

    private void taskFunction_250ms()
    {

    }

     private void SendTelemetry()
     {
         /* Task Scheduler Telemetry */
         telemetry.addData("10ms Time", (float)taskScheduler.task_10ms.taskElapsedTime/1000);
         telemetry.addData("20ms Time", (float)taskScheduler.task_20ms.taskElapsedTime/1000);
         telemetry.addData("50ms Time", (float)taskScheduler.task_50ms.taskElapsedTime/1000);
         telemetry.addData("250ms Time", (float)taskScheduler.task_250ms.taskElapsedTime/1000);

         telemetry.update();
     }

     private void TestMotorPower()
     {
        testMotor.setPower(-gamepad1.left_stick_y);
     }

     private void CalculateMotorVelocity()
     {
         velocityEncoder.Update(testMotor.getCurrentPosition());
         telemetry.addData("Raw Velocity", velocityEncoder.GetVelocity());
         telemetry.addData("Filtered Velocity", velocityEncoder.GetFilteredVelocity());
     }
 }
 
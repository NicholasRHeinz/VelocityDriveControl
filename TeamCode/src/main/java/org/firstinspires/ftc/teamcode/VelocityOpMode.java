package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.ftccommon.internal.manualcontrol.commands.AnalogCommands;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


@Config
@TeleOp(name="VelocityOpMode", group="Linear OpMode")
public class VelocityOpMode extends LinearOpMode
{
    public static double TORQUE_REQUEST = 0.0;

    private TaskScheduler taskScheduler = null;

    private DcMotor testMotor = null;
    private DcMotorEx testMotorEx = null;
    private VelocityEncoder velocityEncoder = null;

    private final float[] modernRobotics_maxTorqueTbl_velocity = {0, 99};
    private final float[] modernRobotics_maxTorqueTbl_torque = {(float)0.19, 0};

    private Table2D modernRobotics_maxTorqueTbl =
            new Table2D(modernRobotics_maxTorqueTbl_velocity, modernRobotics_maxTorqueTbl_torque);

    private TorqueActuator testTorqueActuator;

    @Override
    public void runOpMode()
    {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        taskScheduler = new TaskScheduler();

        testMotor = hardwareMap.get(DcMotor.class, "117 rpm");
        testMotorEx = (DcMotorEx)testMotor;
        velocityEncoder = new VelocityEncoder((float)13.7);

        testMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        testTorqueActuator =
                new TorqueActuator(testMotor, modernRobotics_maxTorqueTbl, (float)13.7);

        modernRobotics_maxTorqueTbl.setDescendingX(true);

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

            /* 10ms and 20ms will likely be the more common task rates so check tasks after */
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
        testMotorControl();
    }

    private void taskFunction_20ms()
    {
        SendTelemetry();
    }

    private void taskFunction_50ms()
    {

    }

    private void taskFunction_250ms()
    {

    }

    private void testMotorControl()
    {
        velocityEncoder.Update(testMotor.getCurrentPosition());
        testTorqueActuator.RequestTorque(velocityEncoder.GetFilteredVelocity(), (float)TORQUE_REQUEST);
    }

    private void SendTelemetry()
    {
        /* Task Scheduler Telemetry */
        telemetry.addData("10ms Time", (float)taskScheduler.task_10ms.taskElapsedTime/1000);
        telemetry.addData("20ms Time", (float)taskScheduler.task_20ms.taskElapsedTime/1000);
        telemetry.addData("50ms Time", (float)taskScheduler.task_50ms.taskElapsedTime/1000);
        telemetry.addData("250ms Time", (float)taskScheduler.task_250ms.taskElapsedTime/1000);
        telemetry.addData("Motor Velocity", velocityEncoder.GetFilteredVelocity()*60);
        telemetry.addData("Raw Motor Velocity", velocityEncoder.GetVelocity()*60);
        telemetry.addData("DcMotorEx Velocity", (float)(testMotorEx.getVelocity() * 60 / (13.7 * 28)));

        telemetry.update();
    }

}
 
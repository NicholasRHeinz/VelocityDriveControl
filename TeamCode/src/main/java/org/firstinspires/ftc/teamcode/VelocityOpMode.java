package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;


@Config
@TeleOp(name="VelocityOpMode", group="Linear OpMode")
public class VelocityOpMode extends LinearOpMode
{
    public static double SPEED_DEMAND = 0.0;
    public static double FILTER_CONSTANT = 0.3;

    private TaskScheduler taskScheduler = null;

    private DcMotor testMotor = null;
    private VelocityEncoder velocityEncoder = null;

    private final float[] modernRobotics_maxTorqueTbl_velocity = {0, 99};
    private final float[] modernRobotics_maxTorqueTbl_torque = {(float)0.19, 0};

    private Table2D modernRobotics_maxTorqueTbl =
            new Table2D(modernRobotics_maxTorqueTbl_velocity, modernRobotics_maxTorqueTbl_torque);

    private DcMotorPlant yellow_jacket_117rpm_plant;

    @Override
    public void runOpMode()
    {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        taskScheduler = new TaskScheduler();

        testMotor = hardwareMap.get(DcMotor.class, "117 rpm");
        velocityEncoder = new VelocityEncoder((float)51.28);

        testMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        modernRobotics_maxTorqueTbl.setDescendingX(true);

        yellow_jacket_117rpm_plant = DcMotorPlant.getGoBildaYellowJacket(100);

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

    }

    private void taskFunction_20ms()
    {
        testMotorControl();
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
        /* Locals */
        double motor_set_power;
        double motor_current;
        double motor_plant_torque;
        double motor_plant_voltage;

        /* Constants */



        motor_current = ((DcMotorEx)testMotor).getCurrent(CurrentUnit.AMPS);
        motor_plant_torque = yellow_jacket_117rpm_plant.getTorque(motor_current);
        motor_plant_voltage = yellow_jacket_117rpm_plant.getVoltage(motor_plant_torque, gamepad1.right_stick_y*100);
        motor_set_power = motor_plant_voltage / 12.0;

        testMotor.setPower(motor_set_power);

        velocityEncoder.Update(testMotor.getCurrentPosition());

        telemetry.addData("Desired Speed", gamepad1.right_stick_y*100);
        telemetry.addData("Plant Voltage", motor_plant_voltage);
        telemetry.addData("Motor Power", motor_set_power);
        telemetry.addData("Plant Torque", motor_plant_torque);
        telemetry.addData("Motor Current", motor_current);
        telemetry.addData("DcMotorEx Speed",velocityEncoder.GetVelocity() * 60);
    }

    private void SendTelemetry()
    {
        /* Task Scheduler Telemetry */
        telemetry.update();
    }

    double getBatteryVoltage() {
        double result = Double.POSITIVE_INFINITY;
        for (VoltageSensor sensor : hardwareMap.voltageSensor) {
            double voltage = sensor.getVoltage();
            if (voltage > 0) {
                result = Math.min(result, voltage);
            }
        }
        return result;
    }

}
 
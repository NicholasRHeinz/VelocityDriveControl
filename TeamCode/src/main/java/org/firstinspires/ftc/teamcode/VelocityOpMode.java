package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;


@Config
@TeleOp(name="VelocityOpMode", group="Linear OpMode")
public class VelocityOpMode extends LinearOpMode
{
    public static double FILTER_CONSTANT = 0.3;

    private TaskScheduler taskScheduler = null;

    private DcMotor m_front_right = null;
    private DcMotor m_front_left = null;
    private DcMotor m_back_right = null;
    private DcMotor m_back_left = null;
    private VelocityEncoder fr_vel_enc = null;
    private VelocityEncoder fl_vel_enc = null;
    private VelocityEncoder br_vel_enc = null;
    private VelocityEncoder bl_vel_enc = null;

    private final float[] modernRobotics_maxTorqueTbl_velocity = {0, 99};
    private final float[] modernRobotics_maxTorqueTbl_torque = {(float)0.19, 0};

    private Table2D modernRobotics_maxTorqueTbl =
            new Table2D(modernRobotics_maxTorqueTbl_velocity, modernRobotics_maxTorqueTbl_torque);

    private TorqueActuator fr_tor_act;
    private TorqueActuator fl_tor_act;
    private TorqueActuator br_tor_act;
    private TorqueActuator bl_tor_act;

    private DcMotorPlant yellow_jacket_312_plant = null;

    @Override
    public void runOpMode()
    {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        taskScheduler = new TaskScheduler();

        m_front_right = hardwareMap.get(DcMotor.class, "fr");
        m_front_left = hardwareMap.get(DcMotor.class, "fl");
        m_back_right = hardwareMap.get(DcMotor.class, "br");
        m_back_left = hardwareMap.get(DcMotor.class, "bl");

        fr_vel_enc = new VelocityEncoder((float)19.2);
        fl_vel_enc = new VelocityEncoder((float)19.2);
        br_vel_enc = new VelocityEncoder((float)19.2);
        bl_vel_enc = new VelocityEncoder((float)19.2);

        m_front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m_front_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m_front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m_front_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m_back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m_back_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m_back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m_back_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        m_front_left.setDirection(DcMotorSimple.Direction.REVERSE);
        m_back_left.setDirection(DcMotorSimple.Direction.REVERSE);

//        fr_tor_act = new TorqueActuator(m_front_right, modernRobotics_maxTorqueTbl, (float)13.7);
//        fl_tor_act = new TorqueActuator(m_front_left, modernRobotics_maxTorqueTbl, (float)13.7);
//        br_tor_act = new TorqueActuator(m_back_right, modernRobotics_maxTorqueTbl, (float)13.7);
//        bl_tor_act = new TorqueActuator(m_back_left, modernRobotics_maxTorqueTbl, (float)13.7);

        yellow_jacket_312_plant = DcMotorPlant.getGoBildaYellowJacket(312);

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
    }

    private void taskFunction_20ms()
    {
        calc_drive_base();
        SendTelemetry();
    }

    private void taskFunction_50ms()
    {

    }

    private void taskFunction_250ms()
    {

    }

    private void calc_drive_base()
    {
        /* LOCALS */
        float joystick_forward_demand;
        float joystick_turn_demand;
        float right_demand;
        float left_demand;
        float max;
        double fr_motor_current;
        double fl_motor_current;
        double br_motor_current;
        double bl_motor_current;
        double fr_motor_estimated_torque;
        double fl_motor_estimated_torque;
        double br_motor_estimated_torque;
        double bl_motor_estimated_torque;
        double fr_motor_estimated_speed;
        double fl_motor_estimated_speed;
        double br_motor_estimated_speed;
        double bl_motor_estimated_speed;



        /* CONSTANTS */


        joystick_forward_demand = -gamepad1.right_stick_y;
        joystick_turn_demand = gamepad1.right_stick_x;

        right_demand = joystick_forward_demand - joystick_turn_demand;
        left_demand = joystick_forward_demand + joystick_turn_demand;
        max = Math.max(Math.abs(right_demand), Math.abs(left_demand));

        if (max > 1.0)
        {
            right_demand /= max;
            left_demand /= max;
        }

        /* Set right and left motor powers */
        m_front_right.setPower(right_demand);
        m_back_right.setPower(right_demand);

        m_front_left.setPower(left_demand);
        m_back_left.setPower(left_demand);

        /* Get the current for each motor */
        fr_motor_current = ((DcMotorEx)m_front_right).getCurrent(CurrentUnit.AMPS);
        fl_motor_current = ((DcMotorEx)m_front_left).getCurrent(CurrentUnit.AMPS);
        br_motor_current = ((DcMotorEx)m_back_right).getCurrent(CurrentUnit.AMPS);
        bl_motor_current = ((DcMotorEx)m_back_left).getCurrent(CurrentUnit.AMPS);

        /* Use that current to estimate torque */
        fr_motor_estimated_torque = yellow_jacket_312_plant.getTorque(fr_motor_current);
        fl_motor_estimated_torque = yellow_jacket_312_plant.getTorque(fl_motor_current);
        br_motor_estimated_torque = yellow_jacket_312_plant.getTorque(br_motor_current);
        bl_motor_estimated_torque = yellow_jacket_312_plant.getTorque(bl_motor_current);

        /* Use the estimated torque and current input voltage to determine estimated speed */
        fr_motor_estimated_speed = yellow_jacket_312_plant.getSpeed(fr_motor_estimated_torque, m_front_right.getPower() * getBatteryVoltage());
        fl_motor_estimated_speed = yellow_jacket_312_plant.getSpeed(fl_motor_estimated_torque, m_front_left.getPower() * getBatteryVoltage());
        br_motor_estimated_speed = yellow_jacket_312_plant.getSpeed(br_motor_estimated_torque, m_back_right.getPower() * getBatteryVoltage());
        bl_motor_estimated_speed = yellow_jacket_312_plant.getSpeed(bl_motor_estimated_torque, m_back_left.getPower() * getBatteryVoltage());

        fr_vel_enc.Update(m_front_right.getCurrentPosition());
        fl_vel_enc.Update(m_front_left.getCurrentPosition());
        br_vel_enc.Update(m_back_right.getCurrentPosition());
        bl_vel_enc.Update(m_back_left.getCurrentPosition());

        telemetry.addData("FR Estimated Speed", fr_motor_estimated_speed);
        telemetry.addData("FR Actual Speed", fr_vel_enc.GetFilteredVelocity());
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
 
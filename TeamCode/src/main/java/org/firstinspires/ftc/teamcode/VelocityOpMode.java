package org.firstinspires.ftc.teamcode;

 import com.acmerobotics.dashboard.FtcDashboard;
 import com.qualcomm.robotcore.eventloop.opmode.Disabled;
 import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
 import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
 import com.qualcomm.robotcore.hardware.DcMotor;
 import com.qualcomm.robotcore.util.ElapsedTime;
 import com.qualcomm.robotcore.util.Range;

 import java.util.concurrent.TimeUnit;

@TeleOp(name="VelocityOpMode", group="Linear OpMode")
 public class VelocityOpMode extends LinearOpMode {
 
     // Declare OpMode members.
     private ElapsedTime runtime = new ElapsedTime();
     private DcMotor testMotor = null;

     private double lastTimestamp;
     private double currentTimestamp;

     private VelocityEncoder testEncoder = new VelocityEncoder((float)13.7);

 
     @Override
     public void runOpMode() {
         FtcDashboard dashboard = FtcDashboard.getInstance();
         telemetry = dashboard.getTelemetry();

         testMotor  = hardwareMap.get(DcMotor.class, "117 rpm");
         testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
 
         // Wait for the game to start (driver presses PLAY)
         waitForStart();
         runtime.reset();

         currentTimestamp = runtime.now(TimeUnit.MICROSECONDS);
         lastTimestamp = currentTimestamp;
 
         // run until the end of the match (driver presses STOP)
         while (opModeIsActive()) {
             currentTimestamp = runtime.now(TimeUnit.MICROSECONDS);
 
             // Setup a variable for each drive wheel to save power level for telemetry
             double joystick_power;
             joystick_power = -gamepad1.left_stick_y;
 
             // Send calculated power to wheels
             testMotor.setPower(joystick_power);

             testEncoder.Update(testMotor.getCurrentPosition());

             telemetry.addData("Execution Rate", currentTimestamp - lastTimestamp );

             telemetry.update();

             lastTimestamp = currentTimestamp;
         }
     }
 }
 
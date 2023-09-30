package org.firstinspires.ftc.teamcode;
/* Includes */
import com.qualcomm.robotcore.hardware.DcMotor;

/** <h1>Velocity Motor</h1>
 * DC motor Class with advanced controls
 */
public class VelocityMotor
{

    /* Object Static Variables */

    /* Object Variables */
    /**
     * <h1>Motor</h1>
     * Reference to the DcMotor object
     */
    private DcMotor motor;

    /**
     * <h1>Motor Encoder</h1>
     * Velocity Encoder object for the Motor object
     */
    private VelocityEncoder motorEncoder;

    /**
     * <h1>Velocity Target</h1>
     * Target output shaft velocity<br>
     * <b>Units:</b> rev/sec
     */
    private float velocityTarget;

    /**
     * <h1>Velocity Error</h1>
     * Difference between current and target velocity<br>
     * <b>Units:</b> rev/sec
     */
    private float velocityError;

    private float p;
    private float pContribution;

    private float i;
    private float iContribution;

    private float d;
    private float dContribution;


    /* END OF DATA DEFINITION SECTION */

    /**
     * <h1>Velocity Motor</h1>
     * Object for controlling the velocity of a standard
     * DC motor
     * @param motor DcMotor Object to control
     * @param gearRatio Divider between motor output speed and gearbox output speed
     */
    public VelocityMotor(DcMotor motor, float gearRatio) {
        this.motor = motor;
        this.motorEncoder = new VelocityEncoder(gearRatio);
    }

    private void calcVelocityError()
    {
        
    }
}
package org.firstinspires.ftc.teamcode;

/**
 * <h1>DC Motor Plant Model</h1>
 * Physic model for a basic DC Motor based on the FRC
 * edu.wpi.first.math.system.plant DCMotor class
 */
public class DcMotorPlant
{
    /* Static Variables */
    private static final double RPM_TO_RAD_PER_SEC = (2 * Math.PI / 60);

    /* Instance Variables */
    private final double nominalVoltage_volts;
    private final double stallTorque_newtonMeters;
    private final double stallCurrent_amps;
    private final double freeCurrent_amps;
    private final double freeSpeed_radiansPerSecond;
    private final double resistance_ohms;
    private final double Kv_radiansPerSecondVolt;
    private final double Kt_newtonMeterPerAmp;

    /**
     * Constructs a DC Motor Plant Model
     * @param nominalVoltage_volts Battery voltage measurements were taken at
     * @param stallTorque_newtonMeters Torque when stalled
     * @param stallCurrent_amps Current when stalled
     * @param freeCurrent_amps Current under no load
     * @param freeSpeed_radiansPerSecond Angular velocity under no load
     */
    public DcMotorPlant(double nominalVoltage_volts,
                        double stallTorque_newtonMeters,
                        double stallCurrent_amps,
                        double freeCurrent_amps,
                        double freeSpeed_radiansPerSecond)
    {
        /* LOCALS */

        /* CONSTANTS */


        this.nominalVoltage_volts = nominalVoltage_volts;
        this.stallTorque_newtonMeters = stallTorque_newtonMeters;
        this.stallCurrent_amps = stallCurrent_amps;
        this.freeCurrent_amps = freeCurrent_amps;
        this.freeSpeed_radiansPerSecond = freeSpeed_radiansPerSecond;

        /* Ohm's law, R = V / I
        /* This is done at stall to determine the resistance of the wire */
        this.resistance_ohms = nominalVoltage_volts / stallCurrent_amps;
        this.Kv_radiansPerSecondVolt =
                freeSpeed_radiansPerSecond /
                        (nominalVoltage_volts - this.resistance_ohms * freeCurrent_amps);
        //TODO: Shouldn't the free current be subtracted from stallCurrent?
        this.Kt_newtonMeterPerAmp = stallTorque_newtonMeters / stallCurrent_amps;
    }

    /**
     * Estimate the current draw based on known speed and input voltage
     * @param speed_rpm Angular velocity in rev/min
     * @param voltageInput_volts Input Voltage
     * @return Estimated current draw in Amps
     */
    public double getCurrent(double speed_rpm, double voltageInput_volts)
    {
        /* LOCALS */

        /* CONSTANTS */


        //TODO: Make this not a horrible looking equation
        return -1.0 / Kv_radiansPerSecondVolt / resistance_ohms * (RPM_TO_RAD_PER_SEC * speed_rpm) + 1.0 / resistance_ohms * voltageInput_volts;
    }

    /**
     * Estimate the generated torque based on known current draw
     * @param currentDraw_amps Current draw in Amps
     * @return Estimated torque generated in Nm
     */
    public double getTorque(double currentDraw_amps)
    {
        /* LOCALS */

        /* CONSTANTS */


        return currentDraw_amps * Kt_newtonMeterPerAmp;
    }

    /**
     * Estimate the required voltage input to generate a desired torque and speed
     * @param torque_Nm Desired torque in Nm
     * @param speed_rpm Desired speed in rev/min
     * @return Estimated input voltage required
     */
    public double getVoltage(double torque_Nm, double speed_rpm)
    {
        /* LOCALS */
        double speed_radPerSec;

        /* CONSTANTS */


        /* Convert angular velocity to radians per second for equation */
        speed_radPerSec = speed_rpm * RPM_TO_RAD_PER_SEC;

        return 1.0 / Kv_radiansPerSecondVolt * speed_radPerSec +
                1.0 / Kt_newtonMeterPerAmp * resistance_ohms * torque_Nm;
    }

    /**
     * Estimate the speed based on torque and input voltage
     * @param torque_nm Output torque in Nm
     * @param voltageInput_volts Input voltage in Volts
     * @return Estimated motor speed in rpm
     */
    public double getSpeed(double torque_nm, double voltageInput_volts)
    {
        /* LOCALS */

        /* CONSTANTS */


        return (voltageInput_volts * Kv_radiansPerSecondVolt -
                1.0 / Kt_newtonMeterPerAmp * torque_nm * resistance_ohms * Kv_radiansPerSecondVolt)
                / RPM_TO_RAD_PER_SEC;
    }

    /**
     * Returns a copy of this motor with the given gearbox reduction applied
     * @param gearboxReduction The gearbox reduction
     * @return A DcMotorPlant with the reduction applied
     */
    public DcMotorPlant withReduction(double gearboxReduction)
    {
        /* LOCALS */

        /* CONSTANTS */


        return new DcMotorPlant(this.nominalVoltage_volts,
                this.stallTorque_newtonMeters * gearboxReduction,
                this.stallCurrent_amps,
                this.freeCurrent_amps,
                this.freeSpeed_radiansPerSecond / gearboxReduction);
    }

    /**
     * Construct a plant model for a GoBilda Yellow Jacket Motor with Gearbox
     * @param empirical_rpm Empirical free speed
     * @return GoBilda YellowJacket DcMotorPlant
     */
    public static DcMotorPlant getGoBildaYellowJacket(double empirical_rpm)
    {
        /* LOCALS */
        double reduction;
        double angularVelocityScaled;

        /* CONSTANTS */


        reduction = empirical_rpm / 6000;
        angularVelocityScaled = empirical_rpm  * RPM_TO_RAD_PER_SEC;

        return new DcMotorPlant(12,
                0.19 * reduction,
                11,
                0.3,
                angularVelocityScaled);
    }
}

package org.firstinspires.ftc.teamcode;

/**
 * <h1>DC Motor Plant Model</h1>
 * Physic model for a basic DC Motor based on the FRC
 * edu.wpi.first.math.system.plant DCMotor class
 */
public class DcMotorPlant
{
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
        final double RPM_TO_RAD_PER_SEC = (2 * Math.PI / 60);

        reduction = empirical_rpm / 6000;
        angularVelocityScaled = empirical_rpm  * RPM_TO_RAD_PER_SEC;

        return new DcMotorPlant(12,
                0.19 * reduction,
                11,
                0.3,
                angularVelocityScaled);
    }
}

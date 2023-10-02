package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class TorqueActuator
{
    /**
     * <h3>DC Motor</h3>
     * Reference to a DcMotor object
     */
    private DcMotor dcMotor;

    /**
     * <h3>Power Lookup Table</h3>
     * <b>X - </b>Motor Velocity (rev/sec)<br>
     * <b>Y - </b>Motor Torque (Nm)<br>
     * <b>Z - </b>Output Power (% 0-1)
     */
    private Table3D powerTable;

    /**
     * <h3>Max Torque Table</h3>
     * Maximum torque the motor can deliver at the current velocity<br>
     * <b>X - </b>Motor Velocity (rev/sec)<br>
     * <b>Z - </b>Motor Torque (Nm)<br>
     */
    private Table2D maxTorqueTable;

    /**
     * <h3>Torque Multiplier</h3>
     * Multiplier between the DC motor output and the Gearbox output shaft
     */
    private float torqueMultiplier;

    /**
     * <h3>Commanded Torque</h3>
     * Torque command sent to the motor<br>
     * <b>Units:</b> Nm
     */
    private float commandedTorque;

    /**
     * <h3>Set Power</h3>
     * Power sent to the motor (% 0-1)
     */
    private float setPower;

    /**
     * <h3>Requested Torque</h3>
     * Torque requested for the motor to produce<br>
     * <b>Units:</b> Nm
     */
    private float requestedTorque;

    public TorqueActuator(DcMotor dcMotor, Table2D maxTorqueTable, float torqueMultiplier)
    {
        this.dcMotor = dcMotor;
        this.maxTorqueTable = maxTorqueTable;
        this.torqueMultiplier = torqueMultiplier;
    }

    /**
     * <h3>Request Torque</h3>
     * @param currentVelocity Current velocity of the motor in rev/sec
     * @param requestedTorque Torque requested in Nm
     * @return True - Torque Actuator could meet request<br>
     *         False - Torque Actuator could not meet request
     */
    boolean RequestTorque(float currentVelocity, float requestedTorque)
    {
        /* Locals */
        boolean torqueMet;

        float maxTorqueAtCurrentVelocity;
        float scaledRequestedTorque;
        float boundRequestedTorque;

        float commandedPower;

        /* Constants */


        /* requestedTorque is torque at the gearbox output shaft, scale it back to the motor
        /* output shaft */
        scaledRequestedTorque = requestedTorque / this.torqueMultiplier;

        maxTorqueAtCurrentVelocity = this.maxTorqueTable.Lookup(Math.abs(currentVelocity) * this.torqueMultiplier);

        /* Requested torque is over what we can generate */
        if (Math.abs(scaledRequestedTorque) > maxTorqueAtCurrentVelocity)
        {
            boundRequestedTorque = maxTorqueAtCurrentVelocity * Math.signum(scaledRequestedTorque);
            torqueMet = false;
        }
        /* We can generate the requested torque */
        else
        {
            boundRequestedTorque = scaledRequestedTorque;
            torqueMet = true;
        }

        this.commandedTorque = boundRequestedTorque * torqueMultiplier;

        this.setPower = boundRequestedTorque / maxTorqueAtCurrentVelocity;
        this.dcMotor.setPower(this.setPower);

        return torqueMet;
    }

    public float getCommandedTorque() {
        return commandedTorque;
    }

    public float getSetPower() {
        return setPower;
    }
}

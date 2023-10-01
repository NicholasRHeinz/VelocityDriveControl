package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class TorqueActuator
{
    /**
     * <h1>DC Motor</h1>
     * Reference to a DcMotor object
     */
    private DcMotor dcMotor;

    /**
     * <h1>Power Lookup Table</h1>
     * <b>X - </b>Motor Velocity (rev/sec)<br>
     * <b>Y - </b>Motor Torque (Nm)<br>
     * <b>Z - </b>Output Power (% 0-1)
     */
    private Table3D powerTable;

    /**
     * <h1>Max Torque Table</h1>
     * Maximum torque the motor can deliver at the current velocity<br>
     * <b>X - </b>Motor Velocity (rev/sec)<br>
     * <b>Z - </b>Motor Torque (Nm)<br>
     */
    private Table2D maxTorqueTable;

    /**
     * <h1>Torque Multiplier</h1>
     * Multiplier between the DC motor output and the Gearbox output shaft
     */
    private float torqueMultiplier;

    /**
     * <h1>Commanded Torque</h1>
     * Torque command sent to the motor<br>
     * <b>Units:</b> Nm
     */
    private float commandedTorque;

    /**
     * <h1>Set Power</h1>
     * Power sent to the motor (% 0-1)
     */
    private float setPower;

    /**
     * <h1>Requested Torque</h1>
     * Torque requested for the motor to produce<br>
     * <b>Units:</b> Nm
     */
    private float requestedTorque;

    public TorqueActuator(DcMotor dcMotor, Table3D powerTable, Table2D maxTorqueTable, float torqueMultiplier)
    {
        this.dcMotor = dcMotor;
        this.powerTable = powerTable;
        this.maxTorqueTable = maxTorqueTable;
        this.torqueMultiplier = torqueMultiplier;
    }

    /**
     * <h1>Request Torque</h1>
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
        final float MINIMUM_TORQUE = 0;


        /* requestedTorque is torque at the gearbox output shaft, scale it back to the motor
        /* output shaft */
        scaledRequestedTorque = requestedTorque / this.torqueMultiplier;

        maxTorqueAtCurrentVelocity = this.maxTorqueTable.Lookup(currentVelocity);

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

        this.commandedTorque = boundRequestedTorque;
        commandedPower = this.powerTable.Lookup(currentVelocity, boundRequestedTorque);

        this.dcMotor.setPower(commandedPower);
        this.setPower = commandedPower;

        return torqueMet;
    }

    public float getCommandedTorque() {
        return commandedTorque;
    }

    public float getSetPower() {
        return setPower;
    }
}

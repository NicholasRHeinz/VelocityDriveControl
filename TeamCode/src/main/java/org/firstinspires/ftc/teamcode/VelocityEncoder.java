package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

/** VelocityEncoder:
 * Calculates velocity from a given encoder
 *
 */
public class VelocityEncoder {

    /* Object Static Variables */

    /* Object Variables */
    /** <h1>Last Time</h1>
     * <b>Units:</b> microseconds
     */
    private float lastMicroSecondTime_fl;

    /** <h1>Last Encoder Count</h1>
     *  <b>Units:</b> Encoder Counts
     */
    private int lastEncoderCount_int;
    private ElapsedTime runtime;

    /**
     * <h1>Last calculated velocity</h1>
     * <b>Units:</b> rev/sec
     */
    private float instantVelocity;

    /**
     * <h1>Filtered Velocity</h1>
     * <b>Units:</b> rev/sec
     */
    private float filteredVelocity;

    private boolean velocitySynched;

    /**
     * <h1>Gear Ratio</h1>
     * Multiplier between motor output speed and gearbox output speed
     */
    private float gearRatio;

    /**
     * <h1>Filtered Velocity Time Constant</h1>
     * Value 0-1 of how much of the delta instant velocity to add to the filtered velocity
     */
    private float filterTimeConstant = (float)0.20;

    /**
     * <h1>Velocity Encoder</h1>
     * Helper object for calculating rev/sec from a standard encoder
     * @param encoderGearRatio Divider between motor output speed and gearbox output speed
     */
    public VelocityEncoder(float encoderGearRatio) {
        /* Locals */

        /* Constants */
        final float INITIAL_TIME = 0;
        final int INITIAL_ENCODER_COUNT = 0;
        final int INITIAL_VELOCITY = 0;
        final boolean INITIAL_SYNC = false;

        /* Reset parameters to initial values */
        this.lastMicroSecondTime_fl = INITIAL_TIME;
        this.lastEncoderCount_int = INITIAL_ENCODER_COUNT;
        this.instantVelocity = INITIAL_VELOCITY;
        this.filteredVelocity = INITIAL_VELOCITY;
        this.velocitySynched = INITIAL_SYNC;

        this.gearRatio = encoderGearRatio;

        this.runtime = new ElapsedTime();
    }

    public float Update(int encoderValue) {
        /* Locals */
        float currentMicroSecondTime_fl;
        float microSecondDelta_fl;
        int encoderDelta_int;
        float motorShaftVelocity_fl;

        /* Constants */
        final int MICROSECONDS_PER_SECOND = 1000000;
        final int MINIMUM_ENCODER_DELTA = 28;
        final float ZERO_VELOCITY_TIMEOUT_MICROSECONDS = 400000;

        /* Update time and encoder deltas */
        currentMicroSecondTime_fl = this.runtime.now(TimeUnit.MICROSECONDS);
        microSecondDelta_fl = currentMicroSecondTime_fl - this.lastMicroSecondTime_fl;
        encoderDelta_int = encoderValue - this.lastEncoderCount_int;

        /* This is likely the first time Update has been
           called for this encoder
         */
        if (!this.velocitySynched)
        {
            /* Update timestamp and counts */
            this.lastMicroSecondTime_fl = currentMicroSecondTime_fl;
            this.lastEncoderCount_int = encoderValue;

            /* Velocity can be calculated next time around */
            this.velocitySynched = true;
            return 0;
        }

        /* Check for zero speed timeout */
        if (microSecondDelta_fl > ZERO_VELOCITY_TIMEOUT_MICROSECONDS)
        {
            this.instantVelocity = 0;
            this.velocitySynched = false;
            UpdateFilteredVelocity(this.instantVelocity);
            return microSecondDelta_fl;
        }

        /* Check for minimum encoder counts */
        if (Math.abs(encoderDelta_int) < MINIMUM_ENCODER_DELTA)
        {
            return 0;
        }

        /* Calculate rev/sec based on encoderDelta_int and microSecondDelta_fl  */
        motorShaftVelocity_fl = ((float)encoderDelta_int / 28) / (microSecondDelta_fl / MICROSECONDS_PER_SECOND);
        this.instantVelocity = motorShaftVelocity_fl / this.gearRatio;

        /* Update last timestamp and encoder counts */
        this.lastMicroSecondTime_fl = currentMicroSecondTime_fl;
        this.lastEncoderCount_int = encoderValue;

        UpdateFilteredVelocity(this.instantVelocity);

        return microSecondDelta_fl;
    }

    private void UpdateFilteredVelocity(float instantVelocity)
    {
        /* Locals */

        /* Constants */

        if (instantVelocity == 0)
        {
            this.filteredVelocity = 0;
        }

        this.filteredVelocity = this.filteredVelocity + (this.filterTimeConstant *
                (instantVelocity - this.filteredVelocity));
    }

    public float GetVelocity() {
        return this.instantVelocity;
    }

    public float GetFilteredVelocity() {
        return filteredVelocity;
    }

    public void setFilterTimeConstant(float filterTimeConstant) {
        this.filterTimeConstant = filterTimeConstant;
    }
}

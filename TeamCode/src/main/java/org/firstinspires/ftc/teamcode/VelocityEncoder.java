package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

/** VelocityEncoder:
 * Calculates velocity from a given encoder
 *
 */
public class VelocityEncoder {

    /* Object Static Variables */

    /* Local Variables */
    /** <h1>Last Time</h1>
     * <b>Units:</b> milliseconds
     */
    private float lastTimeUpdate_fl;

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

    /**
     * <h1>Velocity Encoder</h1>
     * Helper object for calculating rev/sec from a standard encoder
     * @param gearRatio Multiplier between motor output speed and gearbox output speed
     */
    public VelocityEncoder(float gearRatio) {
        /* Locals */

        /* Constants */
        final float INITIAL_TIME = 0;
        final int INITIAL_ENCODER_COUNT = 0;
        final int INITIAL_VELOCITY = 0;

        /* Reset parameters to initial values */
        lastTimeUpdate_fl = INITIAL_TIME;
        lastEncoderCount_int = INITIAL_ENCODER_COUNT;
        instantVelocity = INITIAL_VELOCITY;
        filteredVelocity = INITIAL_VELOCITY;

        runtime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    }
}

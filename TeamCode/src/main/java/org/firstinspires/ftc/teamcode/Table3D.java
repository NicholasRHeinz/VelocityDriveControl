package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Table3D
{
    private float[] tableX;
    private float[] tableY;
    private float[][] tableZ;

    public Table3D(float[] tableX, float[] tableY, float[][] tableZ)
    {
        this.tableX = tableX;
        this.tableY = tableY;
        this.tableZ = tableZ;

        // TODO: We should probably check if the input and output arrays match in size
    }

    /**
     * <h1>Lookup</h1>
     * Lookup value from a 3D table<br>
     * Implementation based off of https://www.omnicalculator.com/math/bilinear-interpolation#bilinear-interpolation-formula
     * @param X
     * @param Y
     * @return Table output value
     */
    public float Lookup(float X, float Y, Telemetry telem)
    {
        /* Locals */
        float lowerX = 0;
        int lowerXIndex = 0;
        float upperX = 0;
        int upperXIndex = 0;

        float lowerY = 0;
        int lowerYIndex = 0;
        float upperY = 0;
        int upperYIndex = 0;

        float lowerX_lowerY_value;
        float upperX_lowerY_value;
        float lowerX_upperY_value;
        float upperX_upperY_value;

        float R1;
        float R2;

        float output;

        /* Constants */


        /* Determine lower and upper X and Y values */

        /* Check if X is at or below the first x value in the table */
        if (X <= this.tableX[0])
        {
            lowerX = this.tableX[0];
            upperX = this.tableX[0];

            lowerXIndex = 0;
            upperXIndex = 0;
        }
        /* Check if X if at or above the last x value in the table */
        else if (X >= this.tableX[tableX.length - 1])
        {
            lowerX = this.tableX[tableX.length - 1];
            upperX = this.tableX[tableX.length - 1];

            lowerXIndex = tableX.length - 1;
            upperXIndex = tableX.length - 1;
        }
        /* X is somewhere in the middle, interpolate between two values */
        else
        {
            /* Find upper and lower X values */
            for (int i=0; i<this.tableX.length; i++)
            {
                if (this.tableX[i] < X)
                {
                    /* We're now above table x value, set the lower and upper limits then break */
                    lowerX = this.tableX[i];
                    upperX = this.tableX[i+1];

                    lowerXIndex = i;
                    upperXIndex = i + 1;
                    break;
                }
            }
        }

        /* Check if Y is below the first y value in the table */
        if (Y == this.tableY[0])
        {
            lowerY = Y;
            upperY = Y;

            lowerYIndex = 0;
            upperYIndex = 0;
        }
        /* Check if Y if at or above the last y value in the table */
        else if (Y >= this.tableY[tableY.length - 1])
        {
            lowerY = this.tableY[tableY.length - 1];
            upperY = this.tableY[tableY.length - 1];

            lowerYIndex = tableY.length - 1;
            upperYIndex = tableY.length - 1;
        }
        /* Y is somewhere in the middle, interpolate between two values */
        else
        {
            /* Find upper and lower Y values */
            for (int i=0; i<this.tableY.length; i++)
            {
                if (this.tableY[i] < Y)
                {
                    /* We're now above table x value, set the lower and upper limits then break */
                    lowerY = this.tableY[i];
                    upperY = this.tableY[i+1];

                    lowerYIndex = i;
                    upperYIndex = i + 1;
                    break;
                }
            }
        }

        /* Lookup output values for each upper/lower combination */
        lowerX_lowerY_value = this.tableZ[lowerXIndex][lowerYIndex];
        upperX_lowerY_value = this.tableZ[upperXIndex][lowerYIndex];

        lowerX_upperY_value = this.tableZ[lowerXIndex][upperYIndex];
        upperX_upperY_value = this.tableZ[upperXIndex][upperYIndex];

        R1 = ((upperX - X) / (upperX - lowerX)) * lowerX_lowerY_value +
                ((X - lowerX) / (upperX - lowerX)) * upperX_lowerY_value;

        R2 = ((upperX - X) / (upperX - lowerX)) * lowerX_upperY_value +
                ((X - lowerX) / (upperX - lowerX)) * upperX_upperY_value;

        output = ((upperY - Y) / (upperY - lowerY)) * R1 +
                ((Y - lowerY) / (upperY - lowerY)) * R2;

        telem.addData("out1", upperY);
        telem.addData("out2", lowerY);

        return output;
    }


}

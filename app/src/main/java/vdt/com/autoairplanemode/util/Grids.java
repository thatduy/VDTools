package vdt.com.autoairplanemode.util;

import vdt.com.autoairplanemode.R;

/**
 * Created by ASUS on 02-Nov-17.
 */

public class Grids {

    public static final int GridSize = 64;
    public static final int GridSideSize = 8;
    public static final int PatternIdCustom = 7;


    public static String[] PatternNames = new String[] {
            "12%",
            "25%",
            "38%",
            "50%",
            "62%",
            "75%",
            "88%",
            "Custom 1", // NON-NLS
            "Custom 2", // NON-NLS
            "Custom 3", // NON-NLS
            "Custom 4", // NON-NLS
            "Custom 5", // NON-NLS
    };

    public static byte[][] Patterns = new byte[][] {
            {
                    1, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 1, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 1, 0,
                    0, 1, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 1, 0, 0,
                    0, 0, 1, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 1,
                    0, 0, 0, 0, 1, 0, 0, 0,
            },
            {
                    1, 0, 0, 0, 1, 0, 0, 0,
                    0, 0, 1, 0, 0, 0, 1, 0,
                    0, 1, 0, 0, 0, 1, 0, 0,
                    0, 0, 0, 1, 0, 0, 0, 1,
                    1, 0, 0, 0, 1, 0, 0, 0,
                    0, 0, 1, 0, 0, 0, 1, 0,
                    0, 1, 0, 0, 0, 1, 0, 0,
                    0, 0, 0, 1, 0, 0, 0, 1,
            },
            {
                    1, 0, 0, 0, 1, 0, 0, 0,
                    0, 1, 0, 1, 0, 1, 0, 1,
                    0, 0, 1, 0, 0, 0, 1, 0,
                    0, 1, 0, 1, 0, 1, 0, 1,
                    1, 0, 0, 0, 1, 0, 0, 0,
                    0, 1, 0, 1, 0, 1, 0, 1,
                    0, 0, 1, 0, 0, 0, 1, 0,
                    0, 1, 0, 1, 0, 1, 0, 1,
            },
            {
                    1, 0, 1, 0, 1, 0, 1, 0,
                    0, 1, 0, 1, 0, 1, 0, 1,
                    1, 0, 1, 0, 1, 0, 1, 0,
                    0, 1, 0, 1, 0, 1, 0, 1,
                    1, 0, 1, 0, 1, 0, 1, 0,
                    0, 1, 0, 1, 0, 1, 0, 1,
                    1, 0, 1, 0, 1, 0, 1, 0,
                    0, 1, 0, 1, 0, 1, 0, 1,
            },
            {
                    0, 1, 1, 1, 0, 1, 1, 1,
                    1, 0, 1, 0, 1, 0, 1, 0,
                    1, 1, 0, 1, 1, 1, 0, 1,
                    1, 0, 1, 0, 1, 0, 1, 0,
                    0, 1, 1, 1, 0, 1, 1, 1,
                    1, 0, 1, 0, 1, 0, 1, 0,
                    1, 1, 0, 1, 1, 1, 0, 1,
                    1, 0, 1, 0, 1, 0, 1, 0,
            },
            {
                    0, 1, 1, 1, 0, 1, 1, 1,
                    1, 1, 0, 1, 1, 1, 0, 1,
                    1, 0, 1, 1, 1, 0, 1, 1,
                    1, 1, 1, 0, 1, 1, 1, 0,
                    0, 1, 1, 1, 0, 1, 1, 1,
                    1, 1, 0, 1, 1, 1, 0, 1,
                    1, 0, 1, 1, 1, 0, 1, 1,
                    1, 1, 1, 0, 1, 1, 1, 0,
            },
            {
                    0, 1, 1, 1, 1, 1, 1, 1,
                    1, 1, 1, 0, 1, 1, 1, 1,
                    1, 1, 1, 1, 1, 1, 0, 1,
                    1, 0, 1, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 1, 0, 1, 1,
                    1, 1, 0, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 0,
                    1, 1, 1, 1, 0, 1, 1, 1,
            },
            // Custom patterns
            {
                    1, 0, 0, 0, 1, 0, 0, 0,
                    0, 0, 1, 0, 0, 0, 1, 0,
                    0, 1, 0, 0, 0, 1, 0, 0,
                    0, 0, 0, 1, 0, 0, 0, 1,
                    1, 0, 0, 0, 1, 0, 0, 0,
                    0, 0, 1, 0, 0, 0, 1, 0,
                    0, 1, 0, 0, 0, 1, 0, 0,
                    0, 0, 0, 1, 0, 0, 0, 1,
            },
            {
                    1, 0, 0, 0, 1, 0, 0, 0,
                    0, 1, 0, 1, 0, 1, 0, 1,
                    0, 0, 1, 0, 0, 0, 1, 0,
                    0, 1, 0, 1, 0, 1, 0, 1,
                    1, 0, 0, 0, 1, 0, 0, 0,
                    0, 1, 0, 1, 0, 1, 0, 1,
                    0, 0, 1, 0, 0, 0, 1, 0,
                    0, 1, 0, 1, 0, 1, 0, 1,
            },
            {
                    1, 0, 1, 0, 1, 0, 1, 0,
                    0, 1, 0, 1, 0, 1, 0, 1,
                    1, 0, 1, 0, 1, 0, 1, 0,
                    0, 1, 0, 1, 0, 1, 0, 1,
                    1, 0, 1, 0, 1, 0, 1, 0,
                    0, 1, 0, 1, 0, 1, 0, 1,
                    1, 0, 1, 0, 1, 0, 1, 0,
                    0, 1, 0, 1, 0, 1, 0, 1,
            },
            {
                    0, 1, 1, 1, 0, 1, 1, 1,
                    1, 0, 1, 0, 1, 0, 1, 0,
                    1, 1, 0, 1, 1, 1, 0, 1,
                    1, 0, 1, 0, 1, 0, 1, 0,
                    0, 1, 1, 1, 0, 1, 1, 1,
                    1, 0, 1, 0, 1, 0, 1, 0,
                    1, 1, 0, 1, 1, 1, 0, 1,
                    1, 0, 1, 0, 1, 0, 1, 0,
            },
            {
                    0, 1, 1, 1, 0, 1, 1, 1,
                    1, 1, 0, 1, 1, 1, 0, 1,
                    1, 0, 1, 1, 1, 0, 1, 1,
                    1, 1, 1, 0, 1, 1, 1, 0,
                    0, 1, 1, 1, 0, 1, 1, 1,
                    1, 1, 0, 1, 1, 1, 0, 1,
                    1, 0, 1, 1, 1, 0, 1, 1,
                    1, 1, 1, 0, 1, 1, 1, 0,
            },
    };

    // Indexes to shift screen pattern in both vertical and horizontal directions
    public static byte[] GridShift = new byte[] {
            0,  1,  8,  9,  2,  3, 10, 11,
            4,  5, 12, 13,  6,  7, 14, 15,
            16, 17, 24, 25, 18, 19, 26, 27,
            20, 21, 28, 29, 22, 23, 30, 31,
            32, 33, 40, 41, 34, 35, 42, 43,
            36, 37, 44, 45, 38, 39, 46, 47,
            48, 49, 56, 57, 50, 51, 58, 59,
            52, 53, 60, 61, 54, 55, 62, 63,
    };

    public static int[] ShiftTimeouts = new int[] { // In milliseconds
            15 * 1000,
            30 * 1000,
            60 * 1000,
            2 * 60 * 1000,
            5 * 60 * 1000,
            10 * 60 * 1000,
            20 * 60 * 1000,
            30 * 60 * 1000,
            60 * 60 * 1000,
    };

}
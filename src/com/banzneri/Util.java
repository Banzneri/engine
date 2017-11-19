package com.banzneri;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Util {

    public static double getRandomInRange(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }

    public static double nanosToSeconds(long nanos) {
        return nanos / 1E9;
    }
}

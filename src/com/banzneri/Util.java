package com.banzneri;

import java.util.Random;

public class Util {

    public static double getRandomInRange(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }
}

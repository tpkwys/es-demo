package com.aaron.esdemo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {
        DecimalFormat decimalFormat=new DecimalFormat("0.000");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        System.out.println(decimalFormat.format(new BigDecimal("10.5")).toString());
    }
}

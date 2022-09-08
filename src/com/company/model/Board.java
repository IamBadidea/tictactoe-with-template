package com.company.model;

import java.util.Arrays;

public class Board {
    public static final int SIDE = 3;

    protected final Figure[] array = new Figure[SIDE * SIDE];

    public Board() {
        Arrays.fill(array, Figure.NULL);
    }

    public void insert(int num, Figure figure) {

        if (num < 0 || num >= array.length) {
            throw new IllegalArgumentException("illegal cell address");
        }

        if (get(num) != Figure.NULL) {
            throw new IllegalArgumentException("you can't go to this cell, it's busy");
        }
        array[num] = figure;
    }


    public Figure get(int num) {
        return array[num];
    }

    public int toInt(Figure figure) {
        int out = 0;
        int mask = 1;
        for (Figure value : array) {
            if (value == figure) {
                out |= mask;
            }
            mask = mask << 1;
        }
        return out;
    }

}
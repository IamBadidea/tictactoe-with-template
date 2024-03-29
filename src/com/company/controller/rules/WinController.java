package com.company.controller.rules;

import com.company.model.Board;
import com.company.model.Figure;
import com.company.model.exception.GameException;

public class WinController {

    private WinController() {
    }

    private static final int[] WIN_TEMPLATES = new int[]{
            0b000_000_000_111,
            0b000_000_111_000,
            0b000_111_000_000,

            0b000_001_001_001,
            0b000_010_010_010,
            0b000_100_100_100,

            0b000_100_010_001,
            0b000_001_010_100,
    };

     public static boolean isWin(Board board, Figure figure) {
        return winLine(board, figure) != 0;
    }

    public static int winLine(Board board, Figure figure) {
        int num = board.figureMovesInBits(figure);
        for (int template : WIN_TEMPLATES) {
            int check = num & template;
            if (check == template) {
                return template;
            }
        }

        return 0;
    }

}

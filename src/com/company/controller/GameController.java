package com.company.controller;

import com.company.model.*;
import com.company.model.Command;
import com.company.model.exception.GameException;
import com.company.model.player.Bot;
import com.company.view.Printer;
import com.company.view.Reader;

public class GameController {

    private static final String GAME_OVER = "Game over";

    public void go(Game game, Printer printer, Reader reader) {
        printer.printBoard(game.boardCharArray());

        while (true) {
            String message = String.format("Player %s, enter you move: ", game.getCurrentName());
            printer.print(message);

            Command command = inputCommand(game, reader);
            if(checkCurrentPlayerIsBot(game)) {
                printer.println(command.getValue());
            }

            if(command.isEnd()) {
                break;
            }

            boolean moveResult = move(command, game, printer);
            if(!moveResult) {
                continue;
            }

            if (game.isWin()) {
                printer.printBoard(game.boardCharArray(), game.winLine());
                String winMessage = String.format("%s: Winner is %s", GAME_OVER, game.getCurrentName());
                printer.println(winMessage);
                break;
            }

            printer.printBoard(game.boardCharArray());

            if(game.isDraw()) {
                printer.println(GAME_OVER + ": draw");
                break;
            }

            game.changeCurrent();
        }
    }

    private boolean move(Command command, Game game, Printer printer) {

        if (!command.isMove()) {
            printer.println("illegal move command");
            return false;
        }

        int num = command.toInt() - 1;
        try {
            game.move(num);
            return true;
        } catch (GameException e) {
            printer.println(e.getMessage());
            return false;
        }
    }

    private Command inputCommand(Game game, Reader reader) {
        String input;
        if(checkCurrentPlayerIsBot(game)) {
            Bot bot = (Bot) game.getCurrent();
            Moves moves = game.getMoves();
            input = bot.nextMove(moves);
        } else {
            input = reader.read();
        }

        return new Command(input);
    }

    private boolean checkCurrentPlayerIsBot(Game game) {
        return game.getCurrent() instanceof Bot;
    }

}

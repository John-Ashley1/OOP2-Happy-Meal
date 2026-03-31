package com.ror.gameutil;

public class ConsoleBattleView implements BattleView {
    @Override
    public void logMessage(String message) {
        // Simply prints the game events to the terminal
        System.out.println(message);
    }
}
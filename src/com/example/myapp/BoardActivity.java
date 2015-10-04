package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Michael on 2015-10-04.
 */
public class BoardActivity extends Activity {
    private enum Result {Win, Tie, Ongoing}

    private final int SIZE = 3;

    private Symbol[][] board = new Symbol[SIZE][SIZE];
    private int moveCount = 0;

    private Player PlayerX;
    private Player PlayerO;
    private Player currentPlayer;

    private Intent currentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);

        currentIntent = getIntent();
        CreatePlayers();
        InitializeBoard();
        SetButtonListeners();
        ShowPlayerNames();
    }

    private void SetButtonListeners() {
        findViewById(R.id.btn1).setOnClickListener(new BoardCaseOnClickListener(0, 0));
        findViewById(R.id.btn2).setOnClickListener(new BoardCaseOnClickListener(0, 1));
        findViewById(R.id.btn3).setOnClickListener(new BoardCaseOnClickListener(0, 2));

        findViewById(R.id.btn4).setOnClickListener(new BoardCaseOnClickListener(1, 0));
        findViewById(R.id.btn5).setOnClickListener(new BoardCaseOnClickListener(1, 1));
        findViewById(R.id.btn6).setOnClickListener(new BoardCaseOnClickListener(1, 2));

        findViewById(R.id.btn7).setOnClickListener(new BoardCaseOnClickListener(2, 0));
        findViewById(R.id.btn8).setOnClickListener(new BoardCaseOnClickListener(2, 1));
        findViewById(R.id.btn9).setOnClickListener(new BoardCaseOnClickListener(2, 2));
    }

    private void ShowPlayerNames() {
        ((TextView)findViewById(R.id.lblPlayerX)).setText(PlayerX.Name);
        ((TextView)findViewById(R.id.lblPlayerO)).setText(PlayerO.Name);
    }

    private void InitializeBoard() {
        for (int i = 0; i < SIZE; ++i)
            for (int j = 0; j < SIZE; ++j)
                board[i][j] = Symbol.Blank;

        moveCount = 0;
    }

    private Result CheckTicTacToe(int x, int y, Symbol s) {
        for (int j = 0; j < SIZE; ++j) {
            if (board[x][j] != s) break;
            if (j == SIZE - 1) return Result.Win;
        }

        for (int i = 0; i < SIZE; ++i) {
            if (board[i][y] != s) break;
            if (i == SIZE - 1) return Result.Win;
        }

        if (x == y) {
            for (int i = 0; i < SIZE; i++) {
                if (board[i][i] != s) break;
                if (i == SIZE - 1) return Result.Win;
            }
        }

        for (int i = 0; i < SIZE; ++i) {
            if (board[i][(SIZE - 1) - i] != s) break;
            if (i == SIZE - 1) return Result.Win;
        }

        if (moveCount == (int)Math.pow(SIZE,2)) return Result.Tie;

        return Result.Ongoing;
    }

    private void CreatePlayers() {
        String playerXName = currentIntent.getStringExtra(Constants.PLAYER_X_NAME);
        String playerOName = currentIntent.getStringExtra(Constants.PLAYER_O_NAME);

        PlayerX = new Player(playerXName, Symbol.X);
        PlayerO = new Player(playerOName, Symbol.O);

        SetCurrentPlayer(PlayerX);
    }

    private void SetCurrentPlayer(Player p) {
        currentPlayer = p;
    }


    public class BoardCaseOnClickListener implements View.OnClickListener {
        private int x, y;

        public BoardCaseOnClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void onClick(View v) {
            if (board[x][y] != Symbol.Blank) {
                return;
            }

            board[x][y] = currentPlayer.Symbol;
            ((Button) v).setText(currentPlayer.Symbol.toString());
            moveCount++;

            Result result = CheckTicTacToe(x, y, currentPlayer.Symbol);
            if (result == Result.Win)
                ShowResult("Winner: " + currentPlayer.Name + " !");
            else if (result == Result.Tie)
                ShowResult("Tied game !!!");
            else
                SetCurrentPlayer(currentPlayer.Name.equals(PlayerX.Name) ? PlayerO : PlayerX);
        }
    }

    private void ShowResult(String message) {
        Toast t = Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG);
        t.show();
        finish();
    }
}

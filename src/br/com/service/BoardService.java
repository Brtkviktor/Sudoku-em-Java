package br.com.service;

import br.com.model.Board;
import javax.swing.SwingUtilities;

public class BoardService {

    private final static int BOARD_LIMIT = 9;

    public static void initGUI(Board board) {
        SwingUtilities.invokeLater(() -> {
            SudokuFrame frame = new SudokuFrame(board);
            frame.setVisible(true);
        });
    }
}

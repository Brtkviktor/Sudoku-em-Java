package br.com.service;

import br.com.model.Board;
import br.com.model.Space;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class BoardPanel extends JPanel {
    private final List<CellPanel> cellPanels = new ArrayList<>();

    public BoardPanel(Board board) {
        setLayout(new GridLayout(9, 9));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Space space = board.getSpaces().get(col).get(row);
                CellPanel cellPanel = new CellPanel(space, s -> validateBoard());
                
                int top = (row % 3 == 0) ? 2 : 1;
                int left = (col % 3 == 0) ? 2 : 1;
                int bottom = (row == 8) ? 2 : 1;
                int right = (col == 8) ? 2 : 1;
                cellPanel.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
                
                cellPanels.add(cellPanel);
                add(cellPanel);
            }
        }
    }

    private void validateBoard() {
        for (CellPanel cellPanel : cellPanels) {
            cellPanel.updateBackground();
        }
    }

    public void validateErrors() {
        for (CellPanel cellPanel : cellPanels) {
            cellPanel.showErrors();
        }
    }
}

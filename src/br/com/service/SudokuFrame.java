package br.com.service;

import br.com.model.Board;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class SudokuFrame extends JFrame {

    private BoardPanel boardPanel;

    public SudokuFrame(Board board) {
        setTitle("Sudoku Game");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        boardPanel = new BoardPanel(board);
        add(boardPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton checkButton = new JButton("Verificar Status");
        checkButton.addActionListener(e -> {
            boardPanel.validateErrors();
            if (board.gameIsFinished()) {
                JOptionPane.showMessageDialog(this, "Parabéns, você concluiu o jogo!");
            } else if (board.hasErrors()) {
                JOptionPane.showMessageDialog(this, "Seu jogo contém erros, verifique seu board e ajuste-o.", "Erros", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Você ainda precisa preencher alguns espaços ou corrigir erros.");
            }
        });
        
        JButton clearButton = new JButton("Limpar Jogo");
        clearButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja limpar seu jogo e perder todo seu progresso?", "Limpar Jogo", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                board.reset();
                remove(boardPanel);
                boardPanel = new BoardPanel(board);
                add(boardPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        buttonPanel.add(clearButton);
        buttonPanel.add(checkButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}

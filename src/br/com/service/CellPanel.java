package br.com.service;

import br.com.model.Space;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class CellPanel extends JPanel {
    private final Space space;
    private final JTextField textField;
    private final JLabel draftLabel;
    private String draftNumber = "";

    public CellPanel(Space space, Consumer<Space> onChange) {
        this.space = space;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        draftLabel = new JLabel(" ");
        draftLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        draftLabel.setForeground(Color.GRAY);
        add(draftLabel, BorderLayout.NORTH);

        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Arial", Font.BOLD, 20));
        textField.setBorder(null);

        if (space.isFixed()) {
            textField.setText(String.valueOf(space.getExpected()));
            textField.setEditable(false);
            textField.setBackground(Color.LIGHT_GRAY);
        } else {
            if (space.getActual() != null) {
                textField.setText(String.valueOf(space.getActual()));
            }
            textField.setBackground(Color.WHITE);
            ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                    String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                    if (newStr.length() <= 1 && newStr.matches("[1-9]?")) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            });

            textField.getDocument().addDocumentListener(new DocumentListener() {
                private void update() {
                    String text = textField.getText();
                    if (text.isEmpty()) {
                        space.clearSpace();
                    } else {
                        space.setActual(Integer.parseInt(text));
                    }
                    onChange.accept(space);
                }

                @Override
                public void insertUpdate(DocumentEvent e) { update(); }

                @Override
                public void removeUpdate(DocumentEvent e) { update(); }

                @Override
                public void changedUpdate(DocumentEvent e) { update(); }
            });

            textField.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        String input = JOptionPane.showInputDialog(CellPanel.this, "Inserir Rascunho:", draftNumber);
                        if (input != null) {
                            draftNumber = input;
                            draftLabel.setText(draftNumber.isEmpty() ? " " : draftNumber);
                        }
                    }
                }
            });
        }

        add(textField, BorderLayout.CENTER);
        updateBackground();
    }

    public void updateBackground() {
        if (!space.isFixed() && space.getActual() != null) {
            textField.setForeground(Color.BLUE);
        } else {
            textField.setForeground(Color.BLACK);
        }
    }

    public void showErrors() {
        if (!space.isFixed() && space.getActual() != null) {
            if (!space.getActual().equals(space.getExpected())) {
                textField.setForeground(Color.RED);
            }
        }
    }
}

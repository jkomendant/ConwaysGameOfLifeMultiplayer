package conways.game.of.life.multiplayer;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GridFrame extends JFrame {
    Grid grid;
    JButton clearButton = new JButton();
    JPanel top = new JPanel();
    JPanel bottom = new JPanel();
    JPanel left = new JPanel();
    JPanel right = new JPanel();
    JPanel leftText = new JPanel();
    JPanel rightText = new JPanel();
    JLabel title = new JLabel();
    JLabel generation = new JLabel();
    JLabel blueShapesLabel = new JLabel();
    JLabel redShapesLabel = new JLabel();
    JComboBox<Shapes> blueShapesJComboBox = new JComboBox<>();
    JComboBox<Shapes> redShapesJComboBox = new JComboBox<>();
    JTextArea blueArea = new JTextArea();
    JTextArea redArea = new JTextArea();
    GridView view;
    JButton playButton;
    boolean play = false;


    public GridFrame(GridMouseListener listener, GridView gridView) {

        this.grid = gridView.getGrid();
        this.view = gridView;
        this.playButton = new JButton("Go");

        setSize(1410, 900);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Game of Life");
        setLayout(new BorderLayout());

        gridView.addMouseListener(listener);
        add(view, BorderLayout.CENTER);
        bottom.setLayout(new FlowLayout());
        top.setLayout(new FlowLayout());
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        leftText.setLayout(new GridLayout());
        rightText.setLayout(new GridLayout());
        Font font = new Font("Times New Roman", Font.BOLD, 18);
        title.setText("Conway's Game of Life Multiplayer");
        title.setFont(font);
        blueShapesLabel.setText("Player 1: ");
        blueShapesLabel.setHorizontalAlignment(SwingConstants.LEFT);
        blueShapesLabel.setVerticalAlignment(SwingConstants.CENTER);
        left.add(blueShapesLabel);
        blueShapesJComboBox.setModel(new DefaultComboBoxModel<>(Shapes.values()));
        blueShapesJComboBox.removeItem(Shapes.BLANK);
        blueShapesJComboBox.addActionListener(ActionEvent -> setPlayerColor(listener));
        blueArea.setEditable(false);
        blueArea.setText("\nStart game by placing a piece down in generation 0 on the board. \n\n Choose your piece carefully, " +
                "for once it's selected, you must place it down on your side of the board. \n\n Then for every turn after, " +
                "you are able to place a single piece down when the generation is a multiple of 10. " +
                "\n\nYou can only click within the light gray area to place a piece down.");
        blueArea.setLineWrap(true);
        blueArea.setWrapStyleWord(true);
        blueArea.setSize(50, 50);
        blueArea.setFont(font);
        leftText.add(blueArea);
        left.add(leftText);
        left.add(blueShapesJComboBox);

        redShapesLabel.setText("Player 2: ");
        redShapesJComboBox.setModel(new DefaultComboBoxModel<>(Shapes.values()));
        redShapesJComboBox.removeItem(Shapes.BLANK);
        if (grid.getGeneration() == 0) {
            redShapesJComboBox.setEnabled(false);
        }
        redShapesJComboBox.addActionListener(ActionEvent -> setPlayerColor(listener));
        redArea.setEditable(false);
        redArea.setText("\nFirst turn is in generation 5 where you place a piece down on the board. \n\nChoose your piece " +
                "carefully, for once it's selected, you must place it down on your side of the board. \n\nThen for every turn " +
                "after, you are able to place a single piece down when the generation is an odd multiple of 5. " +
                "\n\nYou can only click within the dark gray area to place a piece down.");
        redArea.setLineWrap(true);
        redArea.setWrapStyleWord(true);
        redArea.setFont(font);
        rightText.add(redArea);
        right.add(redShapesLabel);
        right.add(rightText);
        right.add(redShapesJComboBox);
        clearButton.setText("New Game");
        clearButton.addActionListener(ActionEvent -> clearBoard(listener));
        bottom.add(clearButton);
        top.add(title);
        generation.setText(grid.toString());
        playButton.addActionListener(ActionEvent -> playLoop(listener));
        bottom.add(generation);
        bottom.add(playButton);

        add(top, BorderLayout.NORTH);
        add(left, BorderLayout.WEST);
        add(bottom, BorderLayout.SOUTH);
        add(right, BorderLayout.EAST);

    }

    private void setPlayerColor(GridMouseListener listener) {
        if (grid.getGeneration() == 0 || grid.getGeneration() % 2 == 0 && grid.getGeneration() % 5 == 0) {
            listener.setCurrentPlayer(Player.BLUE);
            listener.setCurrentShape((Objects.requireNonNull(blueShapesJComboBox.getSelectedItem()).toString()));
            blueShapesJComboBox.setEnabled(false);
            redShapesJComboBox.setEnabled(true);
        }
        if (grid.getGeneration() % 5 == 0 && !(grid.getGeneration() % 10 == 0)) {
            listener.setCurrentPlayer(Player.RED);
            listener.setCurrentShape((Objects.requireNonNull(redShapesJComboBox.getSelectedItem()).toString()));
            redShapesJComboBox.setEnabled(false);
            blueShapesJComboBox.setEnabled(true);
        }
    }

    private void clearBoard(GridMouseListener listener) {
        play = false;
        grid.clearGrid();
        generation.setText(grid.toString());
        view.repaint();
        blueShapesJComboBox.setEnabled(true);
        playButton.setEnabled(true);
        listener.setClick();
        redShapesJComboBox.setEnabled(false);

    }

    private void playLoop(GridMouseListener listener) {
        if (listener.getClick()) {
            play = !play;
            Thread thread = new Thread(() -> {
                while (play) {
                    grid.goToNextGeneration();
                    generation.setText(grid.toString());
                    view.repaint();
                    try {
                        if (grid.getGeneration() < 150) {
                            Thread.sleep(180);
                        } else {
                            Thread.sleep(250);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (grid.getGeneration() % 5 == 0 && grid.getGeneration() < 150) {
                        play = !play;
                    }
                    if (grid.getGeneration() >= 150) {
                        redShapesJComboBox.setEnabled(false);
                        blueShapesJComboBox.setEnabled(false);
                        playButton.setEnabled(false);
                    }
                    if (grid.getGeneration() == 500) {
                        play = !play;
                        int winner = 1;
                        if (grid.getBlueNum() < grid.getRedNum()) {
                            winner = 2;
                        }
                        JOptionPane.showMessageDialog(null, "Game over. Winner is: Player " + winner
                                + "\n              Player 1 total: " + grid.getBlueNum()
                                + "\n              Player 2 total: " + grid.getRedNum());
                    }
                }
            });
            thread.start();
        }
        listener.setClick();
    }
}

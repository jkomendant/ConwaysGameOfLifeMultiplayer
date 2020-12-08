package conways.game.of.life.multiplayer;

import javax.swing.*;
import java.awt.*;


public class GridView extends JComponent {

    /**
     * Paints the cell red, blue or gray depending on whether
     * the cell is holding player BLUE, RED, or DEAD
     */

    public static final int CELL_SIZE = 9;
    public static final int BORDERED_CELL_SIZE = 10;
    private static final Color PLAYER_1 = Color.CYAN;
    private static final Color PLAYER_2 = Color.RED;
    private static final Color DARK_GRAY = new Color(102, 102, 102);
    private static final Color OUT_OF_BOUNDS = new Color(67, 78, 97);
    private final Grid grid;

    public GridView(Grid grid) {
        this.grid = grid;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintLifeStatus(g);
    }

    public Grid getGrid() {
        return grid;
    }

    private void paintLifeStatus(Graphics g) {

        for (int row = 0; row < Grid.ROW; row++) {
            Color DEAD_COLOR;
            for (int col = 0; col < Grid.COL; col++) {
                if(row > 50 && row < 95 && col > 1 && col < 77){
                    DEAD_COLOR = DARK_GRAY;
                }else if(row < 48  && row > 4 && col > 1 && col < 77){
                    DEAD_COLOR = Color.GRAY;
                }else{
                    DEAD_COLOR = OUT_OF_BOUNDS;
                }
                if (grid.getCell(row, col) == Player.BLUE) {
                    g.setColor(grid.getNumAliveNeighbors(row, col) == 3
                            && (grid.getRedAliveNeighbors(row, col) > grid.getBlueAliveNeighbors(row, col))
                            ? PLAYER_2 : PLAYER_1);
                } else if (grid.getCell(row, col) == Player.RED) {
                    g.setColor(grid.getNumAliveNeighbors(row, col) == 3
                            && (grid.getBlueAliveNeighbors(row, col) > grid.getRedAliveNeighbors(row, col))
                            ? PLAYER_1 : PLAYER_2);
                } else if (grid.getCell(row, col) == Player.DEAD) {
                    if (grid.getNumAliveNeighbors(row, col) == 3) {
                        g.setColor(((grid.getBlueAliveNeighbors(row, col) == 2 && grid.getRedAliveNeighbors(row, col) == 1)
                                || (grid.getBlueAliveNeighbors(row, col) == 3 && grid.getRedAliveNeighbors(row, col) == 0))
                                ? PLAYER_1 : PLAYER_2);
                    }else {
                        g.setColor(DEAD_COLOR);
                    }
                } else {
                    g.setColor(DEAD_COLOR);
                }
                g.fillRect(row * BORDERED_CELL_SIZE, col * BORDERED_CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

}

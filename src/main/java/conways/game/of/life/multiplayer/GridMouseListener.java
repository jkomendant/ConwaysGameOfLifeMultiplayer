package conways.game.of.life.multiplayer;

import java.awt.event.MouseEvent;

import static conways.game.of.life.multiplayer.GridView.BORDERED_CELL_SIZE;


public class GridMouseListener implements java.awt.event.MouseListener {
    private final Grid grid;
    private Player currentPlayer = Player.BLUE;
    private Shapes currentShape = null;
    private boolean click = false;

    public GridMouseListener(Grid grid) {
        this.grid = grid;
    }

    public void setCurrentPlayer(Player player) {
        if (player == Player.BLUE) {
            currentPlayer = Player.BLUE;
        } else {
            currentPlayer = Player.RED;
        }
    }

    public void setClick() {
        click = false;
    }

    public boolean getClick() {
        return click;
    }

    public void setCurrentShape(String shape) {
        switch (shape) {
            case "BEEHIVE" -> currentShape = Shapes.BEEHIVE;
            case "BOAT" -> currentShape = Shapes.BOAT;
            case "GLIDER" -> currentShape = Shapes.GLIDER;
            case "HEAVYWEIGHT_SPACESHIP" -> currentShape = Shapes.HEAVYWEIGHT_SPACESHIP;
            case "LIGHTWEIGHT_SPACESHIP" -> currentShape = Shapes.LIGHTWEIGHT_SPACESHIP;
            case "LINE" -> currentShape = Shapes.LINE;
            case "LOAF" -> currentShape = Shapes.LOAF;
            case "MIDDLEWEIGHT_SPACESHIP" -> currentShape = Shapes.MIDDLEWEIGHT_SPACESHIP;
            case "SHIP" -> currentShape = Shapes.SHIP;
            case "SQUARE" -> currentShape = Shapes.SQUARE;
            case "TRAFFIC_LIGHT" -> currentShape = Shapes.TRAFFIC_LIGHT;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        boolean legal = false;
        int row = e.getX();
        int col = e.getY();
        int rowIndex = row / BORDERED_CELL_SIZE;
        int colIndex = col / BORDERED_CELL_SIZE;

        if((rowIndex < 48  && rowIndex > 4 && colIndex > 1 && colIndex < 77 && currentPlayer == Player.BLUE)
        || (rowIndex > 50 && rowIndex < 95 && colIndex > 1 && colIndex < 77 && currentPlayer == Player.RED)){
            legal = true;
        }

        if(!click && legal){
            grid.setCell(rowIndex, colIndex, currentPlayer, currentShape);
            click = true;
            currentShape = Shapes.BLANK;
        }
        e.getComponent().repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

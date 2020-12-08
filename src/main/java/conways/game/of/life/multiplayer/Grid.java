package conways.game.of.life.multiplayer;

public class Grid {

    /**
     * Instantiates the grid and decides if the cell will be alive
     * or dead in the next generation
     * <p>
     * Author: Jennifer Komendant
     */

    public final static int ROW = 100;
    public final static int COL = 80;
    private Player[][] gridArray = new Player[ROW][COL];
    private int generation = 0;
    private int blueNum = 0;
    private int redNum = 0;
    private final int[][] totalAliveNeighbors = new int[ROW][COL];
    private final int[][] blueAliveNeighbors = new int[ROW][COL];
    private final int[][] redAliveNeighbors = new int[ROW][COL];


    public Grid() {

    }

    public void goToNextGeneration() {
        setAliveNeighbors();
        Player[][] futureArray = new Player[ROW][COL];
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                calculateAliveNeighbors(row, col);
                if ((gridArray[row][col] == Player.BLUE || gridArray[row][col] == Player.RED)
                        && (totalAliveNeighbors[row][col] < 2 || (totalAliveNeighbors[row][col] > 3))) {
                    futureArray[row][col] = Player.DEAD;
                } else if (totalAliveNeighbors[row][col] == 3) {
                    if (blueAliveNeighbors[row][col] >= 2) {
                        futureArray[row][col] = Player.BLUE;
                        blueNum++;
                    } else {
                        futureArray[row][col] = Player.RED;
                        redNum++;
                    }
                } else if ((gridArray[row][col] == Player.BLUE) && (totalAliveNeighbors[row][col] == 2)) {
                    if (redAliveNeighbors[row][col] == 2) {
                        futureArray[row][col] = Player.RED;
                        redNum++;
                    } else {
                        futureArray[row][col] = Player.BLUE;
                        blueNum++;
                    }
                } else if ((gridArray[row][col] == Player.RED) && (totalAliveNeighbors[row][col] == 2)) {
                    if (blueAliveNeighbors[row][col] == 2) {
                        futureArray[row][col] = Player.BLUE;
                        blueNum++;
                    } else {
                        futureArray[row][col] = Player.RED;
                        redNum++;
                    }
                } else {
                    futureArray[row][col] = gridArray[row][col];
                }
                totalAliveNeighbors[row][col] = totalAliveNeighbors[row][col];
            }
        }
        gridArray = futureArray;
        increaseGeneration();
    }

    public void calculateAliveNeighbors(int row, int column) {
        for (int neighborRow = -1; neighborRow <= 1; neighborRow++) {
            for (int neighborCol = -1; neighborCol <= 1; neighborCol++) {
                //checks that the row and column is not out of bounds
                //and that the cell being checked does not check itself
                if ((row + neighborRow) != -1 && (column + neighborCol) != -1
                        && (row + neighborRow != row || column + neighborCol != column)
                        && row + neighborRow != ROW && column + neighborCol != COL
                        && ((gridArray[row + neighborRow][column + neighborCol] == Player.BLUE)
                        || (gridArray[row + neighborRow][column + neighborCol] == Player.RED))) {
                    totalAliveNeighbors[row][column]++;
                    if (gridArray[row + neighborRow][column + neighborCol] == Player.BLUE) {
                        blueAliveNeighbors[row][column]++;
                    }
                    if (gridArray[row + neighborRow][column + neighborCol] == Player.RED) {
                        redAliveNeighbors[row][column]++;
                    }
                }
            }
        }
    }

    public void clearGrid() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                gridArray[row][col] = Player.DEAD;
            }
        }
        generation = 0;
        setAliveNeighbors();
    }

    public int getGeneration() {
        return generation;
    }

    public int getNumAliveNeighbors(int row, int col) {
        return totalAliveNeighbors[row][col];
    }

    public int getBlueAliveNeighbors(int row, int col) {
        return blueAliveNeighbors[row][col];
    }

    public int getRedAliveNeighbors(int row, int col) {
        return redAliveNeighbors[row][col];
    }

    public String toString() {
        return "Current generation: " + generation
                + "        Player 1 Total: " + blueNum
                + "        Player 2 total: " + redNum;
    }

    public void increaseGeneration() {
        generation++;
    }

    public int getBlueNum() {
        return blueNum;
    }

    public int getRedNum() {
        return redNum;
    }

    public void setAliveNeighbors() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                totalAliveNeighbors[row][col] = 0;
                blueAliveNeighbors[row][col] = 0;
                redAliveNeighbors[row][col] = 0;
            }
        }
        redNum = 0;
        blueNum = 0;
    }

    public boolean legalMove(int row, int col, Player player){
        boolean legal = false;
        if(row < 48  && row > 4 && col > 1 && col < 77 && player == Player.BLUE){
            legal = true;
        }
        if(row > 50 && row < 95 && col > 1 && col < 77 && player == Player.RED){
            legal = true;
        }
        return legal;
    }

    public Player getCell(int row, int col) {
        return gridArray[row][col];
    }

    public void setCell(int row, int col, Player value, Shapes shape) {
        try {
            if(legalMove(row, col, value)) {
                switch (shape) {
                    case BEEHIVE -> setBeehive(row, col, value);
                    case BOAT -> setBoat(row, col, value);
                    case GLIDER -> {
                        if (value == Player.BLUE) {
                            setGlider1(row, col, value);
                        } else if (value == Player.RED) {
                            setGlider2(row, col, value);
                        }
                    }
                    case HEAVYWEIGHT_SPACESHIP -> {
                        if (value == Player.BLUE) {
                            setHeavyWeightSpaceShip1(row, col, value);
                        } else if (value == Player.RED) {
                            setLargeSpaceShip2(row, col, value);
                        }
                    }
                    case LIGHTWEIGHT_SPACESHIP -> {
                        if (value == Player.BLUE) {
                            setLightWeightSpaceShip1(row, col, value);
                        } else if (value == Player.RED) {
                            setLightWeightSpaceShip2(row, col, value);
                        }
                    }
                    case LINE -> setLine(row, col, value);
                    case LOAF -> setLoaf(row, col, value);
                    case MIDDLEWEIGHT_SPACESHIP -> {
                        if (value == Player.BLUE) {
                            setMiddleWeightSpaceShip1(row, col, value);
                        } else if (value == Player.RED) {
                            setMiddleWeightSpaceShip2(row, col, value);
                        }
                    }
                    case SHIP -> setShip(row, col, value);
                    case SQUARE -> setSquare(row, col, value);
                    case TRAFFIC_LIGHT -> setTrafficLight(row, col, value);

                }
            }
        }catch (Exception ignored){

        }
    }

    public void setGlider1(int row, int col, Player value) {
        gridArray[row][col] = value;
        gridArray[row + 1][col + 1] = value;
        gridArray[row - 1][col + 2] = value;
        gridArray[row][col + 2] = value;
        gridArray[row + 1][col + 2] = value;
    }

    public void setGlider2(int row, int col, Player value) {
        gridArray[row][col] = value;
        gridArray[row - 1][col + 1] = value;
        gridArray[row - 1][col + 2] = value;
        gridArray[row][col + 2] = value;
        gridArray[row + 1][col + 2] = value;
    }

    public void setLightWeightSpaceShip1(int row, int col, Player value) {
        gridArray[row][col] = value;
        gridArray[row + 1][col + 1] = value;
        gridArray[row + 1][col + 2] = value;
        gridArray[row + 1][col + 3] = value;
        gridArray[row][col + 3] = value;
        gridArray[row - 1][col + 3] = value;
        gridArray[row - 2][col + 3] = value;
        gridArray[row - 3][col + 2] = value;
        gridArray[row - 3][col] = value;
    }

    public void setLightWeightSpaceShip2(int row, int col, Player value) {
        gridArray[row][col] = value;
        gridArray[row - 1][col + 1] = value;
        gridArray[row - 1][col + 2] = value;
        gridArray[row - 1][col + 3] = value;
        gridArray[row][col + 3] = value;
        gridArray[row + 1][col + 3] = value;
        gridArray[row + 2][col + 3] = value;
        gridArray[row + 3][col + 2] = value;
        gridArray[row + 3][col] = value;
    }

    public void setMiddleWeightSpaceShip1(int row, int col, Player value) {
        gridArray[row][col] = value;
        gridArray[row + 1][col + 1] = value;
        gridArray[row + 1][col + 2] = value;
        gridArray[row + 1][col + 3] = value;
        gridArray[row][col + 3] = value;
        gridArray[row - 1][col + 3] = value;
        gridArray[row - 2][col + 3] = value;
        gridArray[row - 3][col + 3] = value;
        gridArray[row - 4][col + 2] = value;
        gridArray[row - 4][col] = value;
        gridArray[row - 2][col - 1] = value;
    }

    public void setMiddleWeightSpaceShip2(int row, int col, Player value) {
        gridArray[row][col] = value;
        gridArray[row - 1][col + 1] = value;
        gridArray[row - 1][col + 2] = value;
        gridArray[row - 1][col + 3] = value;
        gridArray[row][col + 3] = value;
        gridArray[row + 1][col + 3] = value;
        gridArray[row + 2][col + 3] = value;
        gridArray[row + 3][col + 3] = value;
        gridArray[row + 4][col + 2] = value;
        gridArray[row + 4][col] = value;
        gridArray[row + 2][col - 1] = value;
    }

    public void setHeavyWeightSpaceShip1(int row, int col, Player value) {
        gridArray[row][col] = value;
        gridArray[row + 1][col + 1] = value;
        gridArray[row + 1][col + 2] = value;
        gridArray[row + 1][col + 3] = value;
        gridArray[row][col + 3] = value;
        gridArray[row - 1][col + 3] = value;
        gridArray[row - 2][col + 3] = value;
        gridArray[row - 3][col + 3] = value;
        gridArray[row - 4][col + 3] = value;
        gridArray[row - 5][col + 2] = value;
        gridArray[row - 5][col] = value;
        gridArray[row - 3][col - 1] = value;
        gridArray[row - 2][col - 1] = value;
    }

    public void setLargeSpaceShip2(int row, int col, Player value) {
        gridArray[row][col] = value;
        gridArray[row - 1][col + 1] = value;
        gridArray[row - 1][col + 2] = value;
        gridArray[row - 1][col + 3] = value;
        gridArray[row][col + 3] = value;
        gridArray[row + 1][col + 3] = value;
        gridArray[row + 2][col + 3] = value;
        gridArray[row + 3][col + 3] = value;
        gridArray[row + 4][col + 3] = value;
        gridArray[row + 5][col + 2] = value;
        gridArray[row + 5][col] = value;
        gridArray[row + 3][col - 1] = value;
        gridArray[row + 2][col - 1] = value;
    }

    public void setSquare(int row, int col, Player value) {
        gridArray[row][col] = value;
        gridArray[row + 1][col] = value;
        gridArray[row][col + 1] = value;
        gridArray[row + 1][col + 1] = value;
    }

    public void setBoat(int row, int col, Player value) {
        gridArray[row][col] = value;
        gridArray[row + 1][col] = value;
        gridArray[row][col + 1] = value;
        gridArray[row + 1][col + 2] = value;
        gridArray[row + 2][col + 1] = value;
    }

    public void setBeehive(int row, int col, Player value) {
        gridArray[row][col] = value;
        gridArray[row + 1][col + 1] = value;
        gridArray[row + 1][col + 2] = value;
        gridArray[row][col + 3] = value;
        gridArray[row - 1][col + 1] = value;
        gridArray[row - 1][col + 2] = value;
    }

    public void setLine(int row, int col, Player value) {
        gridArray[row][col] = value;
        gridArray[row][col + 1] = value;
        gridArray[row][col + 2] = value;
    }

    public void setLoaf(int row, int col, Player value) {
        gridArray[row][col] = value;
        gridArray[row + 1][col] = value;
        gridArray[row - 1][col + 1] = value;
        gridArray[row - 1][col + 2] = value;
        gridArray[row + 1][col + 2] = value;
        gridArray[row][col + 3] = value;
        gridArray[row + 2][col + 1] = value;
    }

    public void setShip(int row, int col, Player value) {
        gridArray[row][col] = value;
        gridArray[row - 1][col] = value;
        gridArray[row + 1][col + 1] = value;
        gridArray[row - 1][col + 1] = value;
        gridArray[row + 1][col + 2] = value;
        gridArray[row][col + 2] = value;
    }

    public void setTrafficLight(int row, int col, Player value) {
        gridArray[row][col] = value;
        gridArray[row + 1][col] = value;
        gridArray[row - 1][col] = value;
        gridArray[row + 1][col + 1] = value;
        gridArray[row][col + 1] = value;
        gridArray[row - 1][col + 1] = value;
        gridArray[row + 1][col + 2] = value;
        gridArray[row][col + 2] = value;
        gridArray[row - 1][col + 2] = value;
    }

}

package conways.game.of.life.multiplayer;

public class Main {

    public static void main(String[] args) {
        Grid grid = new Grid();
        GridView view = new GridView(grid);
        GridMouseListener listener = new GridMouseListener(grid);
        GridFrame frame = new GridFrame(listener, view);
        frame.setVisible(true);
    }
}

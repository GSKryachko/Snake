import java.awt.*;
import java.util.ArrayList;


//Controls all the game logic .. most important class in this project.
public class ThreadsController extends Thread {
    ArrayList<ArrayList<SquarePanel>> Squares = new ArrayList<ArrayList<SquarePanel>>();
    Tuple headSnakePos;
    int sizeSnake = 3;
    long speed = 50;
    int width = 20;
    int height = 20;
    public static Direction directionSnake;

    ArrayList<Tuple> positions = new ArrayList<Tuple>();
    Tuple foodPosition;

    ThreadsController(Tuple positionDepart) {
        //Get all the threads
        Squares = Window.Grid;

        headSnakePos = new Tuple(positionDepart.x, positionDepart.y);
        directionSnake = Direction.RIGHT;

        //!!! Pointer !!!!
        Tuple headPos = new Tuple(headSnakePos.getX(), headSnakePos.getY());
        positions.add(headPos);

        foodPosition = new Tuple(Window.height - 1, Window.width - 1);
        spawnFood(foodPosition);

    }

    //Important part :
    public void run() {
        while (true) {
            moveInterne(directionSnake);
            checkCollision();
            moveExterne();
            deleteTail();
            pauser();
        }
    }

    //delay between each move of the snake
    private void pauser() {
        try {
            sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Checking if the snake bites itself or is eating
    private void checkCollision() {
        Tuple posCritique = positions.get(positions.size() - 1);
        for (int i = 0; i <= positions.size() - 2; i++) {
            boolean biteItself = posCritique.getX() == positions.get(i).getX() && posCritique.getY() == positions.get(i).getY();
            if (biteItself) {
                stopTheGame();
            }
        }

        boolean eatingFood = posCritique.getX() == foodPosition.y && posCritique.getY() == foodPosition.x;
        if (eatingFood) {
            System.out.println("Yummy!");
            sizeSnake = sizeSnake + 1;
            foodPosition = getValAleaNotInSnake();

            spawnFood(foodPosition);
        }
    }

    private void stopTheGame() {
        System.out.println("COLISION! \n");
        while (true) {
            pauser();
        }
    }

    private void spawnFood(Tuple foodPositionIn) {
        Squares.get(foodPositionIn.x).get(foodPositionIn.y).ChangeColor(Color.CYAN);
    }

    //return a position not occupied by the snake
    private Tuple getValAleaNotInSnake() {
        Tuple p;
        int ranX = (int) (Math.random() * width);
        int ranY = (int) (Math.random() * height);
        p = new Tuple(ranX, ranY);
        for (int i = 0; i <= positions.size() - 1; i++) {
            if (p.getY() == positions.get(i).getX() && p.getX() == positions.get(i).getY()) {
                ranX = (int) (Math.random() * width);
                ranY = (int) (Math.random() * height);
                p = new Tuple(ranX, ranY);
                i = 0;
            }
        }
        return p;
    }

    //Moves the head of the snake and refreshes the positions in the arraylist
    private void moveInterne(Direction direction) {
        switch (direction) {
            case DOWN:
                headSnakePos.ChangeData(headSnakePos.x, (headSnakePos.y + 1) % height);
                positions.add(new Tuple(headSnakePos.x, headSnakePos.y));
                break;
            case UP:
                headSnakePos.ChangeData(headSnakePos.x, Math.abs(headSnakePos.y - 1 + height) % height);
                positions.add(new Tuple(headSnakePos.x, headSnakePos.y));
                break;
            case LEFT:
                headSnakePos.ChangeData(Math.abs(headSnakePos.x - 1 + width) % width, headSnakePos.y);
                positions.add(new Tuple(headSnakePos.x, headSnakePos.y));
                break;
            case RIGHT:
                headSnakePos.ChangeData(Math.abs(headSnakePos.x + 1) % width, headSnakePos.y);
                positions.add(new Tuple(headSnakePos.x, headSnakePos.y));
                break;
        }
    }

    //Refresh the squares that needs to be
    private void moveExterne() {
        for (Tuple t : positions) {
            int y = t.getX();
            int x = t.getY();
            Squares.get(x).get(y).ChangeColor(Color.blue);

        }
    }

    //Refreshes the tail of the snake, by removing the superfluous data in positions arraylist
    //and refreshing the display of the things that is removed
    private void deleteTail() {
        int cmpt = sizeSnake;
        for (int i = positions.size() - 1; i >= 0; i--) {
            if (cmpt == 0) {
                Tuple t = positions.get(i);
                Squares.get(t.y).get(t.x).ChangeColor(Color.white);
            } else {
                cmpt--;
            }
        }
        cmpt = sizeSnake;
        for (int i = positions.size() - 1; i >= 0; i--) {
            if (cmpt == 0) {
                positions.remove(i);
            } else {
                cmpt--;
            }
        }
    }
}

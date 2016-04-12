package sabal.minimalosmos;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sergey on 10.04.2016.
 */
public class GameManager {
    public static final int MAX_CIRCLES = 10;
    private MainCircle mainCircle;
    private ArrayList<EnemyCircle> circles;
    private ArrayList<Integer> circlesRadius;
    private CanvasView canvasView;
    private static int width;
    private static int height;

    public GameManager(CanvasView canvasView, int w, int h) {
        this.canvasView = canvasView;
        width = w;
        height = h;
        initMainCircle();
        initEnemyCircles();
    }

    private void initEnemyCircles() {
        SimpleCircle mainCircleArea = mainCircle.getCirlceArea();
        circles = new ArrayList<EnemyCircle>();
        circlesRadius = new ArrayList<Integer>();
        do {
            circles.clear();
            circlesRadius.clear();
            for (int i = 0; i < MAX_CIRCLES; i++) {
                EnemyCircle circle;
                do {
                    circle = EnemyCircle.getRandomCircle();
                } while (circle.isIntersect(mainCircleArea));
                circlesRadius.add(circle.getRadius());
                circles.add(circle);
            }
            Collections.sort(circlesRadius);
        } while (!isPossibleWinWith(circlesRadius));
        calculateAndSetCirclesColor();
    }

    private boolean isPossibleWinWith(ArrayList<Integer> radius) {
        int sumOfRadius = MainCircle.INIT_RADIUS;
        for (int i = 0; i < radius.size(); i++) {
            if(radius.get(i) < sumOfRadius) {
                sumOfRadius += radius.get(i);
            } else {
                return false;
            }
        }
        return true;
    }

    private void calculateAndSetCirclesColor() {
        for (EnemyCircle circle : circles) {
            circle.setEnemyOrFoodColorDependsOn(mainCircle);
        }
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    private void initMainCircle() {
        mainCircle = new MainCircle(width / 2, height / 2);
    }

    public void onDraw() {
        canvasView.drawCircle(mainCircle);
        for (EnemyCircle circle : circles) {
            canvasView.drawCircle(circle);
        }

    }

    public void onTouchEvent(int x, int y) {
        mainCircle.moveMainCircleWhenTouchAt(x, y);
        checkCollision();
        moveCircle();
    }

    private void checkCollision() {
        SimpleCircle circleForDel = null;
        for (EnemyCircle circle : circles) {
            if (mainCircle.isIntersect(circle)){
                if (circle.isSmallerThan(mainCircle)) {
                    mainCircle.growRadius(circle);
                    circleForDel = circle;
                    calculateAndSetCirclesColor();
                    break;
                } else {
                    gameEnd("You lose!!");
                    return;
                }
            }
        }
        if (circleForDel != null) {
            circles.remove(circleForDel);
        }
        if (circles.isEmpty()) {
            gameEnd("You win!!");
        }
    }

    private void gameEnd(String text) {
        canvasView.showMessage(text);
        mainCircle.initRadius();
        initEnemyCircles();
        canvasView.redraw();
    }

    private void moveCircle() {
        for (EnemyCircle circle : circles) {
            circle.moveOneStep();
        }
    }
}

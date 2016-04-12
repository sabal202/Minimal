package sabal.minimalosmos;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Sergey on 10.04.2016.
 */
public class EnemyCircle extends SimpleCircle {

    public static final int FROM_RADIUS = 10;
    public static final int TO_RADIUS = 110;
    public static final int ENEMY_COLOR = Color.rgb(239, 72, 54);
    public static final int FOOD_COLOR = Color.rgb(49, 204, 113);
    public static final int RANDOM_SPEED = 7;
    private int dx;
    private int dy;

    public EnemyCircle(int x, int y, int radius, int dx, int dy) {
        super(x, y, radius);
        this.dx = dx;
        this.dy = dy;
    }

    public static EnemyCircle getRandomCircle() {
        Random random = new Random();
        int x = random.nextInt(GameManager.getWidth());
        int y = random.nextInt(GameManager.getHeight());
        int dx = random.nextInt(RANDOM_SPEED);
        int dy = random.nextInt(RANDOM_SPEED);
        int radius = FROM_RADIUS + random.nextInt(TO_RADIUS - FROM_RADIUS);
        EnemyCircle enemyCircle = new EnemyCircle(x, y, radius, dx, dy);
        enemyCircle.setColor(ENEMY_COLOR);
        return enemyCircle;
    }

    public void setEnemyOrFoodColorDependsOn(MainCircle mainCircle) {
        if (isSmallerThan(mainCircle)) {
            setColor(FOOD_COLOR);
        } else {
            setColor(ENEMY_COLOR);
        }
    }

    public boolean isSmallerThan(SimpleCircle circle) {
        if (radius < circle.radius) {
            return true;
        }
        return false;
    }

    public void moveOneStep() {
        x += dx;
        y += dy;
        checkBound();
    }

    private void checkBound() {
        if (x > GameManager.getWidth() || x < 10){
            dx = -dx;
        }
        if (y > GameManager.getHeight() || y < 10){
            dy = -dy;
        }
    }

}

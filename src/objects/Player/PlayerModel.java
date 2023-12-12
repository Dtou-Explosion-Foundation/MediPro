package objects.Player;

import objects.base.GameObject.GameObjectModel;

public class PlayerModel extends GameObjectModel {

    // movement
    float speedX = 0;
    float speedLimitX = 100;
    float resitX = 3f;
    float accX = 5f;

    // sprite animation
    int spritesIndex = 0;
    int[] spritesRange = { 0, 3 };
    final static float changeSpriteTime = 30;
    float changeSpriteTimer = changeSpriteTime;

    public PlayerModel() {
        super();
        logger.info("PlayerModel created");
    }

    @Override
    protected void finalize() throws Throwable {
        logger.info("PlayerModel destroyed");
    }

    public void moveRight() {
        speedX += accX;
    }

    public void moveLeft() {
        speedX -= accX;
    }

    @Override
    public void update(float dt) {

        // update position
        x += speedX;

        // apply resistance
        if (speedX > 0) {
            speedX -= resitX;
            if (speedX < 0)
                speedX = 0;
        } else if (speedX < 0) {
            speedX += resitX;
            if (speedX > 0)
                speedX = 0;
        }

        // update sprite animation
        changeSpriteTimer -= dt;
        if (changeSpriteTimer <= 0) {
            if (++spritesIndex > spritesRange[1])
                spritesIndex = spritesRange[0];
            // changeSpriteTimer = changeSpriteTime / (this.speedX / this.speedLimitX);
            changeSpriteTimer = changeSpriteTime;
        }
    }
}

package medipro.object.player;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

public class PlayerModel extends GameObjectModel {

    // movement
    double speedX = 0;
    double speedLimitX = 100;
    double resitX = 600;
    double accX = 1200;

    // sprite animation
    int spritesIdleIndex = 1;
    int spritesIndex = 0;
    int[] spritesRange = { 0, 3 };
    final static float changeSpriteTime = 0.15f;
    byte direction = 1;
    Boolean isWalking = false;
    float changeSpriteTimer = 0;

    public PlayerModel(World world) {
        super(world);
        logger.info("PlayerModel created");
    }

    @Override
    protected void finalize() throws Throwable {
        logger.info("PlayerModel destroyed");
    }

    public void moveRight() {
        direction = 1;
        isWalking = true;
    }

    public void moveLeft() {
        direction = -1;
        isWalking = true;
    }

    public void updateAnimation(float dt) {
        // update sprite animation
        changeSpriteTimer += dt;
        if (changeSpriteTimer > changeSpriteTime / (Math.abs(this.speedX) / this.speedLimitX)) {
            if (++spritesIndex > spritesRange[1])
                spritesIndex = spritesRange[0];
            changeSpriteTimer = 0;
        }
    }

    public void updateMovement(float dt) {
        // apply movement
        if (isWalking) {
            speedX += accX * direction * dt;
            isWalking = false;
        } else {
            spritesIndex = spritesIdleIndex;
        }

        // update position
        x += speedX * dt;

        // apply resistance
        if (speedX > 0) {
            speedX -= resitX * dt;
            if (speedX < 0)
                speedX = 0;
        } else if (speedX < 0) {
            speedX += resitX * dt;
            if (speedX > 0)
                speedX = 0;
        }

        // apply speed limit
        if (speedX > speedLimitX)
            speedX = speedLimitX;
        else if (speedX < -speedLimitX)
            speedX = -speedLimitX;
    }
}

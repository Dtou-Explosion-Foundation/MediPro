package medipro.object.camera;

import medipro.object.base.gameobject.GameObjectModel;

public class SmoothFollowingCameraModel extends FollowingCameraModel {

    public float followingSpeed = 0.1f;

    public SmoothFollowingCameraModel(GameObjectModel target) {
        super(target);
    }

    @Override
    public void update(float dt) {
        if (target.isPresent()) {
            this.x = (int) ((target.get().x - this.x) * followingSpeed + this.x);
            this.y = (int) ((target.get().y - this.y) * followingSpeed + this.y);
        }
    }

}

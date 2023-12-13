package medipro.object.camera;

import medipro.object.base.gameobject.GameObjectModel;

public class SmoothFollowingCameraModel extends FollowingCameraModel {

    public float followingSpeed = 0.1f;

    public SmoothFollowingCameraModel(GameObjectModel target) {
        super(target);
    }
}

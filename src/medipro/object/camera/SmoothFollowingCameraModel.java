package medipro.object.camera;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

public class SmoothFollowingCameraModel extends FollowingCameraModel {

    public float followingSpeed = 0.1f;

    public SmoothFollowingCameraModel(World world, GameObjectModel target) {
        super(world, target);
    }
}

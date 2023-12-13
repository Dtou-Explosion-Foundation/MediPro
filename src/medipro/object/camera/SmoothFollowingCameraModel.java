package medipro.object.camera;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.world.World;

public class SmoothFollowingCameraModel extends FollowingCameraModel {

    public Double followingSpeed = 0.1;

    public SmoothFollowingCameraModel(World world, GameObjectModel target) {
        super(world, target);
    }
}

package medipro.object.camera;

import java.util.Optional;

import medipro.object.base.camera.CameraModel;
import medipro.object.base.gameobject.GameObjectModel;

public class FollowingCameraModel extends CameraModel {

    public Optional<GameObjectModel> target = Optional.empty();

    public int originX = 0;
    public int originY = 0;

    public FollowingCameraModel(GameObjectModel target) {
        super();
        this.target = Optional.of(target);
    }
}

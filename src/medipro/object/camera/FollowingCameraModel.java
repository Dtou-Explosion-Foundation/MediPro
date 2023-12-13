package medipro.object.camera;

import java.util.Optional;

import medipro.object.base.camera.CameraModel;
import medipro.object.base.gameobject.GameObjectModel;

public class FollowingCameraModel extends CameraModel {

    public Optional<GameObjectModel> target = Optional.empty();

    // public FollowingCameraModel() {
    // super();
    // }

    public FollowingCameraModel(GameObjectModel target) {
        super();
        this.target = Optional.of(target);
    }

    @Override
    public void update(float dt) {
        if (target.isPresent()) {
            this.x = target.get().x;
            this.y = target.get().y;
        }
    }

}

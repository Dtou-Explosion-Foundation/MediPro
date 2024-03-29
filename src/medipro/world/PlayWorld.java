package medipro.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import medipro.anomaly.CameraAnomaly;
import medipro.anomaly.PlayerIncreasedAnomaly;
import medipro.anomaly.PlayerMovementAnomaly;
import medipro.anomaly.ScaleChangeAnomaly;
import medipro.anomaly.TextureAlternatingChangeAnomaly;
import medipro.anomaly.TextureChangeAnomaly;
import medipro.anomaly.TextureIncreasedAnomaly;
import medipro.anomaly.TextureMoveAnomaly;
import medipro.anomaly.TextureSizeChangingAnomaly;
import medipro.gui.panel.GamePanel;
import medipro.object.base.camera.CameraView;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.camera.SmoothFollowingCameraController;
import medipro.object.camera.SmoothFollowingCameraModel;
import medipro.object.manager.gamemanager.GameManagerController;
import medipro.object.manager.gamemanager.GameManagerModel;
import medipro.object.ornament.texture.TextureObjectController;
import medipro.object.ornament.texture.TextureObjectModel;
import medipro.object.ornament.texture.TextureObjectView;
import medipro.object.overlay.blackfilter.BlackFilterController;
import medipro.object.overlay.blackfilter.BlackFilterModel;
import medipro.object.overlay.blackfilter.BlackFilterView;
import medipro.object.overlay.floor.FloorOverlayModel;
import medipro.object.overlay.floor.FloorOverlayView;
import medipro.object.overlay.fps.FpsOverlayController;
import medipro.object.overlay.fps.FpsOverlayModel;
import medipro.object.overlay.fps.FpsOverlayView;
import medipro.object.overlay.vignette.VignetteController;
import medipro.object.overlay.vignette.VignetteModel;
import medipro.object.overlay.vignette.VignetteView;
import medipro.object.pause.PauseController;
import medipro.object.pause.PauseModel;
import medipro.object.pause.PauseView;
import medipro.object.player.PlayerController;
import medipro.object.player.PlayerModel;
import medipro.object.player.PlayerView;
import medipro.object.stage.background.BackgroundController;
import medipro.object.stage.background.BackgroundModel;
import medipro.object.stage.background.BackgroundView;
import medipro.object.stage.ceil.CeilController;
import medipro.object.stage.ceil.CeilModel;
import medipro.object.stage.ceil.CeilView;
import medipro.object.stage.floor.FloorController;
import medipro.object.stage.floor.FloorModel;
import medipro.object.stage.floor.FloorView;
import medipro.object.stage.stairs.StairsController;
import medipro.object.stage.stairs.StairsModel;
import medipro.object.stage.stairs.StairsView;
import medipro.object.stage.wall.WallController;
import medipro.object.stage.wall.WallModel;
import medipro.object.stage.wall.WallView;

/**
 * プレイワールド.ゲームをプレイするためのワールド.
 */
public class PlayWorld extends World {

    /**
     * PlayWorldを生成する.
     * 
     * @param panel ワールドを表示するパネル
     */
    public PlayWorld(GamePanel panel) {
        super(panel);
    }

    @Override
    public void setupWorld(JPanel panel) {
        // Layers
        // 00X: Background
        // 01X: Ornament
        // 02X: Stairs
        // 03X: Ornament
        // 04X: Ornament
        // 05X: Player
        // 06X: Ornament
        // 07X: Ornament
        // 08X: Wall
        // 10X: Overlay
        // 11X: GUI
        GameObjectModel cameraTarget = null;
        {
            PauseModel model = new PauseModel(this);
            PauseView view = new PauseView(model);
            PauseController controller = new PauseController(model);
            panel.addKeyListener(controller);
            this.addViewAndController(view, controller, 110);
        }
        {
            PlayerModel model = new PlayerModel(this);
            cameraTarget = model;
            model.setY(-80);
            model.setX(-400);
            PlayerView view = new PlayerView(model);
            PlayerController controller = new PlayerController(model);
            panel.addKeyListener(controller);
            this.addViewAndController(view, controller, 50);
            {
                PlayerMovementAnomaly playerMovementAnomaly = new PlayerMovementAnomaly(model);
                this.addControllers(playerMovementAnomaly);
            }
            {
                PlayerIncreasedAnomaly playerIncreasedAnomaly = new PlayerIncreasedAnomaly(model);
                this.addControllers(playerIncreasedAnomaly);
            }
        }
        {
            FloorOverlayModel model = new FloorOverlayModel(this);
            FloorOverlayView view = new FloorOverlayView(model);
            this.addView(view, 101);
        }
        {
            FpsOverlayModel model = new FpsOverlayModel(this);
            FpsOverlayView view = new FpsOverlayView(model);
            FpsOverlayController controller = new FpsOverlayController(model);
            this.addViewAndController(view, controller, 110);
        }
        {
            VignetteModel model = new VignetteModel(this);
            VignetteView view = new VignetteView(model);
            VignetteController controller = new VignetteController(model);
            this.addViewAndController(view, controller, 100);
        }
        {
            StairsModel model = new StairsModel(this);
            model.setX(-500);
            model.setY(-65);
            model.setTriggerRange(100f);
            StairsView view = new StairsView(model);
            StairsController controller = new StairsController(model);
            this.addViewAndController(view, controller, 20);
        }
        {
            StairsModel model = new StairsModel(this);
            model.setX(500);
            model.setY(-65);
            model.setTriggerRange(100f);
            StairsView view = new StairsView(model);
            StairsController controller = new StairsController(model);
            this.addViewAndController(view, controller, 20);
        }
        {
            WallModel model = new WallModel(this);
            WallView view = new WallView(model);
            WallController controller = new WallController(model);
            this.addViewAndController(view, controller, 80);
        }
        {
            BackgroundModel model = new BackgroundModel(this);
            BackgroundController controller = new BackgroundController(model);
            BackgroundView view = new BackgroundView(model);
            this.addViewAndController(view, controller, 0);
        }
        {
            CeilModel model = new CeilModel(this);
            model.setY(-160);
            CeilController controller = new CeilController(model);
            CeilView view = new CeilView(model);
            this.addViewAndController(view, controller, 1);

        }
        {
            FloorModel model = new FloorModel(this);
            model.setY(100);
            FloorController controller = new FloorController(model);
            FloorView view = new FloorView(model);
            this.addViewAndController(view, controller, 1);
        }
        {
            TextureObjectModel model = new TextureObjectModel(this,

                    new String[] { "img/ornament/door/door-nomal.png", "img/ornament/door/door-human.png",
                            "img/ornament/door/door-open.png", "img/ornament/extinction.png" });

            model.setScaleX(0.11);
            model.setScaleY(0.11);
            model.setX(-300);
            model.setY(15);
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureChangeAnomaly(model));
        }

        {
            TextureObjectModel model = new TextureObjectModel(this,

                    new String[] { "img/ornament/extinction.png", "img/ornament/zipper1.png", });
            model.setScaleX(2);
            model.setScaleY(2);
            model.setX(-200);
            model.setY(47);
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureChangeAnomaly(model));
        }
        {
            TextureObjectModel model = new TextureObjectModel(this,
                    new String[] { "img/ornament/clock/clock-nomal.png", "img/ornament/clock/clock-breaking.png",
                            "img/ornament/clock/clock-toocircles.png", "img/ornament/clock/clock-toohands.png",
                            "img/ornament/clock/clock-human.png", "img/ornament/extinction.png" });

            model.setScaleX(1);
            model.setScaleY(1);
            model.setX(-100);
            model.setY(100);
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureChangeAnomaly(model));
            {
                ScaleChangeAnomaly scaleChangeAnomaly = new ScaleChangeAnomaly(model);
                scaleChangeAnomaly.setScaleList(new ArrayList<Double>(List.of(0.6, 1.5, 2.0)));
                this.addControllers(scaleChangeAnomaly);
            }
        }
        {
            TextureObjectModel model = new TextureObjectModel(this,

                    new String[] { "img/ornament/extinction.png", "img/character/charanomal1.png", });
            model.setScaleX(1);
            model.setScaleY(1);
            model.setX(525);
            model.setY(-100);
            model.deltaX = -3.5;
            model.deltaY = 0;
            model.delta2X = 0.005;
            model.delta2Y = 0;
            model.interval = 0.01;
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureMoveAnomaly(model));
        }
        {
            TextureObjectModel model = new TextureObjectModel(this,
                    new String[] { "img/ornament/fun/fun.png", "img/ornament/fun/fun-death.png" });
            model.setScaleX(0.15);
            model.setScaleY(0.15);
            model.setX(-300);
            model.setY(100);
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureChangeAnomaly(model));
        }
        {
            TextureObjectModel model = new TextureObjectModel(this, new String[] { "img/ornament/extinction.png",
                    "img/ornament/fun/fun.png", "img/ornament/fun/fun1.png", "img/ornament/fun/fun2.png" });
            model.setScaleX(0.15);
            model.setScaleY(0.15);
            model.setX(-300);
            model.setY(100);
            model.interval = 0.2;
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureAlternatingChangeAnomaly(model));
        }
        {
            TextureObjectModel model = new TextureObjectModel(this,
                    new String[] { "img/ornament/exit/exit-nomal.png", "img/ornament/exit/exit-longpillar.png",
                            "img/ornament/exit/exit-anomalycolor.png", "img/ornament/exit/exit-longarrow.png",
                            "img/ornament/exit/exit-uparrow.png", "img/ornament/extinction.png" });
            model.setScaleX(1.5);
            model.setScaleY(1.5);
            model.setX(100);
            model.setY(120);
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureChangeAnomaly(model));
        }
        {
            TextureObjectModel model = new TextureObjectModel(this,
                    new String[] { "img/ornament/exit/exit-nomal.png" });
            model.setScaleX(1.5);
            model.setScaleY(1.5);
            model.setX(100);
            model.setY(120);
            model.deltaX = 100;
            model.deltaY = 0;
            model.timesX = 2;
            model.timesY = 0;
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureIncreasedAnomaly(model));
        }
        {
            TextureObjectModel model = new TextureObjectModel(this, new String[] { "img/ornament/extinction.png",
                    "img/ornament/exit/exit-nomal.png", "img/ornament/exit/exit-reverse.png" });
            model.setScaleX(1.5);
            model.setScaleY(1.5);
            model.setX(100);
            model.setY(120);
            model.interval = 0.2;
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureAlternatingChangeAnomaly(model));
        }
        {
            TextureObjectModel model = new TextureObjectModel(this,

                    new String[] { "img/ornament/whitebord/whitebord-nomal.png",
                            "img/ornament/whitebord/whitebord-table.png",
                            "img/ornament/whitebord/whitebord-redroom.png", "img/ornament/extinction.png" });
            model.setScaleX(0.5);
            model.setScaleY(0.5);
            model.setX(200);
            model.setY(75);
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureChangeAnomaly(model));
        }
        {
            TextureObjectModel model = new TextureObjectModel(this,

                    new String[] { "img/ornament/extinction.png", "img/ornament/idiot/Idiot1.png",
                            "img/ornament/idiot/Idiot2.png" });
            model.setScaleX(1.5);
            model.setScaleY(1.27);
            model.setX(213);
            model.setY(47);
            model.interval = 0.3;
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureAlternatingChangeAnomaly(model));
        }
        {
            TextureObjectModel model = new TextureObjectModel(this,
                    new String[] { "img/ornament/table/table-edge1-nomal.png", "img/ornament/table/table-edge1-big.png",
                            "img/ornament/table/table-edge1-fire.png",
                            "img/ornament/table/table-edge1-increase_leg.png" });
            model.setScaleX(2.2);
            model.setScaleY(2.2);
            model.setX(103);
            model.setY(20);
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureChangeAnomaly(model));
        }
        {
            TextureObjectModel model = new TextureObjectModel(this, new String[] {
                    "img/ornament/table/table-mid-nomal.png", "img/ornament/table/table-mid-blood.png" });
            model.setScaleX(2.2);
            model.setScaleY(2.2);
            model.setX(210);
            model.setY(20);
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureChangeAnomaly(model));
        }
        {
            TextureObjectModel model = new TextureObjectModel(this,
                    new String[] { "img/ornament/table/table-edge2-nomal.png",
                            "img/ornament/table/table-edge2-increase_chare.png",
                            "img/ornament/table/table-edge2-locate_switch.png" });
            model.setScaleX(2.2);
            model.setScaleY(2.2);
            model.setX(258);
            model.setY(20);
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureChangeAnomaly(model));
        }
        {
            TextureObjectModel model = new TextureObjectModel(this,
                    new String[] { "img/ornament/table/table3.png", "img/ornament/extinction.png" });
            model.setScaleX(2.2);
            model.setScaleY(2.2);
            model.setX(-150);
            model.setY(20);
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureChangeAnomaly(model));
        }
        {
            TextureObjectModel model = new TextureObjectModel(this,

                    new String[] { "img/ornament/extinction.png", "img/ornament/deathman/deathman0.png",
                            "img/ornament/deathman/deathman1.png" });
            model.setScaleX(2);
            model.setScaleY(2);
            model.setX(-130);
            model.setY(5);
            model.interval = 0.5;
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureAlternatingChangeAnomaly(model));
        }
        {
            TextureObjectModel model = new TextureObjectModel(this,

                    new String[] { "img/ornament/extinction.png", "img/ornament/ball.png", });
            model.setScaleX(2);
            model.setScaleY(2);
            model.setX(-600);
            model.setY(-40);
            model.deltaX = 10;
            model.deltaY = 6;
            model.delta2X = 0;
            model.delta2Y = -0.1;
            model.interval = 0.03;
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureMoveAnomaly(model));
        }
        {
            TextureObjectModel model = new TextureObjectModel(this,

                    new String[] { "img/ornament/extinction.png", "img/ornament/black-circle.png" });
            model.setScaleX(0);
            model.setScaleY(0);
            model.setX(-600);
            model.setY(250);
            model.deltaX = 0.01;
            model.deltaY = 0.01;
            model.delta2X = 0;
            model.delta2Y = 0;
            model.interval = 0.05;
            TextureObjectView view = new TextureObjectView(model);
            TextureObjectController controller = new TextureObjectController(model);
            this.addViewAndController(view, controller, 30);
            this.addControllers(new TextureSizeChangingAnomaly(model));
        }
        {
            SmoothFollowingCameraModel model = new SmoothFollowingCameraModel(this, cameraTarget);
            model.setRawScale(2);
            model.followingSpeed = 4.0;
            model.setFlipSpeed(40);
            model.setMinX(-300);
            model.setMaxX(300);
            model.originY = 70;
            model.originX = 15;
            model.setFollowingRateY(0.85);
            CameraView view = new CameraView(model);
            SmoothFollowingCameraController controller = new SmoothFollowingCameraController(model);
            controller.forceFollow();
            this.addViewAndController(view, controller);
            {
                CameraAnomaly cameraAnomaly = new CameraAnomaly(model);
                cameraAnomaly.setOccurredChance(1);
                this.addControllers(cameraAnomaly);
            }
            this.setCamera(Optional.of(model));
        }
        {
            BlackFilterModel model = new BlackFilterModel(this);
            BlackFilterController controller = new BlackFilterController(model);
            BlackFilterView view = new BlackFilterView(model);
            model.setAlpha(1f);
            controller.blackOut(2f);
            this.addViewAndController(view, controller, 100);
        }
        {
            GameManagerModel model = new GameManagerModel(this);
            GameManagerController controller = new GameManagerController(model);
            this.addControllers(controller);
        }
    }
}

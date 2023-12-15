package medipro.world;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

import javax.swing.JPanel;

import medipro.config.EngineConfig;
import medipro.object.base.camera.CameraModel;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectView;

/**
 * ワールドを管理するクラス。 コントローラーとビューを格納し、毎フレームごとに更新と描画を行う。
 * ビューはレイヤーごとに保存され、レイヤー0が最背面に描画される。
 */
public abstract class World {
    /**
     * ロガー.
     */
    protected final Logger logger = Logger.getLogger(this.getClass().getName());
    /**
     * 格納しているコントローラー.
     */
    ArrayList<GameObjectController> controllers;
    /**
     * 格納しているビュー.
     */
    ArrayList<ArrayList<GameObjectView>> views;

    /**
     * Worldが配置されているパネル.
     */
    public JPanel panel;
    /**
     * カメラ.
     */
    public Optional<CameraModel> camera = Optional.empty();

    /**
     * ワールドを生成する.
     * 
     * @param panel ワールドが配置されているパネル
     */
    public World(JPanel panel) {
        logger.info("Init World");
        this.controllers = new ArrayList<GameObjectController>();
        this.views = new ArrayList<ArrayList<GameObjectView>>();
        for (int i = 0; i < EngineConfig.LAYER_NUM; i++) {
            this.views.add(new ArrayList<GameObjectView>());
        }
        setupWorld(panel);
    }

    /**
     * コントローラーを追加する。（複数可）
     * 
     * @param controllers 格納するコントローラー
     */
    public void addControllers(GameObjectController... controllers) {
        for (GameObjectController controller : controllers)
            this.controllers.add(controller);
    }

    /**
     * ビューを追加する。（複数可）
     * 
     * @param views 格納するビュー
     */
    public void addViews(GameObjectView... views) {
        for (GameObjectView view : views)
            this.addView(view, EngineConfig.DEFAULT_LAYER);
    }

    /**
     * レイヤーを指定して、ビューを追加する。
     * 
     * @param view  格納するビュー
     * @param layer レイヤー
     */
    public void addView(GameObjectView view, int layer) {
        this.views.get(layer).add(view);
    }

    /**
     * ビューとコントローラーを追加する。
     * 
     * @param view       格納するビュー
     * @param controller 格納するコントローラー
     */
    public void addViewAndController(GameObjectView view, GameObjectController controller) {
        this.views.get(EngineConfig.DEFAULT_LAYER).add(view);
        this.controllers.add(controller);
    }

    /**
     * レイヤーを指定して、ビューとコントローラーを追加する。
     * 
     * @param view       格納するビュー
     * @param controller 格納するコントローラー
     * @param layer      レイヤー
     */
    public void addViewAndController(GameObjectView view, GameObjectController controller, int layer) {
        this.views.get(layer).add(view);
        this.controllers.add(controller);
    }

    /**
     * ワールドの初期化を行う。 モデル、ビュー、コントローラーを生成し、addViewAndControllerなどで追加する。
     * 
     * @param panel ワールドが配置されているパネル
     */
    public abstract void setupWorld(JPanel panel);

    /**
     * 格納しているコントローラにアップデート通知を送った後、ビューに描画通知を送る。
     * 
     * @param g  Graphics2D
     * @param dt 前フレームからの経過時間
     */
    public void updateAndDraw(Graphics2D g, float dt) {
        for (GameObjectController controller : controllers) {
            controller.updateModels(dt);
        }

        AffineTransform transform;
        if (camera.isPresent()) {
            CameraModel _camera = camera.get();
            transform = ((CameraModel) _camera).getTransformMatrix();
        } else {
            transform = new AffineTransform();
            transform.translate(this.panel.getWidth() / 2, this.panel.getHeight() / 2);
        }

        for (ArrayList<GameObjectView> views : views) {
            for (GameObjectView view : views) {
                view.drawModels(g, transform);
            }
        }
    }
}

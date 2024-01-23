package medipro.object.base;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import medipro.config.EngineConfig;
import medipro.config.InGameConfig;
import medipro.object.AnomalyListener;
import medipro.object.base.camera.CameraModel;
import medipro.object.base.gameobject.GameObjectController;
import medipro.object.base.gameobject.GameObjectView;

/**
 * ワールドを管理するクラス。 コントローラーとビューを格納し、毎フレームごとに更新と描画を行う。 ビューはレイヤーごとに保存され、レイヤー0が最背面に描画される。
 */
public abstract class World implements GLEventListener {
    /**
     * ロガー.
     */
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * 格納しているコントローラー.
     */
    public ArrayList<GameObjectController> controllers;

    /**
     * 格納しているビュー.
     */
    public ArrayList<ArrayList<GameObjectView>> views;

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
        logger.info("Init " + this.getClass().getSimpleName());
        this.controllers = new ArrayList<GameObjectController>();
        this.views = new ArrayList<ArrayList<GameObjectView>>();
        for (int i = 0; i < EngineConfig.LAYER_NUM; i++) {
            this.views.add(new ArrayList<GameObjectView>());
        }
        this.panel = panel;
        setupWorld(panel);
        logger.info(views.stream().mapToInt(ArrayList::size).sum() + " Views and " + controllers.size()
                + " Controllers are added");
        for (GameObjectController controller : controllers)
            controller.postSetupWorld();
    }

    public void dispose() {
        logger.info("Dispose " + this.getClass().getSimpleName());
        for (GameObjectController controller : controllers)
            controller.dispose();
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
     * 格納しているコントローラにアップデート通知を送る。
     * 
     * @param deltaTime 前フレームからの経過時間
     */
    public void update(double deltaTime) {
        for (GameObjectController controller : controllers)
            controller.preUpdate(deltaTime);
        for (GameObjectController controller : controllers)
            controller.update(deltaTime);
        for (GameObjectController controller : controllers)
            controller.postUpdate(deltaTime);
    }

    /**
     * カメラの変換行列を取得する.
     * 
     * @return カメラの変換行列
     */
    public AffineTransform getCameraTransform() {
        if (camera.isPresent()) {
            CameraModel _camera = camera.get();
            return ((CameraModel) _camera).getTransformMatrix();
        } else {
            AffineTransform transform = new AffineTransform();
            transform.translate(InGameConfig.WINDOW_WIDTH / 2, InGameConfig.WINDOW_HEIGHT / 2);
            return transform;
        }
    }

    /**
     * 格納しているビューに描画通知を送る。
     * 
     * @param g Graphics2D
     */
    public void draw(Graphics2D g) {
        for (ArrayList<GameObjectView> views : views) {
            for (GameObjectView view : views) {
                view.draw(g, getCameraTransform());
            }
        }
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        for (ArrayList<GameObjectView> views : views) {
            for (GameObjectView view : views) {
                view.init(drawable);
            }
        }
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        for (ArrayList<GameObjectView> views : views) {
            for (GameObjectView view : views) {
                view.dispose(drawable);
            }
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        for (ArrayList<GameObjectView> views : views) {
            for (GameObjectView view : views) {
                view.display(drawable);
            }
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        for (ArrayList<GameObjectView> views : views) {
            for (GameObjectView view : views) {
                view.reshape(drawable, x, y, w, h);
            }
        }
    }

    /**
     * 指定したクラスのコントローラーを全て取得する.
     * 
     * @param <T>  コントローラーの型
     * @param type コントローラーの型
     * @return コントローラー
     */
    public <T extends GameObjectController> List<T> getControllers(Class<T> type) {
        return controllers.stream().filter(controller -> type.isInstance(controller)).map(controller -> {
            return type.cast(controller);
        }).collect(Collectors.toList());
    }

    /**
     * 格納しているコントローラーを全て取得する.
     * 
     * @return コントローラー
     */
    public List<GameObjectController> getControllers() {
        return controllers;
    }

    /**
     * 異変のリスナーを全て取得する.
     * 
     * @return 異変のリスナー
     */
    public List<AnomalyListener> getAnormalyListeners() {
        return controllers.stream().filter(controller -> AnomalyListener.class.isAssignableFrom(controller.getClass()))
                .map(controller -> {
                    return (AnomalyListener) controller;
                }).collect(Collectors.toList());
    }

    /**
     * ワールドが配置されているパネルを取得する.
     * 
     * @return パネル
     */
    public JPanel getPanel() {
        return panel;
    }

}

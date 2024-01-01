package medipro.object.base.gridobject;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.math.Matrix4f;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import medipro.config.InGameConfig;
import medipro.object.base.camera.CameraModel;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

/**
 * グリッドオブジェクトのビュー.
 */
public abstract class GridObjectView extends GameObjectView {

    /**
     * グリッドオブジェクトのビューを生成する.
     */
    public GridObjectView(GridObjectModel model) {
        super(model);
    }

    /**
     * 描画範囲内のグリッドを計算し、drawGridを呼び出す.
     * 
     * @param g 描画対象のGraphics2D
     */
    @Override
    public void draw(Graphics2D g) {
        g.setTransform(model.world.camera.get().getTransformMatrix());
        GridObjectModel gridModel = (GridObjectModel) model;
        Rectangle2D.Double bounds;
        if (model.world.camera.isPresent()) {
            CameraModel cameraModel = model.world.camera.get();
            Point2D.Double[] points;
            try {
                points = cameraModel.getVisibleArea();
            } catch (NoninvertibleTransformException e) {
                logger.warning("Unable to get visible area, skip drawing tile");
                return;
            }
            bounds = getEnclosingRectangle(points[0], points[1], points[2], points[3]);
        } else {
            bounds = new Rectangle2D.Double(0, 0, InGameConfig.WINDOW_WIDTH, InGameConfig.WINDOW_HEIGHT);
        }

        // 1グリッドのサイズを計算
        int gridWidth = (int) (gridModel.width * gridModel.scaleX);
        int gridHeight = (int) (gridModel.height * gridModel.scaleY);

        // グリッドの原点(最左上)を計算
        int originX = (int) (bounds.x / gridWidth) * gridWidth + (int) (model.x % gridWidth) - gridWidth / 2;
        int originY = (int) (bounds.y / gridHeight) * gridHeight + (int) (model.y % gridHeight) - gridHeight / 2;

        // グリッドの原点の通し番号を計算
        int originGridX = (int) ((originX - model.x) / gridWidth);
        int originGridY = (int) ((originY - model.y) / gridHeight);

        for (int ix = -2; ix < bounds.width / gridWidth + 2; ix++) {
            for (int iy = -2; iy < bounds.height / gridHeight + 2; iy++) {
                AffineTransform transform = g.getTransform();
                g.translate(originX + ix * gridWidth, originY + iy * gridHeight);
                this.drawGrid(gridModel, g, new Rectangle(0, 0, gridWidth, gridHeight), originGridX + ix,
                        -(originGridY + iy));
                g.setTransform(transform);
            }
        }
    }

    /**
     * グリッドを描画する.
     * 
     * @param model 描画対象のモデル.
     * @param g     描画対象のGraphics2D.
     * @param grid  グリッドの範囲.この内部に描画する.
     * @param gridX グリッドのX座標方向のインデックス. 右下が0.
     * @param gridY グリッドのY座標方向のインデックス. 右下が0.
     */
    public abstract void drawGrid(GameObjectModel model, Graphics2D g, Rectangle grid, int gridX, int gridY);

    /**
     * 4点の座標から、その4点を内部に持つ最小の水平な長方形を返す.
     * 
     * @param point1 4点の座標
     * @param point2 4点の座標
     * @param point3 4点の座標
     * @param point4 4点の座標
     * @return 4点を内部に持つ最小の水平な長方形
     */
    private static Rectangle2D.Double getEnclosingRectangle(Point2D.Double point1, Point2D.Double point2,
            Point2D.Double point3, Point2D.Double point4) {
        double minX = Math.min(Math.min(point1.x, point2.x), Math.min(point3.x, point4.x));
        double maxX = Math.max(Math.max(point1.x, point2.x), Math.max(point3.x, point4.x));
        double minY = Math.min(Math.min(point1.y, point2.y), Math.min(point3.y, point4.y));
        double maxY = Math.max(Math.max(point1.y, point2.y), Math.max(point3.y, point4.y));

        double width = maxX - minX;
        double height = maxY - minY;

        return new Rectangle2D.Double(minX, minY, width, height);
    }

    @Override
    protected String getShaderPath(String ext) {
        return "shader/GridObject/GridObject" + "." + ext;
        // return "shader/gameobject/GameObject" + "." + ext;
    }

    @Override
    protected boolean needUpdateTexture() {
        return false;
    }

    @Override
    protected Matrix4f getModelMatrix() {
        Matrix4f tempMat = new Matrix4f();
        Optional<CameraModel> cameraModel = this.model.world.camera;
        float x = cameraModel.isPresent() ? (float) cameraModel.get().x : (float) model.x;
        float y = cameraModel.isPresent() ? (float) cameraModel.get().y : (float) model.y;
        Matrix4f modelMat = new Matrix4f() // モデルの座標変換行列
                .translate(x, y, 0, tempMat) // 座標
                .scale(getSpriteWidth(), getSpriteHeight(), 1, tempMat)// 基準サイズ
                .rotate((float) model.rotation, 0, 0, 1, tempMat) // 回転
        // .scale((float) model.scaleX, (float) model.scaleY, 1, tempMat) // スケーリング
        ;
        return modelMat;
    }

    @Override
    protected void initTextures(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        textureName.clear();
        gl.glGenTextures(1, textureName);
        TextureData textureData;
        try {
            InputStream textureStream = new FileInputStream("img/background/Brickwall3_Texture.png");
            textureData = TextureIO.newTextureData(gl.getGLProfile(), textureStream, false, TextureIO.PNG);
        } catch (IOException e) {
            logger.warning(e.toString());
            return;
        }

        gl.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(0));

        gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_BASE_LEVEL, 0);
        gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAX_LEVEL, 16);

        gl.glTexImage2D(GL4.GL_TEXTURE_2D, 0, textureData.getInternalFormat(), textureData.getWidth(),
                textureData.getHeight(), 0, textureData.getPixelFormat(), textureData.getPixelType(),
                textureData.getBuffer());

        gl.glGenerateMipmap(GL4.GL_TEXTURE_2D);
    }

    @Override
    protected void initSamplers(GLAutoDrawable drawable) {
        super.initSamplers(drawable);
        GL4 gl = drawable.getGL().getGL4();

        gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_LINEAR_MIPMAP_LINEAR);
        gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_LINEAR_MIPMAP_LINEAR);
    }

    @Override
    protected void updateUniforms(GLAutoDrawable drawable) {
        super.updateUniforms(drawable);
        GL4 gl = drawable.getGL().getGL4();
        GridObjectModel gridModel = (GridObjectModel) model;
        Float cameraScale = model.world.camera.isPresent() ? (float) model.world.camera.get().getScale() : 1f;
        int originOffsetLocation = gl.glGetUniformLocation(shaderProgram, "OriginOffset");
        if (originOffsetLocation != -1)
            gl.glUniform2fv(originOffsetLocation, 1, new float[] { (float) model.x, (float) model.y }, 0);

        int gridSizeLocation = gl.glGetUniformLocation(shaderProgram, "Grids");
        if (gridSizeLocation != -1)
            gl.glUniform2fv(gridSizeLocation, 1,
                    new float[] { InGameConfig.WINDOW_WIDTH / cameraScale / gridModel.width / (float) gridModel.scaleX,
                            InGameConfig.WINDOW_HEIGHT / cameraScale / gridModel.height / (float) gridModel.scaleY },
                    0);
    }
}

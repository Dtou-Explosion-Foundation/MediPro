package medipro.object.overlay.fps;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Optional;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.math.Matrix4f;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import medipro.config.InGameConfig;
import medipro.object.base.camera.CameraModel;
import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

/**
 * FPSを表示するオーバーレイのビュー.
 */
public class FpsOverlayView extends GameObjectView {

    /**
     * FpsOverlayViewを生成する.
     * 
     * @param model 対象のモデル
     */
    public FpsOverlayView(GameObjectModel model) {
        super(model);

    }

    /**
     * OpenGLで扱うテクスチャの幅.
     */
    private static final int TEXTURE_WIDTH = 512;
    /**
     * OpenGLで扱うテクスチャの高さ.
     */
    private static final int TEXTURE_HEIGHT = 48;

    /**
     * 一度に生成する頂点情報の文字数.
     */
    private static final int CHARS_PER_FRAGMENT = 2 * 4;
    /**
     * 一文字あたりの頂点数.
     */
    private static final int VERTICES_PER_CHAR = 4;
    // private static final int VERTICES_SIZE = Float.BYTES * 2;

    /**
     * 何フラグメント分の頂点情報を保持しているか.
     */
    private int vertexBufferFragOffset = 0;
    /**
     * 次に文字を書き込むキャッシュのX座標.
     */
    private int nextCachePositionX = 0;

    /**
     * 現在表示されている文字列.
     */
    private String inBufferString = "";

    /**
     * キャッシュされた文字の情報.
     */
    private HashMap<Character, CachedCharInfo> charCache = new HashMap<>();

    /**
     * フォント.
     */
    private Font font = new Font("Arial", Font.BOLD, 12);

    /**
     * キャッシュされた文字の情報を格納するクラス.
     */
    private class CachedCharInfo {
        /**
         * キャッシュされた文字の範囲.
         */
        public Rectangle boundsInCache;
        /**
         * キャッシュされた文字の表示上の幅.
         */
        public float widthInFrame;

        /**
         * CachedCharInfoのコンストラクタ.
         * 
         * @param boundsInCache キャッシュされた文字の範囲
         * @param widthInFrame  キャッシュされた文字の表示上の幅
         */
        public CachedCharInfo(Rectangle boundsInCache, float widthInFrame) {
            this.boundsInCache = boundsInCache;
            this.widthInFrame = widthInFrame;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setTransform(new AffineTransform());

        FpsOverlayModel fpsOverlayModel = (FpsOverlayModel) model;
        g.setFont(font);
        g.setColor(fpsOverlayModel.getColor());
        FontMetrics fm = g.getFontMetrics();
        String str = String.format("FPS: %2d", fpsOverlayModel.getFps());
        g.drawString(str, 0, fm.getHeight());
    }

    @Override
    protected String getShaderPath(String ext) {
        return "shader/FpsOverlay" + "." + ext;
    }

    @Override
    protected boolean needUpdateTexture() {
        return false;
    }

    /**
     * キャッシュされた文字の情報を取得する.キャッシュになければ追加する.
     * 
     * @param drawable 描画対象
     * @param c        文字
     * @return キャッシュされた文字の情報
     */
    private CachedCharInfo getCharCache(GLAutoDrawable drawable, char c) {
        if (charCache.containsKey(c))
            return charCache.get(c);
        else
            return addCharCache(drawable, c);
    }

    /**
     * キャッシュに文字の情報を追加する.
     * 
     * @param drawable 描画対象
     * @param c        文字
     * @return キャッシュされた文字の情報
     */
    private CachedCharInfo addCharCache(GLAutoDrawable drawable, char c) {
        GL4 gl = drawable.getGL().getGL4();
        FpsOverlayModel fpsOverlayModel = (FpsOverlayModel) model;

        BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        FontMetrics fm = g.getFontMetrics(font);
        int charWidth = fm.stringWidth(String.valueOf(c));
        int charHeight = fm.getHeight();
        g.dispose();

        bufferedImage = new BufferedImage(charWidth, TEXTURE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) bufferedImage.getGraphics();
        g.setFont(font);
        g.setColor(fpsOverlayModel.getColor());
        g.drawString(String.valueOf(c), 0, (TEXTURE_HEIGHT + charHeight) / 2);
        g.dispose();

        TextureData textureData = AWTTextureIO.newTextureData(gl.getGLProfile(), bufferedImage, false);

        gl.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(0));
        gl.glTexSubImage2D(GL4.GL_TEXTURE_2D, 0, nextCachePositionX, 0, charWidth, TEXTURE_HEIGHT,
                textureData.getPixelFormat(), textureData.getPixelType(), textureData.getBuffer());

        Rectangle rect = new Rectangle(nextCachePositionX, 0, fm.stringWidth(String.valueOf(c)), TEXTURE_HEIGHT);

        CachedCharInfo info = new CachedCharInfo(rect, charWidth);

        charCache.put(c, info);
        nextCachePositionX += charWidth;
        return info;
    }

    @Override
    protected float getSpriteWidth() {
        return (float) TEXTURE_HEIGHT / InGameConfig.WINDOW_HEIGHT_BASE * super.getSpriteWidth();
    }

    @Override
    protected float getSpriteHeight() {
        return (float) TEXTURE_HEIGHT / InGameConfig.WINDOW_HEIGHT_BASE * super.getSpriteHeight();
    }

    @Override
    protected Matrix4f getModelMatrix() {
        Matrix4f tempMat = new Matrix4f();
        Optional<CameraModel> cameraModel = this.model.world.camera;
        float x = (float) model.x;
        float y = (float) model.y;
        if (cameraModel.isPresent()) {
            x += (float) cameraModel.get().x;
            y += (float) cameraModel.get().y;
        }

        float cameraScale = cameraModel.isPresent() ? (float) cameraModel.get().getScale() : 1f;
        // cameraScale = 2f;

        Matrix4f modelMat = new Matrix4f() // モデルの座標変換行列
                .translate(x, y, 0, tempMat) // 座標
                .translate(-InGameConfig.WINDOW_WIDTH_BASE / cameraScale / 2,
                        (InGameConfig.WINDOW_HEIGHT_BASE - TEXTURE_HEIGHT) / cameraScale / 2, 0, tempMat)
                .scale(getSpriteWidth(), getSpriteHeight(), 1, tempMat)// 基準サイズ
                .rotate((float) model.rotation, 0, 0, 1, tempMat) // 回転
                .scale((float) model.scaleX, (float) model.scaleY, 1, tempMat) // スケーリング
        ;
        return modelMat;
    }

    @Override
    protected void initTextures(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();

        BufferedImage bufferedImage = new BufferedImage(TEXTURE_WIDTH, TEXTURE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        // {
        //     char c = '0';
        //     Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        //     FontMetrics fm = g.getFontMetrics(font);
        //     int charWidth = fm.stringWidth(String.valueOf(c));

        //     g.setFont(font);
        //     g.setColor(Color.CYAN);
        //     g.drawString(String.valueOf(c), 0, (TEXTURE_HEIGHT + fm.getHeight()) / 2);
        //     g.dispose();
        // }
        TextureData textureData = AWTTextureIO.newTextureData(gl.getGLProfile(), bufferedImage, false);

        gl.glGenTextures(1, textureName);
        gl.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(0));

        gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_BASE_LEVEL, 0);
        gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAX_LEVEL, 0);

        // gl.glTexImage2D(GL4.GL_TEXTURE_2D, 0, GL4.GL_RGBA, TEXTURE_WIDTH, TEXTURE_HEIGHT, 0, GL4.GL_RGBA,
        //         GL4.GL_UNSIGNED_BYTE, textureData.getBuffer());
        gl.glTexImage2D(GL4.GL_TEXTURE_2D, 0, textureData.getInternalFormat(), textureData.getWidth(),
                textureData.getHeight(), 0, textureData.getPixelFormat(), textureData.getPixelType(),
                textureData.getBuffer());
    }

    /**
     * Vertexバッファのデータを生成する.
     * 
     * @param drawable 描画対象
     * @return Vertexバッファのデータ
     */
    private FloatBuffer[] genBufferData(GLAutoDrawable drawable) {
        FloatBuffer vertexBuffer = Buffers
                .newDirectFloatBuffer(CHARS_PER_FRAGMENT * VERTICES_PER_CHAR * 2 * vertexBufferFragOffset);
        FloatBuffer uvBuffer = Buffers
                .newDirectFloatBuffer(CHARS_PER_FRAGMENT * VERTICES_PER_CHAR * 2 * vertexBufferFragOffset);
        for (int c = 0; c < CHARS_PER_FRAGMENT * vertexBufferFragOffset * VERTICES_PER_CHAR * 2; c++) {
            vertexBuffer.put(0);
            uvBuffer.put(0);
        }
        return new FloatBuffer[] { vertexBuffer.flip(), uvBuffer.flip() };
    }

    @Override
    protected void initBuffers(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glGenBuffers(2, vbo);

        vertexBufferFragOffset = 1;
        FloatBuffer[] buffers = genBufferData(drawable);
        for (int i = 0; i < 2; i++) {
            gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(i));
            gl.glBufferData(GL4.GL_ARRAY_BUFFER, Float.BYTES * buffers[i].limit(), buffers[i], GL4.GL_STREAM_DRAW);
        }
    }

    /**
     * 頂点バッファーを更新する.
     * 
     * @param drawable  描画対象
     * @param newString 新しい文字列
     */
    protected void updateStringBuffers(GLAutoDrawable drawable, String newString) {
        GL4 gl = drawable.getGL().getGL4();
        if (newString.equals(inBufferString))
            return;
        int newVertexBufferFragOffset = (int) Math.ceil(newString.length() * 2 / CHARS_PER_FRAGMENT);
        if (newVertexBufferFragOffset > vertexBufferFragOffset) {
            logger.info("updateStringBuffers: newVertexBufferFragOffset: " + newVertexBufferFragOffset);
            // バッファーを拡張する
            vertexBufferFragOffset = newVertexBufferFragOffset;
            FloatBuffer[] buffers = genBufferData(drawable);
            for (int i = 0; i < 2; i++) {
                gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(i));
                gl.glBufferData(GL4.GL_ARRAY_BUFFER, Float.BYTES * buffers[i].limit(), buffers[i], GL4.GL_STREAM_DRAW);
            }
            inBufferString = "";
        }
        float nextVertexPositionX = 0;

        for (int i = 0; i < newString.length(); i++) {
            if (inBufferString.length() > i && newString.charAt(i) == inBufferString.charAt(i)) {
                nextVertexPositionX += getCharCache(drawable, newString.charAt(i)).widthInFrame * 0.02;
                continue;
            }
            CachedCharInfo info = getCharCache(drawable, newString.charAt(i));

            FloatBuffer vertBuffer = Buffers.newDirectFloatBuffer(8);
            vertBuffer.put(nextVertexPositionX); // x1
            vertBuffer.put(0.5f); // y1
            vertBuffer.put(nextVertexPositionX); // x2
            vertBuffer.put(-0.5f); // y2
            nextVertexPositionX += info.widthInFrame * 0.02;
            vertBuffer.put(nextVertexPositionX); // x1
            vertBuffer.put(0.5f); // y1
            vertBuffer.put(nextVertexPositionX); // x2
            vertBuffer.put(-0.5f); // y2

            gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(0));
            gl.glBufferSubData(GL4.GL_ARRAY_BUFFER, Float.BYTES * (i * 8), Float.BYTES * 8, vertBuffer.flip());

            Rectangle rect = info.boundsInCache;
            float x1 = (float) rect.getX() / TEXTURE_WIDTH;
            float x2 = (float) (rect.getX() + rect.getWidth()) / TEXTURE_WIDTH;
            float y1 = (float) rect.getY() / TEXTURE_HEIGHT;
            float y2 = (float) (rect.getY() + rect.getHeight()) / TEXTURE_HEIGHT;

            FloatBuffer uvBuffer = Buffers.newDirectFloatBuffer(8);
            uvBuffer.put(x1);
            uvBuffer.put(y1);
            uvBuffer.put(x1);
            uvBuffer.put(y2);
            uvBuffer.put(x2);
            uvBuffer.put(y1);
            uvBuffer.put(x2);
            uvBuffer.put(y2);

            gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(1));
            gl.glBufferSubData(GL4.GL_ARRAY_BUFFER, Float.BYTES * (i * 8), Float.BYTES * 8, uvBuffer.flip());
        }
        for (int i = newString.length(); i < inBufferString.length(); i++) {
            FloatBuffer uvBuffer = Buffers.newDirectFloatBuffer(8);
            for (int j = 0; j < uvBuffer.limit(); j++)
                uvBuffer.put(0);

            gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(1));
            gl.glBufferSubData(GL4.GL_ARRAY_BUFFER, Float.BYTES * (i * 8), Float.BYTES * 8, uvBuffer.flip());
        }
        inBufferString = newString;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        FpsOverlayModel fpsOverlayModel = (FpsOverlayModel) model;

        gl.glUseProgram(shaderProgram);
        this.updateStringBuffers(drawable, String.format("FPS: %2d", fpsOverlayModel.getFps()));

        this.bindBuffers(drawable);

        if (this.needUpdateTexture())
            this.updateTextures(drawable);
        this.updateUniforms(drawable);

        // gl.glDrawArrays(GL4.GL_TRIANGLE_FAN, 0, 4);
        for (int c = 0; c < vertexBufferFragOffset * CHARS_PER_FRAGMENT; c++) {
            gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, c * VERTICES_PER_CHAR, VERTICES_PER_CHAR);
        }
    }

    @Override
    protected void updateUniforms(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        int modelMatUniform = gl.glGetUniformLocation(shaderProgram, "modelMat");
        if (modelMatUniform != -1) {
            FloatBuffer modelMatBuffer = this.getModelMatrix().transpose().get(FloatBuffer.allocate(4 * 4)).flip();
            gl.glUniformMatrix4fv(modelMatUniform, 1, true, modelMatBuffer);
        }
        int sample2dLocation = gl.glGetUniformLocation(shaderProgram, "uTexture");
        if (sample2dLocation != -1) {
            gl.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(0));
            gl.glBindSampler(sample2dLocation, samplerName.get(0));
        }
    }
}

package medipro.object.player;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;

import javax.imageio.ImageIO;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.math.Matrix4f;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

/**
 * プレイヤーのビュー.
 */
public class PlayerView extends GameObjectView {
    /**
     * アニメーション用のスプライトの配列.
     */
    private Image sprites[];

    /**
     * プレイヤービューを生成する.
     * 
     * @param model 格納するモデル
     */
    public PlayerView(GameObjectModel model) {
        super(model);
        PlayerModel playerModel = (PlayerModel) model;
        sprites = new Image[playerModel.imagePaths.length];
        try {
            for (int i = 0; i < playerModel.imagePaths.length; i++) {
                sprites[i] = ImageIO.read(new File(playerModel.imagePaths[i]));
            }
        } catch (Exception e) {
            logger.warning(e.toString());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g) {
        PlayerModel playerModel = (PlayerModel) model;
        // Image image = sprites[playerModel.spritesIndex];
        Image image = sprites[playerModel.animations[playerModel.animationIndex]];
        if (playerModel.direction == -1) {
            // 反転してから描画する
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            g.drawImage(op.filter((BufferedImage) image, null), (int) (-getSpriteWidth() / 2), -(int) getSpriteHeight(),
                    null);
        } else {
            g.drawImage(image, (int) (-getSpriteWidth() / 2), -(int) getSpriteHeight(), null);
        }
    }

    @Override
    protected String getShaderPath(String ext) {
        super.getShaderPath(ext);
        return "shader/gameobject/GameObject" + "." + ext;
    }

    @Override
    protected float getSpriteWidth() {
        return 64;
    }

    @Override
    protected float getSpriteHeight() {
        return 64;
    }

    @Override
    protected void initNames() {
        super.initNames();
        textureName = Buffers.newDirectIntBuffer(3);
    }

    @Override
    protected void initTextures(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        PlayerModel playerModel = (PlayerModel) model;
        textureName.clear();
        gl.glGenTextures(3, textureName);
        for (int i = 0; i < playerModel.imagePaths.length; i++) {
            TextureData textureData;
            try {
                InputStream textureStream = new FileInputStream(playerModel.imagePaths[i]);
                textureData = TextureIO.newTextureData(gl.getGLProfile(), textureStream, false, TextureIO.PNG);
            } catch (IOException e) {
                logger.warning(e.toString());
                return;
            }

            gl.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(i));

            gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_BASE_LEVEL, 0);
            gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAX_LEVEL, 0);

            gl.glTexImage2D(GL4.GL_TEXTURE_2D, 0, textureData.getInternalFormat(), textureData.getWidth(),
                    textureData.getHeight(), 0, textureData.getPixelFormat(), textureData.getPixelType(),
                    textureData.getBuffer());
        }
    }

    @Override
    protected void updateTextures(GLAutoDrawable drawable) {
    }

    @Override
    protected void updateUniforms(GLAutoDrawable drawable) {
        PlayerModel playerModel = (PlayerModel) model;
        GL4 gl = drawable.getGL().getGL4();
        int modelMatUniform = gl.glGetUniformLocation(shaderProgram, "modelMat");
        if (modelMatUniform != -1) {

            Matrix4f tempMat = new Matrix4f();
            Matrix4f modelMat = new Matrix4f() // モデルの座標変換行列
                    .translate((float) model.x, (float) model.y, 0, tempMat) // 座標
                    .scale(getSpriteWidth(), getSpriteHeight(), 1, tempMat)// スケーリング
                    .scale(playerModel.direction, 1, 1, tempMat)// 左右反転
            ;
            FloatBuffer modelMatBuffer = modelMat.transpose().get(FloatBuffer.allocate(4 * 4)).flip();
            gl.glUniformMatrix4fv(modelMatUniform, 1, true, modelMatBuffer);
        }
        int sample2dLocation = gl.glGetUniformLocation(shaderProgram, "uTexture");
        if (sample2dLocation != -1) {
            gl.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(playerModel.animations[playerModel.animationIndex]));
            gl.glBindSampler(sample2dLocation, samplerName.get(0));
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

}

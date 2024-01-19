package medipro.object.ornament.texture;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.imageio.ImageIO;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.math.Matrix4f;
import com.jogamp.opengl.util.texture.TextureData;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

public class TextureView extends GameObjectView {
    private Image[] textures = null;

    public TextureView(GameObjectModel model) {
        super(model);
        TextureModel textureModel = (TextureModel) model;
        String[] texturePaths = textureModel.getTexturePaths();
        if (texturePaths != null) {
            textures = new Image[texturePaths.length];
            for (int i = 0; i < texturePaths.length; i++) {
                try {
                    textures[i] = ImageIO.read(new File(texturePaths[i]));
                } catch (IOException | NullPointerException e) {
                    logger.warning("Failed to load texture: " + texturePaths[i]);
                }
            }
        }
    }

    @Override
    protected void draw(Graphics2D g) {
        TextureModel textureModel = (TextureModel) model;
        int textureIndex = textureModel.getTextureIndex();
        if (textures != null && textureIndex >= 0 && textureIndex < textures.length) {
            g.drawImage(textures[textureIndex], 0, 0, null);
        }
    }

    @Override
    protected String getShaderPath(String ext) {
        super.getShaderPath(ext);
        return "shader/gameobject/GameObject" + "." + ext;
    }

    @Override
    protected float getSpriteWidth() {
        TextureModel textureModel = (TextureModel) model;
        return textures[textureModel.getTextureIndex()].getWidth(null);
    }

    @Override
    protected float getSpriteHeight() {
        TextureModel textureModel = (TextureModel) model;
        return textures[textureModel.getTextureIndex()].getHeight(null);
    }

    @Override
    protected boolean needUpdateTexture() {
        return false;
    }

    @Override
    protected void initNames() {
        super.initNames();
        TextureModel textureModel = (TextureModel) model;
        textureName = Buffers.newDirectIntBuffer(textureModel.getTexturePaths().length);
    }

    @Override
    protected void initTextures(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        TextureModel textureModel = (TextureModel) model;
        String[] texturePaths = textureModel.getTexturePaths();
        textureName.clear();
        gl.glGenTextures(texturePaths.length, textureName);
        for (int i = 0; i < texturePaths.length; i++) {
            TextureData textureData;
            try {
                InputStream textureStream = new FileInputStream(texturePaths[i]);
                BufferedImage bufferedImage = ImageIO.read(textureStream);
                ByteBuffer buffer = convertBufferedImageToByteBuffer(bufferedImage);

                textureData = new TextureData(gl.getGLProfile(), GL4.GL_RGBA, bufferedImage.getWidth(),
                        bufferedImage.getHeight(), 0, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE, false, false, false, buffer,
                        null);
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
    protected Matrix4f getModelMatrix() {
        Matrix4f tempMat = new Matrix4f();
        Matrix4f modelMat = new Matrix4f() // モデルの座標変換行列
                .translate((float) model.x, (float) model.y, 0, tempMat) // 座標
                .scale(getSpriteWidth(), getSpriteHeight(), 1, tempMat)// 基準サイズ
                .rotate((float) model.rotation, 0, 0, 1, tempMat) // 回転
                .scale((float) model.scaleX, (float) model.scaleY, 1, tempMat) // スケーリング
        ;
        return modelMat;
    }

    @Override
    protected void updateUniforms(GLAutoDrawable drawable) {
        TextureModel textureModel = (TextureModel) model;
        GL4 gl = drawable.getGL().getGL4();

        int modelMatUniform = gl.glGetUniformLocation(shaderProgram, "modelMat");
        if (modelMatUniform != -1) {

            FloatBuffer modelMatBuffer = this.getModelMatrix().transpose().get(FloatBuffer.allocate(4 * 4)).flip();
            gl.glUniformMatrix4fv(modelMatUniform, 1, true, modelMatBuffer);
        }

        int sample2dLocation = gl.glGetUniformLocation(shaderProgram, "uTexture");
        if (sample2dLocation != -1) {

            gl.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(textureModel.getTextureIndex()));
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

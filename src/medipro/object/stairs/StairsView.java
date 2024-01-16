package medipro.object.stairs;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.math.Matrix4f;
import com.jogamp.opengl.util.texture.TextureData;

import medipro.object.base.gameobject.GameObjectModel;
import medipro.object.base.gameobject.GameObjectView;

public class StairsView extends GameObjectView {

    private BufferedImage[] textures = null;

    public StairsView(GameObjectModel model) {
        super(model);
        try {
            textures = new BufferedImage[2];
            textures[0] = ImageIO.read(new File("img/layers/medipro_0001_階段.png"));
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-textures[0].getWidth(), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            textures[1] = op.filter(textures[0], null);
        } catch (IOException | NullPointerException e) {
            logger.warning("Failed to load texture: img/layers/medipro_0001_階段.png");
        }

        StairsModel stairsModel = (StairsModel) model;
        stairsModel.setWidth(getSpriteWidth());
        stairsModel.setHeight(getSpriteHeight());
    }

    @Override
    protected float getSpriteWidth() {
        return textures[0].getWidth(null);
    }

    @Override
    protected float getSpriteHeight() {
        return textures[0].getHeight(null);
    }

    @Override
    protected void draw(Graphics2D g) {
        StairsModel stairsModel = (StairsModel) model;
        if (textures != null) {
            g.drawImage(textures[stairsModel.isLeftUp() ? 1 : 0], (int) (-getSpriteWidth() / 2),
                    (int) (-getSpriteHeight() / 2), null);
        }
    }

    @Override
    protected String getShaderPath(String ext) {
        super.getShaderPath(ext);
        return "shader/gameobject/GameObject" + "." + ext;
    }

    @Override
    protected boolean needUpdateTexture() {
        return false;
    }

    @Override
    protected void initTextures(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        textureName.clear();
        gl.glGenTextures(1, textureName);
        TextureData textureData;
        try {
            InputStream textureStream = new FileInputStream("img/layers/medipro_0001_階段.png");
            BufferedImage bufferedImage = ImageIO.read(textureStream);
            ByteBuffer buffer = convertBufferedImageToByteBuffer(bufferedImage);

            textureData = new TextureData(gl.getGLProfile(), GL4.GL_RGBA, bufferedImage.getWidth(),
                    bufferedImage.getHeight(), 0, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE, false, false, false, buffer, null);
        } catch (IOException e) {
            logger.warning(e.toString());
            return;
        }

        gl.glBindTexture(GL4.GL_TEXTURE_2D, textureName.get(0));

        gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_BASE_LEVEL, 0);
        gl.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAX_LEVEL, 0);

        gl.glTexImage2D(GL4.GL_TEXTURE_2D, 0, textureData.getInternalFormat(), textureData.getWidth(),
                textureData.getHeight(), 0, textureData.getPixelFormat(), textureData.getPixelType(),
                textureData.getBuffer());
    }

    @Override
    protected Matrix4f getModelMatrix() {
        StairsModel stairsModel = (StairsModel) model;
        Matrix4f tempMat = new Matrix4f();
        Matrix4f modelMat = new Matrix4f() // モデルの座標変換行列
                .translate((float) model.x, (float) model.y, 0, tempMat) // 座標
                .scale(getSpriteWidth(), getSpriteHeight(), 1, tempMat)// 基準サイズ
                .rotate((float) model.rotation, 0, 0, 1, tempMat) // 回転
                .scale(stairsModel.isLeftUp() ? -1 : 1, 1, 1, tempMat) // 左右反転
                .scale((float) model.scaleX, (float) model.scaleY, 1, tempMat) // スケーリング
        ;
        return modelMat;
    }

}

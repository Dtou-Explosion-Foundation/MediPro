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
import java.nio.IntBuffer;

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
     * スプライトの幅
     */
    final int SPRITE_WIDTH = 64;
    /**
     * スプライトの高さ
     */
    final int SPRITE_HEIGHT = 64;

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
            g.drawImage(op.filter((BufferedImage) image, null), (int) (-SPRITE_WIDTH / 2), -SPRITE_HEIGHT, null);
        } else {
            g.drawImage(image, (int) (-SPRITE_WIDTH / 2), -SPRITE_HEIGHT, null);
        }
    }

    protected int shaderProgram = -1;
    protected IntBuffer textureName = Buffers.newDirectIntBuffer(3);
    protected IntBuffer samplerName = Buffers.newDirectIntBuffer(1);
    protected IntBuffer vbo = Buffers.newDirectIntBuffer(2);

    @Override
    protected String getShaderPath(String ext) {
        super.getShaderPath(ext);
        return "shader/gameobject/GameObject" + "." + ext;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();

        shaderProgram = initShaders(drawable);
        gl.glUseProgram(shaderProgram);

        initBuffers(drawable, shaderProgram);
        initTextures(drawable);
        initSamplers(drawable);
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
    protected void initSamplers(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        samplerName.clear();
        gl.glGenSamplers(1, samplerName);

        gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_NEAREST);
        gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_NEAREST);

        gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_WRAP_S, GL4.GL_CLAMP_TO_EDGE);
        gl.glSamplerParameteri(samplerName.get(0), GL4.GL_TEXTURE_WRAP_T, GL4.GL_CLAMP_TO_EDGE);
    }

    @Override
    protected void initBuffers(GLAutoDrawable drawable, int program) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glUseProgram(program);
        vbo.clear();

        gl.glGenBuffers(2, vbo);

        float[] vertices = { -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f };
        float[] uv = { 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f };

        FloatBuffer vertexBuffer = Buffers.newDirectFloatBuffer(vertices);
        FloatBuffer uvBuffer = Buffers.newDirectFloatBuffer(uv);

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(0));
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, Float.BYTES * vertices.length, vertexBuffer, GL4.GL_STATIC_DRAW);

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(1));
        gl.glBufferData(GL4.GL_ARRAY_BUFFER, Float.BYTES * uv.length, uvBuffer, GL4.GL_STATIC_DRAW);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glUseProgram(shaderProgram);

        this.bindBuffers(drawable, shaderProgram);

        updateUniforms(drawable);

        gl.glDrawArrays(GL4.GL_TRIANGLE_FAN, 0, 4);
    }

    @Override
    protected void bindBuffers(GLAutoDrawable drawable, int program) {
        GL4 gl = drawable.getGL().getGL4();

        int vertexAttrib = gl.glGetAttribLocation(program, "aPosition");
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(0));
        gl.glEnableVertexAttribArray(vertexAttrib);
        gl.glVertexAttribPointer(vertexAttrib, 2, GL4.GL_FLOAT, false, 0, 0);

        int uvAttrib = gl.glGetAttribLocation(program, "aTexcoord");
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(1));
        gl.glEnableVertexAttribArray(uvAttrib);
        gl.glVertexAttribPointer(uvAttrib, 2, GL4.GL_FLOAT, false, 0, 0);

        int cameraTransformLocation = gl.glGetUniformBlockIndex(program, "CameraTransform");
        int bindingPoint = nextBindingPoint++; // 衝突してはいけない
        int cameraUbo = this.model.world.camera.isPresent() ? this.model.world.camera.get().getUBO() : -1;
        gl.glBindBuffer(GL4.GL_UNIFORM_BUFFER, cameraUbo);
        gl.glUniformBlockBinding(program, cameraTransformLocation, bindingPoint);
        gl.glBindBufferBase(GL4.GL_UNIFORM_BUFFER, bindingPoint, cameraUbo);
    }

    @Override
    protected void updateUniforms(GLAutoDrawable drawable) {
        PlayerModel playerModel = (PlayerModel) model;
        GL4 gl = drawable.getGL().getGL4();
        {
            int modelMatUniform = gl.glGetUniformLocation(shaderProgram, "modelMat");

            Matrix4f tempMat = new Matrix4f();
            Matrix4f modelMat = new Matrix4f() // モデルの座標変換行列
                    .translate((float) model.x, (float) model.y, 0, tempMat) // 座標
                    .scale(SPRITE_WIDTH, SPRITE_HEIGHT, 1, tempMat)// スケーリング
                    .scale(playerModel.direction, 1, 1, tempMat)// 左右反転
            ;
            FloatBuffer modelMatBuffer = modelMat.transpose().get(FloatBuffer.allocate(4 * 4)).flip();
            gl.glUniformMatrix4fv(modelMatUniform, 1, true, modelMatBuffer);
        }
        {
            int sample2dLocation = gl.glGetUniformLocation(shaderProgram, "uTexture");
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

package objects.Player;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import objects.base.GameObject.GameObjectModel;
import objects.base.GameObject.GameObjectView;

public class PlayerView extends GameObjectView {
    Image sprites[] = new Image[4];

    public PlayerView(GameObjectModel model) {
        super(model);
        try {
            sprites[0] = ImageIO.read(new File("img/character/character_sample_right_stand.png"));
            sprites[1] = ImageIO.read(new File("img/character/character_sample_right_right.png"));
            sprites[2] = ImageIO.read(new File("img/character/character_sample_right_stand.png"));
            sprites[3] = ImageIO.read(new File("img/character/character_sample_right_left.png"));
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    @Override
    public void draw(Graphics g) {
        PlayerModel playerModel = (PlayerModel) model;
        g.drawImage(sprites[playerModel.spritesIndex], playerModel.x, playerModel.y, null);
    }
}

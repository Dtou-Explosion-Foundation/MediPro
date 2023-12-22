import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MoveCharaPanel extends JPanel {
    int x = 250; // キャラの描画位置（x座標）
    int y = 500; // キャラの描画位置（y座標）
    int mode = 0; // 状態（0:停止,1:前進,-1:後進）
    Image img[] = new Image[4];
    int p = 0; // 画像番号
    Image back; // 背景画像
    int offset = 0; // 背景の書き出し位置

    public MoveCharaPanel() {
        setPreferredSize(new Dimension(1024, 768));
        addKeyListener(new MoveByKeyPanel());
        this.setFocusable(true);
        try {
            img[0] = ImageIO.read(new File("img/character_sample_right_stand.png"));
            img[1] = ImageIO.read(new File("img/character_sample_right_right.png"));
            img[2] = ImageIO.read(new File("img/character_sample_right_stand.png"));
            img[3] = ImageIO.read(new File("img/character_sample_right_left.png"));
            back = ImageIO.read(new File("img/background_sample.png"));
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }

    }

    // 描画
    public void paintComponent(Graphics g) {
        // 背景
        g.drawImage(back, offset, 0, 1024, 768, null);
        g.drawImage(back, offset + 1024, 0, 1024, 768, null);
        // キャラ
        g.drawImage(img[p], x, y, null);
    }

    // スレッド(アニメーション)
    class AnimeThread extends Thread {
        public void run() {
            // 右に歩く
            while (mode == 1) {
                // 背景を左に動かす
                offset = offset - 5;
                if (offset < -1024) {
                    offset = 0;
                }
                // 画像の変更
                p = (p + 1) % 4;
                repaint();
                // 100ms待機
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
            }
            // 左に歩く
            while (mode == -1) {
                offset = offset + 5;
                if (offset > 0) {
                    offset = -1023;
                }
                // 画像の変更
                if (p == 0) {
                    p = 4;
                }
                p = (p - 1) % 4;
                repaint();
                // 100ms待機
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
            }
        }
    }

    // キー
    class MoveByKeyPanel extends JPanel implements KeyListener {
        public void keyPressed(KeyEvent e) {
            int k = e.getKeyCode();
            switch (k) {
                case KeyEvent.VK_RIGHT:
                    mode = 1;
                    Thread th = new AnimeThread();
                    th.start();
                    break;
                case KeyEvent.VK_LEFT:
                    mode = -1;
                    th = new AnimeThread();
                    th.start();
                    break;
            }
            repaint();
        }

        public void keyTyped(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
            mode = 0;
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.getContentPane().setLayout(new FlowLayout());
        f.getContentPane().add(new MoveCharaPanel());
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setVisible(true);
    }
}
package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    //실제로 플레이어가 화면에 그려지게 되는 좌표
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        // 스프라이트 좌상단이 (0, 0) 이므로, 플레이어를 정확히 화면 중앙에 표시하기 위해서 타일 반개 만큼의 크기를 빼준다
        screenX = (gp.screenWidth / 2) - (gp.tileSize / 2);
        screenY = (gp.screenHeight / 2) - (gp.tileSize / 2);

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {
        // 게임 상에서의 플레이어의 좌표
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;

        speed = 3;
        direction = "down";
    }
    public void getPlayerImage() {

        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/reimu_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/reimu_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/reimu_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/reimu_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/reimu_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/reimu_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/reimu_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/reimu_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";
                worldY -= speed;
            } else if (keyH.downPressed == true) {
                direction = "down";
                worldY += speed;
            } else if (keyH.leftPressed == true) {
                direction = "left";
                worldX -= speed;
            } else if (keyH.rightPressed == true) {
                direction = "right";
                worldX += speed;
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2) {
//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction) {
            case "up" :
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down" :
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left" :
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right" :
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}

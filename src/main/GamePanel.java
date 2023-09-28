package main;
import entity.Player;
import tile.TileManager;

import java.awt.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    // 게임 화면 그 자체인 GamePanel클래스

    // 화면 설정
    final int originalTileSize = 16; // 스프라이트 하나의 크기가 16 * 16픽셀임
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 1픽셀당 scale값만큼 정수배율로 키움
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 가로 768픽셀
    public final int screenHeight = tileSize * maxScreenRow; // 세로 576픽셀

    // 월드 설정
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxScreenRow;


    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public Player player = new Player(this, keyH);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

/*
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // 나노초 단위 ( 1초 == 10억 나노초 )
        double nextDrawTime = System.nanoTime() + drawInterval;


        while (gameThread != null) {

            // 1. update : 각 캐릭터의 위치 정보를 갱신함
            update();

            // 2. draw   : 갱신된 정보로 화면을 표시함
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
*/ // sleep 방식의 interval, 실행 후 interval 만큼 대기시키는 방식, deltaTime방식에 비해 정확성이 떨어지는 편임
@Override
public void run() { // deltaTime 방식의 interval, unity의 Time.deltaTime() 과 비슷함
    double drawInterval = 1000000000 / FPS; // 나노초 단위 ( 1초 == 10억 나노초 )
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;

    long timer = 0;
    int drawCount = 0;

    while (gameThread != null) {

        currentTime = System.nanoTime();

        delta += (currentTime - lastTime) / drawInterval;
        timer += (currentTime - lastTime);
        lastTime = currentTime;

        if (delta >= 1) {
            // 1. update : 각 캐릭터의 위치 정보를 갱신함
            update();
            // 2. draw   : 갱신된 정보로 화면을 표시함
            repaint();

            delta--;
            drawCount++;
        }
        if (timer >= 1000000000) {
            System.out.println("FPS : " + drawCount);
            drawCount = 0;
            timer = 0;
        }
    }
}
    public void update() {
        player.update();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Graphics2D클래스는 Graphics클래스를 상속함
        // 형태, 좌표변환, 색상변환, 텍스트 레이아웃을 정교하게 제어할 수 있음
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        player.draw(g2);

        g2.dispose(); // 없어도 작동은 하지만, 메모리 관리를 위해 필요함
    }
}

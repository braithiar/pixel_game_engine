package com.braithiar.cherno;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.braithiar.cherno.entity.mob.Player;
import com.braithiar.cherno.graphics.Screen;
import com.braithiar.cherno.graphics.Sprite;
import com.braithiar.cherno.input.Keyboard;
import com.braithiar.cherno.world.Level;
import com.braithiar.cherno.world.tile.TileCoordinate;

public class Game extends Canvas implements Runnable {
  private static final long serialVersionUID = 1L;
  private final String TITLE = "Rain";
  private JFrame frame;
  private boolean running;
  private BufferedImage image;
  private int[] dataBufferPixels;
  private Level level;
  private TileCoordinate sporgSpawn;
  private Screen screen;
  private Keyboard input;
  private Player player;

  public static final int WIDTH = 300;
  public static final int HEIGHT = WIDTH / 16 * 9;
  public static final int SCALE = 3;

  //TODO Refactor so Game.class controls all sizes/dimensions so they can be used in other classes

  public Game() {
    Dimension canvasSize = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
    this.setPreferredSize(canvasSize);

    frame = new JFrame();
    running = false;
    image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    dataBufferPixels = ((DataBufferInt) image.getRaster().getDataBuffer())
      .getData();
    level = Level.SPORG_LEVEL;
    sporgSpawn = new TileCoordinate(49, 9, Screen.getTileSize());
    screen = new Screen(WIDTH, HEIGHT);
    input = new Keyboard();
    player = new Player(sporgSpawn, input, Sprite.PLAYER_DOWN);

    addKeyListener(input);
  }

  public String getTitle() {
    return TITLE;
  }

  private Thread gameThread;

  /**
   * 
   */
  public synchronized void start() {
    running = true;
    gameThread = new Thread(this, "Game");

    gameThread.start();
  }

  /**
   * 
   */
  public synchronized void stop() {
    running = false;

    try {
      gameThread.join();
    } catch (InterruptedException ie) {
      ie.printStackTrace();
    }
  }

  private StringBuilder titleMessage;

  private void titleBarBuilder(int updates, int frames) {
    if (titleMessage == null) {
      titleMessage = new StringBuilder();
    }
    titleMessage.setLength(0);
    titleMessage.append(getTitle()).append(" |  ")
      .append(updates).append("ups\t").append(frames).append("fps ");

  }

  @Override
  public void run() {
    long prevTime = System.nanoTime();
    long timer = System.currentTimeMillis();
    final double nsPerTick = 1000000000d / 60d;
    double ticks = 0d;
    int frames = 0;
    int updates = 0;

    requestFocus();

    while (running) {
      long currentTime = System.nanoTime();
      ticks += (currentTime - prevTime) / nsPerTick;
      prevTime = currentTime;

      while (ticks >= 1) {
        update();

        ++updates;
        --ticks;
      }

      render();
      ++frames;

      if (System.currentTimeMillis() - timer > 1000) {
        timer += 1000;

        titleBarBuilder(updates, frames);
        frame.setTitle(titleMessage.toString());

        frames = 0;
        updates = 0;
      }
    }

    stop();
  }

  public void update() {
    player.update();
  }

  public void render() {
    BufferStrategy buffStrategy = getBufferStrategy();

    if (buffStrategy == null) {
      this.createBufferStrategy(3);
      return;
    }

    screen.clear();
    level.getCamera().setCamera(player.getX(), player.getY());
    level.render(screen);
    player.render(screen);

    // Copy screen pixel data to buffer.
    System.arraycopy(screen.getPixels(), 0, dataBufferPixels, 0,
      dataBufferPixels.length);

    Graphics graphics = buffStrategy.getDrawGraphics();

    graphics.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
    graphics.dispose();

    buffStrategy.show();
  }

  public static void main(String[] args) {
    Game game = new Game();

    /*
     * Should be first thing done to frame to prevent graphical errors due to
     * resizing the screen.
     */
    game.frame.setResizable(false);
    game.frame.setTitle(game.getTitle());
    game.frame.add(game);
    game.frame.pack();
    game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    game.frame.setLocationRelativeTo(null);
    game.frame.setVisible(true);

    game.start();
  }
}

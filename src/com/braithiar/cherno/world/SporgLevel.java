package com.braithiar.cherno.world;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SporgLevel extends Level {
  public SporgLevel(String path, int screenWidth, int screenHeight) {
    super(path);
  }

  @Override
  protected void loadLevel(String path) {
    try {
      BufferedImage map = ImageIO.read(SporgLevel.class.getResource(path));
      mapWidth = map.getWidth();
      mapHeight = map.getHeight();
      tiles = new int[mapWidth * mapHeight];

      map.getRGB(0, 0, mapWidth, mapHeight, tiles, 0, mapWidth);
    } catch (IOException ioe) {
      System.err.println("Could not load level file located at: " + path);
      ioe.printStackTrace();
    }
  }

  @Override
  void worldTime() {
    // TODO Auto-generated method needs code

  }

  @Override
  public void update() {
    // TODO Auto-generated method needs code

  }
}

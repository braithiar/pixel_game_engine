package com.braithiar.cherno.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
  // Static fields
  private final static SpriteSheet TILES = new SpriteSheet("/textures/sprites/tiles.png",
                                                           256);
  private final static SpriteSheet SPRITES = new SpriteSheet("/textures/sprites/sprites.png",
                                                             48);

  public static SpriteSheet getTiles() {
    return TILES;
  }

  public static SpriteSheet getSpriteTiles() {
    return SPRITES;
  }

  private String path;
  private final int SIZE;
  private int[] sprites;

  public int getSize() {
    return SIZE;
  }

  public int[] getSprites() {
    return sprites;
  }

  public SpriteSheet(String path, int size) {
    this.path = path;
    SIZE = size;
    sprites = new int[size * size];

    loadSpriteSheet();
  }

  private void loadSpriteSheet() {
    try {
      BufferedImage spriteSheet = ImageIO
        .read(SpriteSheet.class.getResource(path));
      int width = spriteSheet.getWidth();
      int height = spriteSheet.getHeight();

      spriteSheet.getRGB(0, 0, width, height, sprites, 0, width);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}

package com.braithiar.cherno.graphics;

import java.util.Random;

import com.braithiar.cherno.world.tile.Tile;

public class Screen {
  public enum Mirror {
    NO_FLIP,
    FLIP_X_AXIS,
    FLIP_Y_AXIS,
    FLIP_BOTH_AXES
  }

  private int width, height;
  // Exponent of a number 2^MAP_POWER representing map square edge
  private final static int MAP_POWER = 6;
  // Exponent of a number 2^TILE_POWER representing tile square edge
  private final static int TILE_POWER = 4;
  private final static int MAP_SIZE = (int) Math.pow(2d, MAP_POWER);
  private final static int MAP_SIZE_MASK = MAP_SIZE - 1;
  private final static int TILE_SIZE = (int) Math.pow(2d, TILE_POWER);

  private Random random;
  private int[] pixels;
  private int[] tiles;

  public Screen(int width, int height) {
    this.width = width;
    this.height = height;
    random = new Random();
    pixels = new int[width * height];
    tiles = new int[MAP_SIZE * MAP_SIZE];

    for (int i = 0; i < tiles.length; ++i) {
      tiles[i] = random.nextInt(0xffffff);
    }
    tiles[0] = 0x000000;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  /**
   * Returns the exponent of a base 2 number equal to the length of an edge of a
   * map square. Intended to be used for bit-shifting operations.
   * 
   * @return base 2 exponent representing the length of an edge of a map square
   */
  public static int getMapPower() {
    return MAP_POWER;
  }

  /**
   * Returns the exponent of a base 2 number equal to the length of an edge of a
   * tile square. Intended to be used for bit-shifting operations.
   * 
   * @return base 2 exponent representing the length of an edge of a tile square
   */
  public static int getTilePower() {
    return TILE_POWER;
  }

  public static int getMapSize() {
    return MAP_SIZE;
  }

  public static int getMapMask() {
    return MAP_SIZE_MASK;
  }

  public static int getTileSize() {
    return TILE_SIZE;
  }

  public int[] getPixels() {
    return pixels;
  }

  /**
   * Clears the array holding color information for all the pixels that will be
   * sent to the buffer by setting every pixel to black (<code>0x000000</code>).
   */
  public void clear() {
    for (int i = 0; i < pixels.length; ++i) {
      pixels[i] = 0;
    }
  }

  public void renderTile(int xPos, int yPos, Tile tile) {
    int[] tilePixels = tile.getSprite().getPixels();

    // Adjust position to scroll map as character moves.
    xPos -= xOffset;
    yPos -= yOffset;

    for (int y = 0; y < TILE_SIZE; ++y) {
      int yAbsolute = y + yPos;

      for (int x = 0; x < TILE_SIZE; ++x) {
        int xAbsolute = x + xPos;

        if (xAbsolute < -TILE_SIZE || xAbsolute >= width || yAbsolute < 0
            || yAbsolute >= height) {
          break;
        }

        xAbsolute = xAbsolute < 0 ? 0 : xAbsolute;

        pixels[xAbsolute + (yAbsolute * width)] = tilePixels[x
                                                             + (y * TILE_SIZE)];
      }
    }
  }

  public void renderPlayer(int xPos, int yPos, Mirror flip, Sprite sprite) {
    int[] spritePixels = sprite.getPixels();
    int flipTileSize = TILE_SIZE - 1;

    xPos -= xOffset;
    yPos -= yOffset;

    for (int y = 0; y < TILE_SIZE; ++y) {
      int yAbsolute = y + yPos;

      for (int x = 0; x < TILE_SIZE; ++x) {
        int xAbsolute = x + xPos;
        int xSprite, ySprite;

        switch (flip) {
          case FLIP_Y_AXIS:
            ySprite = flipTileSize - y;
            xSprite = x;
            break;
          case FLIP_X_AXIS:
            ySprite = y;
            xSprite = flipTileSize - x;
            break;
          case FLIP_BOTH_AXES:
            ySprite = flipTileSize - y;
            xSprite = flipTileSize - x;
            break;
          default:
            ySprite = y;
            xSprite = x;
        }

        int color = spritePixels[xSprite + (ySprite * TILE_SIZE)];

        xAbsolute = xAbsolute < 0 ? 0 : xAbsolute;
        xAbsolute = xAbsolute >= width ? (width - 1) : xAbsolute;
        yAbsolute = yAbsolute < 0 ? 0 : yAbsolute;
        yAbsolute = yAbsolute >= height ? (height - 1) : yAbsolute;

        if ((color >> 24) != 0x00) {
          pixels[xAbsolute + (yAbsolute * width)] = color;
        }
      }
    }
  }

  private int xOffset, yOffset;

  public void setOffset(int x, int y) {
    xOffset = x;
    yOffset = y;
  }

  public int get_xOffset() {
    return xOffset;
  }

  public int get_yOffset() {
    return yOffset;
  }
}

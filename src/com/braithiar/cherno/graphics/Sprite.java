package com.braithiar.cherno.graphics;

public class Sprite {
  //Static texture fields
  public final static Sprite YELLOW_FLOWERS = new Sprite(16, 0, 0,
                                                         SpriteSheet
                                                           .getTiles());
  public final static Sprite ROCK = new Sprite(16, 1, 0,
                                               SpriteSheet.getTiles());
  public final static Sprite GRASS = new Sprite(16, 2, 0,
                                                SpriteSheet.getTiles());
  public final static Sprite WATER_ROUGH = new Sprite(16, 3, 0,
                                                      SpriteSheet.getTiles());
  public final static Sprite WATER = new Sprite(16, 4, 0,
                                                SpriteSheet.getTiles());
  public final static Sprite DIRT_PATCH = new Sprite(16, 5, 0,
                                                     SpriteSheet.getTiles());
  public final static Sprite WIZARD = new Sprite(16, 0, 0,
                                                 SpriteSheet.getSpriteTiles());
  public final static Sprite PLAYER_UP = new Sprite(16, 1, 0,
                                                    SpriteSheet
                                                      .getSpriteTiles());
  public final static Sprite PLAYER_UP_WALK_FRAME_1 = new Sprite(16, 1, 1,
                                                                 SpriteSheet
                                                                   .getSpriteTiles());
  public final static Sprite PLAYER_UP_WALK_FRAME_2 = new Sprite(16, 1, 2,
                                                                 SpriteSheet
                                                                   .getSpriteTiles());
  public final static Sprite PLAYER_DOWN = new Sprite(16, 0, 0,
                                                      SpriteSheet
                                                        .getSpriteTiles());
  public final static Sprite PLAYER_DOWN_WALK_FRAME_1 = new Sprite(16, 0, 1,
                                                                   SpriteSheet
                                                                     .getSpriteTiles());
  public final static Sprite PLAYER_DOWN_WALK_FRAME_2 = new Sprite(16, 0, 2,
                                                                   SpriteSheet
                                                                     .getSpriteTiles());
  public final static Sprite PLAYER_SIDE = new Sprite(16, 2, 0,
                                                      SpriteSheet
                                                        .getSpriteTiles());
  public final static Sprite PLAYER_SIDE_WALK_FRAME_1 = new Sprite(16, 2, 1,
                                                                   SpriteSheet
                                                                     .getSpriteTiles());
  public final static Sprite PLAYER_SIDE_WALK_FRAME_2 = new Sprite(16, 2, 2,
                                                                   SpriteSheet
                                                                     .getSpriteTiles());
  public final static Sprite JOSH_GRASS_1 = new Sprite(16, 0, 1,
                                                       SpriteSheet
                                                         .getTiles());
  public final static Sprite JOSH_GRASS_2 = new Sprite(16, 0, 2,
                                                       SpriteSheet
                                                         .getTiles());
  public final static Sprite JOSH_GRASS_3 = new Sprite(16, 0, 3,
                                                       SpriteSheet
                                                         .getTiles());
  public final static Sprite NULL_SPRITE = new Sprite(16, 0x00000000);

  private final int SIZE;
  private final int spriteX, spriteY;
  private final SpriteSheet sheet;
  private final int[] pixels;

  /**
   * The pixels constructor takes the pixel size, and spriteX and spriteY
   * indices of the pixels, and applies them to the provided pixels sheet.
   *
   * <code>spriteIndexX</code> and <code>spriteIndexY</code>, the column and
   * row, respectively, of the desired pixels. The first pixels of the first row
   * would be (0, 0). The second: (1, 0); the third on the second row: (2, 1).
   *
   * @param size         the edge size of the pixels-square in pixels
   * @param spriteIndexX the column the pixels resides in
   * @param spriteIndexY the row the pixels resides in
   * @param sheet        the pixels sheet
   */
  public Sprite(int size, int spriteIndexX,
                int spriteIndexY, SpriteSheet sheet) {
    SIZE = size;
    spriteX = spriteIndexX * size;
    spriteY = spriteIndexY * size;
    this.sheet = sheet;
    pixels = new int[size * size];

    loadSprite();
  }

  public Sprite(int size, int color) {
    SIZE = size;
    spriteX = 0;
    spriteY = 0;
    sheet = null;
    pixels = new int[size * size];

    setColor(color);
  }

  private void setColor(int color) {
    int spriteSize = SIZE * SIZE;

    for (int i = 0; i < spriteSize; ++i) {
      pixels[i] = color;
    }
  }

  public int getSize() {
    return SIZE;
  }

  public int[] getPixels() {
    return pixels;
  }

  private void loadSprite() {
    int[] sheetPixels = sheet.getSprites();
    int sheetSize = sheet.getSize();

    for (int y = 0; y < SIZE; ++y) {
      for (int x = 0; x < SIZE; ++x) {
        pixels[x + (y * SIZE)] = sheetPixels[(spriteX + x)
                                             + ((spriteY + y) * sheetSize)];
      }
    }
  }
}

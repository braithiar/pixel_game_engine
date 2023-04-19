package com.braithiar.cherno.world.tile;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.braithiar.cherno.graphics.Sprite;

public class GrassTile extends Tile {
  private static Map<Integer, Tile> grassTiles;
  private static Random rand = new Random();
  
  public final static Tile JOSH_GRASS_1_TILE = new GrassTile(Sprite.JOSH_GRASS_1);
  public final static Tile JOSH_GRASS_2_TILE = new GrassTile(Sprite.JOSH_GRASS_2);
  public final static Tile JOSH_GRASS_3_TILE = new GrassTile(Sprite.JOSH_GRASS_3);

  public GrassTile(Sprite sprite) {
    super(sprite);

    grassTiles = new HashMap<>();
  }

  public static Tile getGrass(int index) {
    int grassID = rand.nextInt(3);

    switch (grassID) {
      case 0:
        if (grassTiles.get(index) == null) {
          grassTiles.put(index, GrassTile.JOSH_GRASS_1_TILE);
        }
        return grassTiles.get(index);
      case 1:
        if (grassTiles.get(index) == null) {
          grassTiles.put(index, GrassTile.JOSH_GRASS_2_TILE);
        }
        return grassTiles.get(index);
      case 2:
        if (grassTiles.get(index) == null) {
          grassTiles.put(index, GrassTile.JOSH_GRASS_3_TILE);
        }
        return grassTiles.get(index);
      default:
        return GrassTile.JOSH_GRASS_1_TILE;
    }
  }
}

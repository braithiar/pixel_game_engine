package com.braithiar.cherno.world.tile;

import java.awt.Point;

public class TileCoordinate {
  private Point coordinate;
  private final int TILE_SIZE;

  /**
   * Creates a <code>TileCoordiantes</code> object that stores a tiles location
   * in a <code>Point</code> object.
   * 
   * Tile locations are stored as (x * tileSize, y * tileSize).
   * 
   * @param x        the x coordinate of the tile
   * @param y        the y coordinate of the tile
   * @param tileSize the length, in pixels, of one edge of a tile square
   */
  public TileCoordinate(int x, int y, int tileSize) {
    TILE_SIZE = tileSize;
    coordinate = new Point(x * TILE_SIZE, y * TILE_SIZE);
  }

  /**
   * Creates a <code>TileCoordiantes</code> object that stores a tiles location
   * in a <code>Point</code> object.
   * 
   * Tile locations are stored as (x * tileSize, y * tileSize).
   * 
   * @param p        the <code>Point</code> that defines the tile location
   * @param tileSize the length, in pixels, of one edge of a tile square
   */
  public TileCoordinate(Point p, int tileSize) {
    TILE_SIZE = tileSize;
    coordinate.setLocation(p.getX() * TILE_SIZE, p.getY() * TILE_SIZE);
  }

  public int getX() {
    return (int) coordinate.getX();
  }

  public int getY() {
    return (int) coordinate.getY();
  }

  public Point getPoint() {
    return coordinate;
  }
}

package com.braithiar.cherno.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EnumMap;
import java.util.Map;

public class Keyboard implements KeyListener {
  public enum Control {
    UP(KeyEvent.VK_W),
    DOWN(KeyEvent.VK_S),
    LEFT(KeyEvent.VK_A),
    RIGHT(KeyEvent.VK_D);

    private final int keyCode;

    private Control(int keyCode) {
      this.keyCode = keyCode;
    }

    public int getKeyCode() {
      return keyCode;
    }
  }

  public final Map<Control, Boolean> controlsMap;

  public Keyboard() {
    controlsMap = new EnumMap<>(Control.class);

    for (Control c : Control.values()) {
      controlsMap.put(c, false);
    }
  }

  @Override
  public void keyTyped(KeyEvent event) {
    // TODO Determine if this is needed in game.
  }

  @Override
  public void keyPressed(KeyEvent event) {
    int keyCode = event.getKeyCode();

    for (Control c : Control.values()) {
      if (c.getKeyCode() == keyCode) {
        controlsMap.put(c, true);
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent event) {
    int keyCode = event.getKeyCode();

    for (Control c : Control.values()) {
      if (c.getKeyCode() == keyCode) {
        controlsMap.put(c, false);
      }
    }
  }

}

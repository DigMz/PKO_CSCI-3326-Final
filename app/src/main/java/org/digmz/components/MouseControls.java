package org.digmz.components;

import static org.lwjgl.glfw.GLFW.*;

import org.digmz.game.Window;
import org.digmz.util.Settings;
import org.digmz.game.GameObject;
import org.digmz.game.MouseListener;

public class MouseControls extends Component {

  GameObject objectHeld = null;

  public void pickupObject(GameObject go) {
    objectHeld = go;
    Window.getScene().addGameObjectToScene(go);
  }

  public void place() {
    this.objectHeld = null;
  }

  @Override
  public void update(float dt) {
    if (objectHeld != null) {
      objectHeld.transform.position.x = MouseListener.getOrthoX();
      objectHeld.transform.position.y = MouseListener.getOrthoY();
      objectHeld.transform.position.x = (int) (objectHeld.transform.position.x / Settings.GRID_WIDTH) * Settings.GRID_WIDTH;
      objectHeld.transform.position.y = (int) (objectHeld.transform.position.y / Settings.GRID_HEIGHT) * Settings.GRID_HEIGHT;

      if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
        place();
      }
    }
  }
}

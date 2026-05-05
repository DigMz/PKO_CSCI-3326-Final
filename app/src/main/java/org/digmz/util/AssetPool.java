package org.digmz.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.digmz.components.Spritesheet;
import org.digmz.game.GameObject;
import org.digmz.renderer.Shader;
import org.digmz.renderer.Texture;

public class AssetPool {
  private static Map<String, Shader> shaders = new HashMap<>(); 
  private static Map<String, Texture> textures = new HashMap<>(); 
  private static Map<String, Spritesheet> spriteSheets = new HashMap<>(); 

  public static Shader getShader(String resourceName) {
    File file = new File(resourceName);
    if (AssetPool.shaders.containsKey(file.getAbsolutePath())) {
      return AssetPool.shaders.get(file.getAbsolutePath());
    } else {
      Shader shader = new Shader(resourceName);
      shader.compile();
      AssetPool.shaders.put(file.getAbsolutePath(), shader);
      return shader;
    }
  }

  public static Texture getTexture(String resourceName) {
    File file = new File(resourceName);
    if (AssetPool.textures.containsKey(file.getAbsolutePath())) {
      return AssetPool.textures.get(file.getAbsolutePath());
    } else {
      Texture texture = new Texture();
      texture.init(resourceName);
      AssetPool.textures.put(file.getAbsolutePath(), texture);
      return texture;
    }
  }

  public static void addSpriteSheet(String resourceName, Spritesheet spriteSheet) {
    File file = new File(resourceName);
    if (!AssetPool.spriteSheets.containsKey(file.getAbsolutePath())) {
      AssetPool.spriteSheets.put(file.getAbsolutePath(), spriteSheet);
    }
  }

  public static Spritesheet getSpriteSheet(String resourceName) {
    File file = new File(resourceName);
    if (!AssetPool.spriteSheets.containsKey(file.getAbsolutePath())) {
      assert false : "Error : Tried to access Spritesheet '" + resourceName + "' not in AssetPool.";
    }
    return AssetPool.spriteSheets.getOrDefault(file.getAbsolutePath(), null);
  }
}

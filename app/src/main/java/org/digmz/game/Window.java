package org.digmz.game;

import org.digmz.util.Time;
import org.digmz.renderer.DebugDraw;
import org.digmz.renderer.Framebuffer;
import org.digmz.scenes.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
  private int width, height;
  private String title;
  private long glfwWindow;
  private Framebuffer framebuffer;

  public float r,g,b,a;

  private static Window window;

  private static Scene currentScene;

  private Window() {
    this.width = 1920;
    this.height = 1080;
    this.title = "Mario";
    r = 0.6f; g = 0.6f; b = 0.6f; a = 0;
  }

  public static void changeScene (int newScene) {
    switch (newScene) {
      case 0:
        currentScene = new LevelEditorScene();
        break;
      case 1:
        currentScene = new LevelScene();
        break;
      default:
        assert false : "Unknown Scene '" + newScene + "'";
    }

    currentScene.init();
    currentScene.start();
  }

  public static Window get() {
    if (Window.window == null) {
      Window.window = new Window();
    }

    return Window.window;
  }

  public static Scene getScene() {
    return get().currentScene;
  }

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
    glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE); // Window will start maximized

		// Create the window
		glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

		if ( glfwWindow == NULL ) {
			throw new RuntimeException("Failed to create the GLFW window");
    }

		// Make the OpenGL context current
		glfwMakeContextCurrent(glfwWindow);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(glfwWindow);

		glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
    glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
    glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
    glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
    glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
      Window.setWidth(newWidth); Window.setHeight(newHeight);
    });

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

    glEnable(GL_BLEND);
    glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

    this.framebuffer = new Framebuffer(1920, 1080);

    Window.changeScene(0);
	}

	private void loop() {

    float beginTime = Time.getTime();
    float endTime = Time.getTime();
    float dt = -1.0f;

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(glfwWindow) ) {
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();

      DebugDraw.beginFrame();

      glClearColor(r, g, b, a);

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

      // this.framebuffer.bind();
      if (dt >= 0.0f) {
        DebugDraw.draw();

        currentScene.update(dt);
      }
      this.framebuffer.unbind();

      MouseListener.endFrame();
      KeyListener.endFrame();

			glfwSwapBuffers(glfwWindow); // swap the color buffers

      endTime = Time.getTime();
      dt = endTime - beginTime;
      beginTime = endTime;
		}

	}

  public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(glfwWindow);
		glfwDestroyWindow(glfwWindow);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
  }

  public static int getWidth() { return get().width; }
  public static int getHeight() { return get().height; }
  public static void setWidth(int width) { get().width = width; }
  public static void setHeight(int height) { get().height = height; }
}

package TetrisGame_PD.Main;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GLGameLoop {
    private long window; //window handle
    private int width;
    private int height;

    public GLGameLoop(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void run() {

        init();
        loop();

        //Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        //Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        //Configure GLFW
        glfwDefaultWindowHints();// optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);// the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);// the window will be resizable

        glfwWindowHint(GLFW_SAMPLES, 4); //AntiAliasing


        //Create the window
        window = glfwCreateWindow(width, height, "Hello World of OpenGL!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        glfwSetWindowSizeCallback(window, (windows, width, height) -> {
            this.width = width;
            this.height = height;

            //configure projection and modelview for new window size.
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, width, height, 0, -1, 1);
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();
            glViewport(0, 0, width, height);

        });



        try (MemoryStack stack = stackPush()) {//the stack frame is popped automatically (try-with-resources)
            IntBuffer pWidth = stack.mallocInt(1); //int*
            IntBuffer pHeight = stack.mallocInt(1); //int*

            //Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            //Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            //Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        //Make the OpenGL context current
        glfwMakeContextCurrent(window);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        //Enable v-sync
        glfwSwapInterval(1);

        //Make the window visible
        glfwShowWindow(window);

    }


    private void loop() {

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_LIGHTING);
        glEnable(GL_ALPHA);
        //glEnable(GL_MULTISAMPLE); //AntiAliasing

        //Set the clear color
        glClearColor(0.07f, 0.07f, 0.1f, 0.0f);

        //
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, height, 0, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glViewport(0, 0, width, height);

        //Run the rendering loop until the user has attempted to close
        //the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //clear the framebuffer
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();

            glBegin(GL_LINES);
            glVertex2d(10, 10);
            glVertex2d(width - 10, height - 10);
            glVertex2d(width - 10, 10);
            glVertex2d(10, height - 10);
            glEnd();

            glfwSwapBuffers(window); //swap the color buffers

            //Poll for window events. The key callback above will only be
            //invoked during this call.
            glfwPollEvents();
        }
    }
}

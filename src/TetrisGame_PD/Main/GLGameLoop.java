package TetrisGame_PD.Main;

import TetrisGame_PD.Main.GameLogic.Element.ElementDraw;
import TetrisGame_PD.Main.GameLogic.Grid.Grid;
import TetrisGame_PD.Main.GameLogic.Grid.GridDraw;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.NativeType;

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
    private int keyPressedCode;
    private static final int FRAMES_PER_SECOND = 25;
    private static final int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;
    int sinceLastMoveDown = 0;

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
        window = glfwCreateWindow(width, height, "Gra Tetris.", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, this::invokeKeyEvent);

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

        long nextGameTick = (long) (glfwGetTime() * 1000.0);
        int sleepTime = 0;

        //Grid grd = new Grid(7, 15);
        GridDraw grdDraw = new GridDraw(7,15);
        long lastUpdateCall=0;


        //Run the rendering loop until the user has attempted to close
        //the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {

            updateGame(grdDraw,(int)(getTime()-lastUpdateCall));
            lastUpdateCall = getTime();
            displayGame(grdDraw);


            nextGameTick += SKIP_TICKS;
            sleepTime = (int) ((nextGameTick) - getTime());
            if (sleepTime >= 0) {
                //Poll for window events. The key callback above will only be
                //invoked during this call.
                glfwPollEvents();
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private long getTime(){
        return (long)(glfwGetTime() * 1000.0);
    }

    private void updateGame(GridDraw grd,int timeElapsed) {
        ElementDraw runningElement = grd.getRunningElement();
        sinceLastMoveDown+=timeElapsed;
        if(sinceLastMoveDown>=400) {
            grd.runningElementMoveDown(1);
            sinceLastMoveDown=0;
        }
    }

    private void displayGame(GridDraw grdDraw) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //clear the framebuffer
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        grdDraw.keyPressed(keyPressedCode);
        keyPressedCode = 0;
        grdDraw.draw(10, 10, width - 20, height - 20);

        glfwSwapBuffers(window); //swap the color buffers
    }

    private void invokeKeyEvent(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
            glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        if (key == GLFW_KEY_UP && action == GLFW_PRESS)
            keyPressedCode = 1;
        if (key == GLFW_KEY_RIGHT && action == GLFW_PRESS)
            keyPressedCode = 2;
        if (key == GLFW_KEY_DOWN && action == GLFW_PRESS)
            keyPressedCode = 3;
        if (key == GLFW_KEY_LEFT && action == GLFW_PRESS)
            keyPressedCode = 4;
        if(key==GLFW_KEY_R && action == GLFW_PRESS)
            keyPressedCode = 5;
    }
}

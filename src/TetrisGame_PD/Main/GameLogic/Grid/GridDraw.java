package TetrisGame_PD.Main.GameLogic.Grid;

import TetrisGame_PD.Main.GameLogic.Element.Element;
import TetrisGame_PD.Main.GameLogic.Element.ElementDraw;
import TetrisGame_PD.Main.GameLogic.Element.TetrisElements;

import java.lang.annotation.ElementType;

import static org.lwjgl.opengl.GL11.*;

public class GridDraw {
    Grid grid;
    private int xOriginPos;
    private int yOriginPos;
    private int widthPx;
    private int heightPx;
    private int slotWidthPx;
    private int slotHeightPx;

    public GridDraw(Grid grid) {
        this.grid = grid;
        this.grid.runningElement = new ElementDraw(TetrisElements.T, 2, 4);
    }

    public void draw(int xPos, int yPos, int widthPx, int heightPx) {
        this.slotWidthPx = widthPx / grid.getSlotsNoWidth();
        this.slotHeightPx = heightPx / grid.getSlotsNoHeight();
        this.xOriginPos = xPos;
        this.yOriginPos = yPos;
        this.widthPx = widthPx;
        this.heightPx = heightPx;


        //Draw running element
        this.grid.runningElement.draw(this);

        //Draw the grid
        glColor4f(0.45f, 0.45f, 0.45f, 0.0f);
        glBegin(GL_LINES); //drawing horizontal lines of grid
        for (int i = 0; i <= grid.getSlotsNoWidth(); i++) {
            glVertex2d(xOriginPos + (i * slotWidthPx), yOriginPos);
            glVertex2d(xOriginPos + (i * slotWidthPx), yOriginPos + heightPx);
        }
        glEnd();

        glBegin(GL_LINES); //drawing vertical lines of grid
        for (int i = 0; i <= grid.getSlotsNoHeight(); i++) {
            glVertex2d(xOriginPos, yOriginPos + (i * slotHeightPx));
            glVertex2d(xOriginPos + widthPx, yOriginPos + (i * slotHeightPx));
        }
        glEnd();



    }

    public void keyPressed(int keyPressedCode) {
        switch (keyPressedCode) {
            case 1:
                this.grid.runningElement.rotate90CW();
                break;
            case 2:
                this.grid.runningElement.moveRight();
                break;
            case 3:
                break;
            case 4:
                this.grid.runningElement.moveLeft();
                break;
            default:
                break;
        }
    }

    public int getxOriginPos() {
        return xOriginPos;
    }

    public int getyOriginPos() {
        return yOriginPos;
    }

    public int getSlotWidthPx() {
        return slotWidthPx;
    }

    public int getSlotHeightPx() {
        return slotHeightPx;
    }
}

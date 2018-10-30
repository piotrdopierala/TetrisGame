package TetrisGame_PD.Main.GameLogic.Grid;

import TetrisGame_PD.Main.GameLogic.BlockDef;
import TetrisGame_PD.Main.GameLogic.Element.ElementDraw;
import TetrisGame_PD.Main.GameLogic.Element.TetrisElements;

import static org.lwjgl.opengl.GL11.*;

public class GridDraw extends Grid {
    private int xOriginPos;
    private int yOriginPos;
    private int slotWidthPx;
    private int slotHeightPx;

    public GridDraw(int SlotsNoWidth, int SlotsNoHeight) {
        super(SlotsNoWidth, SlotsNoHeight);
        runningElement = new ElementDraw(TetrisElements.randomElement(), 2, 0);
    }

    public void draw(int xPos, int yPos, int widthPx, int heightPx) {
        this.slotWidthPx = widthPx / getSlotsNoWidth();
        this.slotHeightPx = heightPx / getSlotsNoHeight();
        this.xOriginPos = xPos;
        this.yOriginPos = yPos;


        //Draw running element
        this.runningElement.draw(this);

        //draw docked elements (elements of previously docked running elements on the bottom of game board;
        for (int i = 0; i < data.length; i++) {//rows
            for (int j = 0; j < data[0].length; j++) {//columns
                if (data[i][j] != 0) {
//                    switch(data[i][j]){
//                        case BlockDef.SOLID_BLOCK_BLUE:
//                            glColor4f(0.15f, 0.15f, 0.5f, 0.0f);//blue
//                            break;
//                        case BlockDef.SOLID_BLOCK_YELLOW:
//                            glColor4f(0.242f, 0.242f, 0.013f, 0.0f);//yellow
//                            break;
//                        case BlockDef.SOLID_BLOCK_VIOLET:
//                            glColor4f(0.145f, 0.048f, 0.110f, 0.0f);//violet
//                            break;
//                        case BlockDef.SOLID_BLOCK_GREEN:
//                            glColor4f(0.00f, 0.145f, 0.061f, 0.0f);//green
//                            break;
//                        case BlockDef.SOLID_BLOCK_ORANGE:
//                            glColor4f(0.242f, 0.145f, 0.013f, 0.0f);//orange
//                            break;
//                        case BlockDef.SOLID_BLOCK_RED:
//                            glColor4f(0.217f, 0.00f, 0.038f, 0.0f);//red
//                            break;
//                        case BlockDef.SOLID_BLOCK_BROWN:
//                            glColor4f(0.102f, 0.051f, 0.00f, 0.0f);//brown
//                            break;
//                        default:
                            glColor4f(0.15f, 0.15f, 0.5f, 0.0f);//blue
//                            break;
//                    }
                    drawRectangle(xOriginPos + slotWidthPx * j, yOriginPos + slotHeightPx * i, slotWidthPx, slotHeightPx);
                }
            }
        }

        //Draw the grid
        glColor4f(0.45f, 0.45f, 0.45f, 0.0f);
        glBegin(GL_LINES); //drawing horizontal lines of grid
        for (int i = 0; i <= getSlotsNoWidth(); i++) {
            glVertex2d(xOriginPos + (i * slotWidthPx), yOriginPos);
            glVertex2d(xOriginPos + (i * slotWidthPx), yOriginPos + heightPx);
        }
        glEnd();

        glBegin(GL_LINES); //drawing vertical lines of grid
        for (int i = 0; i <= getSlotsNoHeight(); i++) {
            glVertex2d(xOriginPos, yOriginPos + (i * slotHeightPx));
            glVertex2d(xOriginPos + widthPx, yOriginPos + (i * slotHeightPx));
        }
        glEnd();


    }

    private void drawRectangle(int x, int y, int width, int height) {
        glBegin(GL_QUADS);
        {
            glVertex2d(x, y);
            glVertex2d(x, y + height);
            glVertex2d(x + width, y + height);
            glVertex2d(x + width, y);
        }
        glEnd();
    }

    public void keyPressed(int keyPressedCode) {
        switch (keyPressedCode) {
            case 1:
                this.runningElement.rotate90CW();
                break;
            case 2:
                if((runningElement.getCurrentSlotPos()[0]+runningElement.getBlocksSize()[1])<(slotsNoWidth))
                    this.runningElement.moveRight();
                break;
            case 3:
                this.runningElementDockDown();
                break;
            case 4:
                if (runningElement.getCurrentSlotPos()[0] > 0) { //block if want to move outside left grid border
                    this.runningElement.moveLeft();
                }
                break;
            case 5:
                this.runningElement.moveToBeginning();
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

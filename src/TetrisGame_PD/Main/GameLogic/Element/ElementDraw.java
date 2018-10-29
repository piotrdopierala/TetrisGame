package TetrisGame_PD.Main.GameLogic.Element;

import TetrisGame_PD.Main.GameLogic.Grid.GridDraw;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class ElementDraw extends Element {

    public ElementDraw(TetrisElements elType, int xSlPos, int ySlPos) {
        super(elType, xSlPos, ySlPos);
    }

    /**Drawing element on grid
     *
     * @param grid grid draw object in witch te element is to be drawn
     */
    public void draw(GridDraw grid){
        int slotWidthPx =  grid.getSlotWidthPx();
        int slotHeightPx = grid.getSlotHeightPx();
        int xOriginPos= grid.getxOriginPos();
        int yOriginPos= grid.getyOriginPos();

        glColor4f(0.25f, 0.25f, 0.6f, 0.0f);

        for (int i = 0; i < this.size; i++) { //rows
            int[] row = this.getRow(i);
            for (int j = 0; j < row.length; j++) { //columns
                if(row[j]>0) {
                    drawRectangle(xOriginPos + slotWidthPx * (xSlPos+j), yOriginPos + slotHeightPx * (ySlPos+i), slotWidthPx, slotHeightPx);
                }
            }
        }
    }

    public void moveLeft(){
        this.xSlPos-=1;
    }

    public void moveRight(){
        this.xSlPos+=1;
    }

    private void drawRectangle(int x, int y,int width,int height){
        glBegin(GL_QUADS);
        {
            glVertex2d(x,y);
            glVertex2d(x,y+height);
            glVertex2d(x+width,y+height);
            glVertex2d(x+width,y);
        }
        glEnd();
    }

}

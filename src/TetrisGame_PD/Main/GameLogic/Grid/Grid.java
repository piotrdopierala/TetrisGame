package TetrisGame_PD.Main.GameLogic.Grid;


import TetrisGame_PD.Main.GameLogic.Element.ElementDraw;
import TetrisGame_PD.Main.GameLogic.Element.TetrisElements;

public class Grid {
    private int slotsNoWidth;
    private int slotsNoHeight;

    protected ElementDraw runningElement;

    int[][] data;//bricks on grid data

    public Grid(int SlotsNoWidth, int SlotsNoHeight) {
        this.slotsNoWidth = SlotsNoWidth;
        this.slotsNoHeight = SlotsNoHeight;
        data = new int[SlotsNoHeight][SlotsNoWidth];

        //fill bottom row with blocks
        for (int i = 0; i < SlotsNoWidth; i++) {
            data[SlotsNoHeight - 1][i] = 100;
        }
    }

    //checks if running element is to be docked in bottom elements stack.
    public void dockElement() {
        int[][] runningElementData = this.runningElement.getData();
        int[] runningElPos = this.runningElement.getCurrentSlotPos();

        // -search every column for bottom block of funning element
        // -if found check if block below is fixed, if true dock the running element
        boolean dockRunning = false;
        for (int i = (0); i < (runningElementData[0].length); i++) { //i - every column in running element
            //in every column search for bottom running block
            for (int j = 0; j < runningElementData.length; j++) { //j - every row
                if (runningElementData[j][i] != 0) //found running element block, check whats below (next row)
                    if ((j + 1) >
                            runningElementData.length) { //does it have running block data below (next row) ?
                        //no running element data below - check if solid block exists below
                        if (data[runningElPos[1] + j + 1][runningElPos[0]+i] != 0) {
                            dockRunning = true;
                            break;
                        }
                    } else {
                        if (runningElementData[j + 1][i] == 0) {
                            //no block below - check if solid block exists
                            if (data[runningElPos[1] + j + 1][runningElPos[0]+i] != 0) {
                                if(ElementDraw.noElement>=2) {
                                    int a = 10;
                                }
                                dockRunning = true;
                                break;
                            }
                        }
                    }
            }
            if (dockRunning)
                break;
        }
        if (dockRunning) {
            for (int i = 0; i < runningElementData.length; i++) { //i - row
                for (int j = 0; j < runningElementData[0].length; j++) { //j-column
                    if ((runningElPos[1] + i) < data.length && (runningElPos[0] + j) < data[0].length) {
                        if (runningElementData[i][j] != 0) {
                            data[runningElPos[1] + i][runningElPos[0] + j] = runningElementData[i][j] + 1000;
                        }
                    }
                }
            }
            newRunningElement();
        }

    }

    public int getSlotsNoWidth() {
        return slotsNoWidth;
    }

    public int getSlotsNoHeight() {
        return slotsNoHeight;
    }

    public ElementDraw getRunningElement() {
        return runningElement;
    }

    public void runningElementMoveDown(int noSlots) {
        runningElement.moveDown(noSlots);
        dockElement();
    }

    public void newRunningElement() {
        this.runningElement = new ElementDraw(TetrisElements.T, 0, 0);
    }
}

package TetrisGame_PD.Main.GameLogic.Grid;


import TetrisGame_PD.Main.GameLogic.Element.ElementDraw;
import TetrisGame_PD.Main.GameLogic.Element.TetrisElements;

public class Grid {
    protected int slotsNoWidth;
    protected int slotsNoHeight;
    private static int noRemovedLines=0;


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

    /**
     *checks if running element is to be docked in bottom elements stack.
     * returns true if element was docked on the bottom
     */

    public boolean dockElement() {
        int[][] runningElementData = this.runningElement.getData();
        int[] runningElPos = this.runningElement.getCurrentSlotPos();

        // -search every column for bottom block of funning element
        // -if found check if block below is fixed, if true dock the running element
        boolean dockRunning = false;
        for (int i = (0); i < (runningElementData[0].length); i++) { //i - every column in running element
            //in every column search for bottom running block
            for (int j = 0; j < runningElementData.length; j++) { //j - every row
                if (runningElementData[j][i] != 0) //found running element block, check whats below (next row)
                    if ((j + 1) > runningElementData.length) { //does it have running block data below (next row) ?
                        //no running element data below - check if solid block exists below
                        if (data[runningElPos[1] + j + 1][runningElPos[0] + i] != 0) {
                            dockRunning = true;
                            break;
                        }
                    } else {
                        //no block below - check if solid block exists
                        if (data[runningElPos[1] + j + 1][runningElPos[0] + i] != 0) {
                            if (ElementDraw.noElement >= 2) {
                                int a = 10;
                            }
                            dockRunning = true;
                            break;
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
            removeAllCompleteLines();
            newRunningElement();
            return true;
        }
        return false;

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

    public boolean runningElementMoveDown(int noSlots) {
        boolean wasDocked = dockElement();
        runningElement.moveDown(noSlots);
        return wasDocked;
    }

    public void runningElementDockDown(){
            while(!runningElementMoveDown(1));
    }

    public void newRunningElement() {
         this.runningElement = new ElementDraw(TetrisElements.randomElement(), this.slotsNoWidth/2, -2p); //next random element
        //this.runningElement = new ElementDraw(TetrisElements.I, 0, 0); //next specific element
    }

    /**
     * Returns number of complete line to remove from
     * @return index of line that is complete and ready to be removed
     */
    private int searchLineComplete(){
        int noBlksInRow;
        for (int i = this.getSlotsNoHeight()-2; i > 0 ; i--) { //i-row
            noBlksInRow=0;
            for (int j = 0; j < this.getSlotsNoWidth(); j++) {
                if(data[i][getSlotsNoWidth()-1-j]!=0){
                    noBlksInRow++;
                }
            }
            if(noBlksInRow==getSlotsNoWidth()){
                //System.out.println("Line: "+i+" is complete.");
                return i;
            }
        }
        return -1;
    }

    private void removeAllCompleteLines(){
        int lineToRemove=-1;
        while((lineToRemove= searchLineComplete())>=0){
            removeCompleteLine(lineToRemove);
        }
    }

    private void removeCompleteLine(int lineNo){
        noRemovedLines++;
        //System.out.println("Removing line number" + noRemovedLines + ".");
        data[lineNo] = new int[data[0].length]; //fill with zero's
        for (int i = lineNo; i > 0; i--) {
            data[i]=data[i-1].clone();
        }
    }

    protected void removeLinesWithHoles(){
        boolean foundBlockInRow=false;
        boolean foundEmptyAndBlockAbove=false;
        System.out.println("użyto tajnego kodu !");
        for (int i = 0; i < data.length-1; i++) {
            foundBlockInRow=false;
            foundEmptyAndBlockAbove=false;
            for (int j = 0; j < data[0].length; j++) {
                if(data[i][j]!=0)
                    foundBlockInRow=true;
                if(data[i][j]==0 && i-1>=0) //above has block
                    if(data[i-1][j]!=0)
                    foundEmptyAndBlockAbove=true;
                if(foundBlockInRow & foundEmptyAndBlockAbove) {
                    System.out.println("usuwam linie "+i);
                    removeCompleteLine(i);
                    break;
                }
            }
        }
    }

}

package TetrisGame_PD.Main.GameLogic.Grid;


import TetrisGame_PD.Main.GameLogic.Element.ElementDraw;

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
            data[SlotsNoHeight-1][i]=100;
        }
    }

    //checks if running element is to be docked in bottom elements stack.
    public void dockElement(){
        int[][] runningElement = this.runningElement.getData();
        //put running element in new data array;
        int[][] dataWithElement = data.clone();



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
}

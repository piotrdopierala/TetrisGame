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
    }

    public void dockElement(){

    }

    public int getSlotsNoWidth() {
        return slotsNoWidth;
    }

    public int getSlotsNoHeight() {
        return slotsNoHeight;
    }

}

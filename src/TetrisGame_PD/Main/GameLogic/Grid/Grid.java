package TetrisGame_PD.Main.GameLogic.Grid;

import TetrisGame_PD.Main.GameLogic.Element.Element;

public class Grid {
    private int noSlotsWidth;
    private int noSlotsHeight;

    private Element runningElement;

    int[][] data;

    public Grid(int noSlotsWidth, int noSlotsHeight) {
        this.noSlotsWidth = noSlotsWidth;
        this.noSlotsHeight = noSlotsHeight;
        data = new int[noSlotsHeight][noSlotsWidth];
    }


    public void dockElement(){

    }



}

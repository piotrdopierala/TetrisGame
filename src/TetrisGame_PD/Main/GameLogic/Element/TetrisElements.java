package TetrisGame_PD.Main.GameLogic.Element;

public enum TetrisElements {
    I('I'),J('J'),L('L'),O('O'),S('S'),T('T'),Z('Z');

    private char type;

    TetrisElements(char z) {
        type=z;
    }

    public int[][] getArray(){
        switch (type){
            case 'I':
                return new int[][]{{1,0,0,0},{1,0,0,0},{1,0,0,0},{1,0,0,0}};
            case 'J':
                return new int[][]{{1,1,1,0},{0,0,1,0},{0,0,0,0},{0,0,0,0}};
            case 'L':
                return new int[][]{{1,1,1,0},{1,0,0,0},{0,0,0,0},{0,0,0,0}};
            case 'O':
                return new int[][]{{1,1,0,0},{1,1,0,0},{0,0,0,0},{0,0,0,0}};
            case'S':
                return new int[][]{{0,1,1,0},{1,1,0,0},{0,0,0,0},{0,0,0,0}};
            case'T':
                return new int[][]{{1,1,1,0},{0,1,0,0},{0,0,0,0},{0,0,0,0}};
            case'Z':
                return new int[][]{{1,1,0,0},{0,1,1,0},{0,0,0,0},{0,0,0,0}};
            default:
                return new int[4][4];
        }
    }
}

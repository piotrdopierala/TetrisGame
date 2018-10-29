package TetrisGame_PD.Main.GameLogic.Element;

import TetrisGame_PD.Main.GameLogic.BlockDef;

public enum TetrisElements {
    I('I'), J('J'), L('L'), O('O'), S('S'), T('T'), Z('Z');

    private char type;

    TetrisElements(char z) {
        type = z;
    }

    public int[][] getArray() {
        int a=BlockDef.RUNNING_BLOCK;
        switch (type) {
            case 'I':
                a= BlockDef.RUNNING_BLOCK_BLUE;
                return new int[][]{{a, 0, 0, 0}, {a, 0, 0, 0}, {a, 0, 0, 0}, {a, 0, 0, 0}};
            case 'J':
                a= BlockDef.RUNNING_BLOCK_YELLOW;
                return new int[][]{{a, a, a, 0}, {0, 0, a, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
            case 'L':
                a= BlockDef.RUNNING_BLOCK_VIOLET;
                return new int[][]{{a, a, a, 0}, {a, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
            case 'O':
                a= BlockDef.RUNNING_BLOCK_GREEN;
                return new int[][]{{a, a, 0, 0}, {a, a, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
            case 'S':
                a= BlockDef.RUNNING_BLOCK_ORANGE;
                return new int[][]{{0, a, a, 0}, {a, a, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
            case 'T':
                a= BlockDef.RUNNING_BLOCK_RED;
                return new int[][]{{a, a, a, 0}, {0, a, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
            case 'Z':
                a= BlockDef.RUNNING_BLOCK_BROWN;
                return new int[][]{{a, a, 0, 0}, {0, a, a, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
            default:
                return new int[4][4];
        }
    }
}

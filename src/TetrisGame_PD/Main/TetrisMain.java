package TetrisGame_PD.Main;

public class TetrisMain {
    public static void main(String[] args) {
        new GLGameLoop(500, 900).run();

        //todo 1) element is docked one game click too early
        //DONE 2) add functionality to fast forward running element (down arrow)
        //DONE 3) when element rotaded on right grid border, bug that allows element to move outside
        //DONE 4) add different collors to elements


    }
}

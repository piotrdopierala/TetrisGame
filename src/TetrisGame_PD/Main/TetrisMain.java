package TetrisGame_PD.Main;

public class TetrisMain {
    public static void main(String[] args) {
        new GLGameLoop(500, 900).run();

        //todo 1: its possible to merge into static (bottom area, previously docked blocks) by moving left/right fast
        //        when moving left/right should check is there is no collision with that static block.
        //todo 2: render some text
        //todo 3: count points


    }
}

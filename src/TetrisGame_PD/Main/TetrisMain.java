package TetrisGame_PD.Main;

public class TetrisMain {
    public static void main(String[] args) {
        new GLGameLoop(500, 900).run();

        //DONE  : its possible to merge into static (bottom area, previously docked blocks) by moving left/right fast
        //        when moving left/right should check is there is no collision with that static block.
        //DONE  : bug when drawing grid. lines go outside boxes because of rounding error.
        //todo 2: render some text
        //todo 3: count points
        //todo 4: its possible to rotate near static blocks and merge into them. Have to check for collision after rotation and prevent this.


    }
}

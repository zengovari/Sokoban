package game.Controller;


import game.menu.Menu;

/**
 *  A controller for menus to display their content with the help of the {@link Menu} interface.
 */

public class MenuController  {

    public static void display(Menu menu) {
        menu.display();
    }
}

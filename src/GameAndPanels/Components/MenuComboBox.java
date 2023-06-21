package GameAndPanels.Components;

import GameAndPanels.Game;

import javax.swing.*;
import java.awt.*;

// consistently styled combo box to be used in menus
public class MenuComboBox<E> extends JComboBox {
    public MenuComboBox() {
        super();
        // set basic combobox properties
        this.setForeground(Color.black);
        this.setFont(new Font("Arial", Font.BOLD, (int)(Game.gameWidth / 60)));
        this.setFocusable(false);
    }

    public MenuComboBox(E[] args) {
        // create combo box with the passed value list
        super(args);
        // set basic combobox properties
        this.setForeground(Color.black);
        this.setFont(new Font("Arial", Font.BOLD, (int)(Game.gameWidth / 60)));
        this.setFocusable(false);
    }

}

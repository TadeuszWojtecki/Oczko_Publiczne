package GameAndPanels.Components;

import GameAndPanels.Game;

import javax.swing.*;
import java.awt.*;

// consistently styled radio button to be used in menus
public class MenuRadioButton extends JRadioButton {
    public MenuRadioButton() {
        // set basic properties
        this.setForeground(Color.black);
        this.setFont(new Font("Arial", Font.BOLD, (int) (Game.gameWidth / 60)));
        this.setFocusable(false);
    }

    public MenuRadioButton(String text) {
        this.setForeground(Color.black);
        this.setFont(new Font("Arial", Font.BOLD, (int) (Game.gameWidth / 60)));
        this.setFocusable(false);
        // add passed text
        this.setText(text);
    }
}

package GameAndPanels.Components;

import GameAndPanels.Game;

import java.awt.*;

// the same as MenuButton, except every button has the same base width
public class MainMenuButton extends MenuButton {
    public MainMenuButton() {
        super();
        // set the same size for every main menu button
        this.setPreferredSize(new Dimension((int)(Game.gameWidth / 3.5), (int)(Game.gameHeight / 11)));
    }

    public MainMenuButton(String text) {
        // create button with the text
        this();
        this.setText(text);
    }
}

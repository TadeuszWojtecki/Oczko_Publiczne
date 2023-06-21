package GameAndPanels.Components;

import GameAndPanels.Game;

import javax.swing.*;
import java.awt.*;

// used to store multiple buttons in a single panel instance
// (also found use in storing texts)
public class MenuButtonContainer extends JPanel {
    public MenuButtonContainer() {
        // basic container properties
        this.setBackground(Game.myWhite);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, Game.gameWidth / 120, Game.gameHeight / 120));
        this.setBorder(BorderFactory.createLineBorder(Game.myRed, Game.gameHeight / 150));
    }
}

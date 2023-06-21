package GameAndPanels.Components;

import GameAndPanels.Game;

import javax.swing.*;
import java.awt.*;

// consistently styled label to be used for storing text
public class MenuText extends JLabel {
    public MenuText() {
        // set basic label properties
        this.setText("text");
        this.setForeground(Game.myRed);
        this.setFont(new Font("Arial", Font.BOLD, (int)(Game.gameWidth / 60)));
    }

    public MenuText(String text) {
        // create label with the text
        this();
        this.setText(text);
    }

}

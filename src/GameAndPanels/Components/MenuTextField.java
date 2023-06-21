package GameAndPanels.Components;

import GameAndPanels.Game;

import javax.swing.*;
import java.awt.*;

// consistently styled textfield to be used in menus
public class MenuTextField extends JTextField {
    public MenuTextField() {
        // set basic textfield properties
        this.setForeground(Color.black);
        this.setFont(new Font("Arial", Font.BOLD, (int)(Game.gameWidth / 60)));
        this.setPreferredSize(new Dimension((int)(Game.gameWidth / 7), (int)(Game.gameWidth / 45)));
    }

}

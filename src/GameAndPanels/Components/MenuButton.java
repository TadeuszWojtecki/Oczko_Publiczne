package GameAndPanels.Components;

import GameAndPanels.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// consistently styled button to be used in menus
public class MenuButton extends JButton implements MouseListener {
    public MenuButton() {
        // set basic button properties
        this.setBackground(Game.myWhite);
        this.setForeground(Game.myRed);
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Game.myWhite, Game.gameHeight/130), BorderFactory.createLineBorder(Game.myRed, Game.gameHeight/130)));
        this.setFont(new Font("Arial", Font.BOLD, (int)(Game.gameWidth / 40)));
        this.setText("button");
        this.setFocusable(false);

        // for mouse hovering effect
        this.addMouseListener(this);
        // change button color when selecting from default blue
        UIManager.put("Button.select", new Color(201, 200, 200));
    }

    public MenuButton(String text) {
        // create button with the text
        this();
        this.setText(text);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (this.isEnabled()) {this.setBackground(new Color(218, 218, 218));}
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setBackground(Color.white);
    }

    // irrelevant
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
}

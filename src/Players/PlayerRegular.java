package Players;

import Cards.Card;
import GameAndPanels.Components.PlayerControlPanel;
import GameAndPanels.MatchPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class PlayerRegular extends Player implements ActionListener {
    // random number generator
    static Random random = new Random();

    // base constructor using super class' constructor
    public PlayerRegular() {
        super();
    }

    // constructor with a nickname for the player (using super's constructor)
    // and JPanel to add the player's control panel to
    public PlayerRegular(String nickname, JPanel matchPanel) {
        super(nickname);
        controlPanel = new PlayerControlPanel(nickname);
        controlPanel.drawButton.addActionListener(this);
        controlPanel.stayButton.addActionListener(this);
        matchPanel.add(controlPanel);
    }


    // player is granted a move
    public void move() {
        // check player and board status before the move
        String status = MatchPanel.checkStatus(this);
        switch (status) {
            // if status is staying or lost, skip move
            case "staying":
            case "lost":
                // let next player make a move
                if (MatchPanel.playerListIterator.hasNext()) {
                    MatchPanel.playerListIterator.next().move();
                }
                else {
                    MatchPanel.playerListIterator = MatchPanel.playerList.iterator();
                    MatchPanel.playerListIterator.next().move();
                }
                break;
            // if status is free, allow user to make a move (click buttons)
            case "free":
                controlPanel.drawButton.setEnabled(true);
                controlPanel.stayButton.setEnabled(true);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // player is drawing
        if (e.getSource() == controlPanel.drawButton) {
            // draw card from the match's card stack
            Card drawn = MatchPanel.cardStack.pop();
            // add it to the player's hand
            hand.add(drawn);
            // draw the card on the board
            controlPanel.middlePanel.add(drawn.cardImage);
            // update user score
            points += drawn.value;
            // update user score text
            controlPanel.updateScore(points);

            // disable controls for the player (he made his move)
            controlPanel.drawButton.setEnabled(false);
            controlPanel.stayButton.setEnabled(false);

            // check player and board status after the move
            String status = MatchPanel.checkStatus(this);

            // check if game hasn't ended (status won, or a draw betwen players)
            if (!status.equals("won") && !status.equals("draw")) {
                // allow next player to move
                if (MatchPanel.playerListIterator.hasNext()) {
                    MatchPanel.playerListIterator.next().move();
                } else {
                    MatchPanel.playerListIterator = MatchPanel.playerList.iterator();
                    MatchPanel.playerListIterator.next().move();
                }
            }
        }
        // player is staying
        else if (e.getSource() == controlPanel.stayButton) {
            // update status to staying
            this.isStaying = true;

            // disable controls for the player (he made his move)
            controlPanel.drawButton.setEnabled(false);
            controlPanel.stayButton.setEnabled(false);

            // check player and board status after the move
            String status = MatchPanel.checkStatus(this);

            // check if game hasn't ended (status won, or a draw betwen players)
            if (!status.equals("won") && !status.equals("draw")) {
                // allow next player to move
                if (MatchPanel.playerListIterator.hasNext()) {
                    MatchPanel.playerListIterator.next().move();
                } else {
                    MatchPanel.playerListIterator = MatchPanel.playerList.iterator();
                    MatchPanel.playerListIterator.next().move();
                }
            }
        }
    }
}

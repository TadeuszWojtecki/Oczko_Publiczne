package Players;

import Cards.Card;
import GameAndPanels.Components.PlayerControlPanel;

import java.util.ArrayList;

// base abstract player class
public abstract class Player {
    // control panel for the player to use
    public PlayerControlPanel controlPanel;
    // player's name
    public String nickname;
    // player's points
    public int points;
    // status flag, tells if player is staying
    public boolean isStaying;
    // status flag, tells if player lost
    public boolean isLost;
    // status flag, tells if player won
    public boolean isWon;
    // cards that the player has on board
    public ArrayList<Card> hand;

    // default constructor, sets default points to 0 and sets status flags to false
    public Player() {
        nickname = "undefined";
        points = 0;
        isStaying = false;
        isLost = false;
        isWon = false;
        hand = null;
        controlPanel = null;
    }

    // constructor with string, sets the nickname to the passed string
    public Player(String nickname) {
        this.nickname = nickname;
        points = 0;
        isStaying = false;
        isLost = false;
        isWon = false;
        hand = new ArrayList<Card>();
        controlPanel = null;
    }

    // updates player's active indicator
    public void updateActiveIndicator(String status) {
        if (status.equals("active")) {controlPanel.setActive();}
        else if (status.equals("inactive")) { controlPanel.setInactive(); }
        else if (status.equals("lost")) { controlPanel.setLost(); }
        else if (status.equals("staying")) { controlPanel.setStaying(); }
    }

    // method to prompt user for move
    public abstract void move();

    // method to return cards the player is holding
    public void giveCards() {
        // remove cards from the player's control panel
        for (Card card : hand) {
            controlPanel.middlePanel.remove(card.cardImage);
        }
        controlPanel.middlePanel.revalidate();
        controlPanel.middlePanel.repaint();

        // reset player's hand
        hand.clear();
        hand = new ArrayList<Card>();
    }

    // override toString to return player nickname
    @Override
    public String toString() {return this.nickname;}
}

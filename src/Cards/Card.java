package Cards;

import GameAndPanels.Game;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

// basic card class
// stores the rank and suit, which is used to determine which card to draw on the board,
// and value, which determines how many points should be given to the player that draws the card
public class Card {
    public String rank;     // rank of the card
    public String suit;     // suit of the card
    public int value;       // value of the card
    public JLabel cardImage;// an image of the card to be added to the board once drawn

    // default card constructor
    public Card() {
        rank = "undefined";
        suit = "undefined";
        value = 0;
        cardImage = null;
    }

    // card constructor, takes in the rank and the suit of the card
    // based on the above information assings appropriate image and value to the card
    public Card(String rank, String suit) {
        // assign rank and suit
        this.rank = rank;
        this.suit = suit;
        ImageIcon cardIcon = null; // image of the card, created based on passed rank and suit
        cardImage = new JLabel();
        cardImage.setPreferredSize(new Dimension((int)(Game.gameWidth / 17), (int)(Game.gameHeight / 6.5))); // na razie wymiary z dupy

        // assign appropriate card value
        switch (rank) {
            case "2" -> value = 2;
            case "3" -> value = 3;
            case "4" -> value = 4;
            case "5" -> value = 5;
            case "6" -> value = 6;
            case "7" -> value = 7;
            case "8" -> value = 8;
            case "9" -> value = 9;
            case "10" -> value = 10;
            case "jack" -> value = 2;
            case "queen" -> value = 3;
            case "king" -> value = 4;
            case "ace" -> value = 11;
        }

        // assign correct card image
        String fname = rank + "_of_" + suit + ".png";
        try {
            cardIcon = new ImageIcon(new ImageIcon(getClass().getResource("/CardsPNG/" + fname)).getImage().getScaledInstance(cardImage.getPreferredSize().width, cardImage.getPreferredSize().height, Image.SCALE_SMOOTH));
            cardImage.setIcon(cardIcon);
            cardImage.setOpaque(false); // to keep the transparency of certain elements (corners of the card PNGs)
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Wczytanie grafiki karty nie powiodło się.\nZamykanie programu.", "Brakujący plik", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    // fill the passed stack with cards
    public static void fillDeck(Stack<Card> cardStack) {
        cardStack.push(new Card("2", "clubs"));
        cardStack.push(new Card("2", "diamonds"));
        cardStack.push(new Card("2", "hearts"));
        cardStack.push(new Card("2", "spades"));
        cardStack.push(new Card("3", "clubs"));
        cardStack.push(new Card("3", "diamonds"));
        cardStack.push(new Card("3", "hearts"));
        cardStack.push(new Card("3", "spades"));
        cardStack.push(new Card("4", "clubs"));
        cardStack.push(new Card("4", "diamonds"));
        cardStack.push(new Card("4", "hearts"));
        cardStack.push(new Card("4", "spades"));
        cardStack.push(new Card("5", "clubs"));
        cardStack.push(new Card("5", "diamonds"));
        cardStack.push(new Card("5", "hearts"));
        cardStack.push(new Card("5", "spades"));
        cardStack.push(new Card("6", "clubs"));
        cardStack.push(new Card("6", "diamonds"));
        cardStack.push(new Card("6", "hearts"));
        cardStack.push(new Card("6", "spades"));
        cardStack.push(new Card("7", "clubs"));
        cardStack.push(new Card("7", "diamonds"));
        cardStack.push(new Card("7", "hearts"));
        cardStack.push(new Card("7", "spades"));
        cardStack.push(new Card("8", "clubs"));
        cardStack.push(new Card("8", "diamonds"));
        cardStack.push(new Card("8", "hearts"));
        cardStack.push(new Card("8", "spades"));
        cardStack.push(new Card("9", "clubs"));
        cardStack.push(new Card("9", "diamonds"));
        cardStack.push(new Card("9", "hearts"));
        cardStack.push(new Card("9", "spades"));
        cardStack.push(new Card("10", "clubs"));
        cardStack.push(new Card("10", "diamonds"));
        cardStack.push(new Card("10", "hearts"));
        cardStack.push(new Card("10", "spades"));
        cardStack.push(new Card("ace", "clubs"));
        cardStack.push(new Card("ace", "diamonds"));
        cardStack.push(new Card("ace", "hearts"));
        cardStack.push(new Card("ace", "spades"));
        cardStack.push(new Card("jack", "clubs"));
        cardStack.push(new Card("jack", "diamonds"));
        cardStack.push(new Card("jack", "hearts"));
        cardStack.push(new Card("jack", "spades"));
        cardStack.push(new Card("king", "clubs"));
        cardStack.push(new Card("king", "diamonds"));
        cardStack.push(new Card("king", "hearts"));
        cardStack.push(new Card("king", "spades"));
        cardStack.push(new Card("queen", "clubs"));
        cardStack.push(new Card("queen", "diamonds"));
        cardStack.push(new Card("queen", "hearts"));
        cardStack.push(new Card("queen", "spades"));
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

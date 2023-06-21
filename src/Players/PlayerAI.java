package Players;

import Cards.Card;
import GameAndPanels.Components.PlayerControlPanel;
import GameAndPanels.MatchPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class PlayerAI extends Player {
    // random number generator
    static Random random = new Random();

    // base constructor using super class' constructor
    public PlayerAI() {
        super();
    }

    // constructor with a nickname for the player (using super's constructor)
    // and JPanel to add the player's control panel to
    public PlayerAI(String nickname, JPanel matchPanel) {
        super(nickname);
        controlPanel = new PlayerControlPanel(nickname);
        matchPanel.add(controlPanel);
    }

    // AI is called to make a move
    public void move() {
        // check player and board status before the move
        String status = MatchPanel.checkStatus(this);
        if (status.equals("free")) {
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    makeMove(status);
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
        else {
            makeMove(status);
        }
    }

    // an actual move, called after a delay from the move(), AI decides which move to make
    void makeMove(String status) {
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
            // if status is free, allow user to make a move
            case "free":
                // check, if player needs to draw

                // check how many players are participating in the match
                int playerCount = MatchPanel.playerList.size();

                // count how many players are lost, and count all staying players
                int stayingCounter = 0;
                int lostCounter = 0;
                for (Player player : MatchPanel.playerList) {
                    if (player.isLost) { lostCounter++; }
                    if (player.isStaying) { stayingCounter++; }
                }

                // check the highest score among non-lost
                int highscore = 0;
                for (Player player : MatchPanel.playerList) {
                    if (!player.isLost && player.points > highscore) {
                        highscore = player.points;
                    }
                }

                // check if there is already staying player with more points than you, if so, draw
                for (Player player : MatchPanel.playerList) {
                    if (player.isStaying && player.points > points) {
                        //System.out.println("Forced to draw (less points than other staying)");
                        draw(); return;
                    }
                }

                // check, if there is only one other player not lost and not staying, and if he has more points, draw
                if (playerCount - 2 == stayingCounter + lostCounter) {
                    for (Player player : MatchPanel.playerList) {
                        if (!player.toString().equals(this.toString()) && player.points > points && !player.isLost) {
                            //System.out.println("Forced to draw, last not lost and not staying player has more points, if i pass he wins");
                            draw(); return;
                        }
                    }
                }

                // check, if 2 players are staying at exact points the AI has, in this case AI has nothing to lose,
                // if it stays its a draw, when it draws it can exceed 21 and match still end in a draw, but he has a chance to draw 21
                if (playerCount > 2) {
                    int highScoreStayingCounter = 0;
                    for (Player player : MatchPanel.playerList) {
                        if (player.isStaying && player.points == highscore) { highScoreStayingCounter++; }
                    }
                    if (highScoreStayingCounter > 1) {
                        //System.out.println("Nothing to lose! Others will draw anyway!");
                        draw(); return;
                    }
                }

                // always draw if points below or equal 13
                if (points <= 13) {
                    //System.out.println("Drawing (points <= 13)");
                    draw(); return;
                } else if (points == 14) {
                    // 90% to draw at 14 points
                    int draw = random.nextInt(100) + 1;
                    if (draw <= 90) {
                        //System.out.println("drew with 90% chance");
                        draw(); return;
                    } else {
                        //System.out.println("staying with 10% chance");
                        stay(); return;
                    }
                } else if (points == 15) {
                    // 80% to draw at 15 points
                    int draw = random.nextInt(100) + 1;
                    if (draw <= 80) {
                        //System.out.println("drew with 80% chance");
                        draw(); return;
                    } else {
                        //System.out.println("staying with 20% chance");
                        stay(); return;
                    }
                } else if (points == 16) {
                    // 70% to draw at 16 points
                    int draw = random.nextInt(100) + 1;
                    if (draw <= 70) {
                        //System.out.println("drew with 70% chance");
                        draw(); return;
                    } else {
                        //System.out.println("staying with 30% chance");
                        stay(); return;
                    }
                } else if (points == 17) {
                    // 60% to draw at 17 points
                    int draw = random.nextInt(100) + 1;
                    if (draw <= 60) {
                        //System.out.println("drew with 60% chance");
                        draw(); return;
                    } else {
                        //System.out.println("staying with 40% chance");
                        stay(); return;
                    }
                } else if (points == 18) {
                    // 30% to draw at 18 points
                    int draw = random.nextInt(100) + 1;
                    if (draw <= 30) {
                        //System.out.println("drew with 30% chance");
                        draw(); return;
                    } else {
                        //System.out.println("staying with 70% chance");
                        stay(); return;
                    }
                } else if (points == 19) {
                    // analyze the board and cards left in the deck

                    // check, if there are any twos left in the deck
                    int twoCounter = 0;
                    for (Card card : MatchPanel.cardStack) {
                        if (card.toString().split(" ", 2)[0].equals("2")) {
                            twoCounter++;
                        }
                    }

                    // if there are at least 2 twos, have a chance to draw
                    if (twoCounter > 0) {
                        // increase draw chance by 3% per two remaining in the deck
                        int draw = random.nextInt(100) + 1;
                        int chance = twoCounter * 3;

                        if (draw <= chance) {
                            //System.out.println("drew with " + chance + "% chance");
                            draw(); return;
                        } else {
                            //System.out.println("didn't draw with " + chance + "% chance");
                            stay(); return;
                        }
                    } else {
                        //System.out.println("Staying, no twos left in the deck");
                        stay(); return;
                    }
                }
                break;
        }
    }

    // draws a card from the card pile
    public void draw() {
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

    // makes the player stay
    public void stay() {
        // update status to staying
        this.isStaying = true;

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

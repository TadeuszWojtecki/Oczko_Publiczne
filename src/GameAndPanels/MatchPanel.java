package GameAndPanels;

import Cards.Card;
import GameAndPanels.Components.MenuButton;
import GameAndPanels.Components.MenuButtonContainer;
import GameAndPanels.Components.MenuText;
import Players.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class MatchPanel extends JPanel implements ActionListener {
    MenuButtonContainer confirmContainer;   // stores confirm logo text
    MenuText confirmPrompt;                 // prompts user to confirm game start
    MenuButtonContainer bottomContainer;    // container for storing the buttons
    MenuButton confirmButton;               // button for the user to confirm starting the game with current settings
    MenuButton matchSetupButton;            // brings user back to match setup menu

    static ArrayList<String> playerNickList;            // list of players' nicks taking part in the match
    public static ArrayList<Player> playerList;         // list of actual players taking part in the match
    public static Iterator<Player> playerListIterator;  // player list iterator
    public static String gamemode;                      // gamemode of the match (best of 1, best of 2 or best of 3)
    public static HashMap<Player, Integer> scores;      // hashmap to store players' scores
    public static Stack<Card> cardStack;                // pile of cards available for users to draw from (resets every new game in the match)
    static Random random;                               // random number generator
    MatchPanel(ArrayList<String> players, String mode) {
        // create list of players' nicks
        playerNickList = players;
        // create player list
        playerList = new ArrayList<Player>();
        // create plyer list iterator
        playerListIterator = null;
        // set gamemode
        gamemode = mode;
        // create random number generator
        random = new Random();
        // create scores
        scores = null;
        // create card stack
        cardStack = null;

        // basic panel properties
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, Game.gameHeight / 30));
        this.setBackground(Game.myGreen);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(Game.gameWidth, Game.gameHeight));
        this.setVisible(false);

        // create panel content

        // create text asking user to confirm game launch
        confirmContainer = new MenuButtonContainer();
        confirmPrompt = new MenuText("CZY CHCESZ URUCHOMIĆ GRĘ Z WYBRANYMI USTAWIENIAMI?");
        confirmContainer.add(confirmPrompt);

        // create button container
        bottomContainer = new MenuButtonContainer();

        // create confirm button
        confirmButton = new MenuButton("TAK");
        confirmButton.addActionListener(this);

        // create decline button (return to the match setup)
        matchSetupButton = new MenuButton("NIE");
        matchSetupButton.addActionListener(this);

        // add content
        this.add(confirmContainer);
        bottomContainer.add(matchSetupButton);
        bottomContainer.add(confirmButton);
        this.add(bottomContainer);
    }

    // start the match, only called once per match at the start
    public void startMatch() {
        // clean the panel from confirmation controls
        this.remove(bottomContainer);
        this.remove(confirmContainer);
        this.revalidate();
        this.repaint();

        // check how many players are participating in the match
        int playerCount = 0;
        for (String player : playerNickList) {
            if (!player.equals("null")) {
                playerCount++;
            }
        }

        // change layout for displaying user panels based on player count
        if (playerCount == 2) {
            this.setLayout(new FlowLayout(FlowLayout.CENTER, Game.gameWidth / 30, Game.gameHeight / 4));
        } else {
            this.setLayout(new FlowLayout(FlowLayout.CENTER, Game.gameWidth / 30, Game.gameHeight / 70));
        }


        // add regular players to the player list
        for (String playerNick : playerNickList) {
            if (!playerNick.equals("null") && !playerNick.equals("AI")) {
                playerList.add(new PlayerRegular(playerNick, this));
            }
        }

        // scan player nickname list and create AI nicknames without duplicates, then add AI players to the list
        for (String playerNick : playerNickList) {
            // if player nick is 'AI', choose an AI name for them
            if (playerNick.equals("AI")) {
                // pool of possible AI nicknames
                String[] AINicknamePool = {"Piotr", "Tadeusz", "Andrzej", "Marcin", "Jakub", "Artur", "Tymoteusz", "Tomasz", "Jan", "Hubert", "Krzysztof"};
                // choose nickname from the pool randomly, do not allow for duplicates
                String AIName;
                while (true) {
                    boolean taken = false; // only exit loop if the nickname isn't taken already
                    int nickIdx = random.nextInt(AINicknamePool.length);
                    AIName = AINicknamePool[nickIdx];
                    for (String pNick : playerNickList) { // scan the player nick list to see, if the nickname is already present there
                        if (pNick.equals(AIName) && playerNickList.indexOf(pNick) != playerNickList.indexOf(playerNick)) {
                            taken = true;
                        }
                    }
                    if (!taken) {
                        break;
                    }
                }
                playerNickList.set(playerNickList.indexOf("AI"), AIName);
                playerList.add(new PlayerAI(AIName, this));
            }
        }

        // reset the card stack
        resetCardStack();

        // initiate player scores with 0's
        scores = new HashMap<Player, Integer>();
        for (Player player : playerList) {
            scores.put(player, 0);
        }

        // choose random player to start the game
        int startingIdx = random.nextInt(playerCount);
        // create iterator and point it at the starting player from list
        playerListIterator = playerList.iterator();
        for (int j = 0; j < startingIdx; j++) {playerListIterator.next();}

        // give a move to the starting player
        // this is the real trigger, starting the chain of actions in the match
        // game moves forward through calling move() on the next players
        playerListIterator.next().move();
    }

    // reset card stack, take cards from players, create new deck, shuffle
    public static void resetCardStack() {
        // create new stack
        if (cardStack != null) {cardStack.clear();}
        cardStack = new Stack<Card>();

        // take all the cards from players
        for (Player player : playerList) {
            player.giveCards();
        }

        // fills the stack with cards
        Card.fillDeck(cardStack);
        // shuffle the deck
        Collections.shuffle(cardStack);
    }

    // checks player status BEFORE the move and AFTER the move, also scan the board to draw a winner/losers
    public static String checkStatus(Player player) {
        // only check player status if player is still in game (not-lost)
        if (!player.isLost) {
            // check the most basic points based statuses
            if (player.points == 20) {
                // if player has 20 points, make him  stay
                player.isStaying = true;
            }
            else if (player.points == 21) {
                // check if player has 21 points after the move, if so, set his won flag to true
                player.isWon = true;
            }
            else if (player.points == 22) {
                // check for perskie oczko
                if (player.hand.get(0).toString().split(" ", 2)[0].equals("ace")
                &&  player.hand.get(1).toString().split(" ", 2)[0].equals("ace")) {
                    player.isWon = true;
                }
                else {
                    // if the player has 22 and it's not a perskie oczko, he lost
                    player.isLost = true;
                }
            }
            else if (player.points > 22) {
                // check if player has over 21 points after the move, if so, set his lost flag to true
                player.isLost = true;
            }


            // various counters based on the state of the board \\

            // count total number of players
            int playerCounter = 0;
            for (Player p : playerList) {
                playerCounter++;
            }

            // count total number of not-lost players
            int notLostCounter = 0;
            for (Player p : playerList) {
                if (!p.isLost) { notLostCounter++; }
            }

            // get the highest score from the not-lost players
            int highestScore = 0;
            for (Player p : playerList) {
                if (p.points > highestScore && !p.isLost) { highestScore = p.points; }
            }

            // check how many not-lost players have highest score
            int highestScoringCounter = 0;
            for (Player p : playerList) {
                // no need to restrict it with !p.isLost as the highestScore value is already gathered from not-lost players
                if (p.points == highestScore) { highestScoringCounter++; }
            }

            // count all not-lost AND staying players
            int notLostStayingCounter = 0;
            for (Player p : playerList) {
                if (!p.isLost && p.isStaying) { notLostStayingCounter++; }
            }

            // count lost players
            int lostCounter = 0;
            for (Player p : playerList) {
                if(p.isLost) { lostCounter++; }
            }

            // check a set of conditions to determine the state of the player and the game \\

            // check if everyone except 1 player lost, if so, he won
            if (playerCounter - lostCounter == 1) {
                for (Player p : playerList) {
                    if (!p.isLost) {
                        gameEnded(p, "win");
                        return "won";
                    }
                }
            }


            // if from all not-lost users there are more than 1 with highest score, and everyone is staying, end game with draw
            if (highestScoringCounter > 1 && notLostStayingCounter == notLostCounter) {
                gameEnded(player, "draw");
                return "draw";
            }

            // check, if all not-lost players are staying and if so, choose the winner or draw
            if (notLostCounter == notLostStayingCounter) {
                if (highestScoringCounter == 1) {
                    for (Player p : playerList) {
                        if (p.points == highestScore) {
                            gameEnded(p, "win");
                            return "won";
                        }
                    }
                }
                else {
                    gameEnded(player, "draw");
                    return "draw";
                }
            }

            // check, if there is only 1 not-staying player from not-lost players AND if it's you AND you have highest score AND you're the only one with it, win
            if (notLostCounter - notLostStayingCounter == 1 && highestScoringCounter == 1) {
                for (Player p : playerList) {
                    if (p.points == highestScore && !p.toString().equals(player.toString()) && p.points == highestScore && !p.isStaying) {
                        gameEnded(p, "win");
                        return "won";
                    }
                }
            }

        }

        // return appropriate string value according to player status
        if (player.isLost) {
            player.updateActiveIndicator("lost");
            return "lost";
        }
        else if (player.isWon) {
            gameEnded(player, "win");
            return "won";
        }
        else if (player.isStaying) {
            player.updateActiveIndicator("staying");
            return "staying";
        }
        else {
            player.updateActiveIndicator("active");
            return "free";
        }
    }

    // end the game with passed result, and if there are games left in the match, start the next one
    public static void gameEnded(Player player, String result) {
        // update score only if player won
        if (result.equals("win")) {
            scores.put(player, scores.get(player) + 1);
        }

        // update win counter for the player
        player.controlPanel.updateWinCount(scores.get(player));

        // check, whether the match is over
        if (gamemode.equals("ROZDANIE DO 1 WYGRANEJ") && scores.get(player) == 1 ||
            gamemode.equals("TURNIEJ DO 2 WYGRANYCH") && scores.get(player) == 2 ||
            gamemode.equals("TURNIEJ DO 3 WYGRANYCH") && scores.get(player) == 3) {
            // inform about the match ending
            JOptionPane.showMessageDialog(null, "Grę wygrywa " + player.nickname +"!", "Wynik gry", JOptionPane.INFORMATION_MESSAGE);

            // match history section \\

            // store strings from history file
            ArrayList<String> entriesList = new ArrayList<String>();

            // scan the history file
            try {
                // open history file, scan all lines
                File configFile = new File("Files//history.txt");
                Scanner scanner = new Scanner(configFile);
                while (scanner.hasNextLine()) {
                    entriesList.add(scanner.nextLine());
                }
            } catch (Exception e1) {
                // in case the file can't be opened, exit
                System.out.println("Can't open the match history file");
                System.exit(0);
            }

            // check if there are 10 entries already (if so, we need to remove the oldest entry)
            if (entriesList.size() >= 10) {
                entriesList.remove(0);
            }

            // create and save the result to the match history file
            try {
                // open file writer
                BufferedWriter writer = new BufferedWriter(new FileWriter("Files//history.txt"));

                // create history entry string
                String historyEntry = "GRACZE: ";
                int pCount = 1;
                for (Player p : playerList) {
                    historyEntry += p.nickname;
                    if (playerList.size() == pCount) {
                        historyEntry += " ";
                    }
                    else {
                        historyEntry += ", ";
                    }
                    pCount++;
                }
                switch (scores.get(player)) {
                    case 1 -> historyEntry += "|| TRYB GRY: DO 1 WYGRANEJ ";
                    case 2 -> historyEntry += "|| TRYB GRY: DO 2 WYGRANYCH ";
                    case 3 -> historyEntry += "|| TRYB GRY: DO 3 WYGRANYCH ";
                }
                historyEntry += "|| WYGRAŁ: " + player.nickname;
                // add new entry to the list
                entriesList.add(historyEntry);

                // save entries list to the history file
                for (String entry : entriesList) {
                    writer.write(entry+"\n");
                }
                writer.close();
            } catch (Exception e2) {
                // in case the file can't be opened, exit
                System.out.println("Can't open the match history file");
                System.exit(0);
            }

            // destroy match panel
            Game.matchSetupPanel.endMatch();
        }
        else {
            // if user won, inform them about his success starting new game in the match
            if (result.equals("win")) {
                JOptionPane.showMessageDialog(null, "Partię wygrał " + player.nickname + ". Rozpoczyna się nowa partia.", "Wynik partii", JOptionPane.INFORMATION_MESSAGE);
            }
            // ohterwise, display communicate about the draw
            else {
                JOptionPane.showMessageDialog(null, "Partia zakończyła się remisem. Rozpoczyna się nowa partia.", "Wynik partii", JOptionPane.INFORMATION_MESSAGE);
            }
            // if the match is not over (meaning, it's a draw or BO2 or BO3)
            // reset players' statuses
            int playerCount = 0;
            for (Player p : playerList) {
                playerCount++;
            }
            for (Player p : playerList) {
                p.points = 0;
                p.isLost = false;
                p.isWon = false;
                p.isStaying = false;
                p.controlPanel.drawButton.setEnabled(false);
                p.controlPanel.stayButton.setEnabled(false);
                p.controlPanel.updateScore(0);
                p.updateActiveIndicator("inactive");
            }
            // choose random player to start the game
            int startingIdx = random.nextInt(playerCount);
            // create iterator and point it at the starting player from list
            playerListIterator = playerList.iterator();
            for (int j = 0; j < startingIdx; j++) {playerListIterator.next();}

            // reset the card stack
            resetCardStack();

            // give a move to the starting player, starting next game in the match
            playerListIterator.next().move();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            // once user confirms game settings, start the game
            startMatch();
        }
        else if (e.getSource() == matchSetupButton) {
            // if players cancels, return to the match setup menu
            Game.currentPanel.setVisible(false);
            Game.matchSetupPanel.setVisible(true);
            Game.mainFrame.remove(Game.currentPanel);
            Game.currentPanel = Game.matchSetupPanel;
        }
    }
}
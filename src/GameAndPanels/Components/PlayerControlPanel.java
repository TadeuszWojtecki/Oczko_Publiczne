package GameAndPanels.Components;

import GameAndPanels.Game;

import javax.swing.*;
import java.awt.*;

// control panel assigned to every instance of the player in a match
public class PlayerControlPanel extends JPanel {
    public JPanel topPanel, middlePanel, bottomPanel;           // panels to reside in the top, middle and bottom of the control panel
    public MenuButton drawButton, stayButton;                   // control buttons
    public MenuButtonContainer scoreContainer, winCountContainer; // container for storing score and win count text
    public MenuText nicknameText, scoreText, winCountText;      // text for the control panel
    public JLabel activeIndicator;                              // visual indicator, gray circle when not active, changes to green when active
    boolean active;                                             // flag to track whether the player's panel is active

    // default constructor of player control panel
    public PlayerControlPanel(String nickname) {
        Color panelBg = new Color(36, 108, 41); // slightly darker than myGreen color
        Color panelTopBarBg = new Color(33, 99, 38); // even darker, for the top bar containing player name

        // set layout to border, top portion for player name, center for displaying cards, bottom for controls and score
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension((int)(Game.gameWidth / 2.2), (int)(Game.gameHeight / 2.1)));
        this.setBackground(panelBg);
        this.setOpaque(true);

        // create top section of control panel

        // create active indicator
        active = false;
        activeIndicator = new JLabel();
        activeIndicator.setPreferredSize(new Dimension(Game.gameWidth / 70, Game.gameHeight / 40));
        ImageIcon activeIcon = new ImageIcon(new ImageIcon(getClass().getResource("/UI/gray_circle.png")).getImage().getScaledInstance(activeIndicator.getPreferredSize().width, activeIndicator.getPreferredSize().height, Image.SCALE_SMOOTH));
        activeIndicator.setIcon(activeIcon);

        // create player nickname text
        nicknameText = new MenuText(nickname);
        nicknameText.setForeground(Color.white);

        // create top panel
        topPanel = new JPanel();
        topPanel.setOpaque(true);
        topPanel.setBackground(panelTopBarBg);
        topPanel.setPreferredSize(new Dimension(0, Game.gameHeight / 20));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, Game.gameWidth / 100, Game.gameHeight / 90));
        // add content to the top panel
        topPanel.add(activeIndicator);
        topPanel.add(nicknameText);

        // create middle section of control panel

        // create middle panel
        middlePanel = new JPanel();
        middlePanel.setOpaque(true);
        middlePanel.setBackground(panelBg);
        middlePanel.setPreferredSize(new Dimension(0, 0));
        middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, Game.gameWidth / 70, Game.gameHeight / 65));

        // create bottom section of control panel

        // create draw button
        drawButton = new MenuButton("DOBIERZ");
        drawButton.setFont(new Font("Arial", Font.BOLD, (int)(Game.gameWidth / 60)));
        drawButton.setBorder(BorderFactory.createLineBorder(Game.myRed, Game.gameHeight / 150));
        drawButton.setPreferredSize(new Dimension((int)(Game.gameWidth / 12), (int)(Game.gameHeight / 20)));
        drawButton.setEnabled(false);

        // create stay button
        stayButton = new MenuButton("STÓJ");
        stayButton.setFont(new Font("Arial", Font.BOLD, (int)(Game.gameWidth / 60)));
        stayButton.setBorder(BorderFactory.createLineBorder(Game.myRed, Game.gameHeight / 150));
        stayButton.setPreferredSize(new Dimension((int)(Game.gameWidth / 12), (int)(Game.gameHeight / 20)));
        stayButton.setEnabled(false);

        // create score text
        scoreText = new MenuText("PUNKTY: 0");
        scoreText.setFont(new Font("Arial", Font.BOLD, (int)(Game.gameWidth / 60)));

        // create score container
        scoreContainer = new MenuButtonContainer();
        scoreContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        scoreContainer.setPreferredSize(new Dimension((int)(Game.gameWidth / 9.2), (int)(Game.gameHeight / 20)));
        // add content to the score container
        scoreContainer.add(scoreText);

        // create win count text
        winCountText = new MenuText("WYNIK: 0");
        winCountText.setFont(new Font("Arial", Font.BOLD, (int)(Game.gameWidth / 60)));

        // create win count container
        winCountContainer = new MenuButtonContainer();
        winCountContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        winCountContainer.setPreferredSize(new Dimension((int)(Game.gameWidth / 9.2), (int)(Game.gameHeight / 20)));
        // add content to the score container
        winCountContainer.add(winCountText);

        // create bottom panel
        bottomPanel = new JPanel();
        bottomPanel.setOpaque(true);
        bottomPanel.setBackground(panelBg);
        bottomPanel.setPreferredSize(new Dimension(0, Game.gameHeight / 15));
        // add content to the bottom panel
        bottomPanel.add(scoreContainer);
        bottomPanel.add(drawButton);
        bottomPanel.add(stayButton);
        bottomPanel.add(winCountContainer);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(middlePanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    // change player indicator to inactive
    public void setInactive() {
        ImageIcon activeIcon = new ImageIcon(new ImageIcon(getClass().getResource("/UI/gray_circle.png")).getImage().getScaledInstance(activeIndicator.getPreferredSize().width, activeIndicator.getPreferredSize().height, Image.SCALE_SMOOTH));
        activeIndicator.setIcon(activeIcon);
        active = false;
    }

    // change active indicator to active
    public void setActive() {
        // check if the player's already active
        if (!active) {
            ImageIcon activeIcon = new ImageIcon(new ImageIcon(getClass().getResource("/UI/green_circle.png")).getImage().getScaledInstance(activeIndicator.getPreferredSize().width, activeIndicator.getPreferredSize().height, Image.SCALE_SMOOTH));
            activeIndicator.setIcon(activeIcon);
            active = true;
        }
        // if so, change to inactive
        else {
            ImageIcon activeIcon = new ImageIcon(new ImageIcon(getClass().getResource("/UI/gray_circle.png")).getImage().getScaledInstance(activeIndicator.getPreferredSize().width, activeIndicator.getPreferredSize().height, Image.SCALE_SMOOTH));
            activeIndicator.setIcon(activeIcon);
            active = false;
        }
    }

    // change player indicator to lost
    public void setLost() {
        ImageIcon activeIcon = new ImageIcon(new ImageIcon(getClass().getResource("/UI/red_circle.png")).getImage().getScaledInstance(activeIndicator.getPreferredSize().width, activeIndicator.getPreferredSize().height, Image.SCALE_SMOOTH));
        activeIndicator.setIcon(activeIcon);
        active = false;
    }

    // change player indicator to staying
    public void setStaying() {
        ImageIcon activeIcon = new ImageIcon(new ImageIcon(getClass().getResource("/UI/yellow_circle.png")).getImage().getScaledInstance(activeIndicator.getPreferredSize().width, activeIndicator.getPreferredSize().height, Image.SCALE_SMOOTH));
        activeIndicator.setIcon(activeIcon);
        active = false;
    }

    // updates points displayed on the panel
    public void updateScore(int score) {
        scoreText.setText("PUNKTY: " + score);
    }

    // update win count displayed on the panel
    public void updateWinCount(int wins) {
        winCountText.setText("WYNIK: " + wins);
    }


}

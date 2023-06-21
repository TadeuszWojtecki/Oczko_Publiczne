package GameAndPanels;

import GameAndPanels.Components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MatchSetupPanel extends JPanel implements ActionListener {
    JLabel setupLogo;  // logo of the match setup screen

    // containers for storing multiple UI elements in a single panel instance
    MenuButtonContainer playerCountCont, playerOneCont, playerTwoCont, playerThreeCont, playerFourCont, modeCont, bottomCont;

    // radio buttons for selecting player count
    MenuRadioButton twoPlayers, threePlayers, fourPlayers;

    // set of buttons for each player customization
    MenuComboBox<String> p1Type, p2Type, p3Type, p4Type;
    MenuTextField p1Nick, p2Nick, p3Nick, p4Nick;

    MenuComboBox<String> gamemode; // combobox for selecting gamemode
    MenuButton startButton;        // starts the game with selected settings
    MenuButton mainMenuButton;     // brings user back to main menu
    MatchSetupPanel() {
        // basic panel properties
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, Game.gameHeight / 30));
        this.setBackground(Game.myGreen);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(Game.gameWidth, Game.gameHeight));
        this.setVisible(false);

        // create logo label
        setupLogo = new JLabel();
        setupLogo.setHorizontalAlignment(JLabel.CENTER);
        setupLogo.setPreferredSize(new Dimension((int)(Game.gameWidth / 3.5), (int)(Game.gameHeight / 6)));
        // creates a scaled image
        ImageIcon logoIcon = new ImageIcon(new ImageIcon(getClass().getResource("/MatchSetup/setupLogo.png")).getImage().getScaledInstance(setupLogo.getPreferredSize().width, setupLogo.getPreferredSize().height, Image.SCALE_SMOOTH));
        setupLogo.setIcon(logoIcon);

        // create panel content

        // create player count container text
        MenuText playerCountText = new MenuText("ILOSC GRACZY");

        // create player count radio buttons
        twoPlayers = new MenuRadioButton("2 GRACZY"); twoPlayers.addActionListener(this);
        threePlayers = new MenuRadioButton("3 GRACZY"); threePlayers.addActionListener(this);
        fourPlayers = new MenuRadioButton("4 GRACZY"); fourPlayers.addActionListener(this);
        // create button group for the radio buttons and add them
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(twoPlayers);radioGroup.add(threePlayers);radioGroup.add(fourPlayers);

        // create player count container
        playerCountCont = new MenuButtonContainer();
        playerCountCont.add(playerCountText);
        playerCountCont.add(twoPlayers); playerCountCont.add(threePlayers); playerCountCont.add(fourPlayers);

        // create players' settings buttons
        String[] pTypes = {"GRACZ", "GRACZ AI"};
        p1Type = new MenuComboBox<String>(pTypes); p1Type.addActionListener(this);
        p1Nick = new MenuTextField();
        p2Type = new MenuComboBox<String>(pTypes); p2Type.addActionListener(this);
        p2Nick = new MenuTextField();
        p3Type = new MenuComboBox<String>(pTypes); p3Type.addActionListener(this);
        p3Nick = new MenuTextField();
        p4Type = new MenuComboBox<String>(pTypes); p4Type.addActionListener(this);
        p4Nick = new MenuTextField();

        // create labes for players's settings containers
        MenuText selectType1 = new MenuText("TYP GRACZA 1:");
        MenuText enterNick1 = new MenuText("WPROWADŹ NAZWĘ GRACZA:");
        MenuText selectType2 = new MenuText("TYP GRACZA 2:");
        MenuText enterNick2 = new MenuText("WPROWADŹ NAZWĘ GRACZA:");
        MenuText selectType3 = new MenuText("TYP GRACZA 3:");
        MenuText enterNick3 = new MenuText("WPROWADŹ NAZWĘ GRACZA:");
        MenuText selectType4 = new MenuText("TYP GRACZA 4:");
        MenuText enterNick4 = new MenuText("WPROWADŹ NAZWĘ GRACZA:");

        // create players' settings containers
        playerOneCont = new MenuButtonContainer();
        playerTwoCont = new MenuButtonContainer();
        playerThreeCont = new MenuButtonContainer();
        playerFourCont = new MenuButtonContainer();

        // add players' settings to the containers
        playerOneCont.add(selectType1); playerOneCont.add(p1Type); playerOneCont.add(enterNick1); playerOneCont.add(p1Nick);
        playerTwoCont.add(selectType2); playerTwoCont.add(p2Type); playerTwoCont.add(enterNick2); playerTwoCont.add(p2Nick);
        playerThreeCont.add(selectType3); playerThreeCont.add(p3Type); playerThreeCont.add(enterNick3); playerThreeCont.add(p3Nick);
        playerFourCont.add(selectType4); playerFourCont.add(p4Type); playerFourCont.add(enterNick4); playerFourCont.add(p4Nick);
        // by default, players's settings containers are invisibile, they become visible upon selecting appropriate player count
        playerOneCont.setVisible(false); playerTwoCont.setVisible(false); playerThreeCont.setVisible(false); playerFourCont.setVisible(false);

        // create gamemode selection combobox
        String[] gamemodes = {"ROZDANIE DO 1 WYGRANEJ", "TURNIEJ DO 2 WYGRANYCH", "TURNIEJ DO 3 WYGRANYCH"};
        gamemode = new MenuComboBox<String>(gamemodes);
        gamemode.addActionListener(this);

        // create mode selection container
        modeCont = new MenuButtonContainer();
        MenuText gamemodeText = new MenuText("TRYB GRY:");
        modeCont.add(gamemodeText);
        modeCont.add(gamemode);

        // create start button
        startButton = new MenuButton("START");
        startButton.addActionListener((this));

        // create main menu button
        mainMenuButton = new MenuButton("WSTECZ");
        mainMenuButton.addActionListener(this);

        // create bottom container for storing startButton and mainMenuButton
        bottomCont = new MenuButtonContainer();
        bottomCont.add(mainMenuButton);
        bottomCont.add(startButton);

        // add content to the panel
        this.add(setupLogo);
        this.add(playerCountCont);
        this.add(playerOneCont); this.add(playerTwoCont); this.add(playerThreeCont); this.add(playerFourCont);
        this.add(modeCont);
        this.add(bottomCont);
    }

    // destroys the match panel and comes back to main menu
    public void endMatch() {
        Game.mainFrame.remove(Game.currentPanel);
        System.gc();
        Game.mainMenuPanel.setVisible(true);
        Game.currentPanel = Game.mainMenuPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // check if the radio buttons were pressed
        if (e.getSource() == twoPlayers) {
            // selected 2 players setting
            playerOneCont.setVisible(true);
            playerTwoCont.setVisible(true);
            playerThreeCont.setVisible(false);
            p3Type.setSelectedItem("GRACZ");
            p3Nick.setText(""); p3Nick.setEnabled(true);
            playerFourCont.setVisible(false);
            p4Type.setSelectedItem("GRACZ");
            p4Nick.setText(""); p4Nick.setEnabled(true);
        }
        else if (e.getSource() == threePlayers) {
            // selected 3 players setting
            playerOneCont.setVisible(true);
            playerTwoCont.setVisible(true);
            playerThreeCont.setVisible(true);
            playerFourCont.setVisible(false);
            p4Type.setSelectedItem("GRACZ");
            p4Nick.setText(""); p4Nick.setEnabled(true);
        }
        else if (e.getSource() == fourPlayers) {
            // selected 4 players setting
            playerOneCont.setVisible(true);
            playerTwoCont.setVisible(true);
            playerThreeCont.setVisible(true);
            playerFourCont.setVisible(true);
        }
        // check if player's type has been changed (regular or AI)
        else if (e.getSource() == p1Type) {
            if (p1Type.getSelectedItem() == "GRACZ") {
                p1Nick.setText("");
                p1Nick.setEnabled(true);
            }
            else {
                p1Nick.setText("losowa");
                p1Nick.setEnabled(false);
            }
        }
        else if (e.getSource() == p2Type) {
            if (p2Type.getSelectedItem() == "GRACZ") {
                p2Nick.setText("");
                p2Nick.setEnabled(true);
            }
            else {
                p2Nick.setText("losowa");
                p2Nick.setEnabled(false);
            }
        }
        else if (e.getSource() == p3Type) {
            if (p3Type.getSelectedItem() == "GRACZ") {
                p3Nick.setText("");
                p3Nick.setEnabled(true);
            }
            else {
                p3Nick.setText("losowa");
                p3Nick.setEnabled(false);
            }
        }
        else if (e.getSource() == p4Type) {
            if (p4Type.getSelectedItem() == "GRACZ") {
                p4Nick.setText("");
                p4Nick.setEnabled(true);
            }
            else {
                p4Nick.setText("losowa");
                p4Nick.setEnabled(false);
            }
        }
        // check if main menu button was pressed
        else if (e.getSource() == mainMenuButton) {
            Game.currentPanel.setVisible(false);
            Game.mainMenuPanel.setVisible(true);
            Game.currentPanel = Game.mainMenuPanel;
        }
        // check if start button was pressed
        else if (e.getSource() == startButton) {

            // variable to determine whether all settings are correct
            boolean allSet = true;

            // trim all users' nicknames
            p1Nick.setText(p1Nick.getText().trim());
            p2Nick.setText(p2Nick.getText().trim());
            p3Nick.setText(p3Nick.getText().trim());
            p4Nick.setText(p4Nick.getText().trim());

            // if user entered multiple words as nickname, take only the 1st word as nickname
            p1Nick.setText(p1Nick.getText().split(" ", 2)[0]);
            p2Nick.setText(p2Nick.getText().split(" ", 2)[0]);
            p3Nick.setText(p3Nick.getText().split(" ", 2)[0]);
            p4Nick.setText(p4Nick.getText().split(" ", 2)[0]);

            // check, if the nicknames are unique for non AI players
            ArrayList<String> strList = new ArrayList<String>();
            if (!p1Nick.getText().equals("")) { strList.add(p1Nick.getText()); }
            if (!p2Nick.getText().equals("")) { strList.add(p2Nick.getText()); }
            if (!p3Nick.getText().equals("")) { strList.add(p3Nick.getText()); }
            if (!p4Nick.getText().equals("")) { strList.add(p4Nick.getText()); }
            for (String str : strList) {
                if (!str.equals("losowa")) {
                    int dupCounter = 0;
                    for (String s : strList) {
                        if (s.equals(str)) {
                            dupCounter++;
                        }
                    }
                    if (dupCounter > 1) {
                        JOptionPane.showMessageDialog(null, "Nazwy gracza muszą byc unikatowe!", "Ustawienia rozgrywki", JOptionPane.INFORMATION_MESSAGE);
                        allSet = false;
                        break;
                    }
                }
            }

            // check, how many players are playing, and if all of them have assigned nicknames
            if (fourPlayers.isSelected()) {
                if (p1Nick.getText().equals("") || p2Nick.getText().equals("") || p3Nick.getText().equals("") || p4Nick.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Musisz podać nazwy wszystkich graczy\nprzed rozpoczeciem rozgrywki.", "Ustawienia rozgrywki", JOptionPane.INFORMATION_MESSAGE);
                    allSet = false;
                }
            }
            else if (threePlayers.isSelected()) {
                if (p1Nick.getText().equals("") || p2Nick.getText().equals("") || p3Nick.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Musisz podać nazwy wszystkich graczy\nprzed rozpoczeciem rozgrywki.", "Ustawienia rozgrywki", JOptionPane.INFORMATION_MESSAGE);
                    allSet = false;
                }
            }
            else if (twoPlayers.isSelected()) {
                if (p1Nick.getText().equals("") || p2Nick.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Musisz podać nazwy wszystkich graczy\nprzed rozpoczeciem rozgrywki.", "Ustawienia rozgrywki", JOptionPane.INFORMATION_MESSAGE);
                    allSet = false;
                }
            }
            // check, if the amount of players is selected at all
            else {
                JOptionPane.showMessageDialog(null, "Musisz wybrać ilość graczy\nbiorących udział w rozgrywce.", "Ustawienia rozgrywki", JOptionPane.INFORMATION_MESSAGE);
                allSet = false;
            }

            // if all settings are fine, launch the match
            if (allSet) {
                // create list of players
                ArrayList<String> players = new ArrayList<String>();
                players.add(p1Nick.getText());
                players.add(p2Nick.getText());
                players.add(p3Nick.getText());
                players.add(p4Nick.getText());

                // replace empty strings (players not participating in match) with "null",
                // replace "random" communicate extracted from the text fields with "AI"
                for (String player : players) {
                    if (player.equals("")) {
                        players.set(players.indexOf(player), "null");
                    }
                    else if (player.equals("losowa")) {
                        players.set(players.indexOf(player), "AI");
                    }
                }

                // close previous panel (setup), create brand new match panel, add it to the main frame, update currentPanel variable
                Game.currentPanel.setVisible(false);
                MatchPanel newMatchPanel = new MatchPanel(players, (String)gamemode.getSelectedItem());
                Game.mainFrame.add(newMatchPanel);
                newMatchPanel.setVisible(true);
                Game.currentPanel = newMatchPanel;
            }
        }
    }
}

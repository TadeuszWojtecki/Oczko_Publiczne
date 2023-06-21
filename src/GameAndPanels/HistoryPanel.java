package GameAndPanels;

import GameAndPanels.Components.MenuButton;
import GameAndPanels.Components.MenuButtonContainer;
import GameAndPanels.Components.MenuText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class HistoryPanel extends JPanel implements ActionListener {
    JLabel historyLogo;                                 // logo of the history screen
    MenuButton mainMenuButton;                          // brings user back to main menu
    MenuButtonContainer emptyHistory;                   // used to display empty history communicate
    ArrayList<MenuButtonContainer> entryContainerList;  // list of entries containers (used for display)
    HistoryPanel() {
        // basic panel properties
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, Game.gameHeight / 65));
        this.setBackground(Game.myGreen);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(Game.gameWidth, Game.gameHeight));
        this.setVisible(false);

        // create logo label
        historyLogo = new JLabel();
        historyLogo.setHorizontalAlignment(JLabel.CENTER);
        historyLogo.setPreferredSize(new Dimension((int)(Game.gameWidth / 2.2), (int)(Game.gameHeight / 9)));
        // creates a scaled image
        ImageIcon logoIcon = new ImageIcon(new ImageIcon(getClass().getResource("/History/historyLogo.png")).getImage().getScaledInstance(historyLogo.getPreferredSize().width, historyLogo.getPreferredSize().height, Image.SCALE_SMOOTH));
        historyLogo.setIcon(logoIcon);

        // create panel content

        // create main menu button
        mainMenuButton = new MenuButton("WSTECZ");
        mainMenuButton.addActionListener(this);

        // create entry contaier list
        entryContainerList = new ArrayList<MenuButtonContainer>();

        // create empty communicate container
        emptyHistory = new MenuButtonContainer();
        emptyHistory.add(new MenuText("Brak rozegranych meczów do wyświetlenia."));
        emptyHistory.setVisible(false);

        // add content
        this.add(historyLogo);
        this.add(emptyHistory);
        this.add(mainMenuButton);
    }

    // displays match history
    public void displayHistory() {
        // remove main menu button
        this.remove(mainMenuButton);
        // remove all entries using entry list
        for (MenuButtonContainer cont : entryContainerList) {
            this.remove(cont);
        }
        // remove the empty history communicate
        emptyHistory.setVisible(false);

        // create main menu button
        mainMenuButton = new MenuButton("WSTECZ");
        mainMenuButton.addActionListener(this);

        // create match history

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

        // display match history entries
        int entryCounter = 1;
        if (!entriesList.isEmpty()) {
            for (int i = entriesList.size() - 1; i >= 0; i--) {

                // create container to store entry text
                MenuButtonContainer entryCont = new MenuButtonContainer();
                entryCont.setPreferredSize(new Dimension(Game.gameWidth, (int)(Game.gameHeight / 17)));
                MenuText entryText = new MenuText();

                // create displayed entry text
                entryText.setText("[MECZ " + entryCounter + "] || " + entriesList.get(i));
                entryCont.add(entryText);
                entryCounter++;
                // add entry container to the list of containers
                entryContainerList.add(entryCont);
                // add container to the panel
                this.add(entryCont);
            }
        }
        else {
            emptyHistory.setVisible(true);
        }

        // add main menu button at the bottom
        this.add(mainMenuButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenuButton) {
            // close previous panel, open main menu panel, update currentPanel variable
            Game.currentPanel.setVisible(false);
            Game.mainMenuPanel.setVisible(true);
            Game.currentPanel = Game.mainMenuPanel;
        }
    }
}

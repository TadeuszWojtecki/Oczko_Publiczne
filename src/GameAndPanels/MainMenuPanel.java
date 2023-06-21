package GameAndPanels;

import GameAndPanels.Components.MainMenuButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel implements ActionListener {
    JLabel oczkoLogo; // main menu logo
    MainMenuButton playButton, rulesButton, historyButton, settingsButton, exitButton; // main menu buttons

    MainMenuPanel() {
        // set basic panel properties
        this.setBackground(Game.myGreen);
        this.setOpaque(true);
    }

    // Create and draw menu content
    // Create labels and buttons, scale them to fit window size
    void createMenu() {
        // set layout
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, Game.gameHeight / 28));

        // create logo label
        oczkoLogo = new JLabel();
        oczkoLogo.setHorizontalAlignment(JLabel.CENTER);
        oczkoLogo.setPreferredSize(new Dimension((int)(Game.gameWidth / 2), (int)(Game.gameHeight / 3.2)));
        // create a scaled logo image
        ImageIcon logoIcon = new ImageIcon(new ImageIcon(getClass().getResource("/MainMenu/oczkoLogo.png")).getImage().getScaledInstance(oczkoLogo.getPreferredSize().width, oczkoLogo.getPreferredSize().height, Image.SCALE_SMOOTH));
        oczkoLogo.setIcon(logoIcon);

        // create all the buttons and assign them action listener (this)
        playButton = new MainMenuButton("ROZPOCZNIJ GRĘ"); playButton.addActionListener(this);
        rulesButton = new MainMenuButton("ZASADY GRY"); rulesButton.addActionListener(this);
        historyButton = new MainMenuButton("HISTORIA MECZÓW"); historyButton.addActionListener(this);
        settingsButton = new MainMenuButton("USTAWIENIA"); settingsButton.addActionListener(this);
        exitButton = new MainMenuButton("WYJDŹ Z GRY"); exitButton.addActionListener(this);

        // add content to the panel
        this.add(oczkoLogo);
        this.add(playButton);
        this.add(rulesButton);
        this.add(historyButton);
        this.add(settingsButton);
        this.add(exitButton);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            // close previous panel, open game setup panel, update currentPanel variable
            Game.currentPanel.setVisible(false);
            Game.matchSetupPanel.setVisible(true);
            Game.currentPanel = Game.matchSetupPanel;
        }
        else if (e.getSource() == rulesButton) {
            // close previous panel, open rules panel, update currentPanel variable
            Game.currentPanel.setVisible(false);
            Game.rulesPanel.setVisible(true);
            Game.currentPanel = Game.rulesPanel;
        }
        else if (e.getSource() == historyButton) {
            // close previous panel, open match history panel, update currentPanel variable
            Game.currentPanel.setVisible(false);
            Game.historyPanel.displayHistory();
            Game.historyPanel.setVisible(true);
            Game.currentPanel = Game.historyPanel;
        }
        else if (e.getSource() == settingsButton) {
            // close previous panel, open settings panel, update currentPanel variable
            Game.currentPanel.setVisible(false);
            Game.settingsPanel.setVisible(true);
            Game.currentPanel = Game.settingsPanel;
        }
        else if (e.getSource() == exitButton) {
            // upon clicking "EXIT" prompt user for confirmation before exiting
            String[] arr = {"Tak", "Nie", "Anuluj"};
            int exit = JOptionPane.showOptionDialog(null, "Czy na pewno chcesz opuścić grę?", "Zamykanie", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, arr, arr[0]);
            if (exit == 0) { System.exit(0); }
        }

    }
}

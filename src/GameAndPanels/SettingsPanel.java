package GameAndPanels;

import GameAndPanels.Components.MenuButton;
import GameAndPanels.Components.MenuButtonContainer;
import GameAndPanels.Components.MenuComboBox;
import GameAndPanels.Components.MenuText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class SettingsPanel extends JPanel implements ActionListener {
    JLabel settingsLogo;                       // settings panel logo
    MenuButton mainMenuButton;                 // button that brings user back to main menu
    MenuButtonContainer resPanel;              // panel for storing resolution settings
    MenuText resLabel;                         // label for resolution combo box
    MenuComboBox<String> resComboBox;          // resolution combo box
    MenuButton applyButton;                    // button to apply the changes
    MenuButtonContainer bottomContainer;       // container for storing buttons at the bottom

    SettingsPanel() {
        // basic panel properties
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, Game.gameHeight / 25));
        this.setBackground(Game.myGreen);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(Game.gameWidth, Game.gameHeight));
        this.setVisible(false);

        // create logo label
        settingsLogo = new JLabel();
        settingsLogo.setHorizontalAlignment(JLabel.CENTER);
        settingsLogo.setPreferredSize(new Dimension((int)(Game.gameWidth / 2.7), (int)(Game.gameHeight / 10)));
        // create a scaled logo image
        ImageIcon logoIcon = new ImageIcon(new ImageIcon(getClass().getResource("/Settings/settingsLogo.png")).getImage().getScaledInstance(settingsLogo.getPreferredSize().width, settingsLogo.getPreferredSize().height, Image.SCALE_SMOOTH));
        settingsLogo.setIcon(logoIcon);

        // create resolution panel
        resPanel = new MenuButtonContainer();

        // create resolution label
        resLabel = new MenuText("ROZMIAR OKNA");

        // create resolution combo box
        String[] resolutions = {"960x540 (okienko)", "1024x576 (okienko)", "1280x720 (okienko)", "1366x768 (okienko)", "1600x900 (okienko)", "1920x1080 (borderless)"};
        resComboBox = new MenuComboBox<String>(resolutions);
        // make the default selected resolution match current resolution settings
        switch (Game.gameWidth) {
            case 960:
                resComboBox.setSelectedItem("960x540 (okienko)"); break;
            case 1024:
                resComboBox.setSelectedItem("1024x576 (okienko)"); break;
            case 1280:
                resComboBox.setSelectedItem("1280x720 (okienko)"); break;
            case 1366:
                resComboBox.setSelectedItem("1366x768 (okienko)"); break;
            case 1600:
                resComboBox.setSelectedItem("1600x900 (okienko)"); break;
            case 1920:
                resComboBox.setSelectedItem("1920x1080 (borderless)"); break;
            default: break;
        }

        // add content to the resolution panel
        resPanel.add(resLabel);
        resPanel.add(resComboBox);

        // create bottom container
        bottomContainer = new MenuButtonContainer();

        // create apply button
        applyButton = new MenuButton("POTWIERDŹ");
        applyButton.addActionListener(this);

        // create main menu button
        mainMenuButton = new MenuButton("WSTECZ");
        mainMenuButton.addActionListener(this);

        // add content to the bottom container
        bottomContainer.add(mainMenuButton);
        bottomContainer.add(applyButton);

        // add content to the setting panel
        this.add(settingsLogo);
        this.add(resPanel);
        this.add(bottomContainer);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenuButton) {
            // close previous panel, open main menu panel, update currentPanel variable
            Game.currentPanel.setVisible(false);
            Game.mainMenuPanel.setVisible(true);
            Game.currentPanel = Game.mainMenuPanel;
        }
        else if (e.getSource() == applyButton) {
            // apply selected settings to the config
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("Files//config.txt"));
                int newWidth = 0; int newHeight = 0;
                // get selected resolution settings
                switch (resComboBox.getSelectedIndex()) {
                    case 0: newWidth = 960;newHeight = 540;break;
                    case 1: newWidth = 1024;newHeight = 576;break;
                    case 2: newWidth = 1280;newHeight = 720;break;
                    case 3: newWidth = 1366;newHeight = 768;break;
                    case 4: newWidth = 1600;newHeight = 900;break;
                    case 5: newWidth = 1920;newHeight = 1080;break;
                    default: break;
                }
                // update config file
                writer.write("windowWidth " + newWidth + "\n");
                writer.write("windowHeight " + newHeight + "\n");
                writer.close();
            } catch (Exception e1) {
                // in case the file can't be overriden, exit
                JOptionPane.showMessageDialog(null, "Nie można nadpisać pliku konfiguracyjnego.\nZamykanie programu.", "Ustawienia", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            // inform that the settings will apply after reset, offer closing the program on spot
            String[] arr = {"Wyjdź", "Zostań"};
            int exit = JOptionPane.showOptionDialog(null, "Zmiany zostaną wprowadzone po ponownym\nuruchomieniu gry. Możesz wyjść z gry już teraz.", "Ustawienia", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, arr, arr[0]);
            if (exit == 0) { System.exit(0); }
        }
    }
}

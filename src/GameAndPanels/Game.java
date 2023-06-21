package GameAndPanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Scanner;

public class Game {
    public static int gameWidth;                    // stores game width
    public static int gameHeight;                   // stores game height
    public static Color myRed, myGreen, myWhite;    // colors to be used as default theme in other components

    public static JFrame mainFrame;                 // main display frame
    static JPanel currentPanel;                     // points to the current panel displayed
    static MainMenuPanel mainMenuPanel;             // main menu panel (start the game here by default)
    static MatchSetupPanel matchSetupPanel;         // match setup panel
    static RulesPanel rulesPanel;                   // rules panel
    static GuidePanel guidePanel;                   // guide panel
    static HistoryPanel historyPanel;               // match history panel
    static SettingsPanel settingsPanel;             // settings panel

    Action exitAction; // action for exiting the game using a keybinding (ESC)
    public Game() {
        // set color theme for the game \\
        myRed = new Color(200, 56, 90);
        myGreen = new Color(49, 147, 56);
        myWhite = Color.white;

        // create key components \\
        // main frame
        mainFrame = new JFrame();
        // menu panel
        mainMenuPanel = new MainMenuPanel();
        // draw the main frame starting on main menu panel
        // (important to go 1st, as it sets the gameWidth and gameHeight)
        createMainFrame(mainFrame, mainMenuPanel);
        // add content to the menu panel
        mainMenuPanel.createMenu();
        // create game setup panel
        matchSetupPanel = new MatchSetupPanel();
        // create rules panel
        rulesPanel = new RulesPanel();
        // create guide panel
        guidePanel = new GuidePanel();
        // create match history panel
        historyPanel = new HistoryPanel();
        // create settings panel
        settingsPanel = new SettingsPanel();

        // create trash label to attach the exit action to
        JLabel pauseLabel = new JLabel();
        // create exit game keybinding
        exitAction = new ExitAction();
        pauseLabel.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "exitAction");
        pauseLabel.getActionMap().put("exitAction", exitAction);

        // by default currentPanel points at main menu panel
        currentPanel = mainMenuPanel;

        // add content to the frame \\
        mainFrame.add(matchSetupPanel);
        mainFrame.add(rulesPanel);
        mainFrame.add(guidePanel);
        mainFrame.add(historyPanel);
        mainFrame.add(settingsPanel);
        mainFrame.add(pauseLabel);
        mainFrame.setVisible(true);
    }

    // action for exiting the game
    class ExitAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] arr = {"Tak", "Nie", "Anuluj"};
            int exit = JOptionPane.showOptionDialog(null, "Czy na pewno chcesz opuścić grę?", "Zamykanie", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, arr, arr[0]);
            if (exit == 0) { System.exit(0); }
        }
    }

    /* Create and draw the main frame
    Initialize the main frame, get window size from config using setWindowSize,
    adjust starting panel dimensions, add said panel to the frame, pack the frame
     */
    void createMainFrame(JFrame frame, JPanel startingPanel) {
        // set basic frame properties
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.red);
        frame.setTitle("Oczko");
        frame.setIconImage(null);
        ImageIcon gameIcon = new ImageIcon(getClass().getResource("/serce.png"));
        frame.setIconImage(gameIcon.getImage());

        // create starting panel based on config
        setWindowSize(startingPanel);
        // add starting panel to the frame
        frame.add(startingPanel);
        // pack the frame
        mainFrame.pack();
    }

    /* Set starting panel dimensions from config
    Open config file, find "width" and "height" values, set the panel's
    dimensions accordingly, update variables gameWidth and gameHeight
     */
    void setWindowSize(JPanel panel) {

        int width = 0; // variable for storing width read from config
        int height = 0;// variable for storing height read from config

        // look for width and height in config file until success
        boolean success = false;
        while (!success) {
            try {
                // open config file, keep looking for resolution portion
                File configFile = new File("Files//config.txt");
                Scanner scanner = new Scanner(configFile);
                while (scanner.hasNextLine()) {
                    String str = scanner.next();
                    // if the resolution portion is found, break
                    if (str.equals("windowWidth")) {
                        break;
                    }
                }
                width = scanner.nextInt(); // read window width from config
                scanner.next();            // eat "screenHeight"
                height = scanner.nextInt();// read window height from config
                scanner.close();           // close the scanner
                success = true;            // success achieved
            } catch (Exception e1) {
                // in case something goes wrong (missing resolution part, polluted file etc.)
                System.out.println("Config file corrupted! Restoring...");
                try {
                    // write default settings to the config file
                    BufferedWriter writer = new BufferedWriter(new FileWriter("Files//config.txt"));
                    writer.write("windowWidth 1280\n");
                    writer.write("windowHeight 720\n");
                    writer.close();
                } catch (Exception e2) {
                    // in case file cannot be restored, exit
                    JOptionPane.showMessageDialog(null, "Uszkodzony plik konfiguracyjny nie może zostać odnowiony.\nZamykanie programu.", "Ustawienia", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }
        }

        panel.setPreferredSize(new Dimension(width, height));  // apply read values to the panel
        if (width == 1920) { mainFrame.setUndecorated(true); } // enter borderless in case of 1080p
        gameWidth = panel.getPreferredSize().width;            // set public Game.gameWidth
        gameHeight = panel.getPreferredSize().height;          // set public Game.gameHeight
    }
}

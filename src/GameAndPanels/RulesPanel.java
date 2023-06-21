package GameAndPanels;

import GameAndPanels.Components.MenuButton;
import GameAndPanels.Components.MenuButtonContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RulesPanel extends JPanel implements ActionListener {
    JLabel rulesLogo;               // logo of the rules screen
    JLabel rules;                   // label to store the rules
    MenuButtonContainer bottomCont; // container to store buttons at the bottom of the panel
    MenuButton mainMenuButton;      // brings user back to main menu
    MenuButton guideButton;         // takes user to the guide page
    RulesPanel() {
        // basic panel properties
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, Game.gameHeight / 30));
        this.setBackground(Game.myGreen);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(Game.gameWidth, Game.gameHeight));
        this.setVisible(false);

        // create logo label
        rulesLogo = new JLabel();
        rulesLogo.setHorizontalAlignment(JLabel.CENTER);
        rulesLogo.setPreferredSize(new Dimension((int)(Game.gameWidth / 3.2), (int)(Game.gameHeight / 12)));
        // creates a scaled image
        ImageIcon logoIcon = new ImageIcon(new ImageIcon(getClass().getResource("/Rules/rulesLogo.png")).getImage().getScaledInstance(rulesLogo.getPreferredSize().width, rulesLogo.getPreferredSize().height, Image.SCALE_SMOOTH));
        rulesLogo.setIcon(logoIcon);

        // create panel content
        rules = new JLabel();
        rules.setBackground(Game.myWhite);
        rules.setOpaque(true);
        rules.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Game.myRed, Game.gameHeight/130), BorderFactory.createLineBorder(Game.myWhite, Game.gameHeight/130)));
        rules.setFont(new Font("Arial", Font.PLAIN, Game.gameWidth / 65));
        // display rules
        rules.setText("<html>" +
                "<b>WYMAGANIA WSTĘPNE</b><br/>" +
                "Do gry w \"Oczko\" wykorzystuje się pełną talię kart (bez jokerów). W grę może grać od dwóch do czterech osób jednocześnie.<br/><br/>" +
                "<b>PODSTAWOWE ZASADY</b><br/>" +
                "Każda karta z talii posiada wartość liczbową. Karty od 2 do 10 są warte tyle, na ile wskazuje ich numer (trójka jest warta 3 punkty itd.),<br/>" +
                "walety są warte 2 punkty, damy 3 punkty, królowie 4 punkty, natomiast asy są warte 11 punktów.<br/><br/>" +
                "W każdym rozdaniu w sposób losowy wyznaczany jest gracz zaczynający. Poczynając od niego każdy gracz po kolei otrzymuje turę,<br/>" +
                "podczas której może albo dobrać kartę ze stosu, lub spasować. Spasowanie oznacza, że do końca rozgrywki gracz nie może dobierać<br/>" +
                "kart. Za dobrane karty gracze otrzymują tyle punktów, ile wynosi wartość karty, którą dobrali. Dobrane karty są widoczne dla wszystkich.<br/><br/>" +
                "Celem każdego uczestnika jest zdobyć 21 punktów (oczko). Gracz, który pierwszy to osiągnie zostaje zwycięzcą rozdania.<br/>" +
                "Jeżeli zawodnik po dobraniu karty przekroczy limit 21 punktów, automatycznie przegrywa i nie bierze udziału w dalszym przebiegu gry.<br/>" +
                "W momencie w którym wszyscy nieprzegrani zawodnicy spasują, zanim którykolwiek z nich osiągnie wynik równy 21, zwycięzcą zostaje<br/>" +
                "ten, kto był najbliżej tej wartości. Przykładowo gracz A ma 17 punktów, pasuje. Gracz B dobiera kartę, teraz jego wynik to 19 punktów.<br/>" +
                "Gracz B wygrywa rozdanie, ponieważ zabrakło mu 2 punktów do 21, podczas gdy graczowi A zabrakło aż 4.<br/><br/>" +
                "<b>PERSKIE OCZKO</b><br/>" +
                "Istnieje sytuacja, w której gracz po przekroczeniu limitu 21 punktów wygrywa rozdanie.  Dzieje się tak, gdy podczas<br/>" +
                "dwóch pierwszych tur dobierze dokładnie dwa asy. Wtedy suma jego punktów wynosi 22, jednak taką sytuację nazywamy <br/>" +
                "Perskim Oczkiem i gracz który je zdobędzie zostaje zwycięzcą.<br/>" +
                "</html>");

        // create bottom container
        bottomCont = new MenuButtonContainer();

        // create main menu button
        mainMenuButton = new MenuButton("WSTECZ");
        mainMenuButton.addActionListener(this);

        // create guide button
        guideButton = new MenuButton(("INSTRUKCJA OBSŁUGI"));
        guideButton.addActionListener(this);

        // add content
        this.add(rulesLogo);
        this.add(rules);
        bottomCont.add(mainMenuButton);
        bottomCont.add(guideButton);
        this.add(bottomCont);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenuButton) {
            // close previous panel, open main menu panel, update currentPanel variable
            Game.currentPanel.setVisible(false);
            Game.mainMenuPanel.setVisible(true);
            Game.currentPanel = Game.mainMenuPanel;
        }
        else if (e.getSource() == guideButton) {
            // close previous panel, open guide panel, update currentPanel variable
            Game.currentPanel.setVisible(false);
            Game.guidePanel.reset();
            Game.guidePanel.setVisible(true);
            Game.currentPanel = Game.guidePanel;
        }
    }
}

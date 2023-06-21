package GameAndPanels;

import GameAndPanels.Components.MenuButton;
import GameAndPanels.Components.MenuButtonContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuidePanel extends JPanel implements ActionListener {
    JLabel guidePage;                          // label displaying pages of the guide
    MenuButtonContainer bottomCont;            // container to store buttons at the bottom of the panel
    MenuButton mainMenuButton;                 // brings user back to main menu
    MenuButton prevPageButton, nextPageButton; // buttons for switching displayed page of the guide
    JLabel pageText;                           // text displayed on the guide page
    int currentPageIndex;                      // index to keep track of the current page
    GuidePanel() {
        // basic panel properties
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, Game.gameHeight / 50));
        this.setBackground(Game.myGreen);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(Game.gameWidth, Game.gameHeight));
        this.setVisible(false);

        // set current page index, by default we start at 0
        currentPageIndex = 0;

        // create panel content

        // create bottom container for the buttons
        bottomCont = new MenuButtonContainer();

        // create main menu button
        mainMenuButton = new MenuButton("MENU GŁÓWNE");
        mainMenuButton.addActionListener(this);

        // create previous page button
        prevPageButton = new MenuButton("COFNIJ");
        prevPageButton.addActionListener(this);

        // create next page button
        nextPageButton = new MenuButton("DALEJ");
        nextPageButton.addActionListener(this);

        // create page text
        pageText = new JLabel();
        pageText.setBackground(Game.myWhite);
        pageText.setOpaque(true);
        pageText.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Game.myRed, Game.gameHeight/130), BorderFactory.createLineBorder(Game.myWhite, Game.gameHeight/130)));
        pageText.setFont(new Font("Arial", Font.PLAIN, Game.gameWidth / 65));
        // set text to the text of the first slide by default
        pageText.setText("<html>Podczas korzystania z programu użytkownik może wchodzić w interakcję z różnymi przyciskami<br/>" +
                "wyświetlanymi na ekranie. Aby wykonać opisaną na przycisku czynność należy najechać na niego<br/>" +
                "kursorem i wcisnąć lewy przycisk myszy. W ten sposób możemy przykładowo z menu głównego<br/>" +
                "wyświetlić zasady gry, historię meczów, przejść do menu zmiany ustawień lub wyjść z gry.<html/>");

        // create the label to display guide pages
        guidePage = new JLabel();
        guidePage.setPreferredSize(new Dimension((int)(Game.gameWidth/3), (int)(Game.gameHeight/1.5)));
        guidePage.setOpaque(true);
        // creates a scaled image, initially displaying the 'buttons page'
        ImageIcon pageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/Guide/buttons.png")).getImage().getScaledInstance(guidePage.getPreferredSize().width, guidePage.getPreferredSize().height, Image.SCALE_SMOOTH));
        guidePage.setIcon(pageIcon);

        // add content
        this.add(guidePage);
        this.add(pageText);
        bottomCont.add(mainMenuButton);
        bottomCont.add(prevPageButton);
        bottomCont.add(nextPageButton);
        this.add(bottomCont);
    }

    // reset guide page to the default page
    // reset required upon every visit
    public void reset() {
        // reset page iterator
        currentPageIndex = 0;
        // reset page size
        guidePage.setPreferredSize(new Dimension((int)(Game.gameWidth/3), (int)(Game.gameHeight/1.5)));
        // creates a scaled image, initially displaying the buttons page
        ImageIcon pageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/Guide/buttons.png")).getImage().getScaledInstance(guidePage.getPreferredSize().width, guidePage.getPreferredSize().height, Image.SCALE_SMOOTH));
        guidePage.setIcon(pageIcon);
        // reset text to the 'buttons slide' text
        pageText.setText("<html>Podczas korzystania z programu użytkownik może wchodzić w interakcję z różnymi przyciskami<br/>" +
                "wyświetlanymi na ekranie. Aby wykonać opisaną na przycisku czynność należy najechać na niego<br/>" +
                "kursorem i wcisnąć lewy przycisk myszy. W ten sposób możemy przykładowo z menu głównego<br/>" +
                "wyświetlić zasady gry, historię meczów, przejść do menu zmiany ustawień lub wyjść z gry.<html/>");
    }

    // update page being displayed on the guide label
    public void updatePage(int index) {
        ImageIcon pageIcon;
        switch (index) {
            case 0:
                guidePage.setPreferredSize(new Dimension((int)(Game.gameWidth/3), (int)(Game.gameHeight/1.5)));
                pageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/Guide/buttons.png")).getImage().getScaledInstance(guidePage.getPreferredSize().width, guidePage.getPreferredSize().height, Image.SCALE_SMOOTH));
                guidePage.setIcon(pageIcon);
                pageText.setText("<html>Podczas korzystania z programu użytkownik może wchodzić w interakcję z różnymi przyciskami<br/>" +
                        "wyświetlanymi na ekranie. Aby wykonać opisaną na przycisku czynność należy najechać na niego<br/>" +
                        "kursorem i wcisnąć lewy przycisk myszy. W ten sposób możemy przykładowo z menu głównego<br/>" +
                        "wyświetlić zasady gry, historię meczów, przejść do menu zmiany ustawień lub wyjść z gry.</html>");
                break;
            case 1:
                guidePage.setPreferredSize(new Dimension((int)(Game.gameWidth/2.1), (int)(Game.gameHeight/1.5)));
                pageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/Guide/configuration.png")).getImage().getScaledInstance(guidePage.getPreferredSize().width, guidePage.getPreferredSize().height, Image.SCALE_SMOOTH));
                guidePage.setIcon(pageIcon);
                pageText.setText("<html>Po wybraniu opcji 'ROZPOCZNIJ GRĘ' użytkownik zostaje przeniesiony do menu konfiguracji rozgrywki. W tym miejscu wybiera<br/>" +
                        "ilość graczy w rozgrywce, tryb gry oraz definiuje typ gracza dla każdego uczestnika. Może to być zwykły 'GRACZ' lub 'GRACZ AI',<br/>" +
                        "który w rozdaniu zastąpi człowieka graczem sterowanym przez komputer. Każdy zwykły gracz musi podać swoją nazwę, podczas gdy<br/>" +
                        "gracze sterowani przez komputer otrzymują losową nazwę.</html>");
                break;
            case 2:
                guidePage.setPreferredSize(new Dimension((int)(Game.gameWidth/1.5), (int)(Game.gameHeight/1.5)));
                pageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/Guide/first.png")).getImage().getScaledInstance(guidePage.getPreferredSize().width, guidePage.getPreferredSize().height, Image.SCALE_SMOOTH));
                guidePage.setIcon(pageIcon);
                pageText.setText("<html>Po wybraniu opcji 'START' rozpoczyna się mecz, który w zależności od wybranego trybu może<br/>" +
                        "trwać wiele rozdań (do 1 / 2 / 3 wygranych gracza). Na ekranie pojawia się panel sterowania<br/>" +
                        "dla każdego gracza. Widać na nim status gracza w postaci kropki, nazwę gracza, jego punkty,<br/>" +
                        "przyciski 'DOBIERZ' i 'STÓJ', które wciska gracz chcąc wykonać ruch oraz jego wynik.</html>");
                break;
            case 3:
                pageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/Guide/second.png")).getImage().getScaledInstance(guidePage.getPreferredSize().width, guidePage.getPreferredSize().height, Image.SCALE_SMOOTH));
                guidePage.setIcon(pageIcon);
                pageText.setText("<html>Gracze w trakcie przebiegu rozgrywki dobierają karty, lub stoją, w zależności od stanu ich<br/>" +
                        "punktów, punktów przeciwników, dobranych kart oraz indywidualnej strategii uczestnika.<br/>" +
                        "Wszystkie dobrane przez danego gracza karty umieszczone zostają na jego panelu sterowania.<br/>" +
                        "Zasady gry 'Oczko' można znaleźć w zakładce 'ZASADY GRY' dostępnej z menu głównego.</html>");
                break;
            case 4:
                pageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/Guide/third.png")).getImage().getScaledInstance(guidePage.getPreferredSize().width, guidePage.getPreferredSize().height, Image.SCALE_SMOOTH));
                guidePage.setIcon(pageIcon);
                pageText.setText("<html>Wykonując ruchy statusy graczy ulegają zmianie. Wyrażane jest to poprzez obecną obok nazwy<br/>" +
                        "użytkownika kropkę, której kolor informuje użytkownika o stanie uczestnika. Identyfikator<br/>" +
                        "może przybrać kolor: szary (gracz oczekuje na swoją kolej), zielony (gracz wykonuje ruch),<br/>" +
                        "żółty (gracz stoi), czerwony (gracz odpadł z rozgrywki).</html>");
                break;
            case 5:
                pageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/Guide/fourth.png")).getImage().getScaledInstance(guidePage.getPreferredSize().width, guidePage.getPreferredSize().height, Image.SCALE_SMOOTH));
                guidePage.setIcon(pageIcon);
                pageText.setText("<html>Jeżeli w trakcie rozgrywki gracz spełni jeden z warunków wygranej (zdobycie perskiego oczka,<br/>" +
                        "dobranie równej ilości 21 punktów, posiadanie największej ilości punktów ze stojących i <br/>" +
                        "nieprzegranych graczy, odpadnięcie wszystkich oponentów z gry), rozdanie zakończy się<br/>" +
                        "komunikatem ogłaszającym zwycięzcę (lub ewentualny remis).</html>");
                break;
            case 6:
                guidePage.setPreferredSize(new Dimension((int)(Game.gameWidth/1.5), (int)(Game.gameHeight/1.5)));
                pageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/Guide/fifth.png")).getImage().getScaledInstance(guidePage.getPreferredSize().width, guidePage.getPreferredSize().height, Image.SCALE_SMOOTH));
                guidePage.setIcon(pageIcon);
                pageText.setText("<html>Po ogłoszeniu werdyktu meczu użytkownik zostaje przeniesiony z powrotem do menu głównego,<br/>" +
                        "z którego ma dostęp do wszystkich opisanych na przyciskach opcji. Klikając ponownie w <br/>" +
                        "przycisk 'ROZPOCZNIJ GRĘ' użytkownik zobaczy menu konfiguracji rozgrywki z identycznymi<br/>" +
                        "ustawieniami wględem poprzedniej rozgrywki. Aby zacząć instrukcję od początku, przejdź DALEJ.</html>");
                break;
            default: break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == prevPageButton) {
            // change slide to the previous one, if it's the first slide, loop
            if (currentPageIndex >= 1 && currentPageIndex <= 6) {
                currentPageIndex--;
            }
            else if (currentPageIndex == 0) {
                currentPageIndex = 6;
            }
            updatePage(currentPageIndex);
        }
        // change slide to the next one, if it's the last slide, loop
        else if (e.getSource() == nextPageButton) {
            if (currentPageIndex >= 0 && currentPageIndex <= 5) {
                currentPageIndex++;
            }
            else if (currentPageIndex == 6) {
                currentPageIndex = 0;
            }
            updatePage(currentPageIndex);
        }
        else if (e.getSource() == mainMenuButton) {
            // close previous panel, open main menu panel, update currentPanel variable
            Game.currentPanel.setVisible(false);
            Game.mainMenuPanel.setVisible(true);
            Game.currentPanel = Game.mainMenuPanel;
        }
    }
}

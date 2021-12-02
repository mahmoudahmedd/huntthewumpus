import view.ConsoleViewImpl;
import view.DesktopViewImpl;
import view.WumpusView;

public class PlayHuntTheWumpusGame {
    public static void main(String[] args) {
        WumpusView desktopViewImpl = new DesktopViewImpl();
        desktopViewImpl.startTheGame();

        WumpusView consoleViewImpl = new ConsoleViewImpl();
        consoleViewImpl.startTheGame();
    }
}

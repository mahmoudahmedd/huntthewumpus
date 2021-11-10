package view;

import presenter.WumpusPresenter;
import presenter.WumpusPresenterImpl;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.util.List;
import javax.swing.*;

import static java.util.stream.Collectors.*;
import static javax.swing.SwingUtilities.*;

public class WumpusViewImpl extends JPanel implements WumpusView {

    WumpusPresenter wumpusPresenter;


    Graphics2D g;

    public WumpusViewImpl() {

        wumpusPresenter = new WumpusPresenterImpl();

        setPreferredSize(new Dimension(721, 687));
        setBackground(Color.white);
        setForeground(Color.lightGray);
        setFont(new Font("SansSerif", Font.PLAIN, 18));
        setFocusable(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int ex = e.getX();
                int ey = e.getY();
                handleMouseClick(ex, ey, isLeftMouseButton(e), isRightMouseButton(e));
            }

        });
    }


    // don't place hazards close to the starting room

    void drawPlayer() {
        int x = wumpusPresenter.getRooms()[wumpusPresenter.getCurrRoom()][0] + (wumpusPresenter.getRoomSize() - wumpusPresenter.getPlayerSize()) / 2;
        int y = wumpusPresenter.getRooms()[wumpusPresenter.getCurrRoom()][1] + (wumpusPresenter.getRoomSize() - wumpusPresenter.getPlayerSize()) - 2;

        Path2D player = new Path2D.Double();
        player.moveTo(x, y);
        player.lineTo(x + wumpusPresenter.getPlayerSize(), y);
        player.lineTo(x + wumpusPresenter.getPlayerSize() / 2, y - wumpusPresenter.getPlayerSize());
        player.closePath();

        g.setColor(Color.white);
        g.fill(player);
        g.setStroke(new BasicStroke(1));
        g.setColor(Color.black);
        g.draw(player);
    }

    void drawStartScreen() {
        g.setColor(new Color(0xDDFFFFFF, true));
        g.fillRect(0, 0, getWidth(), getHeight() - 60);

        g.setColor(Color.darkGray);
        g.setFont(new Font("SansSerif", Font.BOLD, 48));
        g.drawString("hunt the wumpus", 160, 240);

        g.setFont(getFont());
        g.drawString("left-click to move, right-click to shoot", 210, 310);
        g.drawString("be aware that hazards may be in the same room", 175, 345);
        g.drawString("click to start", 310, 380);
    }

    void drawRooms() {

        g.setColor(Color.darkGray);
        g.setStroke(new BasicStroke(2));

        for (int i = 0; i < wumpusPresenter.getLinks().length; i++) {
            for (int link : wumpusPresenter.getLinks()[i]) {
                int x1 = wumpusPresenter.getRooms()[i][0] + wumpusPresenter.getRoomSize() / 2;
                int y1 = wumpusPresenter.getRooms()[i][1] + wumpusPresenter.getRoomSize() / 2;
                int x2 = wumpusPresenter.getRooms()[link][0] + wumpusPresenter.getRoomSize() / 2;
                int y2 = wumpusPresenter.getRooms()[link][1] + wumpusPresenter.getRoomSize() / 2;
                g.drawLine(x1, y1, x2, y2);
            }
        }

        g.setColor(Color.orange);
        for (int[] r : wumpusPresenter.getRooms())
            g.fillOval(r[0], r[1], wumpusPresenter.getRoomSize(), wumpusPresenter.getRoomSize());

        if (!wumpusPresenter.isGameOver()) {
            g.setColor(Color.magenta);
            for (int link : wumpusPresenter.getLinks()[wumpusPresenter.getCurrRoom()])
                g.fillOval(wumpusPresenter.getRooms()[link][0], wumpusPresenter.getRooms()[link][1], wumpusPresenter.getRoomSize(), wumpusPresenter.getRoomSize());
        }

        g.setColor(Color.darkGray);
        for (int[] r : wumpusPresenter.getRooms())
            g.drawOval(r[0], r[1], wumpusPresenter.getRoomSize(), wumpusPresenter.getRoomSize());
    }

    void drawMessage() {
        if (!wumpusPresenter.isGameOver())
            g.drawString("arrows  " + wumpusPresenter.getNumArrows(), 610, 30);

        if (wumpusPresenter.getMessages() != null) {
            g.setColor(Color.black);

            List<String> messages = wumpusPresenter.getMessages();
            // collapse identical messages
            messages = messages.stream().distinct().collect(toList());

            // concat at most three
            String msg = messages.stream().limit(3).collect(joining(" & "));
            g.drawString(msg, 20, getHeight() - 40);

            // if there's more, print underneath
            if (messages.size() > 3) {
                g.drawString("& " + messages.get(3), 20, getHeight() - 17);
            }

            messages.clear();
        }
    }

    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        drawRooms();
        drawMap();
        drawMessage();
    }

    private void drawMap() {
        if (wumpusPresenter.isGameOver()) {
            drawStartScreen();
        } else {
            drawPlayer();
        }
    }

    @Override
    public void render() {
        repaint();
    }

    private void handleMouseClick(int ex, int ey, boolean leftClick, boolean rightClick) {
        if (wumpusPresenter.isGameOver()) {
            wumpusPresenter.startNewGame();
        } else  {
            continueGame(ex, ey, leftClick, rightClick) ;
        }
        render();
    }

    private void continueGame(int ex, int ey, boolean leftClick, boolean rightClick) {
        int selectedRoom = getSelectedRoom(ex, ey);
        executeMouseClickActionBasedOnSelectedRoom(leftClick, rightClick, selectedRoom);
    }

    private void executeMouseClickActionBasedOnSelectedRoom(boolean leftClick, boolean rightClick, int selectedRoom) {
        final int invalidRoom = -1;
        if (selectedRoom == invalidRoom){
            return;
        } else{
            executeActionBasedOnMouseButtonClick(leftClick, rightClick, selectedRoom);
        }
    }

    private void executeActionBasedOnMouseButtonClick(boolean leftClick, boolean rightClick, int selectedRoom) {
        if (leftClick) {
            // TODO refactor move signature to take a room and setCurrRoom inside move function!
            wumpusPresenter.setCurrRoom(selectedRoom);
            wumpusPresenter.move();
        } else if (rightClick) {
            wumpusPresenter.shoot(selectedRoom);
        }
    }

    private int getSelectedRoom(int ex, int ey) {
        int selectedRoom = -1;
        for (int link : wumpusPresenter.getLinks()[wumpusPresenter.getCurrRoom()]) {
            int cx = wumpusPresenter.getRooms()[link][0];
            int cy = wumpusPresenter.getRooms()[link][1];
            if (isMouseClickWithinCorrectRoom(ex, ey, cx, cy)) {
                selectedRoom = link;
                break;
            }
        }
        return selectedRoom;
    }

    private boolean isMouseClickWithinCorrectRoom(int ex, int ey, int cx, int cy) {
        return (ex > cx && ex < cx + wumpusPresenter.getRoomSize())
                && (ey > cy && ey < cy + wumpusPresenter.getRoomSize());
    }
}

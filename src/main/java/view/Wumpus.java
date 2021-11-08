package view;

import presenter.WumpusPresenter;
import presenter.WumpusPresenterImpl;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.util.*;
import java.util.List;
import javax.swing.*;
import static java.util.stream.Collectors.*;
import static javax.swing.SwingUtilities.*;

public class Wumpus extends JPanel implements WumpusView{

    WumpusPresenter wumpusPresenter;
    Graphics2D g;

    public Wumpus() {
        WumpusPresenter wumpusPresenter = new WumpusPresenterImpl(this);

        setPreferredSize(new Dimension(721, 687));
        setBackground(Color.white);
        setForeground(Color.lightGray);
        setFont(new Font("SansSerif", Font.PLAIN, 18));
        setFocusable(true);

        addMouseListener(new MouseAdapter() {
            WumpusGameDTO wumpusDTO = wumpusPresenter.getWumpusGameDTO();

            @Override
            public void mousePressed(MouseEvent e) {
                if (wumpusDTO.isGameOver()) {
                    wumpusPresenter.startNewGame();

                } else {
                    int selectedRoom = -1;
                    int ex = e.getX();
                    int ey = e.getY();
                    int[][] links = wumpusDTO.getLinks();
                    int[][] rooms = wumpusDTO.getRooms();

                    for (int link : links[wumpusDTO.getCurrRoom()]) {
                        int cx = rooms[link][0];
                        int cy = rooms[link][1];
                        if (insideRoom(ex, ey, cx, cy)) {
                            selectedRoom = link;
                            break;
                        }
                    }

                    if (selectedRoom == -1)
                        return;

                    if (isLeftMouseButton(e)) {
                        // TODO write presenter.updateRoom(int room) ?????
                        currRoom = selectedRoom;
                        situation();

                    } else if (isRightMouseButton(e)) {
                        shoot(selectedRoom);
                    }
                }
                repaint();
            }

            boolean insideRoom(int ex, int ey, int cx, int cy) {
                int roomSize = wumpusDTO.getRoomSize();
                return ((ex > cx && ex < cx + roomSize)
                        && (ey > cy && ey < cy + roomSize));
            }
        });
    }


    // don't place hazards close to the starting room

    void situation() {
        this.wumpusPresenter.move();
    }

    void shoot(int room) {
        this.wumpusPresenter.shoot(room);
    }

    void drawPlayer(WumpusGameDTO wumpusGameDTO) {
        int x = wumpusGameDTO.getRooms()[wumpusGameDTO.getCurrRoom()][0] + ( wumpusGameDTO.getRoomSize() - wumpusGameDTO.getPlayerSize()) / 2;
        int y = wumpusGameDTO.getRooms()[wumpusGameDTO.getCurrRoom()][1] + ( wumpusGameDTO.getRoomSize() - wumpusGameDTO.getPlayerSize()) - 2;

        Path2D player = new Path2D.Double();
        player.moveTo(x, y);
        player.lineTo(x + wumpusGameDTO.getPlayerSize(), y);
        player.lineTo(x + wumpusGameDTO.getPlayerSize() / 2, y - wumpusGameDTO.getPlayerSize());
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

    void drawRooms(WumpusGameDTO wumpusGameDTO) {
        g.setColor(Color.darkGray);
        g.setStroke(new BasicStroke(2));

        for (int i = 0; i < wumpusGameDTO.getLinks().length; i++) {
            for (int link : wumpusGameDTO.getLinks()[i]) {
                int x1 = wumpusGameDTO.getRooms()[i][0] + wumpusGameDTO.getRoomSize() / 2;
                int y1 = wumpusGameDTO.getRooms()[i][1] + wumpusGameDTO.getRoomSize() / 2;
                int x2 = wumpusGameDTO.getRooms()[link][0] + wumpusGameDTO.getRoomSize() / 2;
                int y2 = wumpusGameDTO.getRooms()[link][1] + wumpusGameDTO.getRoomSize() / 2;
                g.drawLine(x1, y1, x2, y2);
            }
        }

        g.setColor(Color.orange);
        for (int[] r : wumpusGameDTO.getRooms())
            g.fillOval(r[0], r[1], wumpusGameDTO.getRoomSize(), wumpusGameDTO.getRoomSize());

        if (!wumpusGameDTO.isGameOver()) {
            g.setColor(Color.magenta);
            for (int link : wumpusGameDTO.getLinks()[wumpusGameDTO.getCurrRoom()])
                g.fillOval(wumpusGameDTO.getRooms()[link][0], wumpusGameDTO.getRooms()[link][1], wumpusGameDTO.getRoomSize(), wumpusGameDTO.getRoomSize());
        }

        g.setColor(Color.darkGray);
        for (int[] r : wumpusGameDTO.getRooms())
            g.drawOval(r[0], r[1], wumpusGameDTO.getRoomSize(), wumpusGameDTO.getRoomSize());
    }

    void drawMessage(WumpusGameDTO wumpusGameDTO) {
        if (!wumpusGameDTO.isGameOver())
            g.drawString("arrows  " + wumpusGameDTO.getNumArrows(), 610, 30);

        if (wumpusGameDTO.getMessages() != null) {
            g.setColor(Color.black);

            List<String> messages = wumpusGameDTO.getMessages();
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

        drawMap(this.wumpusPresenter.getWumpusGameDTO());
    }

    public static void main(String[] args) {
        invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Hunt The view.Wumpus");
            f.setResizable(false);
            f.add(new Wumpus(), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }

    @Override
    public void drawMap(WumpusGameDTO wumpusGameDTO) {
        drawRooms(wumpusGameDTO);
        if (wumpusGameDTO.isGameOver()) {
            drawStartScreen();
        } else {
            drawPlayer(wumpusGameDTO);
        }
        drawMessage(wumpusGameDTO);
    }

    @Override
    public void onMouseClick() {

    }
}

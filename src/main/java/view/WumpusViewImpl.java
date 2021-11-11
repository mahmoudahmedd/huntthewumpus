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

    int[][] cavesLocations = {{334, 20}, {609, 220}, {499, 540}, {169, 540}, {62, 220},
            {169, 255}, {232, 168}, {334, 136}, {435, 168}, {499, 255}, {499, 361},
            {435, 447}, {334, 480}, {232, 447}, {169, 361}, {254, 336}, {285, 238},
            {387, 238}, {418, 336}, {334, 393}};

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
                int mouseClickXAxis = e.getX();
                int mouseClickYAxis = e.getY();
                handleMouseClick(mouseClickXAxis, mouseClickYAxis, isLeftMouseButton(e), isRightMouseButton(e));
            }

        });
    }

    public int[][] getCaves() {
        return cavesLocations;
    }

    void drawPlayer() {
        int x = getCaves()[wumpusPresenter.getCurrentCave()][0] + (wumpusPresenter.getCaveCount() - wumpusPresenter.getPlayerSize()) / 2;
        int y = getCaves()[wumpusPresenter.getCurrentCave()][1] + (wumpusPresenter.getCaveCount() - wumpusPresenter.getPlayerSize()) - 2;

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
        g.drawString("be aware that hazards may be in the same cave", 175, 345);
        g.drawString("click to start", 310, 380);
    }

    void drawCaves() {

        g.setColor(Color.darkGray);
        g.setStroke(new BasicStroke(2));

        for (int i = 0; i < wumpusPresenter.getCavesLinks().length; i++) {
            for (int link : wumpusPresenter.getCavesLinks()[i]) {
                int x1 = getCaves()[i][0] + wumpusPresenter.getCaveCount() / 2;
                int y1 = getCaves()[i][1] + wumpusPresenter.getCaveCount() / 2;
                int x2 = getCaves()[link][0] + wumpusPresenter.getCaveCount() / 2;
                int y2 = getCaves()[link][1] + wumpusPresenter.getCaveCount() / 2;
                g.drawLine(x1, y1, x2, y2);
            }
        }

        g.setColor(Color.orange);
        for (int[] r : getCaves())
            g.fillOval(r[0], r[1], wumpusPresenter.getCaveCount(), wumpusPresenter.getCaveCount());

        if (!wumpusPresenter.isGameOver()) {
            g.setColor(Color.magenta);
            for (int link : wumpusPresenter.getCavesLinks()[wumpusPresenter.getCurrentCave()])
                g.fillOval(getCaves()[link][0], getCaves()[link][1], wumpusPresenter.getCaveCount(), wumpusPresenter.getCaveCount());
        }

        g.setColor(Color.darkGray);
        for (int[] r : getCaves())
            g.drawOval(r[0], r[1], wumpusPresenter.getCaveCount(), wumpusPresenter.getCaveCount());
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
        drawCaves();
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

    private void handleMouseClick(int mouseClickXAxis, int mouseClickYAxis, boolean leftClick, boolean rightClick) {
        if (wumpusPresenter.isGameOver()) {
            wumpusPresenter.startNewGame();
        } else  {
            continueGame(mouseClickXAxis, mouseClickYAxis, leftClick, rightClick) ;
        }
        render();
    }

    private void continueGame(int mouseClickXAxis, int mouseClickYAxis, boolean leftClick, boolean rightClick) {
        int selectedCave = getSelectedCaveBasedOnMouseClickLocation(mouseClickXAxis, mouseClickYAxis);
        executeMouseClickActionBasedOnSelectedCave(leftClick, rightClick, selectedCave);
    }

    private void executeMouseClickActionBasedOnSelectedCave(boolean leftClick, boolean rightClick, int selectedCave) {
        final int invalidCave = -1;
        if (selectedCave == invalidCave){
            return;
        } else{
            executeActionBasedOnMouseButtonClick(leftClick, rightClick, selectedCave);
        }
    }

    private void executeActionBasedOnMouseButtonClick(boolean leftClick, boolean rightClick, int selectedCave) {
        if (leftClick) {
            wumpusPresenter.move(selectedCave);
        } else if (rightClick) {
            wumpusPresenter.shoot(selectedCave);
        }
    }

    private int getSelectedCaveBasedOnMouseClickLocation(int mouseClickXAxis, int mouseClickYAxis) {
        int selectedCave = -1;
        for (int link : wumpusPresenter.getCavesLinks()[wumpusPresenter.getCurrentCave()]) {
            int cx = getCaves()[link][0];
            int cy = getCaves()[link][1];
            if (isMouseClickWithinCorrectCave(mouseClickXAxis, mouseClickYAxis, cx, cy)) {
                selectedCave = link;
                break;
            }
        }
        return selectedCave;
    }

    private boolean isMouseClickWithinCorrectCave(int ex, int ey, int cx, int cy) {
        return (ex > cx && ex < cx + wumpusPresenter.getCaveCount())
                && (ey > cy && ey < cy + wumpusPresenter.getCaveCount());
    }
}

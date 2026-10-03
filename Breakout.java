import acm.program.*;
import acm.graphics.*;

import java.awt.event.*;
import java.awt.Color;

public class Breakout extends GraphicsProgram {
    GRect paddle = new GRect(180, 570, 60, 10);

    public void run() {

        double lives = 3;
        GOval live1 = new GOval(10, 10);
        GOval live2 = new GOval(10, 10);
        GOval live3 = new GOval(10, 10);
        live1.setFilled(true);
        live1.setFillColor(Color.RED);
        live2.setFilled(true);
        live2.setFillColor(Color.RED);
        live3.setFilled(true);
        live3.setFillColor(Color.RED);
        add(live1, 10, 580);
        add(live2, 20, 580);
        add(live3, 30, 580);
        setSize(400, 700);
        addMouseListeners();
        add(paddle);
        GRect[] rects = new GRect[104];
        rects[0] = paddle;//paddledan ro aireklos
        rects[1] = new GRect(0, 0, 400, 1);
        rects[2] = new GRect(0, 0, 1, 600);
        rects[3] = new GRect(400, 0, 1, 600);
        rects[1].setFilled(true);
        rects[2].setFilled(true);
        rects[3].setFilled(true);//kedlebidan ro aireklos

        double x = getWidth();
        double y = getHeight();
        println(x);
        println(y);

        double brickwidth = 36;
        double brickheight = 8;
        double xx = 0;
        double yy = 70;
        int idx = 4;
        for (int i = 0; i < 10; i++) {
            xx = 0;
            y = 12 * i;
            for (int j = 0; j < 10; j++) {
                GRect brick = new GRect(xx, yy, 36, 8);
                brick.setFilled(true);
                if (i / 2 == 0) {
                    brick.setFillColor(Color.RED);
                }
                if (i / 2 == 1) {
                    brick.setFillColor(Color.ORANGE);
                }
                if (i / 2 == 2) {
                    brick.setFillColor(Color.YELLOW);
                }
                if (i / 2 == 3) {
                    brick.setFillColor(Color.GREEN);
                }
                if (i / 2 == 4) {
                    brick.setFillColor(Color.BLUE);
                }
                rects[idx] = brick;
                idx++;
                add(brick);
                xx += 40;
            }
            yy += 12;
        }


        GOval ball = new GOval(300, 300, 20, 20);
        ball.setFilled(true);
        ball.setFillColor(Color.BLUE);
        add(ball);


        double vx = 1;
        double vy = 1;
        double bricks = 100;
        while (true) {
            if (ball.getX() + ball.getWidth() > 400 || ball.getY() + ball.getWidth() > 600) {
                //tu "garet" gadavarda burti
                lives--;
                if (lives == 2) {
                    remove(live3);
                }
                if (lives == 1) {
                    remove(live2);
                }
                if (lives == 0) {
                    removeAll();
                    GLabel label = new GLabel("YOU LOST", 200, 200);
                    add(label);
                    break;
                }
                ball.setLocation(200, 300);
            }
            ball.move(vx, vy);

            pause(10);
            for (int i = 0; i < 104; i++) {
                double side = touch(rects[i], ball);
                if (side != -1) {
                    if (i > 3) remove(rects[i]);//anu tu agursmoxvda da ara kedels anpaddls
                    if (i > 3) rects[i] = new GRect(0, 0, 0, 0);
                    if (i > 3) bricks--;
                    if (bricks == 0) {
                        removeAll();
                        GLabel g = new GLabel("YOU WON", 200, 200);
                        add(g);
                        break;
                    }
                    if (side == 0) {//tu zustad wveroze xvdeba ukan shvabrune mimartuleba180 gradusit
                        vx *= -1;
                        vy *= -1;
                        continue;
                    }
                    if (vx == 1 && vy == 1) {
                        if (side % 2 == 1) {
                            vx = 1;
                            vy = -1;
                        }
                        if (side % 2 == 0) {
                            vx = -1;
                            vy = 1;
                        }

                    } else if (vx == -1 && vy == -1) {
                        println(1);
                        if (side % 2 == 1) {
                            vx = -1;
                            vy = 1;
                        }
                        if (side % 2 == 0) {
                            vx = 1;
                            vy = -1;
                        }
                    } else if (vx == 1 && vy == -1) {
                        if (side % 2 == 1) {
                            vx = 1;
                            vy = 1;
                        }
                        if (side % 2 == 0) {
                            vx = -1;
                            vy = -1;
                        }
                    } else if (vx == -1 && vy == 1) {
                        if (side % 2 == 1) {
                            vx = -1;
                            vy = -1;
                        }
                        if (side % 2 == 0) {
                            vx = 1;
                            vy = 1;
                        }
                    }

                }
            }

        }
    }

    private double touch(GRect paddle, GOval ball) {
        double paddlex = paddle.getX();
        double paddley = paddle.getY();
        double paddlesigane = paddle.getWidth();
        double paddlesimagle = paddle.getHeight();
        double centerx = ball.getX() + ball.getWidth() / 2;
        double centery = ball.getY() + ball.getHeight() / 2;
        double radius = ball.getWidth() / 2;
//tu wveroze moxvda da ara gverdze anu (AB) ze da ara [AB]ze
        if (ball.contains(paddlex, paddley) || ball.contains(paddlex + paddlesigane, paddley + paddlesimagle) ||
                ball.contains(paddlex + paddlesigane, paddley) || ball.contains(paddlex, paddley + paddlesimagle)) {
            return 0;
        }
        if (paddley == centery + radius && centerx >= paddlex && centerx <= paddlex + paddlesigane) return 1;
        if (paddley + paddlesimagle == centery - radius && centerx >= paddlex && centerx <= paddlex + paddlesigane)
            return 3;
        if (centerx + radius == paddlex && centery >= paddley && centery <= paddley + paddlesimagle) return 2;
        if (centerx - radius == paddlex + paddlesigane && centery >= paddley && centery <= paddley + paddlesimagle)
            return 4;
        return -1;
    }

    public void mouseMoved(MouseEvent e) {
        double x = e.getX() - paddle.getWidth();
        if (x < 0) x = 0;
        if (x > 370) x = 370;
        paddle.setLocation(x, 550);
    }

    public static void main(String[] args) {
        new Breakout().start(args);
    }
}
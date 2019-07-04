import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Random;

class Meteorites extends JPanel {
    ImageIcon image;
    JLabel imageLabel;
    JLabel wordLabel;
    int time; // 下降的速度
    private int x, y; // 陨石的坐标
    private int tx, ty; // 陨石出现的位置与飞船之间的坐标差，
    private int blood;  // 陨石的“血量”，大小与单词的长度一致，每击中一次减一
    PaintThread paintThread;
    boolean waitFlag;           // 因为 wait()方法总会把主线程阻塞，所以使用 waitFlag 标志来控制run 方法的运行过程，达到原本wait的目的

    Meteorites() {   // 因为子类的构造函数会首先调用父类的构造函数，又涉及到路径问题，所以没办法在这里进行过多的操作子类需要
        waitFlag = false;
        this.setSize(660, 880);
        this.setOpaque(false);   // 将面板设置为透明
//        this.setVisible(true);

        setSpeed();
//        paintThread = new PaintThread();
//        paintThread.start();
    }

    void setImageLabel() {       // 设置图片标签
        imageLabel = new JLabel(image);
        imageLabel.setSize(image.getIconWidth(), image.getIconHeight());
        imageLabel.setLocation(0, -80);      // 使陨石不至于马上出现在界面上
        this.add(imageLabel);
    }

    void setWordLabel() {// 设置文字标签
        Font f = getFont(15);
        this.add(this.wordLabel);
        wordLabel.setLocation(0, -50);
        this.wordLabel.setSize(200, 40);
        this.wordLabel.setFont(f);
        this.wordLabel.setForeground(Color.WHITE);
        this.wordLabel.setBackground(Color.BLACK);
//        this.wordLabel.setOpaque(true); // 试试label的透明是什么效果
    }

    void setSpeed() {// 设置下降速度
        Random rd = new Random();
        x = rd.nextInt(600); // 陨石从最上面的随机位置出现
        y = 0;

        if (x - 290 > 0) {
            tx = -1;
        } else {
            tx = 1;
        }
        ty = 1;
    }

    void setLocation() {  // 设置位置的移动
        imageLabel.setLocation(x, y);
        wordLabel.setLocation(x + image.getIconWidth() - 10, y + image.getIconHeight() - 20);
        int num = 1;
        if (x != 290) {
            num = 600 / (x - 290);
        }
        if (x != 300 && y % num == 0) {
            x = x + tx;
        }
        y = y + ty;

    }

    void setBlood() {
        blood = blood - 1;
    }

    int getBlood() {
        return blood;
    }

    Font getFont(int x) {        // 获得字体大小为 x 的 font 对象
        Font font = new Font("宋体", Font.BOLD, x);
        return font;
    }


    class PaintThread extends Thread {   // 1 个线程只能控制一个陨石
        boolean flag = true;  // 控制线程进行

        public void run() {
            try {
                sleep(1000 * time);     // 为了让陨石间隔一段时间出现
                Meteorites.this.setVisible(true);
                while (flag) {
                    if (!waitFlag) {     // waitFlag 为false 时，线程正常运行，否则，不更新位置
                        setLocation();
                    }
//                if (meteorites.getBlood() == 0) { // 陨石血量为 0 ，或者飞船被撞毁，线程结束
//                    flag = false;
//                }
                    if (y == 800) {
                        flag = false;
                    }
                    sleep(15);
                }
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    void setWait() {
        waitFlag = true;
    }
    void setNotify(){
        waitFlag = false;
    }

}


class miniMeteorites extends Meteorites {   // 小型陨石
    miniMeteorites(String word, int x) {
        time = x;
        image = new ImageIcon("res/mine.png");
        setImageLabel();

        this.wordLabel = new JLabel(word);
        setWordLabel();

        paintThread = new PaintThread();
        paintThread.start();
    }
}

class midMeteorites extends Meteorites {    // 中型陨石
    midMeteorites(String word, int x) {
        time = x;
        image = new ImageIcon("res/destroyer.png");
        setImageLabel();

        this.wordLabel = new JLabel(word);
        setWordLabel();

        paintThread = new PaintThread();
        paintThread.start();
    }
}

class largMeteorites extends Meteorites {// 大型陨石

    largMeteorites(String word, int x) {
        time = x;
        image = new ImageIcon("res/oppressor.png");
        setImageLabel();

        this.wordLabel = new JLabel(word);
        setWordLabel();

        paintThread = new PaintThread();
        paintThread.start();
    }
}

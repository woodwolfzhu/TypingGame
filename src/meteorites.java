import javax.swing.*;
import java.awt.*;
import java.util.Random;

class Meteorites {
    ImageIcon image;
    JLabel imageLabel;
    JLabel word;
    int time; // 下降的速度
    JFrame frame;
    int x, y; // 陨石的坐标
    int tx, ty; // 陨石出现的位置与飞船之间的坐标差，

    Meteorites(JFrame frame) {
        this.frame = frame;
    }

    void setImage() {// 设置显示图片
    }

    void setWord(String word) {// 设置显示文字
        this.word.setText(word);
        Font f = getFont(20);
        this.word.setFont(f);
        this.word.setForeground(Color.WHITE);
        this.word.setBackground(Color.BLACK);
//        this.word.setOpaque(true); // 试试label的透明是什么效果
    }

    void setSpeed(int time) {// 设置下降速度
        Random rd = new Random();
        x = rd.nextInt(600); // 陨石从最上面的随机位置出现
        y = 0;
        tx = x - 290;
        ty = 700;
        this.time = time;
    }

    void setLocation(Graphics g) {  // 设置位置
        imageLabel.setLocation(x,y);
        word.setLocation(x+32,y+32);
        x= x+tx/time;
        y=y+ty/time;
    }

    Font getFont(int x) {        // 获得字体大小为 x 的 font 对象
        Font font = new Font("宋体", Font.BOLD, x);
        return font;
    }
}


class miniMeteorites extends Meteorites {
    miniMeteorites(JFrame frame) {
        super(frame);
        image = new ImageIcon("res/mine.png");
        imageLabel = new JLabel(image);
        word = new JLabel();
    }// 小型陨石
}


class midMeteorites extends Meteorites {
    midMeteorites(JFrame frame) {
        super(frame);
        image = new ImageIcon("res/destroyer.png");
        imageLabel = new JLabel(image);
        word = new JLabel();
    }// 小型陨石
}

class largMeteorites extends Meteorites {
    largMeteorites(JFrame frame) {
        super(frame);
        image = new ImageIcon("res/oppressor.png");
        imageLabel = new JLabel(image);
        word = new JLabel();
    }// 小型陨石
}

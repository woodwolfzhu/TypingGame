import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Random;

class Meteorites extends JPanel{
    ImageIcon image;
    JLabel imageLabel;
    JLabel word;
    private int time; // 下降的速度
    private int x, y; // 陨石的坐标
    private int tx, ty; // 陨石出现的位置与飞船之间的坐标差，
    private int blood;  // 陨石的“血量”，大小与单词的长度一致，每击中一次减一
    Graphics g;
    String text1; // 游戏使用的文本文件
    String[] text2; // 处理后的文本，去掉空格和各种标点

    Meteorites() {
        setSpeed();
        this.setOpaque(false);   // 将面板设置为透明
        this.setVisible(true);
    }


    void setWord(String word) {// 设置显示文字
        this.word.setText(word);
        Font f = getFont(20);
        this.word.setFont(f);
        this.word.setForeground(Color.WHITE);
        this.word.setBackground(Color.BLACK);
//        this.word.setOpaque(true); // 试试label的透明是什么效果
    }

    void setSpeed() {// 设置下降速度
        Random rd = new Random();
        x = rd.nextInt(600); // 陨石从最上面的随机位置出现
        y = 0;

        if(x-290>0 ){
            tx = -1;
        }else{
            tx=1;
        }
//        tx = 1;
        ty = 1;
    }

    void setLocation() {  // 设置位置的移动
        imageLabel.setLocation(x, y);
        word.setLocation(x + 32, y + 32);
        int num=0;
        num = 700/(x-290)+1;
        if (x != 290&& y%num == 0) {
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
        Meteorites meteorites;


        public void run() {
            try {
                while (true) {
                    setLocation();
//                if (meteorites.getBlood() == 0) { // 陨石血量为 0 ，或者飞船被撞毁，线程结束
//                    flag = false;
//                }
                    sleep(15);
                }
            }catch(InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }

    void initText() {
        try {
            File f = new File("res/text.txt");
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String str = "";
            while ((str = br.readLine()) != null) {
                text1 = text1 + str;
            }
            dealText();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    void dealText(){
        text2 = text1.split("[^a-z]"); // 获得干净的单词
    }


}


class miniMeteorites extends Meteorites {   // 小型陨石
    miniMeteorites() {

        image = new ImageIcon("res/mine.png");
        imageLabel = new JLabel(image);
        imageLabel.setSize(32,42);
        this.add(imageLabel);
        word = new JLabel("aa");
        new PaintThread().start();
    }
}


class midMeteorites extends Meteorites {
    midMeteorites() {
        image = new ImageIcon("res/destroyer.png");
        imageLabel = new JLabel(image);
        word = new JLabel();
    }// 小型陨石
}

class largMeteorites extends Meteorites {
    largMeteorites() {
        image = new ImageIcon("res/oppressor.png");
        imageLabel = new JLabel(image);
        word = new JLabel();
    }// 小型陨石
}

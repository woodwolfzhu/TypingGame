import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TypingGUI {
    JFrame frame;
    JPanel jPanel;
    Graphics g;
    JLabel label;            // 游戏大标题
    JLabel backGroundLabel; // 背景图所在标签
    ImageIcon imageIcon;    // 背景图
    JButton newGameButton;  // 开始新游戏按钮
    JButton loadTextButton; // 加载游戏所需的文档按钮
    JPanel mainPanel;
    JButton pauseButton;          // 暂停按钮
    JLabel pauseLabel;      // 暂停时的提示语
    JButton resumeButton;         // 继续按钮
    JLabel mshipLabel;      // 主界面小飞船
//    JLabel gshipLabel;      // 游戏界面飞船
    Meteorites meteorites;


    TypingGUI() {
        frame = new JFrame("Typing Game");

        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLocation(500, 200);
        g = frame.getGraphics();
        mainGUI();    // 设置主界面背景图
        gamingGUI();
        frame.setResizable(false);  // 禁止改变窗口大小
        frame.setVisible(true);

    }

    void mainGUI() { // 之后可以做成滚动的 https://wenku.baidu.com/view/85d254ce0508763231121260.html
        imageIcon = new ImageIcon("res/stars.jpg");
        backGroundLabel = new JLabel(imageIcon);
        frame.getLayeredPane().add(backGroundLabel, new Integer(Integer.MIN_VALUE)); // 将背景标签添加到frame的LayeredPane 面板里
        backGroundLabel.setBounds(0, 0, 600, 800);
        ((JPanel) frame.getContentPane()).setOpaque(false); // 将内容面板设置为透明
//        try {
//            File file = new File(this.getClass().getResource("/res/Snipaste_2019-03-17_09-45-02.png").getPath() );
//            FileInputStream fis = new FileInputStream(file);
//            BufferedImage im = new BufferedImage(600,800,BufferedImage.TYPE_INT_ARGB);

//            getClass().getResource("res/Snipaste_2019-03-17_09-45-02.png");

//            im = ImageIO.read(getClass().getResource("res/Snipaste_2019-03-17_09-45-02.png"));
////            if(file.exists()){
////                System.out.println("存在a");
////            }
//            g.drawImage(im,0,0,null);

            label = new JLabel("Typing");       // 游戏大标题
            label.setFont(getFont(80));
            label.setForeground(Color.WHITE);
            label.setBounds(170, 100, 280, 200);
            frame.add(label);

            setButton();    // 对主界面按钮进行设置

            //主界面小飞船
            ImageIcon imageIcon1 = new ImageIcon("res/ship.png");
            mshipLabel = new JLabel(imageIcon1);
            mshipLabel.setBounds(300, 350, 23, 297);
            frame.add(mshipLabel);
//        }catch(IOException e){
//            System.out.println(e.getMessage());
//        }

    }

    void gamingGUI() {  // 游戏时的画面
        frame.remove(newGameButton);
        frame.remove(loadTextButton);
        frame.remove(label);
        mshipLabel.setBounds(290,700,23,297);
        ImageIcon imageIcon = new ImageIcon("res/pause.png");
        pauseButton = new JButton(imageIcon);
        pauseButton = setButtonOpaque(pauseButton); // 设置为只显示图片
        pauseButton.setBounds(20, 20, 18, 21);

        frame.add(pauseButton);
        frame.setVisible(true);
    }

    void pauseGUI() {    // 暂停时游戏画面
        pauseLabel = new JLabel("休息一下。。。");
        pauseLabel.setFont(getFont(50));
        pauseLabel.setForeground(Color.WHITE);
        pauseLabel.setBounds(100, 100, 500, 200);
        resumeButton = new JButton("继续");
        resumeButton.setFont(getFont(40));
        resumeButton.setForeground(Color.WHITE);
        resumeButton = setButtonOpaque(resumeButton);
        resumeButton.setBounds(200,550,200,80);
        frame.add(pauseLabel);
        frame.add(resumeButton);
    }

    void paint(){   // 实现对陨石的绘制

    }

    void setButton() {  // 将主界面按钮进行设置
        newGameButton = new JButton("new game");
        loadTextButton = new JButton("load text");

        newGameButton = setButtonOpaque(newGameButton); // 设置为只显示图片或文字
        loadTextButton = setButtonOpaque(loadTextButton);

        newGameButton.setFont(getFont(40));
        loadTextButton.setFont(getFont(40));
        newGameButton.setForeground(Color.WHITE);
        loadTextButton.setForeground(Color.WHITE);

        newGameButton.setBounds(new Rectangle(200, 450, 200, 80));
        loadTextButton.setBounds(new Rectangle(200, 550, 200, 80));
        frame.add(newGameButton);
        frame.add(loadTextButton);
    }

    void  endGUI(){ // 结束画面

    }

    JButton setButtonOpaque(JButton button) {    // 将按钮设置为只显示图片
        button.setBorderPainted(false); // 不打印边框
        button.setBorder(null);  // 除去边框
        button.setFocusPainted(false);   // 除去焦点的框
        button.setContentAreaFilled(false);    // 除去默认的背景填充
        return button;
    }

    Font getFont(int x) {        // 获得字体大小为 x 的 font 对象
        Font font = new Font("宋体", Font.BOLD, x);
        return font;
    }
}

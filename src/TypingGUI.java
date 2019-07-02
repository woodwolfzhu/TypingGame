import javax.swing.*;
import java.awt.*;

public class TypingGUI {
    JFrame frame;
    JPanel jPanel;
    Graphics g;
    JLabel label;
    JLabel backGroundLabel; // 背景图所在标签
    ImageIcon imageIcon;    // 背景图
    JButton newGameButton;  // 开始新游戏
    JButton loadTextButton; // 加载游戏所需的文档
    JPanel mainPanel;

    TypingGUI() {
        frame = new JFrame("Typing Game");
        newGameButton = new JButton("new game");
        loadTextButton = new JButton("load text");
        label = new JLabel("Typing");
        Font f = new Font("宋体", Font.BOLD, 80);
        label.setFont(f);
        label.setForeground(Color.WHITE);
        label.setBounds(170, 100, 280, 200);
        frame.add(label);
        setButton();    // 对主界面按钮进行设置
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLocation(500, 200);
        g = frame.getGraphics();
        setBackGround();    // 设置主界面背景图
        frame.setResizable(false);  // 禁止改变窗口大小
        frame.setVisible(true);

    }

    void setBackGround() { // 之后可以做成滚动的 https://wenku.baidu.com/view/85d254ce0508763231121260.html
        imageIcon = new ImageIcon("res/stars.jpg");
        backGroundLabel = new JLabel(imageIcon);
        frame.getLayeredPane().add(backGroundLabel, new Integer(Integer.MIN_VALUE)); // 将背景标签添加到frame的LayeredPane 面板里
        backGroundLabel.setBounds(0, 0, 640, 800);
        ((JPanel) frame.getContentPane()).setOpaque(false); // 将内容面板设置为透明

        ImageIcon imageIcon1 = new ImageIcon("res/ship.png");
        JLabel shipLabel = new JLabel(imageIcon1);
        shipLabel.setBounds(300,320,23,297);
        frame.add(shipLabel);

    }

    void setButton() {  // 将主界面按钮进行设置

        newGameButton.setBorderPainted(false); // 不打印边框
        newGameButton.setBorder(null);  // 除去边框
        newGameButton.setFocusPainted(false);   // 除去焦点的框
        newGameButton.setContentAreaFilled(false);    // 除去默认的背景填充

        loadTextButton.setBorderPainted(false);
        loadTextButton.setBorder(null);
        loadTextButton.setFocusPainted(false);
        loadTextButton.setContentAreaFilled(false);

        Font f = new Font("宋体", Font.BOLD, 40);
        newGameButton.setFont(f);
        loadTextButton.setFont(f);
        newGameButton.setForeground(Color.WHITE);
        loadTextButton.setForeground(Color.WHITE);

        newGameButton.setBounds(new Rectangle(200, 450, 200, 80));
        loadTextButton.setBounds(new Rectangle(200, 550, 200, 80));
        frame.add(newGameButton);
        frame.add(loadTextButton);
    }

}

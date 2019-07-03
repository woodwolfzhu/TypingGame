import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TypingGUI implements ActionListener{
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
    Meteorites[] min;     // 小陨石
    Meteorites[] mid;     // 中陨石
    Meteorites[] larg;     // 大陨石
    int checkPoint;             // 关卡
    int shipBlood;        // 飞船的血，暂时默认为 1；被撞后游戏结束
    String[] text;        // 陨石上的单词
    int wordNum;          // 记录text中未使用的单词的位置

    TypingGUI() {
        frame = new JFrame("Typing Game");
        wordNum =0;

        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLocation(500, 200);
        g = frame.getGraphics();
        shipBlood = 1;
        initButton();
        mainGUI();    // 设置主界面背景图
//        pauseGUI();
//        gamingGUI();
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
//            File file = new File("res/1.png" );
//            FileInputStream fis = new FileInputStream(file);
//            BufferedImage im = new BufferedImage(600,800,BufferedImage.TYPE_INT_ARGB);
//
//            im = ImageIO.read(fis);
//            if(file.exists()){
//                System.out.println("存在a");
//            }
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
        pauseButton.setBounds(20, 20, 18, 21);
        mshipLabel.setBounds(290, 700, 23, 297);

        frame.add(pauseButton);
        frame.setVisible(true);
    }

    void pauseGUI() {    // 暂停时游戏画面
        pauseLabel = new JLabel("休息一下。。。");
        pauseLabel.setFont(getFont(50));
        pauseLabel.setForeground(Color.WHITE);
        pauseLabel.setBounds(100, 100, 500, 200);

        resumeButton.setFont(getFont(40));
        resumeButton.setForeground(Color.WHITE);
        resumeButton = setButtonOpaque(resumeButton);
        resumeButton.setBounds(200, 550, 200, 80);
        frame.add(pauseLabel);
        frame.add(resumeButton);
    }

    void endGUI() { // 结束画面
    }


    void initButton(){

        newGameButton = new JButton("new game");
        loadTextButton = new JButton("load text");
        resumeButton = new JButton("继续");
        ImageIcon imageIcon = new ImageIcon("res/pause.png");
        pauseButton = new JButton(imageIcon);
        pauseButton = setButtonOpaque(pauseButton); // 设置为只显示图片

        newGameButton.addActionListener(this);
        loadTextButton.addActionListener(this);
        pauseButton.addActionListener(this);
        resumeButton.addActionListener(this);


    }


    void setButton() {  // 将主界面按钮进行设置


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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            gamingGUI();
            makeMeteorites();
        } else if (e.getSource() == loadTextButton) {
            loadText();
        } else if (e.getSource() == pauseButton) {
            pauseGUI();
        } else if (e.getSource() == resumeButton) {

            frame.remove(pauseLabel);
            frame.remove(resumeButton);
            gamingGUI();
        }
        frame.repaint();
    }

    void loadText() { // 从TextArea 中获得字符串并赋给gui.text;

    }



    void makeMeteorites() {
        int i = checkPoint;
        int sum = 0;
        int min, mid, larg;
        mid = i / 3;   // 中型陨石每3关增加一个
        larg = i / 5;  // 大陨石每5关增加一个
        if (i % 3 != 0 && i % 5 != 0) {   // 小陨石在这些特定关卡中不增加
            min = i + 4;
        } else {
            min = i + 3;
        }
        sum = min + mid + larg;

        this.min = new miniMeteorites[min];
        this.mid = new midMeteorites[mid];
        this.larg = new largMeteorites[larg];

        int x;
        for (x = 0; x < min; x++) {
            this.min[x] = new miniMeteorites();
            this.min[x].setWord(text[wordNum%text.length]);
            frame.add(this.min[x]);
            wordNum++;
        }
        for (x = 0; x < mid; x++) {
            this.mid[x] = new midMeteorites();
            this.mid[x].setWord(text[wordNum%text.length]);
            frame.add(this.mid[x].imageLabel);
            wordNum++;
        }
        for (x = 0; x < larg; x++) {
            this.larg[x] = new largMeteorites();
            this.larg[x].setWord(text[wordNum%text.length]);
            frame.add(this.larg[x]);
            wordNum++;
        }
    }

    void setText(String[] text){   // 给text 赋值
        this.text = text;
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


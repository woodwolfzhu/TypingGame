import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypingGUI implements ActionListener {
    JFrame frame;
    JPanel jPanel;
    Graphics g;
    JLabel label;            // 游戏大标题
    JLabel backGroundLabel; // 背景图所在标签
    ImageIcon imageIcon;    // 背景图
    JButton newGameButton;  // 开始新游戏按钮
    JButton loadTextButton; // 加载游戏所需的文档按钮
    JButton gonButton;      // 继续下一关按钮
    JButton exit;           // 退出按钮

    JButton pauseButton;          // 暂停按钮
    JLabel pauseLabel;      // 暂停时的提示语
    JButton resumeButton;         // 继续按钮
    JLabel mshipLabel;      // 主界面小飞船
    JLabel cheerLabel1;
    JLabel cheerLabel2;

    Meteorites[] meteorites;    // 陨石
    int sum;                    // 陨石总数量
    int number;                 // 被击毁的陨石数量

    int checkPoint;             // 关卡
    int shipBlood;        // 飞船的血，暂时默认为 1；被撞后游戏结束

    String text1;          // 未处理的文本
    String[] text2;        // 从text1 中提取的单词，陨石上的单词
    int wordBegin;         // 去单词开始的位置
    int wordEnd;          // 记录text中未使用的单词的位置
    int[] wordNum1;          // 记录那些单词被输入过

    DataDeal dataDeal;         // 处理数据


    TypingGUI() {
        frame = new JFrame("Typing Game");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLocation(500, 200);

        shipBlood = 1;
        initButton();
        mainGUI();    // 设置主界面背景图
        frame.setResizable(false);  // 禁止改变窗口大小
        frame.setVisible(true);
        File file = new File("res/text.txt");   // 默认文本
        initText(file);
        dealText();
        wordBegin = 0;
        wordEnd = 0;
        checkPoint = 1;
        wordNum1 = new int[100];

        dataDeal = new DataDeal();
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
        JButton button = new JButton("hahah");
        button.addKeyListener(new Attack());
//        button.setSize(100,100);

        frame.repaint();
        frame.add(button);
        frame.addKeyListener(new Attack());

    }

    void pauseGUI() {    // 暂停时游戏画面
        frame.remove(pauseButton);

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
        frame.remove(pauseButton);
        frame.add(gonButton);
        frame.add(exit);
        cheerLabel1 = new JLabel("你刚刚成功穿过了危险的");
        cheerLabel1.setFont(getFont(45));
        cheerLabel1.setForeground(Color.WHITE);
        cheerLabel1.setBounds(20, 100, 700, 200);
        cheerLabel2 = new JLabel("陨石带，是否继续旅行？");
        cheerLabel2.setFont(getFont(45));
        cheerLabel2.setForeground(Color.WHITE);
        cheerLabel2.setBounds(20, 250, 700, 200);
        frame.add(cheerLabel1);
        frame.add(cheerLabel2);
    }

    class Attack extends Thread implements KeyListener {    // 相应键盘事件
        int letterNum;         // 记录敲击到了单词的第几个字母
        boolean flag;
        int wordNum;           // 当前敲击单词在text2 中的位置
        Bullet[] bullet;

        Attack() {
            flag = true;  // 第一次敲击键盘，之后设置为false ，直到把一个单词敲完
            letterNum = 0;
            wordNum = -1;
            bullet = new Bullet[20];
        }

        public void run() {
            int x = 0;
            boolean flag =true;
            while (flag) {
                if (wordNum >= 0) {
                    for (x = 0; x < text2[wordNum].length(); x++) {
                        if (bullet[x].getFlag()) {   // bullet 到达终点后，flag 的值为false
                            frame.remove(bullet[x]);
                        }
                    }
                }
                for (x = 0; x < sum; x++) {
                    if (meteorites[x].getLocationY() == 800) {
                        wordNum1[x] = 1;
                        frame.remove(meteorites[x]);
                        endGUI();
                    }
                }
                if (number == sum) {    // 所有陨石都被击毁
                    number=0;           //  重新置为 0
                    checkPoint++;          // 关卡数加 1
                    endGUI();
                    flag = false;
//                    gamingGUI();
//                    makeMeteorites();
                    frame.repaint();
                }
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            char letter = e.getKeyChar();
            System.out.println(letter);
            if (flag) {
                wordNum = getFirstWord(letter);
                if (wordNum != -1) {      // 敲对了某个单词的第一个字母
                    flag = false;
                    int x = wordNum - wordBegin; // 这个算出来后对应着陨石目标的序号
                    bullet[letterNum] = new Bullet(meteorites[x].getLocationX(), meteorites[x].getLocationY());
                    meteorites[x].wordLabel.setForeground(Color.ORANGE);
                    frame.add(bullet[letterNum]);
                    letterNum++;
                }
            } else {
                getFollowWord(letter);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        int getFirstWord(char letter) { // 获得首字母与敲击字母相同的单词，若有单词首字母重复，就取靠前的
            // 返回对应单词的位置
            // 没找到返回 -1
            int x = 0;
            for (x = wordBegin; x < wordEnd; x++) {
                if (wordNum1[x] != 1 && meteorites[x - wordBegin].getLocationY() > 0) {
                    if (text2[x].charAt(0) == letter) {   // 判断首字母
                        text2[x].replace(letter, ' ');
                        wordNum1[x] = 1;
                        dataDeal.score++;
                        return x;
                    }
                }
            }
            return -1;
        }

        void getFollowWord(char letter) {

            int x = wordNum - wordBegin; // 这个算出来后对应着陨石目标的序号

            if (text2[wordNum].charAt(letterNum) == letter) {   // 后续每敲对一个字母，letterNum 后移一个
                bullet[letterNum] = new Bullet(meteorites[x].getLocationX(), meteorites[x].getLocationY());
//                text2[x].replace(letter, ' ');
//                meteorites[x].wordLabel.setText(text2[x]);
                frame.add(bullet[letterNum]);
                letterNum++;
                dataDeal.score++;
                if (letterNum == text2[wordNum].length()) {      // 敲完最后一个字母，设置相关属性
                    flag = true;
                    letterNum = 0;
                    meteorites[x].wordLabel.setVisible(false);
                    meteorites[x].imageLabel.setVisible(false);
                    number++;
                    frame.remove(meteorites[x]);

                }
            }else {
                dataDeal.error++;
            }
        }
    }

    void initButton() {

        newGameButton = new JButton("new game");
        loadTextButton = new JButton("load text");
        resumeButton = new JButton("resume");
        gonButton = new JButton("next");
        exit = new JButton("exit");

        ImageIcon imageIcon = new ImageIcon("res/pause.png");
        pauseButton = new JButton(imageIcon);
        pauseButton = setButtonOpaque(pauseButton); // 设置为只显示图片

        newGameButton.addActionListener(this);
        loadTextButton.addActionListener(this);
        pauseButton.addActionListener(this);
        resumeButton.addActionListener(this);
        gonButton.addActionListener(this);
        exit.addActionListener(this);
    }

    void setButton() {  // 将主界面按钮进行设置
        newGameButton = setButtonOpaque(newGameButton); // 设置为只显示图片或文字
        loadTextButton = setButtonOpaque(loadTextButton);
        gonButton = setButtonOpaque(gonButton);
        exit = setButtonOpaque(exit);

        newGameButton.setFont(getFont(40));
        loadTextButton.setFont(getFont(40));
        gonButton.setFont(getFont(40));
        exit.setFont(getFont(40));

        newGameButton.setForeground(Color.WHITE);
        loadTextButton.setForeground(Color.WHITE);
        gonButton.setForeground(Color.WHITE);
        exit.setForeground(Color.WHITE);

        newGameButton.setBounds(new Rectangle(200, 450, 200, 80));
        loadTextButton.setBounds(new Rectangle(200, 550, 200, 80));
        gonButton.setBounds(new Rectangle(200, 450, 200, 80));
        exit.setBounds(new Rectangle(200, 550, 200, 80));

        frame.add(newGameButton);
        frame.add(loadTextButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            gamingGUI();
            makeMeteorites();
            new Attack().start();
        }
        else if (e.getSource() == loadTextButton) {
            loadText();
        } else if (e.getSource() == pauseButton) {
            pauseGUI();
            waitAllThread();
        } else if (e.getSource() == resumeButton) {
            frame.remove(pauseLabel);
            frame.remove(resumeButton);
            gamingGUI();
            notifyAllThread();
        }else if(e.getSource() == gonButton){
            frame.remove(gonButton);
            frame.remove(exit);
            frame.remove(cheerLabel1);
            frame.remove(cheerLabel2);
            gamingGUI();
            makeMeteorites();
            new Attack().start();
        }else if(e.getSource() == exit){
            System.exit(0);
        }
        frame.setVisible(true);
        frame.repaint();
    }

    void loadText() { // 从TextArea 中获得字符串并赋给gui.text;
        try {
            JFileChooser fileChooser = new JFileChooser("C:\\Users\\王\\Desktop");
            fileChooser.showOpenDialog(frame);
            File file = fileChooser.getSelectedFile();
            initText(file);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    void initText(File f) {
        try {
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

    void dealText() {
        String pattern = "[a-zA-Z]+('?[a-zA-Z])?";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text1);
        int x = 0;
        text2 = new String[1000];
        while (m.find()) {
            if (m.group().length() != 1 && !m.group().equals("")) {
                text2[x] = m.group();
                x++;
            }
        }
    }


    void makeMeteorites() {
        int i = checkPoint;
        sum = 0;
        int min, mid, larg;
        mid = i / 3;   // 中型陨石每3关增加一个
        larg = i / 5;  // 大陨石每5关增加一个
        if (i % 3 != 0 && i % 5 != 0) {   // 小陨石在这些特定关卡中不增加
            min = i + 3;
        } else {
            min = i + 2;
        }
        sum = min + mid + larg;

        wordBegin = wordEnd;
        meteorites = new Meteorites[sum];
        int x;
        for (x = 1; x < sum + 1; x++) {
            if (x % 5 != 0 && x % 7 != 0) {
                meteorites[x - 1] = new miniMeteorites(text2[wordEnd], x);
            } else if (x % 5 == 0) {
                meteorites[x - 1] = new midMeteorites(text2[wordEnd], x);
            } else if (x % 7 == 0) {
                meteorites[x - 1] = new largMeteorites(text2[wordEnd], x);
            }
            frame.add(meteorites[x - 1]);
            wordEnd++;
        }
    }

    void waitAllThread() {
        int x = 0;
        for (x = 0; x < meteorites.length; x++) {
            meteorites[x].setWait();
        }
    }

    void notifyAllThread() {
        int x = 0;
        for (x = 0; x < meteorites.length; x++) {
            meteorites[x].setNotify();
        }
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


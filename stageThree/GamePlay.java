package stageThree;

import main.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class GamePlay extends JPanel implements ActionListener,KeyListener, Tile
{
    public static void main(String[] args) {
        new GamePlay();
    }
    private ImageIcon icon = new ImageIcon("images/stage3_background.png");
    private Image img = icon.getImage();
    String passage=""; //Passage we get
    String typedPass=""; //Passage the user types
    String message=""; //Message to display at the end of the TypingTest

    int typed=0; //typed stores till which character the user has typed
    int count=0;
    int WPM;

    double start, end, elapsed, seconds;

    boolean running; //If the person is typing
    boolean ended; //Whether the typing test has ended or not
    final int DELAY=100;

    JButton button;
    Timer timer;
    JLabel label;

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }

    public GamePlay()
    {
        setLayout(new BorderLayout());
        setSize(screenWidth,screenHeight);
        setVisible(true);

        passage = getPassage();
        timer=new Timer(DELAY,this);
        timer.start();
        running=true;
        ended=false;

        typedPass="";
        message="";

        typed=0;
        count=0;

        if(running)
            repaint();
        if(ended)
            repaint();

        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
        this.revalidate();
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        g.setFont(new Font("Sam3KRFont", Font.BOLD, 25));

        if(running)
        {
            //This will put our passage on the screen
            if(passage.length()>1)
            {
                g.setColor(Color.gray);
                g.drawString(passage.substring(0,30), g.getFont().getSize(), g.getFont().getSize()*7);
                g.drawString(passage.substring(30,60), g.getFont().getSize(), g.getFont().getSize()*9);
                g.drawString(passage.substring(60,90), g.getFont().getSize(), g.getFont().getSize()*11);
                g.drawString(passage.substring(120,150), g.getFont().getSize(), g.getFont().getSize()*13);
                g.drawString(passage.substring(150,180), g.getFont().getSize(), g.getFont().getSize()*15);
                //g.drawString(passage.substring(150,200), g.getFont().getSize(), g.getFont().getSize()*15);
            }

            //이거 색깡 구현
            g.setColor(Color.BLACK);
            if(typedPass.length()>0)
            {
                //This is if the typedPassages length is greater than 0 and less than 50
                if(typed<30) //if the typed index is in the first line
                    g.drawString(typedPass.substring(0,typed), g.getFont().getSize(),g.getFont().getSize()*7); //From the first letter to the currently typed one in green
                else
                    g.drawString(typedPass.substring(0,30), g.getFont().getSize(),g.getFont().getSize()*7); //If the typed character exceeds 50 we can directly show the whole line in green
            }
            if(typedPass.length()>30)
            {
                if(typed<60)
                    g.drawString(typedPass.substring(60,typed), g.getFont().getSize(),g.getFont().getSize()*9);
                else
                    g.drawString(typedPass.substring(30,60), g.getFont().getSize(),g.getFont().getSize()*9);
            }
            if(typedPass.length()>60)
            {
                if(typed<90)
                    g.drawString(typedPass.substring(90,typed), g.getFont().getSize(),g.getFont().getSize()*11);
                else
                    g.drawString(typedPass.substring(90,150), g.getFont().getSize(),g.getFont().getSize()*11);
            }
            if(typedPass.length()>90)
            {
                if(typed<120)
                    g.drawString(typedPass.substring(120,typed), g.getFont().getSize(),g.getFont().getSize()*13);
                else
                    g.drawString(typedPass.substring(90,120), g.getFont().getSize(),g.getFont().getSize()*13);
            }
            if(typedPass.length()>30)
            {
                if(typed<60)
                    g.drawString(typedPass.substring(60,typed), g.getFont().getSize(),g.getFont().getSize()*15);
                else
                    g.drawString(typedPass.substring(30,60), g.getFont().getSize(),g.getFont().getSize()*15);
            }
            if(typedPass.length()>30)
            {
                if(typed<60)
                    g.drawString(typedPass.substring(60,typed), g.getFont().getSize(),g.getFont().getSize()*7);
                else
                    g.drawString(typedPass.substring(30,60), g.getFont().getSize(),g.getFont().getSize()*7);
            }
            if(typedPass.length()>200) {
                g.drawString(typedPass.substring(150, typed), g.getFont().getSize(), g.getFont().getSize() * 11);
                running = false;
            }
        }
        if(ended)
        {
            if(WPM<=40)
                message="실력이 아직 부족하구나! 서당에 가서 더 배우고 오너라";
            else if(WPM>40 && WPM<=60)
                message="이정도면 쓸만하구나";
            else if(WPM>60 && WPM<=100)
                message="명필이구나";
            else
                message="아주 훌륭한 글솜씨구나";

            FontMetrics metrics=getFontMetrics(g.getFont());
            g.setColor(Color.BLUE);
            g.drawString("Typing Test Completed!", (screenWidth-metrics.stringWidth("Typing Test Completed!"))/2, g.getFont().getSize()*6);

            g.setColor(Color.BLACK);
            g.drawString("Typing Speed: "+WPM+" Words Per Minute", (screenWidth-metrics.stringWidth("Typing Speed: "+WPM+" Words Per Minute"))/2, g.getFont().getSize()*9);
            g.drawString(message, (screenWidth-metrics.stringWidth(message))/2, g.getFont().getSize()*11);

            timer.stop();
            ended=false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        if(passage.length()>1)
        {
            if(count==0)
                start=LocalTime.now().toNanoOfDay();
            else if(count==200)
            {
                end=LocalTime.now().toNanoOfDay();
                elapsed=end-start;
                seconds=elapsed/1000000000.0;
                WPM=(int)(((200.0/5)/seconds)*60);
                ended=true;
                running=false;
                count++;
            }
            char[] pass=passage.toCharArray();
            if(typed<200)
            {
                running=true;
                if(e.getKeyChar()==pass[typed])
                {
                    typedPass=typedPass+pass[typed];
                    typed++;
                    count++;
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {    }

    @Override
    public void keyReleased(KeyEvent e)
    {    }

    public static String getPassage()
    {
        ArrayList<String> Passages=new ArrayList<String>();
        String pas1 ="이 몸 생겼을 때 임을 좇아 생겼으니 한평생의 연분임을 하늘이 모를 일이던가 나 하나 젊어 있고 임 하나 날 사랑하시니 이 마음 이 사랑 견줄 데가 전혀 없다 하루도 열두 때 한 달도 서른 날 마음에 맺혀 있어 뼛속까지 꿰쳤으니 아아, 내 병이야 이 임의 탓이로다 차라리 사라져서 범나비 되오리라 꽃나무 가지마다 간 데 족족 앉았다가 향기 묻은 날개로 임의 옷에 옮으리라 임이야 나인 줄 모르셔도 나는 임을 좇으려 하노라";
        //청산별곡
        String pas2="살어리살어리랏다 청산에 살어리랏다. 머루랑 달래랑 먹고 먹자 청산에 살어리랏다네. 얄리얄리 얄라셩 울어라 새여 울어라 새여 자고서 울어라 새여 널 나와 시름한 너도 나도 자고서 우노라 운다네 가는새 본다 가는 새 본다 물 아래 가는 새 본다 잉무든 장글란 가지고 가고 물 아래 가는 새 본다네 이링공 하야 뎌링공 하야 나즈란 디내와 손뎌 오리도 가리도 없이 없고 밤으란 또 러찌 하리라 어디라 동코 누리라 마치던 돌코 미리도 괴리도 없이 없어 맞아서 우노나 운다네";
        Passages.add(pas1);
        Passages.add(pas2);

        Random rand=new Random();
        int place=(rand.nextInt(2));

        String toReturn=Passages.get(place).substring(0,200);
        if(toReturn.charAt(199)==32)
        {
            toReturn=toReturn.strip();
            toReturn=toReturn+".";
        }
        return toReturn;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==button)
        {
            timer=new Timer(DELAY,this);
            timer.start();
            running=true;
            ended=false;

            typedPass="";
            message="";

            typed=0;
            count=0;
        }
        if(running)
            repaint();
        if(ended)
            repaint();
    }
}

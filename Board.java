import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
public class Board extends JPanel{
    Timer timer;
    BufferedImage backgroundImage;
    Player player;
    Enemy enemies[]=new Enemy[3];
    public Board(){
        setSize(1500,920);
        loadBackgroundImage();
        player=new Player();
        loadEnemies();
        gameLoop();
        setFocusable(true);
        bindEvents();
    }
    private void gameOver(Graphics pen){
        if(player.outofScreen()){
            pen.setFont(new Font("times",Font.BOLD,30));
            pen.setColor(Color.BLACK);
            pen.drawString("GAME WON", 750, 450); 
            timer.stop();
            return;
        }
        for(Enemy enemy:enemies){
            if(isCollide(enemy)){
                pen.setFont(new Font("times",Font.BOLD,30));
                pen.setColor(Color.BLACK);
                pen.drawString("GAME OVER", 750, 450);
                timer.stop();
            }
        }
    }
    private boolean isCollide(Enemy enemy){
        int xDistance=Math.abs(player.x-enemy.x);
        int yDistance=Math.abs(player.y-enemy.y);
        int maxH=Math.max(player.h,enemy.h);
        int maxW=Math.max(player.w,enemy.w);
        return xDistance<=maxW-80 && yDistance<=maxH-80;

    }
    private void bindEvents(){
        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e){
                
            }
            public void keyReleased(KeyEvent e){
                player.speed=0;
            }
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                player.speed=10;
                }
                else if(e.getKeyCode()==KeyEvent.VK_LEFT){
                player.speed=-10;
                }
            }
        });
    }
    private void loadEnemies(){
        int x=400;
        int gap=450;
        int speed=5;
        for(int i=0;i<enemies.length;i++){
            enemies[i]=new Enemy(x,speed);
            x+=gap;
            speed+=5;
        }
    }
    private void gameLoop(){
        timer=new Timer(50,(e)->repaint());
        timer.start();
    }
    private void loadBackgroundImage(){
       try{ 
        backgroundImage=ImageIO.read(Board.class.getResource("game-bg.jpeg"));
    }catch(IOException e){
        System.out.println("Not found");
        System.exit(1);
        e.printStackTrace();
    }
}
private void printEnemies(Graphics pen){
    for(Enemy enemy:enemies){
        enemy.draw(pen);
        enemy.move();
    }
}
@Override
    public void paintComponent(Graphics pen){
        super.paintComponent(pen);
        pen.drawImage(backgroundImage,0,0,1500,920,null);
        player.draw(pen);
        player.move();
        printEnemies(pen);
        gameOver(pen);
    }
}

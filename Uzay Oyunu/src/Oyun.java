import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Oyun extends JPanel implements KeyListener, ActionListener {
    Timer timer = new Timer(10,this);
    private int gecenSure = 0;
    private int harcananAtes = 0;
    private BufferedImage image;
    private ArrayList<Ates> atesler = new ArrayList();
    private int atesDirY = 20;
    private int topX = 0;
    private int topDirX = 2;
    private int uzayGemisiX = 0;
    private int dirUzayX = 20;
    int vurmaSayaci = 0;
    public long sayac = 0L;
    public int sayac2 = 0;
    public long mermi = 20L;
    public int sonX = 0;
    public int sonXTop = 0;
    public boolean yon = true;

    public Oyun(){
        try{
            this.image = ImageIO.read(new FileImageInputStream(new File("gemi.png")));
        }catch (IOException var2){}

        JButton button = new JButton("Oyundan çık");
        button.setBounds(620, 520, 150, 30);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog((Component) null, Oyun.this.vurmaSayaci + " kez düşmanı vurdun.\n" + Oyun.this.mermi + " tane mermin kaldı.\n", "Oyun kapandı...", 1);
                System.exit(0);
            }
        });

        this.add(button);
        this.setLayout((LayoutManager) null);
        this.setBackground(Color.BLACK);
        this.timer.start();
    }

    public void paint(Graphics grap){
        super.paint(grap);
        ++this.sayac;
        ++this.sayac2;

        if(this.sayac % 200L == 0L && this.sonX != this.uzayGemisiX){
            this.mermi += 2L;
        }else if(this.sayac % 100L == 0L){
            this.sonX = this.uzayGemisiX;
        }

        Random r = new Random();
        int salla = r.nextInt(175);
        if(this.sayac2 % salla == 0){
            this.yon = Math.random() < 0.5 ? !this.yon : this.yon;
        }

        grap.setColor(Color.RED);
        grap.fillOval(this.topX, 0, 20, 20);
        grap.drawImage(this.image, this.uzayGemisiX, 500, this.image.getWidth(), this.image.getHeight(), this);

        for(int i = 0; i < this.atesler.toArray().length; i++){
            if(((Ates)this.atesler.get(i)).getY() < 0){
                this.atesler.remove(i);
            }else if(((Ates)this.atesler.get(i)).getX() + 10 > this.topX && ((Ates)this.atesler.get(i)).getX() - 10 < this.topX && ((Ates)this.atesler.get(i)).getY() < 15){
                ++this.vurmaSayaci;
                this.topDirX = (int)((double)this.topDirX * 1.4);
                this.mermi += 5L;
                this.sonX = this.uzayGemisiX;
                this.atesler.clear();

                for(this.sonXTop = this.topX; this.sonXTop + 10 < this.topX && this.sonXTop - 10 < this.topX; this.topX = r.nextInt(750)){}
            }
        }

        grap.drawString(this.vurmaSayaci + " Kez Düşmanı vurdun", 10, 55);
        grap.drawString(this.mermi + " Mermin kaldı", 10, 80);
        grap.setColor(Color.BLUE);
        Iterator var6 = this.atesler.iterator();

        while(var6.hasNext()){
            Ates ates = (Ates)var6.next();
            grap.fillRect(ates.getX(), ates.getY(), 10, 20);
        }
    }

    public void repaint(){
        super.repaint();
    }

    public void actionPerformed(ActionEvent e){
        Iterator var2 = this.atesler.iterator();

        while(var2.hasNext()){
            Ates ates = (Ates)var2.next();
            ates.setY(ates.getY() - this.atesDirY);
        }

        if(this.topX > 745 && this.topX <= 750){
            this.yon = false;
        }
        if(this.topX >= 0 && this.topX <= 5){
            this.yon = true;
        }

        if(this.yon){
            this.topX += this.topDirX;
        }else{
            this.topX -= this.topDirX;
        }

        this.repaint();
    }

    public void keyTyped(KeyEvent e){
    }

    public void keyPressed(KeyEvent e){
        int c = e.getKeyCode();
        if(c == 39){
            if(this.uzayGemisiX >= 750){
                this.uzayGemisiX = 750;
            }else{
                this.uzayGemisiX += this.dirUzayX;
            }
        } else if(c == 37){
            if(this.uzayGemisiX <= 0){
                this.uzayGemisiX = 0;
            }else{
                this.uzayGemisiX -= this.dirUzayX;
            }
        } else if(c == 17){
            if(this.mermi > 0L && this.sonX != this.uzayGemisiX){
                this.atesler.add(new Ates(this.uzayGemisiX + 10, 470));
                --this.mermi;
            }
            ++this.harcananAtes;
        }
    }

    public void keyReleased(KeyEvent e) {}
}

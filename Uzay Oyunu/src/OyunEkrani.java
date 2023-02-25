import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class OyunEkrani extends JFrame{
    public OyunEkrani(String title) throws HeadlessException{
        super(title);
    }

    public static void main(String[] args){
        OyunEkrani ekran = new OyunEkrani("Uzay Oyunu");
        ekran.setResizable(false);
        ekran.setFocusable(false);
        ekran.setSize(800,600);
        ekran.setDefaultCloseOperation(3);

        int soru = JOptionPane.showConfirmDialog(ekran, "Oyuna başlamak istiyorsan 'Evet'e tıklayın.", "Oyun başlasın mı?", 1);
        if(soru == 0){
            System.out.println("Oyun Başladı!");
            Oyun oyun = new Oyun();
            oyun.requestFocus();
            oyun.addKeyListener(oyun);
            oyun.setFocusable(true);
            oyun.setFocusTraversalKeysEnabled(false);

            ekran.add(oyun);
            ekran.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(ekran, "Oyun durduruldu ve kapatıldı.", "Kapatıldı", 1);
            System.out.println("Oyun Başlamadı!");
            System.exit(0);
        }
    }

}

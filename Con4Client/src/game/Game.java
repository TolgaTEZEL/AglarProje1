/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import con4client.Client;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
// newframden baka baka matris ?= 1 set icon red ble vs yap. Evt get source ile butonun indislerini mesaj olarak yolla. TAMAMLANDIK COK SUKUR
//kalan sorunlar
// kontrol metodu sadece 1 clientte calısıyor
// oyunun bitti aşaması 
// clientlerin sizesi 
// 
/**
 *
 * @author Tolga
 */
public class Game extends javax.swing.JFrame {
     public static Game ThisGame;
      public Thread tmr_slider;
    public int RivalSelection = -1;
    public int myselection = -1;
        Random rand;
    JButton[][] button = new JButton[6][7];
    public int[][] matris = new int[6][7];
    public String durum = "";
     public String matrisVerisi="";
     
    ImageIcon icon = new ImageIcon("C:\\Users\\Tolga\\Desktop\\yellw\\kırmızı.png");
    Image img = icon.getImage();
    Image newimg = img.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
    ImageIcon red = new ImageIcon(newimg);
    
    ImageIcon icon1 = new ImageIcon("C:\\Users\\Tolga\\Desktop\\yellw\\sarı.png");
    Image img1 = icon1.getImage();
    Image newimg1 = img1.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
    ImageIcon yellow = new ImageIcon(newimg1);
    int x = 100;
    int y = 100;

    /**
     * Creates new form Gameboard
     */
    public Game() {
        initComponents();
        ThisGame = this;
        rand = new Random();
        button[0][0] = jButton1;
        button[0][1] = jButton2;
        button[0][2] = jButton3;
        button[0][3] = jButton4;
        button[0][4] = jButton5;
        button[0][5] = jButton6;
        button[0][6] = jButton7;
        
        button[1][0] = jButton8;
        button[1][1] = jButton9;
        button[1][2] = jButton10;
        button[1][3] = jButton11;
        button[1][4] = jButton12;
        button[1][5] = jButton13;
        button[1][6] = jButton14;
        
        button[2][0] = jButton15;
        button[2][1] = jButton16;
        button[2][2] = jButton17;
        button[2][3] = jButton18;
        button[2][4] = jButton19;
        button[2][5] = jButton20;
        button[2][6] = jButton21;
        
        button[3][0] = jButton22;
        button[3][1] = jButton23;
        button[3][2] = jButton24;
        button[3][3] = jButton25;
        button[3][4] = jButton26;
        button[3][5] = jButton27;
        button[3][6] = jButton28;
        
        button[4][0] = jButton29;
        button[4][1] = jButton30;
        button[4][2] = jButton31;
        button[4][3] = jButton32;
        button[4][4] = jButton33;
        button[4][5] = jButton34;
        button[4][6] = jButton35;
        
        button[5][0] = jButton36;
        button[5][1] = jButton37;
        button[5][2] = jButton38;
        button[5][3] = jButton39;
        button[5][4] = jButton40;
        button[5][5] = jButton41;
        button[5][6] = jButton42;
           tmr_slider = new Thread(() -> {
            //soket bağlıysa dönsün
            while (Client.socket.isConnected()) {
                try {
                    //
                    Thread.sleep(100);
                    //SERVERDAN VE DİĞER CLİENTTEN GELEN BÜTÜN BİLGİLERİ BURADA TUTUP DEĞERLENDİRECEĞİZ.
                    if(durum.equals("basla")){//biz başlıyoruz, yani oynama sırası bizde.
                        //oynama sırası bizde olduğunda hep bu bilgiyi almalıyız.
                        //kendi sıramız bitince de bu bilgiyi rakibe göndereceğiz zaten.
                        oyundurumu.setText("Biz oynuyoruz.");
                        //sıra bizdeyken olması gereken bütün işlemler metodlara yazılır ve metodlar buradan çağırılabilir.
                        //örneğin rakibin sırası bittiğinde rakip bize "basla" mesajı gönderecekki sıranın bize geçtiğini
                        //anlayacağız. ama rakip elini bitirdiğinde daha önce bize kendi oynadığı matrisin görüntüsünü göndermiş olacaktır
                        //yani buraya geldiysek matrisimize veri yüklenmiş demektir. matrisi ekrandaki labellere yansıtalım.
                        if(!matrisVerisi.equals("")){
                            
                            matrisi_cozumle(matrisVerisi);
                            matrisVerisi ="";
                            oyun_tahtasini_guncelle();
                        }
                    }else if(durum.equals("bekle")){
                        //bekle diye bir mesaj gelmişse kimden geldiği önemsiz, sıra rakipte demektir beklememiz gerekiyor.
                        oyundurumu.setText("Rakip oynuyor");
                    }else if(durum.equals("bitti")){
                        //eğer bitti diye bir mesaj gelmişse demekki oyun rakibin son hamlesi sonucunda
                        //bitmiş. o zaman rakip oyunu kazanmıştır. rakibin oyunu kazandığıyla ilgili mesaj ver.
                        //oyunu biz kazanmışsak zaten rakibe mesajı biz gönderiyoruz. metodda kazandığımızla ilgili şeler vardır.
                    }
                    
                        //tmr_slider.stop();
                       
                        //7 saniye sonra oyun bitsin tekrar bağlansın
                        //Thread.sleep(7000);
                        //Reset();

                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        oyundurumu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jButton1.setText("jButton2");
        jButton1.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton1.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton1.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton2.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton2.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jButton3.setText("jButton2");
        jButton3.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton3.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton3.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jButton4.setText("jButton2");
        jButton4.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton4.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton4.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        jButton5.setText("jButton2");
        jButton5.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton5.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton5.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });

        jButton6.setText("jButton2");
        jButton6.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton6.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton6.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });

        jButton7.setText("jButton2");
        jButton7.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton7.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton7.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });

        jButton8.setText("jButton2");
        jButton8.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton8.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton8.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });

        jButton9.setText("jButton2");
        jButton9.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton9.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton9.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton9MouseClicked(evt);
            }
        });

        jButton10.setText("jButton2");
        jButton10.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton10.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton10.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton10MouseClicked(evt);
            }
        });

        jButton11.setText("jButton2");
        jButton11.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton11.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton11.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton11MouseClicked(evt);
            }
        });

        jButton12.setText("jButton2");
        jButton12.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton12.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton12.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton12MouseClicked(evt);
            }
        });

        jButton13.setText("jButton2");
        jButton13.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton13.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton13.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton13MouseClicked(evt);
            }
        });

        jButton14.setText("jButton2");
        jButton14.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton14.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton14.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton14MouseClicked(evt);
            }
        });

        jButton15.setText("jButton2");
        jButton15.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton15.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton15.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton15MouseClicked(evt);
            }
        });

        jButton16.setText("jButton2");
        jButton16.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton16.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton16.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton16MouseClicked(evt);
            }
        });

        jButton17.setText("jButton2");
        jButton17.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton17.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton17.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton17MouseClicked(evt);
            }
        });

        jButton18.setText("jButton2");
        jButton18.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton18.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton18.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton18MouseClicked(evt);
            }
        });

        jButton19.setText("jButton2");
        jButton19.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton19.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton19.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton19MouseClicked(evt);
            }
        });

        jButton20.setText("jButton2");
        jButton20.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton20.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton20.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton20MouseClicked(evt);
            }
        });

        jButton21.setText("jButton2");
        jButton21.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton21.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton21.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton21MouseClicked(evt);
            }
        });

        jButton22.setText("jButton2");
        jButton22.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton22.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton22.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton22MouseClicked(evt);
            }
        });

        jButton23.setText("jButton2");
        jButton23.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton23.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton23.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton23MouseClicked(evt);
            }
        });

        jButton24.setText("jButton2");
        jButton24.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton24.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton24.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton24MouseClicked(evt);
            }
        });

        jButton25.setText("jButton2");
        jButton25.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton25.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton25.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton25MouseClicked(evt);
            }
        });

        jButton26.setText("jButton2");
        jButton26.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton26.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton26.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton26MouseClicked(evt);
            }
        });

        jButton27.setText("jButton2");
        jButton27.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton27.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton27.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton27MouseClicked(evt);
            }
        });

        jButton28.setText("jButton2");
        jButton28.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton28.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton28.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton28MouseClicked(evt);
            }
        });

        jButton29.setText("jButton2");
        jButton29.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton29.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton29.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton29MouseClicked(evt);
            }
        });

        jButton30.setText("jButton2");
        jButton30.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton30.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton30.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton30MouseClicked(evt);
            }
        });

        jButton31.setText("jButton2");
        jButton31.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton31.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton31.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton31MouseClicked(evt);
            }
        });

        jButton32.setText("jButton2");
        jButton32.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton32.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton32.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton32MouseClicked(evt);
            }
        });

        jButton33.setText("jButton2");
        jButton33.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton33.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton33.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton33MouseClicked(evt);
            }
        });

        jButton34.setText("jButton2");
        jButton34.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton34.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton34.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton34MouseClicked(evt);
            }
        });

        jButton35.setText("jButton2");
        jButton35.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton35.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton35.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton35MouseClicked(evt);
            }
        });

        jButton36.setText("jButton2");
        jButton36.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton36.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton36.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton36MouseClicked(evt);
            }
        });

        jButton37.setText("jButton2");
        jButton37.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton37.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton37.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton37MouseClicked(evt);
            }
        });

        jButton38.setText("jButton2");
        jButton38.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton38.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton38.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton38MouseClicked(evt);
            }
        });

        jButton39.setText("jButton2");
        jButton39.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton39.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton39.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton39MouseClicked(evt);
            }
        });

        jButton40.setText("jButton2");
        jButton40.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton40.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton40.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton40MouseClicked(evt);
            }
        });

        jButton41.setText("jButton2");
        jButton41.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton41.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton41.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton41MouseClicked(evt);
            }
        });

        jButton42.setText("jButton2");
        jButton42.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton42.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton42.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton42MouseClicked(evt);
            }
        });

        jButton43.setText("Connect");
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });

        jTextField1.setText("Name");

        oyundurumu.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(181, 181, 181)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(oyundurumu, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(239, 239, 239)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(jButton43)))
                .addGap(157, 732, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(oyundurumu))
                .addGap(18, 18, 18)
                .addComponent(jButton43)
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(397, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    public void oyun_tahtasini_guncelle(){
        //1 bizim oynadığımız renkleri ifade ediyor ve sarı rengi gösteriyor, 
        //2 rakibin rengini ifade ediyor ve kırmızıyı işaret ediyor..
        //var olan matrisi ekrana gösterelim
        //bu matris ya bizim oynadığımız matristir ya da rakibin oynayıp bize gönderdiği matristir
        //farketmez zaten hepsi tek bir matrise kaydoluyor.
        
        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button[i].length; j++) {
                if(matris[i][j]==1)
                    button[i][j].setIcon(yellow);
                else if(matris[i][j] == 2)
                     button[i][j].setIcon(red);
                else
                     button[i][j].setIcon(null);
            }
        }
        
//     
        
    }
    
    public void Reset()
    {
        if (Client.socket!=null) {
            if (Client.socket.isConnected())
            {
                Client.Stop();
            }
        }
    
    }
    
     public void matrisi_cozumle(String matrisStringHali){
         
         for (int q = 0; q < matrisStringHali.length(); q+=3) {
             int renk = Character.getNumericValue(matrisStringHali.charAt(q));
             int x = Character.getNumericValue(matrisStringHali.charAt(q+1));
             int y = Character.getNumericValue(matrisStringHali.charAt(q+2));
             
             matris[x][y]=renk;
         }
         
         for (int i = 0; i < button.length; i++) {
             for (int j = 0; j < button[i].length; j++) {
                 if(matris[i][j]==1){
                      button[i][j].setIcon(yellow);
                 }
                 else if(matris[i][j]==2){
                      button[i][j].setIcon(red);
                 }
                 else{
                     button[i][j].setIcon(null);
                 }
             }
         }
       
   
         
    }
    
    public void matrisi_rakibe_gonder(){
        //matrisi rakibe gönderirken şöyle bir durumn var. bize göre
        //0:boş, 1:biz, 2:rakip
        //o zaman rakibe gönderirken biz rakibin rakibi olduğumuzdan 
        //1leri 2 ye, 2leri 1e çevirmemiz lazım ki doğru olsun ?

        //zaten o da bize matrisi bize göre dönüştürerek göndermiş oluyor bu sayede
        String matrisinStringHali = "";

        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button[i].length; j++) {
                if (matris[i][j] == 1) {
                    matris[i][j] = 2;
                    matrisinStringHali+="2"+i+j;
                } else if (matris[i][j] == 2) {
                    matris[i][j] = 1;
                    matrisinStringHali+="1"+i+j;
                }
            }

        }

        Message msg = new Message(Message.Message_Type.MatrisGonder);
        msg.content = matrisinStringHali;
        Client.Send(msg); //gönderdik oh.
    }
    
    public void sirayi_rakibe_devret(){
        Message msg = new Message(Message.Message_Type.Start);
        msg.content = "basla";
        Client.Send(msg);
        //sırayı rakibe devrettiysek, kendi sıramızı da "bekle" olarak güncellememiz lazım ki
        //bizim client bizim bekliyor olduğumuzu anlayabilsin.
        durum = "bekle";
    }
    
    public void oyun_bitti_mi_kontrol_et(){
        System.out.println("geldim buraya");
        //burada gerekli kontrolleri yapcan işte oyun bittiyse rakibe bitti mesajını göndericem
        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button[i].length; j++) {
                // sağ 4 kutucuk kontrolü i sabit j+1 ; jler degiseceği icin button[i]
                if( j<=3 && j<=button[i].length && button[i][j].getIcon() == red &&  button[i][j+1].getIcon() == red && button[i][j+2].getIcon() == red &&j+1<=button[i].length && button[i][j+3].getIcon() == red && j+2<=button[i].length && j+3<=button[i].length ){
//                    if(button[i][j].getIcon() == red && button[i][j+1].getIcon() == red && button[i][j+2].getIcon() == red && button[i][j+3].getIcon() == red  ){
//                        System.out.println("1.kural 1. if");
//                    }
//                    else{
//                        System.out.println("gg");
//                    }
                    System.out.println("1. kural");
                }
//                else if(j>=1 && j-1<=button[i].length && j<=button[i].length && j+1<=button[i].length && j+2<=button[i].length){
//                     if(button[i][j-1].getIcon() == red && button[i][j].getIcon() == red && button[i][j+1].getIcon() == red && button[i][j+2].getIcon() == red  ){
//                        System.out.println("1.kural 2. if");
//                    }
//                }
//                else if(j>=2 &&j-2<=button[i].length && j-1<=button[i].length && j<=button[i].length && j<=button[i].length){
//                     if(button[i][j].getIcon() == red && button[i][j+1].getIcon() == red && button[i][j+2].getIcon() == red && button[i][j+3].getIcon() == red  ){
//                        System.out.println("1.kural 1. if");
//                    }
//                }
//                else if(j>=3 &&j-3<=button[i].length && j-2<=button[i].length && j-1<=button[i].length && j-2<=button[i].length){
//                     if(button[i][j].getIcon() == red && button[i][j+1].getIcon() == red && button[i][j+2].getIcon() == red && button[i][j+3].getIcon() == red  ){
//                        System.out.println("1.kural 1. if");
//                    }
//                }
                
                // yukardan aşağıya 4 kutucuk i+1 , j sabit . i ler degiseceği icin button
                else if(i<=2 && i<=button.length && button[i][j].getIcon() == red && i+1<=button.length && button[i+1][j].getIcon() == red && i+2<=button.length && button[i+2][j].getIcon() == red && i+3<=button.length ){
                   
                        System.out.println("2.kural 1. if");
                    
                }
//                else if(i>=1 &&i-1<=button[i].length && i<=button[i].length && i+1<=button[i].length && i+2<=button[i].length){
//                    
//                }
//                else if(i>=2 && i-2<=button[i].length && i-1<=button[i].length && i<=button[i].length && i+1<=button[i].length){
//                    
//                }
//                else if(i>=3 && i-3<=button[i].length && i-2<=button[i].length && i-1<=button[i].length && i<=button[i].length){
//                    
//                }
                
                // sağ üst köşeden sol alt köşeye doğru i+1,j-1 
                else if((i <= 2  && j >= 2 ) && (i<=button.length && j<=button[i].length ) && (i+1<=button.length && j-1<=button[i].length ) && (i+2<=button.length && j-2<=button[i].length ) && (i+3<=button.length && j-3<=button[i].length )){
                    if(button[i][j].getIcon() == red && button[i+1][j-1].getIcon() == red && button[i+2][j-2].getIcon() == red && button[i+3][j-3].getIcon() == red  ){
                        System.out.println("3.kural 1. if");
                    }
                }
//                else if((i >= 1 && j <= 2 ) && (i-1<=button.length && j+1<=button[i].length ) && (i<=button.length && j<=button[i].length ) && (i+1<=button.length && j-1<=button[i].length ) && (i+2<=button.length && j-2<=button[i].length )){
//                    
//                }
//                else if((i <= 2 && j <= 1 ) && (i-2<=button.length && j+2<=button[i].length ) && (i-1<=button.length && j+1<=button[i].length ) && (i<=button.length && j<=button[i].length ) && (i+1<=button.length && j-1<=button[i].length )){
//                    
//                }
//                else if((i <= 3 && j <= 0 ) && (i-3<=button.length && j+3<=button[i].length ) && (i-2<=button.length && j+2<=button[i].length ) && (i-1<=button.length && j+1<=button[i].length ) && (i<=button.length && j<=button[i].length )){
//                    
//                }
                
                // sol üst köşeden sağ alt köşeye doğru
                
                else if((i <= 2 && j <= 2 ) &&(i<=button.length && j<=button[i].length ) && (i+1<=button.length && j+1<=button[i].length ) && (i+2<=button.length && j+2<=button[i].length ) && (i+3<=button.length && j+3<=button[i].length )){
                    if(button[i][j].getIcon() == red && button[i+1][j+1].getIcon() == red && button[i+2][j+2].getIcon() == red && button[i+3][j+3].getIcon() == red  ){
                        System.out.println("4.kural 1. if");
                    }
                }
//                else if((i <= 1 && j <= 1 ) &&(i-1<=button.length && j-1<=button[i].length ) && (i<=button.length && j<=button[i].length ) && (i+1<=button.length && j+1<=button[i].length ) && (i+2<=button.length && j+2<=button[i].length )){
//                    
//                }
//                else if((i <= 2 && j <= 2 ) &&(i-2<=button.length && j-2<=button[i].length ) && (i-1<=button.length && j-1<=button[i].length ) && (i<=button.length && j<=button[i].length ) && (i+1<=button.length && j+1<=button[i].length )){
//                    
//                }
//                else if((i <= 3 && j <= 3 ) && (i-3 <=button.length && j-3<=button[i].length ) && (i-2<=button.length && j-2<=button[i].length ) && (i-1<=button.length && j-1<=button[i].length ) && (i<=button.length && j<=button[i].length )){
//                    
//                }
                
                
                
            }
       }
        //Message msg = new Message(Message.Message_Type.Text);
        //msg.content = "bitti";
        //Client.Send(msg);
        //mesajdan sonrada bizim ekrana oyunun bittiğini felan yazarım.
    }
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
      // button[0][0].setIcon(red);
//        if(button[4].getIcon() == red){
//            button[1].setText("sa");
//        }
         if(matris[0][0]== 0){
            matris[0][0]=1;
            
            oyun_tahtasini_guncelle();
           
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        // TODO add your handling code here:
     //  button[0][4].setIcon(red);
        if(matris[0][4]== 0){
            matris[0][4]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
//        button[0][2].setIcon(red);
//         button[0][0].setIcon(red);
//        if(button[4].getIcon() == red){
//            button[1].setText("sa");
//        }
         if(matris[0][2]== 0){
            matris[0][2]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
    //    button[0][3].setIcon(red);
        if(matris[0][3]== 0){
            matris[0][3]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
       // button[0][1].setIcon(red);
//         if(button[0][1].getIcon() == null){
//            button[0][1].setIcon(red);
//            oyun_tahtasini_guncelle();
//            matrisi_rakibe_gonder();
//            sirayi_rakibe_devret();
//            oyun_bitti_mi_kontrol_et();
//         }
        if(matris[0][1]== 0){
            matris[0][1]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
     //   button[0][5].setIcon(red);   
         if(matris[0][5]== 0){
            matris[0][5]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
        // TODO add your handling code here:
        //button[0][6].setIcon(red);
        if(matris[0][6]== 0){
            matris[0][6]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton7MouseClicked

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked
        // TODO add your handling code here:
       // button[1][0].setIcon(red);
        if(matris[1][0]== 0){
            matris[1][0]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton8MouseClicked

    private void jButton9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseClicked
        // TODO add your handling code here:
       // button[1][1].setIcon(red); 
        if(matris[1][1]== 0){
            matris[1][1]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
        
    }//GEN-LAST:event_jButton9MouseClicked

    private void jButton10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseClicked
        // TODO add your handling code here:
       if(matris[1][2]== 0){
            matris[1][2]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton10MouseClicked

    private void jButton11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseClicked
        // TODO add your handling code here:
        //button[1][3].setIcon(red);
        if(matris[1][3]== 0){
            matris[1][3]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton11MouseClicked

    private void jButton12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton12MouseClicked
        // TODO add your handling code here:
       // button[1][4].setIcon(red);
        if(matris[1][4]== 0){
            matris[1][4]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton12MouseClicked

    private void jButton13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton13MouseClicked
        // TODO add your handling code here:
       // button[1][5].setIcon(red);
       if(matris[1][5]== 0){
            matris[1][5]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton13MouseClicked

    private void jButton14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton14MouseClicked
        // TODO add your handling code here:
       // button[1][6].setIcon(red);
       if(matris[1][6]== 0){
            matris[1][6]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
         
    }//GEN-LAST:event_jButton14MouseClicked

    private void jButton15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton15MouseClicked
        // TODO add your handling code here:
        if(matris[2][0]== 0){
            matris[2][0]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
       // button[2][0].setIcon(red);
    }//GEN-LAST:event_jButton15MouseClicked

    private void jButton16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton16MouseClicked
        // TODO add your handling code here:
        //button[2][1].setIcon(red);
        if(matris[2][1]== 0){
            matris[2][1]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton16MouseClicked

    private void jButton17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton17MouseClicked
        // TODO add your handling code here:
        //button[2][2].setIcon(red);
        if(matris[2][2]== 0){
            matris[2][2]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton17MouseClicked

    private void jButton18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton18MouseClicked
        // TODO add your handling code here:
        if(matris[2][3]== 0){
            matris[2][3]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton18MouseClicked

    private void jButton19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton19MouseClicked
        // TODO add your handling code here:
        if(matris[2][4]== 0){
            matris[2][4]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton19MouseClicked

    private void jButton20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton20MouseClicked
        // TODO add your handling code here:
       if(matris[2][5]== 0){
            matris[2][5]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton20MouseClicked

    private void jButton21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton21MouseClicked
        // TODO add your handling code here:
       if(matris[2][6]== 0){
            matris[2][6]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton21MouseClicked

    private void jButton22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton22MouseClicked
        // TODO add your handling code here:
        if(matris[3][0]== 0){
            matris[3][0]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton22MouseClicked

    private void jButton23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton23MouseClicked
        // TODO add your handling code here:
        if(matris[3][1]== 0){
            matris[3][1]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton23MouseClicked

    private void jButton24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton24MouseClicked
        // TODO add your handling code here:
        if(matris[3][2]== 0){
            matris[3][2]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton24MouseClicked

    private void jButton25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton25MouseClicked
        // TODO add your handling code here:
        if(matris[3][3]== 0){
            matris[3][3]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton25MouseClicked

    private void jButton26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton26MouseClicked
        // TODO add your handling code here:
        if(matris[3][4]== 0){
            matris[3][4]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton26MouseClicked

    private void jButton27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton27MouseClicked
        // TODO add your handling code here:

       if(matris[3][5]== 0){
            matris[3][5]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton27MouseClicked

    private void jButton28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton28MouseClicked
        // TODO add your handling code here:
        if(matris[3][6]== 0){
            matris[3][6]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton28MouseClicked

    private void jButton29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton29MouseClicked
        // TODO add your handling code here:
        if(matris[4][0]== 0){
            matris[4][0]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton29MouseClicked

    private void jButton30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton30MouseClicked
        // TODO add your handling code here:
       if(matris[4][1]== 0){
            matris[4][1]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton30MouseClicked

    private void jButton31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton31MouseClicked
        // TODO add your handling code here:
        if(matris[4][2]== 0){
            matris[4][2]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton31MouseClicked

    private void jButton32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton32MouseClicked
        // TODO add your handling code here:
       if(matris[4][3]== 0){
            matris[4][3]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton32MouseClicked

    private void jButton33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton33MouseClicked
        // TODO add your handling code here:
        if(matris[4][4]== 0){
            matris[4][4]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton33MouseClicked

    private void jButton34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton34MouseClicked
        // TODO add your handling code here:
       if(matris[4][5]== 0){
            matris[4][5]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton34MouseClicked

    private void jButton35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton35MouseClicked
        // TODO add your handling code here:
        if(matris[4][6]== 0){
            matris[4][6]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton35MouseClicked

    private void jButton36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton36MouseClicked
        // TODO add your handling code here:
        if(matris[5][0]== 0){
            matris[5][0]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton36MouseClicked

    private void jButton37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton37MouseClicked
        // TODO add your handling code here:
        if(matris[5][1]== 0){
            matris[5][1]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton37MouseClicked

    private void jButton38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton38MouseClicked
        // TODO add your handling code here:
       if(matris[5][2]== 0){
            matris[5][2]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton38MouseClicked

    private void jButton39MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton39MouseClicked
        // TODO add your handling code here:
       if(matris[5][3]== 0){
            matris[5][3]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton39MouseClicked

    private void jButton40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton40MouseClicked
        // TODO add your handling code here:
       if(matris[5][4]== 0){
            matris[5][4]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton40MouseClicked

    private void jButton41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton41MouseClicked
        // TODO add your handling code here:
        if(matris[5][5]== 0){
            matris[5][5]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton41MouseClicked

    private void jButton42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton42MouseClicked
        // TODO add your handling code here:
       if(matris[5][6]== 0){
            matris[5][6]= 1;
           
            oyun_tahtasini_guncelle();
            matrisi_rakibe_gonder();
            sirayi_rakibe_devret();
            oyun_bitti_mi_kontrol_et();
         }
    }//GEN-LAST:event_jButton42MouseClicked

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        // TODO add your handling code here:
        Client.Start("127.0.0.1", 2000);
        
    }//GEN-LAST:event_jButton43ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
         Client.Stop();
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
         for(int i=0;i<button.length;i++)
            for(int j=0;j<button[i].length;j++)
                button[i][j].setIcon(null);
        oyun_tahtasini_guncelle();
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Game().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton10;
    public javax.swing.JButton jButton11;
    public javax.swing.JButton jButton12;
    public javax.swing.JButton jButton13;
    public javax.swing.JButton jButton14;
    public javax.swing.JButton jButton15;
    public javax.swing.JButton jButton16;
    public javax.swing.JButton jButton17;
    public javax.swing.JButton jButton18;
    public javax.swing.JButton jButton19;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton20;
    public javax.swing.JButton jButton21;
    public javax.swing.JButton jButton22;
    public javax.swing.JButton jButton23;
    public javax.swing.JButton jButton24;
    public javax.swing.JButton jButton25;
    public javax.swing.JButton jButton26;
    public javax.swing.JButton jButton27;
    public javax.swing.JButton jButton28;
    public javax.swing.JButton jButton29;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton30;
    public javax.swing.JButton jButton31;
    public javax.swing.JButton jButton32;
    public javax.swing.JButton jButton33;
    public javax.swing.JButton jButton34;
    public javax.swing.JButton jButton35;
    public javax.swing.JButton jButton36;
    public javax.swing.JButton jButton37;
    public javax.swing.JButton jButton38;
    public javax.swing.JButton jButton39;
    public javax.swing.JButton jButton4;
    public javax.swing.JButton jButton40;
    public javax.swing.JButton jButton41;
    public javax.swing.JButton jButton42;
    public static javax.swing.JButton jButton43;
    public javax.swing.JButton jButton5;
    public javax.swing.JButton jButton6;
    public javax.swing.JButton jButton7;
    public javax.swing.JButton jButton8;
    public javax.swing.JButton jButton9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel oyundurumu;
    // End of variables declaration//GEN-END:variables
}

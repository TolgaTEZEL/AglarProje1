/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import con4client.Client;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

// 
/**
 *
 * @author Tolga
 */
public class Game extends javax.swing.JFrame {
     public static Game ThisGame;
    
    public static String name ="";
    public Thread tmr_slider;
    public int RivalSelection = -1;
    public int myselection = -1;
        Random rand;
    JButton[][] button = new JButton[6][7];
    public int[][] matris = new int[6][7];
    public String durum = "";
     public String matrisVerisi="";
    public  int status = 0 ;
    ImageIcon icons[];
  
 
    /**
     * Creates new form Gameboard
     */
    public Game() throws IOException {
        initComponents();
        
  
         
        ThisGame = this;
        this.getContentPane().setBackground(Color.blue);
        ThisGame.setSize(1000, 900);
        icons = new ImageIcon[3];
        icons[0] = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/images/kırmızı.png"))).getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
     icons[1] = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/images/sarı.png"))).getImage().getScaledInstance(70,70, Image.SCALE_DEFAULT));
      icons[2] = new ImageIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/images/tolgacon.png"))).getImage().getScaledInstance(300,100, Image.SCALE_DEFAULT));
      jLabel2.setIcon(icons[2]);
      jLabel2.setText(null);
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
        temizle();
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
                        //yani buraya geldiysek matrisimize veri yüklenmiş demektir. matrisi ekrandaki butonlara
                            if(status == 0)
                           oyun_bitti_mi_kontrol_et();
                            
                            
                            status = 1;
                         for (int i = 0; i < button.length; i++) {
                            for (int j = 0; j < button[i].length; j++) {
                                button[i][j].setEnabled(true);
                                
                            }
                        }
                        if(!matrisVerisi.equals("")){
                            
                            matrisi_cozumle(matrisVerisi);
                            matrisVerisi ="";
                            oyun_tahtasini_guncelle();
                            status = 0;
                        }
                    }else if(durum.equals("bekle")){
                        //bekle diye bir mesaj gelmişse kimden geldiği önemsiz, sıra rakipte demektir beklememiz gerekiyor.
                        
                        for (int i = 0; i < button.length; i++) {
                            for (int j = 0; j < button[i].length; j++) {
                                button[i][j].setEnabled(false);
                            }
                        }
                        oyundurumu.setText("Rakip oynuyor");
                    }
                    
                       

                    
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 255, 255));
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
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton2.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton2.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("jButton2");
        jButton3.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton3.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton3.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("jButton2");
        jButton4.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton4.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton4.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("jButton2");
        jButton5.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton5.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton5.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("jButton2");
        jButton6.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton6.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton6.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("jButton2");
        jButton7.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton7.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton7.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("jButton2");
        jButton8.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton8.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton8.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("jButton2");
        jButton9.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton9.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton9.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("jButton2");
        jButton10.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton10.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton10.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("jButton2");
        jButton11.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton11.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton11.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText("jButton2");
        jButton12.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton12.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton12.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText("jButton2");
        jButton13.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton13.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton13.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setText("jButton2");
        jButton14.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton14.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton14.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setText("jButton2");
        jButton15.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton15.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton15.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setText("jButton2");
        jButton16.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton16.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton16.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setText("jButton2");
        jButton17.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton17.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton17.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setText("jButton2");
        jButton18.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton18.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton18.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton19.setText("jButton2");
        jButton19.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton19.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton19.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setText("jButton2");
        jButton20.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton20.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton20.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton21.setText("jButton2");
        jButton21.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton21.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton21.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton22.setText("jButton2");
        jButton22.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton22.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton22.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton23.setText("jButton2");
        jButton23.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton23.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton23.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton24.setText("jButton2");
        jButton24.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton24.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton24.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jButton25.setText("jButton2");
        jButton25.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton25.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton25.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jButton26.setText("jButton2");
        jButton26.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton26.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton26.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jButton27.setText("jButton2");
        jButton27.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton27.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton27.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jButton28.setText("jButton2");
        jButton28.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton28.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton28.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        jButton29.setText("jButton2");
        jButton29.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton29.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton29.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jButton30.setText("jButton2");
        jButton30.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton30.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton30.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        jButton31.setText("jButton2");
        jButton31.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton31.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton31.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jButton32.setText("jButton2");
        jButton32.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton32.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton32.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        jButton33.setText("jButton2");
        jButton33.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton33.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton33.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        jButton34.setText("jButton2");
        jButton34.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton34.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton34.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        jButton35.setText("jButton2");
        jButton35.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton35.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton35.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        jButton36.setText("jButton2");
        jButton36.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton36.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton36.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });

        jButton37.setText("jButton2");
        jButton37.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton37.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton37.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });

        jButton38.setText("jButton2");
        jButton38.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton38.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton38.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        jButton39.setText("jButton2");
        jButton39.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton39.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton39.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        jButton40.setText("jButton2");
        jButton40.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton40.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton40.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });

        jButton41.setText("jButton2");
        jButton41.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton41.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton41.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });

        jButton42.setText("jButton2");
        jButton42.setMaximumSize(new java.awt.Dimension(100, 100));
        jButton42.setMinimumSize(new java.awt.Dimension(100, 100));
        jButton42.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });

        jButton43.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jButton43.setText("Connect");
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jTextField1.setText("Name");

        oyundurumu.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        oyundurumu.setForeground(new java.awt.Color(255, 51, 0));
        oyundurumu.setText("Oyun Durumu");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 0));
        jLabel1.setText("Oyuncu Adı :");

        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton43))
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(oyundurumu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(321, 321, 321)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(854, 854, 854))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oyundurumu)
                    .addComponent(jButton43))
                .addGap(18, 18, 18)
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
                .addContainerGap(386, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public void temizle(){
        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button[i].length; j++) {
                button[i][j].setText(null);
            }
        }
    }
    
    public void oyun_tahtasini_guncelle(){
        //1 bizim oynadığımız renkleri ifade ediyor ve sarı rengi gösteriyor, 
        //2 rakibin rengini ifade ediyor ve kırmızıyı işaret ediyor..
        //var olan matrisi ekrana gösterelim
        //bu matris ya bizim oynadığımız matristir ya da rakibin oynayıp bize gönderdiği matristir
        //farketmez zaten hepsi tek bir matrise kaydoluyor.
      
           for (int i = 0; i < button.length; i++) { 
            for (int j = 0; j < button[i].length; j++) {
                if(matris[i][j]==1)
                    button[i][j].setIcon(icons[1]);
                else if(matris[i][j] == 2)
                     button[i][j].setIcon(icons[0]);
                else
                     button[i][j].setIcon(null);
            }
        }
        
  
        
    }
    
    public static void Reset()
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
                      button[i][j].setIcon(icons[1]);
                 }
                 else if(matris[i][j]==2){
                      button[i][j].setIcon(icons[0]);
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
        //1leri 2 ye, 2leri 1e çevirmemiz lazım ki doğru olsun 

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
        Client.Send(msg); //gönderdik 
    }
    
    public void sirayi_rakibe_devret(){
        Message msg = new Message(Message.Message_Type.Start);
        msg.content = "basla";
        Client.Send(msg);
        //sırayı rakibe devrettiysek, kendi sıramızı da "bekle" olarak güncellememiz lazım ki
        //bizim client bizim bekliyor olduğumuzu anlayabilsin.
        durum = "bekle";
    }
 
    
    public  void oyun_bitti_mi_kontrol_et(){
       
        label:
        for (int i = 0; i < button.length ; i++) {
            for (int j = 0; j < button[i].length; j++) {
                // sağ 4 kutucuk kontrolü i sabit j+1 ; jler degiseceği icin button[i]
                if( j<=3 && j<=button[i].length && matris[i][j] == 1 &&  matris[i][j+1] == 1 && matris[i][j+2]== 1 && matris[i][j+3]== 1  ){

                    kapat(name,"kazandınız , Yeni oyun isterseniz Lütfen uygulamayı tekrar başlatınız.");
                   break label;
                  
                }
                else if(j<=3 && j<=button[i].length && matris[i][j] == 2 &&  matris[i][j+1] == 2 && matris[i][j+2]== 2 && matris[i][j+3]== 2  ){
                    
                             kapat(name," kaybettiniz. , Yeni oyun isterseniz Lütfen uygulamayı tekrar başlatınız.");
                             break label;
                }
                
                // yukardan aşağıya 4 kutucuk i+1 , j sabit . i ler degiseceği icin button
                else if( i<=2 && matris[i][j] == 1 && matris[i+1][j]== 1  && matris[i+2][j] == 1  && matris[i+3][j] == 1 ){
                   
                       
                             kapat(name," kazandınız , Yeni oyun isterseniz Lütfen uygulamayı tekrar başlatınız.");
                             break label;
                       
                }
              
                else if( i<=2 && matris[i][j] == 2 && matris[i+1][j]== 2  && matris[i+2][j] == 2  && matris[i+3][j] == 2 ){
                   
                       
                             kapat(name," kaybettiniz , Yeni oyun isterseniz Lütfen uygulamayı tekrar başlatınız.");
                             break label;
                       
                }
                
                // sağ üst köşeden sol alt köşeye doğru i+1,j-1 
                else if((i <= 2  && j >= 2 ) ){ // && (i<=button.length && j<=button[i].length ) && (i+1<=button.length && j-1<=button[i].length ) && (i+2<=button.length && j-2<=button[i].length ) && (i+3<=button.length && j-3<=button[i].length )
                    if(matris[i][j] == 1 && matris[i+1][j-1] == 1 && matris[i+2][j-2] == 1 && matris[i+3][j-3] == 1  ){
                        
                       kapat(name," kazandınız , Yeni oyun isterseniz Lütfen uygulamayı tekrar başlatınız.");
                       
                         break label;
                    }
                    else if(matris[i][j] == 2 && matris[i+1][j-1] == 2 && matris[i+2][j-2] == 2 && matris[i+3][j-3] == 2){
                        kapat(name," kaybettiniz , Yeni oyun isterseniz Lütfen uygulamayı tekrar başlatınız..");
                        
                         break label;
                         
                    }
                }

                
                // sol üst köşeden sağ alt köşeye doğru
                
                else if((i <= 2 && j <= 2 ) ){  //&&(i<=button.length && j<=button[i].length ) && (i+1<=button.length && j+1<=button[i].length ) && (i+2<=button.length && j+2<=button[i].length ) && (i+3<=button.length && j+3<=button[i].length )
                    if(matris[i][j] == 1 && matris[i+1][j+1] == 1 && matris[i+2][j+2] == 1 && matris[i+3][j+3] == 1 ){
                         kapat(name," kazandınız , Yeni oyun isterseniz Lütfen uygulamayı tekrar başlatınız.");
                      
                         break label;
                        
                        //name = jLabel1.getText();
                        //kapat(name);
                    }
                    else if(matris[i][j] == 2 && matris[i+1][j+1] == 2 && matris[i+2][j+2] == 2 && matris[i+3][j+3] == 2 ){
                         kapat(name," kaybettiniz , Yeni oyun isterseniz Lütfen uygulamayı tekrar başlatınız.");
                         
                         break label;
                        
                    }
                }

            

                
                
            }
       }
     
    }
    
    public void kapat(String name,String oyunsonu){
        JOptionPane.showMessageDialog(null, name +"" + oyunsonu, "Sonuç" , JOptionPane.INFORMATION_MESSAGE);
        
        
    }
    
    public void kontrol(int x,int y){
       
            
        
    int q = button.length-1;
    for (int i = 0; i < button.length; i++) {
        for (int j = 0; j < button[i].length; j++) {
            while(q>=0){
                if(matris[q][y] == 0){
                    matris[q][y] = 1;
                    q=-1;
                    
                }
                else{
                    q--;
                }
                
            }
        }
    }
    
}
    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        // TODO add your handling code here:
        Client.Start("127.0.0.1", 2000);
        jLabel1.setText( jLabel1.getText() +""+ jTextField1.getText());
        
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(matris[0][0] == 0){
            
       
          kontrol(0,0); 
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
         if(matris[0][1] == 0){
            
       
          kontrol(0,1); 
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
         if(matris[0][2] == 0){
            
       
          kontrol(0,2);
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
         if(matris[0][3] == 0){
            
       
          kontrol(0,3); 
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
         if(matris[0][4] == 0){
            
       
          kontrol(0,4); 
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
         if(matris[0][5] == 0){
            
       
          kontrol(0,5); 
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
         if(matris[0][6] == 0){
            
       
          kontrol(0,6); 
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
         if(matris[1][0] == 0){
            
       
          kontrol(1,0);
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
           if(matris[1][1] == 0){
            
       
          kontrol(1,1);
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
           if(matris[1][2] == 0){
             kontrol(1,2); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
         if(matris[1][3] == 0){
             kontrol(1,3); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
         if(matris[1][4] == 0){
             kontrol(1,4); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
         if(matris[1][5] == 0){
             kontrol(1,5); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
         if(matris[1][6] == 0){
             kontrol(1,6);
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
         if(matris[2][0] == 0){
             kontrol(2,0);
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        if(matris[2][1] == 0){
             kontrol(2,1); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        if(matris[2][2] == 0){
             kontrol(2,2);
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        if(matris[2][3] == 0){
             kontrol(2,3); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        if(matris[2][4] == 0){
             kontrol(2,4);
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        if(matris[2][5] == 0){
             kontrol(2,5); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        if(matris[2][6] == 0){
             kontrol(2,6); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
        if(matris[3][0] == 0){
             kontrol(3,0);
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here:
         if(matris[3][1] == 0){
             kontrol(3,1); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
         if(matris[3][2] == 0){
             kontrol(3,2); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
         if(matris[3][3] == 0){
             kontrol(3,3); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:
         if(matris[3][4] == 0){
             kontrol(3,4); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
         if(matris[3][5] == 0){
             kontrol(3,5); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        // TODO add your handling code here:
         if(matris[3][6] == 0){
             kontrol(3,6);
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:
         if(matris[4][0] == 0){
             kontrol(4,0); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        // TODO add your handling code here:
         if(matris[4][1] == 0){
             kontrol(4,1); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:
         if(matris[4][2] == 0){
             kontrol(4,2); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        // TODO add your handling code here:
         if(matris[4][3] == 0){
             kontrol(4,3); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        // TODO add your handling code here:
         if(matris[4][4] == 0){
             kontrol(4,4);
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        // TODO add your handling code here:
         if(matris[4][5] == 0){
             kontrol(4,5); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        // TODO add your handling code here:
         if(matris[4][6] == 0){
             kontrol(4,6); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton35ActionPerformed

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        // TODO add your handling code here:
         if(matris[5][0] == 0){
             kontrol(5,0); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton36ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        // TODO add your handling code here:
         if(matris[5][1] == 0){
             kontrol(5,1); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton37ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        // TODO add your handling code here:
         if(matris[5][2] == 0){
             kontrol(5,2); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        // TODO add your handling code here:
         if(matris[5][3] == 0){
             kontrol(5,3); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        // TODO add your handling code here:
         if(matris[5][4] == 0){
             kontrol(5,4);
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton40ActionPerformed

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        // TODO add your handling code here:
         if(matris[5][5] == 0){
             kontrol(5,5); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton41ActionPerformed

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        // TODO add your handling code here:
         if(matris[5][6] == 0){
             kontrol(5,6); 
       
         
             
            oyun_tahtasini_guncelle();
              oyun_bitti_mi_kontrol_et();
            matrisi_rakibe_gonder();
          
            sirayi_rakibe_devret();
             }
    }//GEN-LAST:event_jButton42ActionPerformed

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
                try {
                    new Game().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
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
    public static javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    public static javax.swing.JLabel oyundurumu;
    // End of variables declaration//GEN-END:variables
}

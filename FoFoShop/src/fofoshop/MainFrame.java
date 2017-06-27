/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fofoshop;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class MainFrame extends JFrame implements ActionListener
{
    JFrame mainWin, scaleWin;
    
    JMenuBar mainMenuBar;
    JMenu fileMenu, fileSubMenu, editMenu;
    JMenuItem fItem1, fItem2, eItem1, eItem2;
    BufferedImage currentimg;
    JLabel imgLabel;
    JPanel imgPanel;
    ImageIcon image;
    
    int imgWidth, imgHeight, frameWidth, frameHeight, scaleInput;
    double scale;
    String absoPath;
    
    ImgMaster im01;
    
    public MainFrame()
    {
        
    }
    
    public void createMFrame()
    {
        im01 = new ImgMaster();
        mainWin = new JFrame("Image Manipulation");
        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        mainWin.setSize(600, 600);
        mainWin.setResizable(false);
        mainWin.setLocationRelativeTo(null);
        mainWin.setVisible(true);

        mainMenuBar();
        imgPanel = new JPanel(new BorderLayout());
    }
    
    public final void mainMenuBar()
    {
        mainMenuBar = new JMenuBar();
        
        //First Menu Item//
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_A);
        fileMenu.getAccessibleContext().setAccessibleDescription("File Navigation");
        mainMenuBar.add(fileMenu);         
            //File Menu 1st Item//
            fItem1 = new JMenuItem("Open", KeyEvent.VK_T);
            fItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
            fItem1.getAccessibleContext().setAccessibleDescription("Open Image");
            //Fill In Action Listener With Method Calls When Completed//
            fItem1.addActionListener(this);
            fileMenu.add(fItem1);           
            //File Menu 1st Item//
            fItem2 = new JMenuItem("Recent", KeyEvent.VK_T);
            fItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
            fItem2.getAccessibleContext().setAccessibleDescription("Recent Image History");
            //Fill In Action Listener With Method Calls When Completed//
            fItem2.addActionListener(this);
            fileMenu.add(fItem2);
        //Second Menu Item//
        editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_A);
        editMenu.getAccessibleContext().setAccessibleDescription("Edit Navigation");
        mainMenuBar.add(editMenu);
            //File Menu 1st Item//
            eItem1 = new JMenuItem("Scale", KeyEvent.VK_T);
            eItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
            eItem1.getAccessibleContext().setAccessibleDescription("Open Image");
            //Fill In Action Listener With Method Calls When Completed//
            eItem1.addActionListener(this);
            editMenu.add(eItem1);           
            //File Menu 1st Item//
            eItem2 = new JMenuItem("Convert", KeyEvent.VK_T);
            eItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
            eItem2.getAccessibleContext().setAccessibleDescription("Open Image");
            //Fill In Action Listener With Method Calls When Completed//
            eItem2.addActionListener(this);
            editMenu.add(eItem2);
        //Set Final Window Bar
        mainWin.setJMenuBar(mainMenuBar);
    }
    /*
    /
    */ 
    public void reDrawFrame(BufferedImage buff)
    {
        image = new ImageIcon(buff);
        imgLabel = new JLabel("", image, JLabel.CENTER);
        imgPanel.removeAll();
        imgPanel.add(imgLabel, BorderLayout.CENTER );
        mainWin.getContentPane().add(imgPanel);
        mainWin.setVisible(true);
    }
    public void refreshPanel()
    {
        mainWin.remove(imgPanel);
        mainWin.revalidate();
        mainWin.repaint();
    }
    /*
    
    */
    public void reSizeFrame(BufferedImage buff)
    {
        frameWidth =  buff.getWidth();
        frameHeight = buff.getHeight();
        mainWin.setSize(frameWidth, frameHeight);
    }
    
    public BufferedImage scaleImg(BufferedImage buff)
    {
        BufferedImage b4 = buff;
        imgWidth = b4.getWidth();
        imgHeight = b4.getHeight();
        double scale = scaleWindow();
        
        BufferedImage after = new BufferedImage((int)(imgWidth * scale), (int) (imgHeight * scale), BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(b4, after);
        System.out.println("End of scaleImg method");
        return after;
    }
        
    public double scaleWindow()
    {
       String inputValue = JOptionPane.showInputDialog(mainWin, "Scale Image", "100%");  
       inputValue = inputValue.replaceAll("[^\\d.]", "");
       scaleInput = Integer.parseInt(inputValue);
       return scaleInput / 100.0;
    }
    
    public void displayImg(File imgAbsoPath)
    {
        String imgAP = imgAbsoPath.getAbsolutePath();
        ImageIcon jImgIco = new ImageIcon(imgAP);
        JLabel imgLbl = new JLabel(jImgIco);
    }
    /*
    
    */
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        
        Line2D line = new Line2D.Double(10, 10, 40, 40);
        g2.setColor(Color.blue);
        g2.setStroke(new BasicStroke(10));
        g2.draw(line);
    }
    /*
    
    */
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == fItem1)
        {     
            try {
                currentimg = ImageIO.read(im01.chooseImg());
                reSizeFrame(currentimg);
                reDrawFrame(currentimg);
                
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(e.getSource() == fItem2)
        {
            System.out.println("Recent Button Selected");
        }
        else if(e.getSource() == eItem1)
        {
            currentimg = scaleImg(currentimg);
            reDrawFrame(currentimg);
            reSizeFrame(currentimg);
        }
        else if(e.getSource() == eItem2)
        {
            System.out.println("Convert Button Selected");
        }
        
    }
    /*
    Getter for Frame for other classes
    */
    public JFrame getFrame()
    {
        return mainWin;
    }
     /*
    Getter for Panel for other classes
    */
    public JPanel getPanel()
    {
        return imgPanel;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fofoshop;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
Class containing methods for Image Manipulation. Constructor is empty as this
class is for the organization of all tool methods.
*/
public class ImgMaster 
{
    String imageLocation;  
    JFileChooser openFile;   
    
    MainFrame mf;
    /*
    Empty Constructor for class.
    */
    public ImgMaster()
    {
        mf = new MainFrame();
    }
    /*
    This method is responsible for loading up file explorer for the selection
    of an image to load in the main frame. 
    
    Returns: Chosen file by user.
    */
    public File chooseImg()
    {
       openFile = new JFileChooser();
       FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("Image "
               + "Files", ".png", ".jpg", ".bmp", ".gif");
       openFile.setFileFilter(imgFilter);
       int returnVal = openFile.showOpenDialog(null);
       if (returnVal == JFileChooser.APPROVE_OPTION)
       {
           File selectedImg = openFile.getSelectedFile();
           System.out.println(selectedImg.getAbsolutePath());
           imageLocation = selectedImg.getAbsolutePath();
           return selectedImg;
       }
       else
       {
           //JOptionPane.showMessageDialog(mF.getFrame(), "Error: Invalid Selection");
           return null;
       }
    }
    /*
    Getter method for return the absolute location for the chosen image.
    (IE: ("C:\\Users\\tyler\\desktop\\ChosenImage.jpg)
    
    return: Image location as string.
    */
    public String getAbsoluteLocation()
    {
        return imageLocation;
    }
}



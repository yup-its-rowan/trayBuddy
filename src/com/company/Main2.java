package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
//import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
//import java.io.InputStream;
//import java.net.URI;
//import java.net.URISyntaxException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Main2 {

    public static void main(String[] args)
	{
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        Image image;
        try {
            image = ImageIO.read(new File("images/icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        
        final TrayIcon trayIcon = new TrayIcon(image);
        final SystemTray tray = SystemTray.getSystemTray();
        trayIcon.setImageAutoSize(true);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
           Frame aFrame = new Frame("The cat and the fiddle");
           aFrame.add(new TextField("Text To Display"));
           aFrame.setSize(400, 100);
           aFrame.setVisible(true);
	}
}

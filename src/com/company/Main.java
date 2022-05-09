package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        Image image = ImageIO.read(new File("src/com/company/icon.png"));
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(image);
        final SystemTray tray = SystemTray.getSystemTray();
        trayIcon.setImageAutoSize(true);
        Desktop desktop = Desktop.getDesktop();

        // Create a pop-up menu components
        //MenuItem aboutItem = new MenuItem("About");
        //CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
        //CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
        Menu shortcutsMenu = new Menu("Shortcuts");
            MenuItem emailItem = new MenuItem("Email");
            MenuItem schoolItem = new MenuItem("School");
            MenuItem tf2Item = new MenuItem("TF2");
        MenuItem exitItem = new MenuItem("Exit");

        //mess with menu items
        ActionListener exitListener = e -> tray.remove(trayIcon);
        exitItem.addActionListener(exitListener);

        ActionListener emailListener = e -> {
            try {
                desktop.browse(new URI("https://mail.google.com/mail/u/0/#inbox"));
                desktop.browse(new URI("https://mail.yahoo.com/d/folders/1"));
                desktop.browse(new URI("https://outlook.office365.com/mail/"));
            } catch (IOException | URISyntaxException ioException) {
                ioException.printStackTrace();
            }

        };
        emailItem.addActionListener(emailListener);

        ActionListener schoolListener = e -> {
            try {
                desktop.browse(new URI("https://sakai.unc.edu/portal/site/1002d5b2-6fae-498d-981c-ec89bd586fc2"));
                desktop.browse(new URI("https://www.gradescope.com"));
            } catch (IOException | URISyntaxException ioException) {
                ioException.printStackTrace();
            }
        };
        schoolItem.addActionListener(schoolListener);

        ActionListener tf2Listener = e -> {
            /*
            try {
                Process p = Runtime.getRuntime().exec("D:\\Steam\\steamapps\\common\\Team Fortress 2\\hl2.exe");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
             */
            try {
                desktop.browse(new URI("https://uncletopia.com/servers"));
                desktop.browse(new URI("steam://rungameid/440"));
            } catch (IOException | URISyntaxException ioException) {
                ioException.printStackTrace();
            }
        };
        tf2Item.addActionListener(tf2Listener);

        //Add components to pop-up menu
        //popup.add(aboutItem);
        //popup.addSeparator();
        //popup.add(cb1);
        //popup.add(cb2);
        //popup.addSeparator();
        popup.add(shortcutsMenu);
        shortcutsMenu.add(emailItem);
        shortcutsMenu.add(schoolItem);
        shortcutsMenu.add(tf2Item);
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }
}

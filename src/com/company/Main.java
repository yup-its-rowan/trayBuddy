package com.company;

import javax.imageio.ImageIO;
import javax.sound.midi.MidiDevice;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static com.company.PiBoard.PiBoardSingleton;

public class Main {

    public static void main(String[] args) throws IOException {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        } //"src/com/company/images/icon.png"
        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
        //Image image = ImageIO.read(new File("images/icon.png"));

        InputStream inputStream = Main.class.getResourceAsStream("/icon.png");
        if (inputStream == null){
            return;
        }
        Image image = ImageIO.read(inputStream);
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(image, "traybuddy");
        final SystemTray tray = SystemTray.getSystemTray();
        trayIcon.setImageAutoSize(true);
        Desktop desktop = Desktop.getDesktop();

        //setup for how menu should look
        Menu shortcutsMenu = new Menu("Shortcuts");
            MenuItem emailItem = new MenuItem("Email");
            MenuItem schoolItem = new MenuItem("School");
            MenuItem tf2Item = new MenuItem("TF2");
        Menu midiMenu = new Menu("Midikey");
            Menu midiInputMenu = new Menu("Input");
                MenuItem refreshMidiInputs = new MenuItem("Refresh");
        MenuItem piBoardItem = new MenuItem("PiBoard");
        MenuItem exitItem = new MenuItem("Exit");

        //email listener and stuff
        ActionListener emailListener = e -> {
            try {
                desktop.browse(new URI("https://mail.google.com/mail/u/0/#inbox"));
                desktop.browse(new URI("https://mail.yahoo.com/d/folders/1"));
            } catch (IOException | URISyntaxException ioException) {
                ioException.printStackTrace();
            }

        };
        emailItem.addActionListener(emailListener);

        //tf2 listener and stuff
        ActionListener tf2Listener = e -> {
            try {
                desktop.browse(new URI("https://uncletopia.com/servers"));
                desktop.browse(new URI("steam://rungameid/440"));
            } catch (IOException | URISyntaxException ioException) {
                ioException.printStackTrace();
            }
        };
        tf2Item.addActionListener(tf2Listener);

        //midi input devices listener and stuff
        ActionListener refreshListener = e -> {
            populateMidiInputMenu(midiInputMenu, refreshMidiInputs);
        };
        refreshMidiInputs.addActionListener(refreshListener);

        //pi board opener and stuff
        ActionListener piBoardItemListener = e -> {
            PiBoardSingleton.show();
        };
        piBoardItem.addActionListener(piBoardItemListener);

        //mess with menu items
        ActionListener exitListener = e -> tray.remove(trayIcon);
        exitItem.addActionListener(exitListener);

        shortcutsMenu.add(emailItem);
        shortcutsMenu.add(schoolItem);
        shortcutsMenu.add(tf2Item);

        midiInputMenu.add(refreshMidiInputs);

        midiMenu.add(midiInputMenu);

        popup.add(shortcutsMenu);
        popup.add(midiMenu);
        popup.add(piBoardItem);
        popup.addSeparator();
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }

        //onStartThings
        populateMidiInputMenu(midiInputMenu, refreshMidiInputs);
    }

    public static void populateMidiInputMenu(Menu midiInputMenu, MenuItem refreshMidiInputs) {
        //System.out.println("Refreshing midi input devices");
        MidiDevice.Info[] midiDeviceInfos = MidiKeyboard.MidiKeyboardSingleton.getListOfMidiDevices();
        midiInputMenu.removeAll();
        ArrayList<CheckboxMenuItem> midiInputs = new ArrayList<>();
        for (MidiDevice.Info info : midiDeviceInfos) {
            CheckboxMenuItem midiInput = new CheckboxMenuItem(info.getName());
            midiInputs.add(midiInput);
        }
        for (int i = 0; i < midiInputs.size(); i++) {
            int finalI = i;
            midiInputs.get(i).addItemListener(e -> {
                //System.out.println("Setting midi input device to " + info.getName());
                MidiKeyboard.MidiKeyboardSingleton.setMidiDevice(midiDeviceInfos[finalI]);
                for (int j = 0; j < midiInputs.size(); j++) {
                    if (j != finalI) {
                        midiInputs.get(j).setState(false);
                    }
                }
                midiInputs.get(finalI).setState(true);
            });
            midiInputMenu.add(midiInputs.get(i));
        }
        midiInputMenu.addSeparator();
        midiInputMenu.add(refreshMidiInputs);
    }
}

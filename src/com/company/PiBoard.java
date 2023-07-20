package com.company;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.util.*;

public class PiBoard {
    public static String password;
    public static PiBoard PiBoardSingleton = new PiBoard();

    JFrame frame = new JFrame();
    JPanel boardOnlinePanel = new JPanel();
    JPanel serverOnlinePanel = new JPanel();
    JPanel settingsPanel = new JPanel();
    JPanel titlePanel = new JPanel();
    JPanel tileBackgroundPanel = new JPanel();

    final Color settingsC = Color.PINK;
    final Color titleC = Color.PINK;
    final Color tileBackgroundC = Color.PINK;
    final Color tileC = Color.CYAN;
    final Color tileTileC = Color.YELLOW;

    final int settingsV = 50;
    final int titleV = 90;
    final int spacerV = 30;
    final int tileV = 80;
    final int exitV = 55;

    final int spacerH = 50;
    final int tileH = 140;
    final int midH = 40;
    final int fileSQ = 20;

    final int windowWidth = spacerH + tileH + midH + tileH + spacerH;
    final int windowHeight = settingsV + titleV + spacerV + tileV + spacerV + tileV + spacerV + exitV + spacerV;
    final int windowBottomBuffer = 150;

    private PiBoard (){
        password = readPasswordFile();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int boardX = (int) ((dimension.getWidth() - windowWidth - windowBottomBuffer));
        int boardY = (int) ((dimension.getHeight() - windowHeight - windowBottomBuffer));

        JLabel boardOnLabel = new JLabel("board");
        JLabel serverOnLabel = new JLabel("server");

        JButton offButton = new JButton("Turn Off");
        JButton picButton = new JButton("Picture");
        JButton slideshowButton = new JButton("Slideshow");
        JButton vidButton = new JButton("Video");
        JButton paintButton = new JButton("Paint");

        JButton pushPic = new JButton("push pic");
        JButton pushSlide = new JButton("push slide");
        JButton pushVid = new JButton("push video");
        JButton clearSlides = new JButton("clear slides");

        offButton.setBorderPainted(false);
        picButton.setBorderPainted(false);
        slideshowButton.setBorderPainted(false);
        vidButton.setBorderPainted(false);
        paintButton.setBorderPainted(false);

        addStateChangerListener(offButton, "off");
        addStateChangerListener(picButton, "staticPicture");
        addStateChangerListener(slideshowButton, "slideshow");
        addStateChangerListener(vidButton, "vid");
        addStateChangerListener(paintButton, "paint");

        pushPic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pushPic();
            }
        });
        pushSlide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pushSlideshow();
            }
        });
        pushVid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pushVideo();
            }
        });
        clearSlides.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSlides();
            }
        });

        //This is for some online checking things
        serverOnlinePanel.setBackground(Color.RED);
        boardOnlinePanel.setBackground(Color.RED);
        serverOnlinePanel.add(serverOnLabel);
        boardOnlinePanel.add(boardOnLabel);

        settingsPanel.setBounds(0, 0, windowWidth, settingsV);
        titlePanel.setBounds(0, settingsV, windowWidth, titleV);
        tileBackgroundPanel.setBounds(0, settingsV + titleV, windowWidth, windowHeight - settingsV - titleV);
        settingsPanel.setBackground(settingsC);
        titlePanel.setBackground(titleC);
        tileBackgroundPanel.setBackground(tileBackgroundC);

        picButton.setBounds(spacerH, settingsV + titleV + spacerV, tileH, tileV);
        slideshowButton.setBounds(spacerH + tileH + midH, settingsV + titleV + spacerV, tileH, tileV);
        vidButton.setBounds(spacerH, settingsV + titleV + spacerV + tileV + spacerV, tileH, tileV);
        paintButton.setBounds(spacerH + tileH + midH, settingsV + titleV + spacerV + tileV + spacerV, tileH, tileV);
        offButton.setBounds(spacerH, settingsV + titleV + spacerV + tileV + spacerV + tileV + spacerV, tileH + tileH + midH, exitV);

        pushPic.setBounds(spacerH + tileH - fileSQ, settingsV + titleV + spacerV, fileSQ, fileSQ);
        pushSlide.setBounds(spacerH + tileH + midH + tileH - fileSQ, settingsV + titleV + spacerV, fileSQ, fileSQ);
        pushVid.setBounds(spacerH + tileH - fileSQ, settingsV + titleV + spacerV + tileV + spacerV, fileSQ, fileSQ);

        frame.add(serverOnlinePanel);
        frame.add(boardOnlinePanel);

        frame.add(pushPic);
        frame.add(pushSlide);
        frame.add(pushVid);

        frame.add(picButton);
        frame.add(slideshowButton);
        frame.add(vidButton);
        frame.add(paintButton);
        frame.add(offButton);

        frame.add(settingsPanel);
        frame.add(titlePanel);
        frame.add(tileBackgroundPanel);

        frame.setComponentZOrder(pushPic, 3);
        frame.setComponentZOrder(pushSlide, 3);
        frame.setComponentZOrder(pushVid, 3);


        //frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(windowWidth + 16, windowHeight + 30);
        frame.setLayout(null);
        //frame.setResizable(false);
        frame.setLocation(boardX,boardY);
        frame.setVisible(true);
    }

    public String stateCheck() {
        try {
            URL stateURL = new URL("https://rohanakki.com:1256/stateCheck");
            HttpsURLConnection urlConnection = (HttpsURLConnection) stateURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(2000);

            int responseCode = urlConnection.getResponseCode();
            System.out.println("response code is : " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK){
                System.out.println("both up");
                boardOnlinePanel.setBackground(Color.GREEN);
                serverOnlinePanel.setBackground(Color.GREEN);
                return "both up";
            } else if ( responseCode == HttpsURLConnection.HTTP_UNAVAILABLE){
                System.out.println("server up");
                serverOnlinePanel.setBackground(Color.GREEN);
                return "server up";
            } else {
                System.out.println("this should never happen");
                return "how did you get here???";
            }

        } catch (Exception e) {
            System.out.println("both down");
            return "both down";
        }
    }

    public void startState(String state){
        ArrayList<String> possibilities = new ArrayList<>(Arrays.asList("off", "vid", "slideshow", "staticPicture", "paint"));
        if (!possibilities.contains(state)){
            return;
        }
        try {
            URL stateURL = new URL("https://rohanakki.com:1256/stateChange");
            HttpsURLConnection urlConnection = (HttpsURLConnection) stateURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoOutput(true);
            String jsonInputString = "{\"state\": \"" + state + "\", \"pass\": \"" + password + "\"}";
            System.out.println(jsonInputString);
            try(OutputStream os = urlConnection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }
        } catch (IOException e) {
            System.out.println("uhh idk");
            throw new RuntimeException(e);
        }
    }
    public void clearSlides(){
        try {
            URL stateURL = new URL("https://rohanakki.com:1256/clearSlides");
            HttpsURLConnection urlConnection = (HttpsURLConnection) stateURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoOutput(true);
            String jsonInputString = "{\"pass\": \"" + password + "\"}";
            System.out.println(jsonInputString);
            try(OutputStream os = urlConnection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }
        } catch (IOException e) {
            System.out.println("uhh idk");
            throw new RuntimeException(e);
        }
    }

    public void pushPic(){
        try {
            // Set header
            FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
            dialog.setFile("*.jpg;*.png;*.jpeg");
            dialog.setMode(FileDialog.LOAD);
            dialog.setVisible(true);
            String file = dialog.getDirectory() + dialog.getFile();

            dialog.dispose();
            System.out.println(file + " chosen.");

            Map<String, String> headers = new HashMap<>();
            headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
            HttpPostMultipart multipart = new HttpPostMultipart("https://rohanakki.com:1256/uploadPic", "utf-8", headers);
            // Add form field
            multipart.addFormField("pass", password);
            // Add file
            multipart.addFilePart("myFile", new File(file));
            // Print result
            String response = multipart.finish();
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pushSlideshow(){
        try {
            // Set header
            FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
            dialog.setFile("*.jpg;*.png;*.jpeg");
            dialog.setMode(FileDialog.LOAD);
            dialog.setVisible(true);
            String file = dialog.getDirectory() + dialog.getFile();

            dialog.dispose();
            System.out.println(file + " chosen.");

            Map<String, String> headers = new HashMap<>();
            headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
            HttpPostMultipart multipart = new HttpPostMultipart("https://rohanakki.com:1256/uploadSlide", "utf-8", headers);
            // Add form field
            multipart.addFormField("pass", password);
            // Add file
            multipart.addFilePart("myFile", new File(file));
            // Print result
            String response = multipart.finish();
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pushVideo(){
        try {
            // Set header
            FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
            dialog.setFile("*.mp4");
            dialog.setMode(FileDialog.LOAD);
            dialog.setVisible(true);
            String file = dialog.getDirectory() + dialog.getFile();

            dialog.dispose();
            System.out.println(file + " chosen.");

            Map<String, String> headers = new HashMap<>();
            headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
            HttpPostMultipart multipart = new HttpPostMultipart("https://rohanakki.com:1256/uploadVid", "utf-8", headers);
            // Add form field
            multipart.addFormField("pass", password);
            multipart.addFormField("videoDowngrade", "true");
            // Add file
            multipart.addFilePart("myFile", new File(file));
            // Print result
            String response = multipart.finish();
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(){
        this.frame.setVisible(true);
        stateCheck();
        //startState("off");
    }

    private void addStateChangerListener(JButton jbutton, String correctState){
        jbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startState(correctState);
            }
        });
    }
    private String readPasswordFile(){
        String data = "oopsy";
        try {
            URL url = getClass().getResource("pass.txt");
            File file = new File(url.getPath());
            Scanner myReader = new Scanner(file);
            data = myReader.nextLine();

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println(data);
        return data;
    }

}

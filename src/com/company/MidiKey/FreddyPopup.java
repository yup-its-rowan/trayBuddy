package com.company.MidiKey;

import javax.swing.*;

public class FreddyPopup {
    public FreddyPopup FreddyPopupSingleton = new FreddyPopup();
    private JFrame frame = new JFrame("Freddy");
    private FreddyPopup() {
    }

    public void showPopup() {
        this.frame.setVisible(true);
    }
}

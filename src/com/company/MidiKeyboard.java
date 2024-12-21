package com.company;
import javax.sound.midi.*;

public class MidiKeyboard {

    public static MidiKeyboard MidiKeyboardSingleton = new MidiKeyboard();

    private static MidiDevice inputDevice;
    private static Sequencer mainSequencer;
    private MidiKeyboard() {
    }

    public MidiDevice.Info[] getListOfMidiDevices() {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            //MidiDevice device = MidiSystem.getMidiDevice(info);
            System.out.println(info.getName());
        }
        return infos;
    }

    public void setMidiDevice(MidiDevice.Info info) {
        try {
            inputDevice = MidiSystem.getMidiDevice(info);
            inputDevice.open();
            //System.out.println(info.getName() + " opened");
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
            //System.out.println(info.getName() + " failed to open");
        }
    }

    public static void play() throws MidiUnavailableException {
        mainSequencer = MidiSystem.getSequencer();
        mainSequencer.open();

        Transmitter transmitter = inputDevice.getTransmitter();
        Receiver receiver = mainSequencer.getReceiver();
        transmitter.setReceiver(receiver);


    }

    public static void close() {
        if (mainSequencer != null && mainSequencer.isOpen()) {
            mainSequencer.close();
        }
        if (inputDevice != null && inputDevice.isOpen()) {
            inputDevice.close();
        }
    }



}

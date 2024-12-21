package com.company;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

public class MidiKeyboard {

    public static MidiKeyboard MidiKeyboardSingleton = new MidiKeyboard();

    private static MidiDevice.Info[] midiDeviceInfos;
    private MidiKeyboard() {
        midiDeviceInfos = getListOfMidiDevices();

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
            MidiDevice device = MidiSystem.getMidiDevice(info);
            device.open();
            //System.out.println(info.getName() + " opened");
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
            //System.out.println(info.getName() + " failed to open");
        }
    }

}

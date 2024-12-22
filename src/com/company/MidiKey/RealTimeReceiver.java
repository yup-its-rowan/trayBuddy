package com.company.MidiKey;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import static com.company.MidiKey.PatternInterpreter.PatternInterpreterSingleton;

public class RealTimeReceiver implements Receiver {
    @Override
    public void send(MidiMessage message, long timeStamp) {
        if (message instanceof ShortMessage shortMessage) {
            int command = shortMessage.getCommand();
            int channel = shortMessage.getChannel();
            int data1 = shortMessage.getData1();
            int data2 = shortMessage.getData2();
            MIDIinterpreter(command, channel, data1, data2);
        }
    }

    @Override
    public void close() {
        System.out.println("Receiver closed");
    }

    public void MIDIinterpreter(int command, int channel, int key, int velocity) {
        //System.out.println("Command: " + command + " Channel: " + channel + " Key: " + key + " Velocity: " + velocity);
        if (command == ShortMessage.NOTE_ON) {
            System.out.println("Note " + key + " on at " + velocity);
            PatternInterpreterSingleton.interpretNote(key);
        } else if (command == ShortMessage.NOTE_OFF) {
            System.out.println("Note " + key + " off");
        }
    }
}

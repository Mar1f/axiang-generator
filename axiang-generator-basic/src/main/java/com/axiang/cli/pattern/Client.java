package com.axiang.cli.pattern;

/**
 * @description；
 * @author:mar1
 * @data:2024/05/17
 **/
public class Client {
    public static void main(String[] args) {
        Device tv = new Device("TV");
        Device stereo = new Device("Stereo");

        TurnOffCommand turnOff = new TurnOffCommand(stereo);
        TurnOnCommand turnOn = new TurnOnCommand(tv);

        RemoteControl remote = new RemoteControl();

        remote.setCommand(turnOn);
        remote.pressButton();

        remote.setCommand(turnOff);
        remote.pressButton();
    }
}

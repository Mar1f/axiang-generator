package com.axiang.cli.pattern;

/**
 * @description；
 * @author:mar1
 * @data:2024/05/17
 **/
public class TurnOffCommand implements Command{
    private Device device;

    public TurnOffCommand(Device device){
        this.device = device;
    }
    public void execute(){
        device.turnOff();
    }
}

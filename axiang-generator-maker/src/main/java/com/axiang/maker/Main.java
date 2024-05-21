package com.axiang.maker;

//import com.axiang.maker.cli.CommandExecutor;

import com.axiang.maker.generator.main.MainGenerator;
import freemarker.template.TemplateException;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        MainGenerator mainGenerator = new MainGenerator();
        mainGenerator.doGenerate();
    }
}
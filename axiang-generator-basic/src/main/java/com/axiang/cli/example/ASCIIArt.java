package com.axiang.cli.example;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;



@Command(name = "ASCIIArt", version = "ASCIIArt 1.0", mixinStandardHelpOptions = true)
public class ASCIIArt implements Runnable {

    @Option(names = {"-s", "--font-size"}, description = "Font size")
    int fontSize=19;

    @Parameters(paramLabel= "<word>", defaultValue= "Hello picocli.", description = "words to be translated into ASCII art")
    private String[] words = {"hello", "picocli"};


    @Override
    public void run(){ // your business logic goes here...
        //自己实现业务逻辑
        System.out.println("fontsize ="+ fontSize);
        System.out.println("word = "+ String.join(",",words));
    }

    // this example implements Callable, so parsing, error handling and handling user
    // requests for usage help or version help can be done with one line of code.
    public static void main(String... args) {
        int exitCode = new CommandLine(new ASCIIArt()).execute(args);
        System.exit(exitCode);
    }
}
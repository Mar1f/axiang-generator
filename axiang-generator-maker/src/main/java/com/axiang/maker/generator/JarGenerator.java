package com.axiang.maker.generator;

import java.io.*;

/**
 * @description；
 * @author:mar1
 * @data:2024/05/19
 **/
public class JarGenerator {
    public static void doGenerate(String projectDir) throws IOException, InterruptedException {
        //调用Process类执行maven打包命令
        String winMavenCommand = "mvn.cmd clean package -DskipTests=true";
        String otherMavenCommand = "mvn clean package -DskipTests=true";
        String mavenCommand = winMavenCommand;

        ProcessBuilder processBuilder = new ProcessBuilder(mavenCommand.split(" "));
        processBuilder.directory(new File(projectDir));

        Process process = processBuilder.start();
        // 读取命令输出
        InputStream inputStream = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line = bufferedReader.readLine())!=null){
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        System.out.println("执行命令结束，退出码："+exitCode);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        doGenerate("F:\\java\\code\\axiang-generator\\axiang-generator-maker\\generated");
    }
}

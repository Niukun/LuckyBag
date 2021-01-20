package com.luckybag.multiclient.roster;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BatFileUtils {
    private static final String parentPath = "D:\\data\\Intellij\\LuckyBag\\src\\main\\resources\\roster\\";

    public static void main(String[] args) throws Exception {

        clearFiles();
        generateRunnerBats();
        generateRunAllBat();


    }

    private static void clearFiles() {
        File file = new File(parentPath);
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                File[] fs = f.listFiles();
                for (File ff : fs) {
                    ff.delete();
                }
            }
        }
    }

    private static void generateRunAllBat() {
        File parentFile = new File(parentPath);
        File[] files = parentFile.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                generateAllBat(f.listFiles(), f.getPath() + "\\");
                System.out.println(f.getPath());
            }
        }
    }

    private static void generateAllBat(File[] files, String file) {
        BufferedWriter bufw = null;
        try {
            bufw = new BufferedWriter(new FileWriter(file + "run.bat"));
            bufw.write("@echo off");
            bufw.newLine();
            for (File f : files) {
                bufw.write("start call ./" + f.getName());
                bufw.newLine();
            }
            bufw.flush();
            bufw.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufw != null) {
                try {
                    bufw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void generateRunnerBats() throws IOException {
        List<String> rosterList = new ArrayList<String>();

        File parentFile = new File(parentPath);
        File[] files = parentFile.listFiles();
        for (File f : files) {
            if (!f.isDirectory()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    rosterList.add(line);
                }
                generateBatFilesForUser(f.getPath().substring(0, f.getPath().lastIndexOf(".")) + "\\", rosterList, f.getName());
                rosterList.clear();
            }
        }
    }

    public static void generateBatFilesForUser(String path, List<String> rosterList, String runner) {
        for (int i = 0; i < rosterList.size(); i++) {
            generateBatFile(path, rosterList.get(i), i, runner);
        }
    }

    private static void generateBatFile(String path, String phone, int index, String runner) {
        try {
//            FileWriter fileWriter = new FileWriter(phone + index);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path + "\\" + phone + ".bat"));
            bufferedWriter.write("@echo off \n");
            bufferedWriter.write("java -jar LuckyBag-1.0-SNAPSHOT.jar " + phone + " " + runner.substring(0, runner.lastIndexOf(".")) + "user-" + index);

            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testid() {
//        generateBatFile("D:\\data\\Intellij\\LuckyBag\\target\\","jwj","18225528695",3);
    }

}

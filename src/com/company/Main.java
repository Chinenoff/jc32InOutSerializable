package com.company;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        var gameProgress1 = new GameProgress(100, 1, 1, 0);
        var gameProgress2 = new GameProgress(50, 5, 50, 500);
        var gameProgress3 = new GameProgress(10, 10, 99, 999);

        saveGame("D://00_java//All_My_Project//Games//savegames//save1.dat", gameProgress1);
        saveGame("D://00_java//All_My_Project//Games//savegames//save2.dat", gameProgress2);
        saveGame("D://00_java//All_My_Project//Games//savegames//save3.dat", gameProgress3);

        zipFiles("D://00_java//All_My_Project//Games//savegames//savegames.zip", "D://00_java" +
                "//All_My_Project//Games//savegames//save1.dat", "D://00_java//All_My_Project" +
                "//Games//savegames//save2.dat", "D://00_java//All_My_Project//Games//savegames" +
                "//save3.dat");

        //showZipContents();
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        File saveFile = new File(path);
        try (FileOutputStream fos = new FileOutputStream(saveFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //ZIP
    public static void zipFiles(String path, String... files) {
        File zipFile = new File(path);
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFile));) {
            for (String zipedFileName : files
            ) {
                File zippedFile = new File(zipedFileName);
                try (FileInputStream fisSave = new FileInputStream(zippedFile);
                ) {
                    ZipEntry entry = new ZipEntry(zipedFileName);
                    System.out.println(entry.getName());
                    zout.putNextEntry(entry);

                    byte[] buffer = new byte[fisSave.available()];
                    fisSave.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                if (zippedFile.delete()) System.out.println("файл удален");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //showZipContents
    static void showZipContents() {
        try (var zf = new ZipFile("D://00_java//All_My_Project//Games//savegames//savegames.zip")) {
            System.out.println(String.format("Inspecting contents of: %s\n", zf.getName()));
            Enumeration<? extends ZipEntry> zipEntries = zf.entries();
            zipEntries.asIterator().forEachRemaining(entry -> {
                System.out.println(String.format(
                        "Item: %s \nType: %s \nSize: %d\n",
                        entry.getName(),
                        entry.isDirectory() ? "directory" : "file",
                        entry.getSize()
                ));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package shuffle;

import java.awt.Color;
import java.io.File;

/**
 *
 * @author yaron
 */
public class logic {
    
    public static Color ShuffleAlgo(String Path, boolean ToShuffle) {
        if (Path.equals("")) {
            return Color.RED;
        } else {
            File folder = new File(Path);
            File[] listOfFiles = folder.listFiles();
            int[] numbers = new int[listOfFiles.length];
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    if (ToShuffle) {
                        double r = Math.random() * listOfFiles.length;
                        int rnd = (int) r;
                        int itr = 0;
                        while ((numbers[rnd] == 1) && (itr < 10)) {
                            rnd = ((int) (((rnd * rnd) + (Math.random() * rnd)))) % listOfFiles.length;
                            if (numbers[rnd] == 1) {
                                r = Math.random() * listOfFiles.length;
                                rnd = (int) r;
                                itr++;
                            }
                        }
                        if (numbers[rnd] == 1) {
                            while ((rnd < numbers.length) && (numbers[rnd] == 1)) {
                                rnd++;
                            }
                            if (rnd == numbers.length) {
                                rnd--;
                                while ((rnd >= 0) && (numbers[rnd] == 1)) {
                                    rnd--;
                                }
                            }
                        }
                        String newPath = listOfFiles[i].getAbsolutePath();
                        String[] arr = newPath.split("\\\\");
                        arr[arr.length - 1] = (rnd + 1) + "-" + arr[arr.length - 1];
                        newPath = "";
                        for (int j = 0; j < arr.length - 1; j++) {
                            newPath += arr[j] + "\\";
                        }
                        newPath += arr[arr.length - 1];
                        File NewName = new File(newPath);
                        listOfFiles[i].renameTo(NewName);
                        numbers[rnd] = 1;
                    } else {
                        String newPath = listOfFiles[i].getAbsolutePath();
                        String[] arr = newPath.split("\\\\");
                        arr[arr.length - 1] = (i + 1) + "-" + arr[arr.length - 1];
                        newPath = "";
                        for (int j = 0; j < arr.length - 1; j++) {
                            newPath += arr[j] + "\\";
                        }
                        newPath += arr[arr.length - 1];
                        File NewName = new File(newPath);
                        listOfFiles[i].renameTo(NewName);
                    }
                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory " + listOfFiles[i].getName());
                }
            }
            return Color.GREEN;
        }
    }
    
    public static Color UndoAlgo(String Path) {
        if (Path.equals("")) {
            return Color.RED;
        } else {
            File folder = new File(Path);
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    String newPath = listOfFiles[i].getAbsolutePath();
                    String[] arr = newPath.split("\\\\");
                    String[] temp = arr[arr.length - 1].split("-");
                    try{
                        Integer.parseInt(temp[0]);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    arr[arr.length - 1] = "";
                    for (int j = 1; j < temp.length - 1; j++) {
                        arr[arr.length - 1] += temp[j] + "-";
                    }
                    arr[arr.length - 1] += temp[temp.length - 1];
                    newPath = "";
                    for (int j = 0; j < arr.length - 1; j++) {
                        newPath += arr[j] + "\\";
                    }
                    newPath += arr[arr.length - 1];
                    File NewName = new File(newPath);
                    listOfFiles[i].renameTo(NewName);
                }
            }
            return Color.GREEN;
        }
    }
    public static Color organizeAlgo(String Path) {
        if (Path.equals("")) {
            return Color.RED;
        } else {
            File folder = new File(Path);
            File[] listOfFiles = folder.listFiles();
            int biggest = biggestNumber(listOfFiles);
            int bigger = biggest > listOfFiles.length ? biggest : listOfFiles.length;
            boolean[] bucket = new boolean[bigger];
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    String newPath = listOfFiles[i].getAbsolutePath();
                    String[] arr = newPath.split("\\\\");
                    String[] temp = arr[arr.length - 1].split("-");
                    try{
                        Integer.parseInt(temp[0]);
                        bucket[Integer.parseInt(temp[0]) - 1] = true;
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
            }
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    String newPath = listOfFiles[i].getAbsolutePath();
                    String[] arr = newPath.split("\\\\");
                    String[] temp = arr[arr.length - 1].split("-");
                    try{
                        Integer.parseInt(temp[0]);
                        continue;
                    } catch (NumberFormatException e) {
                    }
                    for (int j = 0; j < bucket.length; j++) {
                        if (!bucket[j]) {
                            bucket[j] = true;
                            arr[arr.length - 1] = (j + 1) + "-" + arr[arr.length - 1];
                            newPath = "";
                            for (int k = 0; k < arr.length - 1; k++) {
                                newPath += arr[k] + "\\";
                            }
                            newPath += arr[arr.length - 1];
                            File NewName = new File(newPath);
                            listOfFiles[i].renameTo(NewName);
                            break;
                        }
                    }
                }
            }
        }
        
        return Color.GREEN;
    }
    
    private static int biggestNumber(File[] listOfFiles){
        int biggest = 0;
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String newPath = listOfFiles[i].getAbsolutePath();
                String[] arr = newPath.split("\\\\");
                String[] temp = arr[arr.length - 1].split("-");
                try{
                    int num = Integer.parseInt(temp[0]);
                   if (num > biggest){
                       biggest = num;
                   }
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }
        return biggest;
    }
}

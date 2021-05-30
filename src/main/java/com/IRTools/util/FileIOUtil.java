package com.IRTools.util;

import com.IRTools.document.Artifact;
import com.IRTools.document.ArtifactsCollection;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIOUtil {
    public static void abort(String m) {
        System.err.println(m);
        System.err.flush();
        Thread.dumpStack();
        System.exit(1);
    }

    @Nullable
    public static String readFile(@NotNull String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return Charset.forName("UTF-8").decode(ByteBuffer.wrap(encoded)).toString();
        } catch (IOException e) {
            return null;
        }
    }

    public static void appendFile(String fileName, String content) {
        try {
//打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(@NotNull String input, String path) {
        Path outPath = Paths.get(path);
        Charset charset = Charset.forName("UTF-8");

        try (BufferedWriter writer = Files.newBufferedWriter(outPath, charset)) {
            writer.write(input, 0, input.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
    private static void writeFile(File javaFile, String targetPath) throws IOException {
        String name = javaFile.getName();
        BufferedReader br = new BufferedReader(new FileReader(javaFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(targetPath+File.separator+name));

        String line;
        while((line=br.readLine())!=null) {
            bw.write(line);
            bw.newLine();
        }
        br.close();
        bw.close();
    }

    public static void moveFiles(String originPath,String targetPath){
        File dir = new File(originPath);
        if(dir.isDirectory()) {
            System.out.println("direct name: "+dir.getName());
            File[] childs = dir.listFiles();
            System.out.println("num of child files: "+childs.length);
            for(File child:childs) {
                moveFiles(child.getAbsolutePath(), targetPath);
            }

        }
        else {
            String fileName = dir.getName();
            if(fileName.endsWith(".txt")) {
                String className = fileName.substring(0,fileName.lastIndexOf("."));
//                writeFile(dir,targetPath);
            }
        }
    }
    public static ArtifactsCollection getCollections(String dirPath, String postfixName) {
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            abort("Artifacts directory doesn't exist");
        }

        if (!dirFile.isDirectory()) {
            abort("Artifacts path should be a directory");
        }

        ArtifactsCollection collections = new ArtifactsCollection();
        for (File f : dirFile.listFiles()) {
            if (f.getName().endsWith(postfixName)) {
                String id = f.getName().split(".txt")[0];
                // warning!! jsp文件名 存在“-”字符的编码问题
                id = id.replace("‐", "-");
//                System.out.println(id);

                Artifact artifact = new Artifact(id, FileIOUtil.readFile(f.getPath()));
                collections.put(id, artifact);
            }
        }

//        System.out.println(collections.size() + " " + postfixName + " collections improted.");
        return collections;
    }

    public static void deleteFileInDir(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        } else {
            for (File f : files) {
                f.delete();
            }
        }
    }



    public static void main(String[] args) {
        String s = readFile("C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\dataMini\\体育\\0001.txt");
        String s1 = readFile("C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\result\\0001.txt");
        System.out.println(s);
        System.out.println(s1);
        System.out.println();
    }

}

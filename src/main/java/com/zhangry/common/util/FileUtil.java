package com.zhangry.common.util;

import com.google.common.io.Files;
import com.zhangry.common.util.AssertUtil;
import com.zhangry.common.util.ExceptionUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
/**
 * Created by zhangry on 2017/2/20.
 */
public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private FileUtil() {
    }

    public static byte[] toByteArray(File file) throws IOException {
        return Files.toByteArray(file);
    }

    public static String toString(File file, Charset charset) throws IOException {
        return Files.toString(file, charset);
    }

    public static boolean equals(File file1, File file2) throws IOException {
        return Files.equal(file1, file2);
    }

    public static void createParentDirs(File file) throws IOException {
        Files.createParentDirs(file);
    }

    public static File createTempDir() {
        return Files.createTempDir();
    }

    public static boolean deleteDir(File folder) {
        if(folder.isDirectory()) {
            String[] children = folder.list();

            for(int i = 0; i < children.length; ++i) {
                boolean success = deleteDir(new File(folder, children[i]));
                if(!success) {
                    return false;
                }
            }
        }

        return folder.delete();
    }

    public static String getFileExtension(String fullName) {
        return Files.getFileExtension(fullName);
    }

    public static String getNameWithoutExtension(String fullName) {
        return Files.getNameWithoutExtension(fullName);
    }

    public static void touch(File file) throws IOException {
        createParentDirs(file);
        Files.touch(file);
    }

    public static void touch(String filePath) throws IOException {
        File file = new File(filePath);
        createParentDirs(file);
        Files.touch(file);
    }

    public static void write(byte[] from, File to) throws IOException {
        Files.write(from, to);
    }

    public static void write(CharSequence from, File to, Charset charset) throws IOException {
        Files.write(from, to, charset);
    }

    public static void append(CharSequence from, File to, Charset charset) throws IOException {
        Files.append(from, to, charset);
    }

    public static void copy(File from, File to) throws IOException {
        Files.copy(from, to);
    }

    public static void copy(File from, OutputStream to) throws IOException {
        Files.copy(from, to);
    }

    public static void move(File from, File to) throws IOException {
        Files.move(from, to);
    }

    public static BufferedReader newReader(File file, Charset charset) throws FileNotFoundException {
        return Files.newReader(file, charset);
    }

    public static BufferedWriter newWriter(File file, Charset charset) throws FileNotFoundException {
        return Files.newWriter(file, charset);
    }

    public static InputStream getInputStream(String path) throws IOException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(path);
        if(resource.exists()) {
            return resource.getInputStream();
        } else {
            throw new IllegalStateException("the target resource does not exists. path: " + path);
        }
    }

    public static File getFile(String path) throws IOException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(path);
        if(resource.exists()) {
            return resource.getFile();
        } else {
            throw new IllegalStateException("the target resource does not exists. path: " + path);
        }
    }

    public static String getFileSeparator() {
        String fileSeparator = "/";

        try {
            if(System.getProperty("os.name").toLowerCase().startsWith("windows")) {
                fileSeparator = "\\";
            }

            return fileSeparator;
        } catch (Exception var2) {
            logger.error(var2.getMessage(), var2);
            return fileSeparator;
        }
    }

    public static String zip(String filePath, String zipPath, String zipName) {
        AssertUtil.notEmpty(filePath, "待压缩文件路径不能为空.");
        AssertUtil.notEmpty(zipPath, "压缩包存放路径不能为空.");
        AssertUtil.notEmpty(zipName, "压缩包名不能为空.");
        String zipFilePath = null;
        ZipOutputStream out = null;

        try {
            File e = getFile(filePath);
            String fileSeparator = getFileSeparator();
            zipFilePath = zipPath + fileSeparator + zipName;
            touch((String)zipFilePath);
            out = new ZipOutputStream(new FileOutputStream(new File(zipFilePath)));
            zip(out, e, zipPath, zipName, "");
        } catch (Exception var14) {
            throw ExceptionUtil.unchecked(var14);
        } finally {
            try {
                if(out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException var13) {
                var13.printStackTrace();
            }

        }

        return zipFilePath;
    }

    private static void zip(ZipOutputStream out, File file, String zipPath, String zipName, String zipDir) throws Exception {
        String fileSeparator = getFileSeparator();
        FileInputStream in = null;

        try {
            String e = file.getParent();
            if(file.isDirectory()) {
                File[] isSamePath = file.listFiles();
                zipDir = zipDir.length() == 0?"":zipDir + fileSeparator;
                File[] b = isSamePath;
                int len$ = isSamePath.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    File f = b[i$];
                    zip(out, f, zipPath, zipName, zipDir + f.getName());
                }
            } else {
                boolean var22 = zipPath.equalsIgnoreCase(e);
                if(!var22 || !file.getName().equalsIgnoreCase(zipName)) {
                    out.putNextEntry(new ZipEntry(zipDir));
                    in = new FileInputStream(file);

                    int var23;
                    while((var23 = in.read()) != -1) {
                        out.write(var23);
                    }
                }
            }
        } catch (Exception var20) {
            throw ExceptionUtil.unchecked(var20);
        } finally {
            try {
                if(in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException var19) {
                var19.printStackTrace();
            }

        }

    }

    public static void unzip(String zipfilePath, String destDir) {
        destDir = destDir.endsWith("\\")?destDir:destDir + "\\";
        byte[] b = new byte[1024];
        ZipFile zipFile = null;
        FileOutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            zipFile = new ZipFile(getFile(zipfilePath), "GBK");
            Enumeration e = zipFile.getEntries();
            org.apache.tools.zip.ZipEntry zipEntry = null;

            while(e.hasMoreElements()) {
                try {
                    zipEntry = (org.apache.tools.zip.ZipEntry)e.nextElement();
                    File e1 = new File(destDir + zipEntry.getName());
                    if(zipEntry.isDirectory()) {
                        e1.mkdirs();
                    } else {
                        if(!e1.getParentFile().exists()) {
                            e1.getParentFile().mkdirs();
                        }

                        outputStream = new FileOutputStream(e1);
                        inputStream = zipFile.getInputStream(zipEntry);
                        new ZipInputStream(inputStream);

                        int length;
                        while((length = inputStream.read(b)) > 0) {
                            outputStream.write(b, 0, length);
                        }
                    }
                } catch (Exception var34) {
                    throw var34;
                } finally {
                    if(inputStream != null) {
                        inputStream.close();
                        inputStream = null;
                    }

                    if(outputStream != null) {
                        outputStream.close();
                        outputStream = null;
                    }

                }
            }
        } catch (IOException var36) {
            throw ExceptionUtil.unchecked(var36);
        } finally {
            if(zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException var33) {
                    zipFile = null;
                    throw ExceptionUtil.unchecked(var33);
                }
            }

            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException var32) {
                    inputStream = null;
                    throw ExceptionUtil.unchecked(var32);
                }
            }

            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException var31) {
                    outputStream = null;
                    throw ExceptionUtil.unchecked(var31);
                }
            }

        }

    }
}


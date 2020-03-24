package com.threefriend.lightspace.xcxutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author xqli7
 * @date 2019/1/23 16:14
 */
public final class ZipUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ZipUtils.class);

    private static final int BUFFER = 2048;

    private static final int TRANS_BUFFER = 10240;

    private ZipUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件并存放到zipFilePath路径下
     *
     * @param sourceFilePath 待压缩的文件路径
     * @param zipFilePath    压缩后存放路径
     * @param fileName       压缩后文件的名称
     * @return
     */
    public static boolean fileToZip(String sourceFilePath, String zipFilePath, String fileName) {
        boolean flag = false;
        File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists()) {
            LOG.info("待压缩的文件目录：{}不存在.", sourceFilePath);
            sourceFile.mkdir();
        }
        File zipFile = new File(zipFilePath + File.separator + fileName + ".zip");

        if (zipFile.exists()) {
            LOG.info("{}目录下存在名字为:{}.zip打包文件", zipFilePath, fileName);
            zipFile.delete();
        }
        File[] sourceFiles = sourceFile.listFiles();
        if (null == sourceFiles || sourceFiles.length < 1) {
            LOG.info("待压缩的文件目录：{}里面不存在文件，无需压缩.", sourceFilePath);
        } else {
            try (
                    FileOutputStream fos = new FileOutputStream(zipFile);
                    ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos))
            ) {
                byte[] bytes = new byte[TRANS_BUFFER];
                loopCreateZip(sourceFiles, zos, bytes);
                flag = true;
            } catch (Exception e) {
                LOG.error("", e);
            }
        }
        return flag;
    }

    /**
     * 递归删除文件、文件夹
     *
     * @param file
     */
    public static void deleteDirectory(File file) {

        File[] list = file.listFiles();
        int i = 0;
        if (list != null && list.length > 0) {
            for (File f : list) {
                if (f.isDirectory()) {
                    //删除子文件夹
                    deleteDirectory(new File(f.getPath()));
                } else {
                    //删除文件
                    f.delete();
                    i++;
                }
            }
            //重新遍历一下文件夹内文件是否已删除干净，删除干净后则删除文件夹。
            if (Objects.requireNonNull(file.listFiles()).length <= 0) {
                file.delete();
            }
        }
    }

    private static void loopCreateZip(File[] sourceFiles, ZipOutputStream zos, byte[] bytes) throws IOException {
        for (int i = 0; i < sourceFiles.length; i++) {
            // 创建ZIP实体，并添加进压缩包
            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
            zos.putNextEntry(zipEntry);
            // 读取待压缩的文件并写进压缩包里
            try (
                    FileInputStream fis = new FileInputStream(sourceFiles[i]);
                    BufferedInputStream bis = new BufferedInputStream(fis, TRANS_BUFFER)
            ) {
                int read = 0;
                while ((read = bis.read(bytes, 0, TRANS_BUFFER)) != -1) {
                    zos.write(bytes, 0, read);
                }
            } catch (IOException e) {
                LOG.error("", e);
            }
        }
    }

    /**
     * 读取zip包中的文本文件以及文件内容
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static boolean readZipFile(String filePath) {
        File sourceFile = new File(filePath);
        if (!sourceFile.exists()) {
            LOG.info("待读取的文件：{}不存在.", filePath);
            return false;
        }
        try (
                FileInputStream fis = new FileInputStream(sourceFile);
                ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
        ) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                LOG.info("Extracting:{} ", entry);
                // write the files to the disk
                write(entry, zis);
            }
        } catch (Exception e) {
            LOG.error("", e);
        }
        return true;
    }

    private static void write(ZipEntry entry, ZipInputStream zis) {
        int count;
        byte[] data = new byte[BUFFER];
        try (
                BufferedOutputStream dest = new BufferedOutputStream(new FileOutputStream(entry.getName()), BUFFER)
        ) {
            while ((count = zis.read(data, 0, BUFFER)) != -1) {
                dest.write(data, 0, count);
            }
            dest.flush();
        } catch (Exception e) {
            LOG.error("", e);
        }
    }

    /**
     * 对zip文件进行解压
     *
     * @param sourcePath 解压文件路径
     * @param targetDir  解压目标地址
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<File> unzip(String sourcePath, String targetDir) {
        List<File> files = new ArrayList<>();
        File targetDirFile = new File(targetDir);
        if (!Files.exists(targetDirFile.toPath())) {
            targetDirFile.mkdir();
        }
        File file = new File(sourcePath);
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file, Charset.forName("GBK"));
            ZipEntry entry;
            File entryFile;
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zipFile.entries();
            while (entries.hasMoreElements()) {
                entry = entries.nextElement();
                if (entry.isDirectory()) {
                    return null;
                }
                entryFile = new File(targetDir + File.separator + entry.getName());
                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(entryFile));
                     BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry))
                ) {
                    int length;
                    while ((length = bis.read(buffer, 0, bufferSize)) != -1) {
                        bos.write(buffer, 0, length);
                    }
                    bos.flush();
                    files.add(entryFile);
                } catch (Exception e) {
                    LOG.error("文件读取出错", e);
                    return null;
                }
            }
            return files;
        } catch (IOException e) {
            LOG.error("zip文件读取错误", e);
            return null;
        } finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (IOException e) {
                LOG.error("流关闭异常", e);
            }
        }
    }

    /**
     * @param fileName 文件名
     * @param path     文件保存路径（含文件名称）
     * @param response
     * @return
     */
    public static String downLoadZip(String tempPath, HttpServletResponse response) {
        try {
            int read = 0;
            byte[] buffer = new byte[1024];
            //创建输出流，下载zip
            OutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(new File(tempPath + "code.zip"));
            //设置响应头，控制浏览器下载该文件
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + java.net.URLEncoder.encode("code.zip", "UTF-8"));
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush();
            out.close();
            File zipFile = new File(tempPath + "/code.zip");
            if (zipFile.exists()) {
                zipFile.delete();
            }
            File file = new File(tempPath);
            deleteDirectory(file);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}

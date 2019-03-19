package Encrypt;

import java.io.*;

public class Crypt {
    public static byte[] crypt(String key, byte[] decrypt) {
        byte[] crypt = new byte[decrypt.length];
        for (int i = 0; i < crypt.length; i++) {
            crypt[i] = (byte) (decrypt[i] + 1);
        }
        return crypt;
    }

    public static void cryptAllFile(File dir, String extension) throws IOException {
        File[] findfiles = findFiles(dir, extension);
        for (File file : findfiles) {
            byte[] decryptByte = loadFileAsBytes(file);
            byte[] cryptByte = crypt("null", decryptByte);
            file.delete();
            file.createNewFile();
            saveFile(file, cryptByte);
        }
    }

    private static File[] findFiles(File dir, String extension) {
        return dir.listFiles(pathname -> pathname.getName().endsWith(extension));

    }

    private static byte[] loadFileAsBytes(File file)
            throws IOException {
        byte[] result = new byte[(int) file.length()];
        try (FileInputStream f = new FileInputStream(file)) {
            f.read(result, 0, result.length);
        }
        return result;
    }

    private static void saveFile(File file, byte[] cryptByte) throws IOException {
        try (FileOutputStream f = new FileOutputStream(file)) {
            f.write(cryptByte);
        }
    }

    public static void main(String[] args) throws IOException {
        File dir = new File("C:\\Users\\Alex\\IdeaProjects\\lession7\\cryptClasses");
        cryptAllFile(dir, ".class");

    }


}

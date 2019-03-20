package Encrypt;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class EncryptedClassLoader extends ClassLoader {
    private final String key;
    private final File dir;
    private final File[] files;
    private final DecryptInterface decryptor = new Decrypt();

    public EncryptedClassLoader(String key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        if (!dir.isDirectory()) throw new IllegalArgumentException();
        this.dir = dir;
        files = findFiles(".class");
    }

    /**
     * ищет файлы с расширением extension в dir
     *
     * @param extension расширение файлов
     * @return список всех файлов с расширением extension в рутовой папке
     */
    protected File[] findFiles(String extension) {
        return dir.listFiles(pathname -> pathname.getName().endsWith(extension));

    }

    protected File findFile(String name, String extension) {
        for (File file : files) {
            if (file.getName().equals(name + extension)) return file;
        }
        return null;

    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class result;
        result = findClass(name);
        if (resolve) resolveClass(result);
        return result;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class result;
        File f = findFile(name.replace('.', '/'), ".class");
        if (f == null) return findSystemClass(name);
        try {
            byte[] classCryptBytes = loadFileAsBytes(f);
            byte[] classDecryptByres = decryptor.decrypt("lol", classCryptBytes);

            result = defineClass(name, classDecryptByres, 0, classDecryptByres.length);
            return result;

        } catch (IOException x) {
            throw new ClassNotFoundException("Cannot load class " + name + ":" + x.toString());
        } catch (ClassFormatError x) {
            throw new ClassNotFoundException("Format of class file incorrect for class " +
                    "name +  : " + x.toString());

        }

    }

    protected static byte[] loadFileAsBytes(File file) throws IOException {
        byte[] result = new byte[(int) file.length()];
        try (FileInputStream f = new FileInputStream(file)) {
            f.read(result, 0, result.length);
        }
        return result;
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        File dir = new File("C:\\Users\\Alex\\IdeaProjects\\lession7\\cryptClasses");

        EncryptedClassLoader encryptedClassLoader = new EncryptedClassLoader("key", dir, null);
        encryptedClassLoader.loadClass("CryptTest", false).newInstance();
    }


}

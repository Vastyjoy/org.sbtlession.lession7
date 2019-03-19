import Encrypt.Encryptor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PluginClassLoader extends ClassLoader {
    Map<String, Class> classMap = new HashMap<>();
    private final String[] classPath;

    public PluginClassLoader(String[] classPath) {
        this.classPath = classPath;
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
        Class result = classMap.get(name);
        if (result != null) return result;
        File f = findFile(name.replace('.', '/'), ".class");
        if (f == null) return findSystemClass(name);
        try {
            byte[] classBytes = loadFileAsBytes(f);
            System.out.println(name + " = " + classBytes.length);
            result = defineClass(name, classBytes, 0, classBytes.length);


        } catch (IOException x) {
            throw new ClassNotFoundException("Cannot load class " + name + ":" + x.toString());
        } catch (ClassFormatError x) {
            throw new ClassNotFoundException("Format of class file incorrect for class " +
                    "name +  : " + x.toString());

        }
        classMap.put(name, result);
        return result;

    }

    protected File findFile(String name, String extension) {
        File f;
        for (int i = 0; i < classPath.length; i++) {
            f = new File((new File(classPath[i]).getPath() + File.separatorChar +
                    name.replace('/', File.separatorChar)
                    + extension));
            if (f.exists()) return f;
        }
        return null;

    }

    protected static byte[] loadFileAsBytes(File file)
            throws IOException {
        byte[] result = new byte[(int) file.length()];
        try (FileInputStream f = new FileInputStream(file)) {
            f.read(result, 0, result.length);
        }
        return result;
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        ClassLoader loader;
        for (; ; ) {
            loader = new PluginClassLoader(
                    new String[]{"."});
            // текущий каталог "." будет единственным
            // каталогом поиска
            Class clazz = Class.forName("plugin.helloWorldPlugin.helloWorldPlugin", true,
                    loader);
            Object object = clazz.newInstance();
            System.out.println(object);
            new BufferedReader(
                    new InputStreamReader(System.in)).readLine();
        }
    }
}
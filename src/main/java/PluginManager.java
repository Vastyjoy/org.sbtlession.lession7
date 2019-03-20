import interfaces.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PluginManager {
    private final String pluginRootDirectory;
    private List<Plugin> plugins = new ArrayList<>();
    private PluginClassLoader pluginClassLoader;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
        pluginClassLoader = new PluginClassLoader(new String[]{pluginRootDirectory});
    }

    /**
     * Загружает классы плагинов. По условию имя главного класса плагина должно совпадать с именем папки в которой он расположен
     * @param pluginName
     * @param pluginClassName
     * @return
     * @throws ClassNotFoundException
     */
    protected Plugin load(String pluginName, String pluginClassName) throws ClassNotFoundException {
        Plugin plugin = null;
        try {
            plugin = (Plugin) pluginClassLoader.loadClass(pluginName + "." + pluginClassName).newInstance();
            plugins.add(plugin);

        } catch (InstantiationException x) {
            System.err.println("Bad instance class :" + pluginName + "." + pluginClassName);
        } catch (IllegalAccessException x) {
            System.err.println(x.toString());
        } catch (ClassCastException x){
            System.err.println("Class "+pluginName+"."+pluginClassName+".class not implements Plugin interface ");
        }
        return plugin;

    }

    /**
     *
     * @return возвращает все директории в рутовой папке.
     */
    protected List<String> getAllPath() {
        File file = new File(pluginRootDirectory);
        List<String> listResult = new ArrayList<>();
        File[] allFiles = file.listFiles();
        for (File curFile : allFiles) {
            if (curFile.isDirectory()) listResult.add(curFile.getName());
        }
        return listResult;
    }

    /**
     * Инициализирует все плагины из рутовой папки
     */
    public void initializePlugins() {

        List<String> pluginName = getAllPath();

            for (String name : pluginName) {
                try {
                    load(name, name);
                }catch (ClassNotFoundException x) {
                        System.err.println("Plugin bad load:"+ x.toString());
                    }

                }

        }

    /**
     * Запускает все зарегистрированные плагины
     */
    public void startAll() {
        for (Plugin plugin : plugins) plugin.doUseful();
    }
}

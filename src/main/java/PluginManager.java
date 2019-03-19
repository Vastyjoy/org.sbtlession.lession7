import interfaces.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PluginManager {
    private final String pluginRootDirectory;
    private List<Plugin> plugins=new ArrayList<>();
    private PluginClassLoader pluginClassLoader;

    public PluginManager(String pluginRootDirectory){
        this.pluginRootDirectory=pluginRootDirectory;
        pluginClassLoader=new PluginClassLoader(new String []{pluginRootDirectory});
    }

    protected Plugin load(String pluginName, String pluginClassName) throws ClassNotFoundException {
        Plugin plugin=null;
        try {
            plugin = (Plugin) pluginClassLoader.loadClass(pluginName + "." + pluginClassName).newInstance();
            plugins.add(plugin);

        }catch (InstantiationException x){
            System.err.println("Bad instance class :"+pluginName+"."+pluginClassName);
        }
        catch (IllegalAccessException x){
            System.err.println(x.toString());
        }
        return plugin;

    }

    protected List<String> getAllPath(){
        File file=new File(pluginRootDirectory);
        List<String> listResult=new ArrayList<>();
        File [] allFiles=file.listFiles();
        for(File curFile:allFiles){
            if(curFile.isDirectory())listResult.add(curFile.getName());
        }
        return listResult;
    }

    public void initializePlugins(){

        List<String> pluginName=getAllPath();
        try{
            for(String name:pluginName){
                load(name,name);
            }

        }catch (ClassNotFoundException x){
            System.err.println(x.toString());
        }

    }
    public void startAll(){
        for(Plugin plugin:plugins) plugin.doUseful();
    }
}

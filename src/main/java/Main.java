public class Main {
    public static void main(String[] args)  {
        PluginManager pluginManager=new PluginManager("C:\\Users\\Alex\\IdeaProjects\\lession7\\plugin");
        for(;;) {
            pluginManager.initializePlugins();
            pluginManager.startAll();
        }
    }
}

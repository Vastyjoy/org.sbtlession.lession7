public class Main {
    public static void main(String[] args) {
        for (; ; ) {
            PluginManager pluginManager = new PluginManager("C:\\Users\\Alex\\IdeaProjects\\lession7\\plugin");
            pluginManager.initializePlugins();
            pluginManager.startAll();
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        while (true){
            PluginManager pluginManager = new PluginManager("C:\\Users\\Alex\\IdeaProjects\\lession7\\plugin");
            pluginManager.initializePlugins();
            pluginManager.startAll();
            Thread.sleep(1000);
        }
    }
}

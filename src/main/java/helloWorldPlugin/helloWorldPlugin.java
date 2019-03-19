package helloWorldPlugin;

import interfaces.Plugin;

/**
 * после компиляции весь пакет положить в plugin
 */
public class helloWorldPlugin implements Plugin {

    @Override
    public void doUseful() {
        new Print().print();
    }
}

package helloWorldPlugin;

import interfaces.Plugin;

public class helloWorldPlugin implements Plugin {

    @Override
    public void doUseful() {
        new Print().print();
    }
}

package randomNumberPlugin;

import interfaces.Plugin;

import java.util.Random;

public class randomNumberPlugin implements Plugin {

    @Override
    public void doUseful() {
        Random random=new Random();
        System.out.println("Random number ="+random.nextInt(100));
    }
}

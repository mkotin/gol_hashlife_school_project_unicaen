package app;

/**
 * The thread resposable of the simulation
 */
public class GeneratorThread extends Thread {
    private volatile boolean running = true;
    private Game game;

    public GeneratorThread(Game game) {
        this.game = game;
    }

    public void run() {
        // We generate next generations until all cells of the grid are dead
        while (running && !game.getGrid().isAllDead()) {
           // it's absolutely necessary to wait at least 100 milliseconds, otherwise we can have a crash
            try {
                Thread.sleep(game.getGenWaitIntervalInMls());
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            // Generate next gen based on the algo
            if(game.isUseHashlife()) {
                game.nextGenerationHashlife();
            } else {
                game.nextGenerationClassic();
            }
            game.setIteration(game.getIteration() + 1);
        }
    }


    /**
     * Stop the thread
     */
    public void stopThread() {
        running = false;
    }
}

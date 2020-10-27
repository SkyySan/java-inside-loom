package fr.umlv.javainsideloom;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Scheduler {

    enum Policy {STACK, FIFO, RANDOM}
    private final List<Continuation> continuations = new ArrayList<>();
    private final Policy executionPolicy;

    public Scheduler(Policy policy){
        this.executionPolicy = policy;
    }

    public void enqueue(ContinuationScope scope){
        var currentContinuation = Continuation.getCurrentContinuation(scope);
        if(currentContinuation != null) {
            this.continuations.add(currentContinuation);
            Continuation.yield(scope);
        }
        else
            throw new IllegalStateException("No current continuation");
    }
    public void runLoop() {
        while (!this.continuations.isEmpty()){
            int index = switch (this.executionPolicy) {
                case STACK      -> this.continuations.size()-1;
                case FIFO       -> 0;
                case RANDOM     -> ThreadLocalRandom.current().nextInt(0,this.continuations.size());
            };
        this.continuations.remove(index).run();
        }
    }
}

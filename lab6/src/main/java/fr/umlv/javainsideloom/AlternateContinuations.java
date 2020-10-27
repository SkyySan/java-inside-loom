package fr.umlv.javainsideloom;

import java.util.List;

public class AlternateContinuations {

    public static void main(String[] args) {
        var scope = new ContinuationScope("scope");
        var continuation1 = new Continuation(scope, () -> {
            System.out.println("start 1");
            Continuation.yield(scope);
            System.out.println("middle 1");
            Continuation.yield(scope);
            System.out.println("end 1");
        });
        var continuation2 = new Continuation(scope, () -> {
            System.out.println("start 2");
            Continuation.yield(scope);
            System.out.println("middle 2");
            Continuation.yield(scope);
            System.out.println("end 2");
        });
        var list = List.of(continuation1, continuation2);
        boolean stop = false;
        while(!stop)
            for (var continuation : list)
                if (!continuation.isDone()) {
                    continuation.run();
                    stop = false;
                } else
                    stop = true;

    }
}

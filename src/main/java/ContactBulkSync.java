import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class ContactBulkSync {
    static ExecutorService executor = Executors.newFixedThreadPool(5);
    public static void main(String args[]) throws ExecutionException, InterruptedException {
        LinkedBlockingQueue q = new LinkedBlockingQueue(10);
        Reader reader = new Reader(q);
        Writer writer = new Writer(q);
        Future<?> readerThread = executor.submit(reader::read);

        for(int i=0;i<10;i++)
            executor.submit(writer::write);

        readerThread.get();

        for(int i=0;i<10;i++)
            q.put(Constants.END);

        executor.shutdown();
    }
}

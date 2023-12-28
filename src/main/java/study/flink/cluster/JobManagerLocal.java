package study.flink.cluster;

public class JobManagerLocal {

    public static void main(String[] args) {
        args = new String[] {"-c", "src\\main\\resources",
                "-D", "jobmanager.memory.process.size=1g",
                "-D", "rest.address=0.0.0.0",
                "-D", "rest.bind-address=0.0.0.0",
                "-D", "rest.bind-port=8082",
                "-D", "jobmanager.rpc.address=0.0.0.0",
                "-D", "jobmanager.rpc.port=6166"};
        org.apache.flink.runtime.entrypoint.StandaloneSessionClusterEntrypoint.main(args);
    }
}

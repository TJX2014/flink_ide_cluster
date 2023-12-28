package study.flink.cluster;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.JobManagerOptions;
import org.apache.flink.configuration.MemorySize;
import org.apache.flink.configuration.TaskManagerOptions;
import org.apache.flink.runtime.clusterframework.TaskExecutorProcessSpec;
import org.apache.flink.runtime.clusterframework.TaskExecutorProcessUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.flink.configuration.ConfigurationUtils.parseTmResourceDynamicConfigs;
import static org.apache.flink.configuration.TaskManagerOptions.TASK_MANAGER_RESOURCE_ID;

public class TaskManagerLocal {

    public static void main(String[] args) throws Exception {
        Configuration tmConfig = new Configuration();
        tmConfig.set(TaskManagerOptions.TOTAL_FLINK_MEMORY, MemorySize.parse("1g"));
        TaskExecutorProcessSpec taskExecutorProcessSpec =
                TaskExecutorProcessUtils.processSpecFromConfig(tmConfig);
        String dynamicConfigsStr =
                TaskExecutorProcessUtils.generateDynamicConfigsStr(taskExecutorProcessSpec);
        Map<String, String> dynamicConfigs = parseTmResourceDynamicConfigs(dynamicConfigsStr);

        dynamicConfigs.put(TASK_MANAGER_RESOURCE_ID.key(), "tm1");
//        dynamicConfigs.put(TASK_MANAGER_PROCESS_WORKING_DIR_BASE.key(), "file:///tmp/");
        dynamicConfigs.put(JobManagerOptions.ADDRESS.key(), "0.0.0.0");
        dynamicConfigs.put(JobManagerOptions.PORT.key(), "6166");
        int configNum = dynamicConfigs.size();
        List<String> tmArg = new ArrayList<>(configNum * 2);
        for (Map.Entry<String, String> kv : dynamicConfigs.entrySet()) {
            tmArg.add("-D");
            tmArg.add(kv.getKey() + "=" + kv.getValue());
        }
        tmArg.add("-c");
        tmArg.add("src\\main\\resources");

        args = tmArg.toArray(new String[0]);
        org.apache.flink.runtime.taskexecutor.TaskManagerRunner.main(args);
    }
}

package fr.cnes.regards.security.iptable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App {

    private static final String IPTABLE_RULE_FORMAT = "-A INPUT -p %s -s %s --dport %s -j ACCEPT";

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    private static final String COMPO_IP_CSV = "compoIpCsv";

    private static final String FLOW_MATRIX = "flowMatrix";

    public static void main(String[] args) throws IOException {
        String compoIpFileName = System.getProperty(COMPO_IP_CSV);
        String flowMatrixFileName = System.getProperty(FLOW_MATRIX);

        if (compoIpFileName == null) {
            LOG.error(usage());
            System.exit(1);
        }
        if (flowMatrixFileName == null) {
            LOG.error(usage());
            System.exit(1);
        }
        // first lets get a map associating each component to its IP address
        List<String> compoIpList = Files.readAllLines(Paths.get(compoIpFileName));
        Map<String, String> compoIpMap = new HashMap<>();
        for (String compoIp : compoIpList) {
            String[] fields = compoIp.split(";");
            compoIpMap.put(fields[0], fields[1]);
        }
        // now, lets process flow matrix
        Map<String, List<String>> flowPerDestinationComponentMap = new HashMap<>();
        List<String> flowMatrixLines = Files.readAllLines(Paths.get(flowMatrixFileName));
        for (String flowMatrixLine : flowMatrixLines) {
            String[] lineFields = flowMatrixLine.split(";");
            if (lineFields[0].isEmpty() || lineFields[3].isEmpty()) {
                // thats the headers
                continue;
            }
            String proto = lineFields[0];
            String compoSrc = lineFields[1];
            String compoDest = lineFields[2];
            String port = lineFields[3];
            if (flowPerDestinationComponentMap.get(compoDest) == null) {
                flowPerDestinationComponentMap.put(compoDest, new ArrayList<>());
            }
            List<String> rules = flowPerDestinationComponentMap.get(compoDest);
            rules.add(String.format(IPTABLE_RULE_FORMAT, proto, compoIpMap.get(compoSrc), port));
            flowPerDestinationComponentMap.put(compoDest, rules);
        }

        // now that we have processed the flow matrix and generated each rule for each component,
        // lets create a file per IP address
        Map<String, Set<String>> ipCompoMap = new HashMap<>();
        String resultFileNamePrefix = "REGARDS_iptables_";
        String resultFileNameSuffix = ".txt";
        // first lets revert compoIpMap so we get all compo installed on a machine
        for (Map.Entry<String, String> entry : compoIpMap.entrySet()) {
            if (ipCompoMap.get(entry.getValue()) == null) {
                ipCompoMap.put(entry.getValue(), new HashSet<>());
            }
            Set<String> compos = ipCompoMap.get(entry.getValue());
            compos.add(entry.getKey());
            ipCompoMap.put(entry.getValue(), compos);
        }
        // now let use reverted map to create files
        for (String ip : ipCompoMap.keySet()) {
            for (String compo : ipCompoMap.get(ip)) {
                if (flowPerDestinationComponentMap.get(compo) != null) {
                    Files.deleteIfExists(Paths.get(resultFileNamePrefix + ip + resultFileNameSuffix));
                    Files.createFile(Paths.get(resultFileNamePrefix + ip + resultFileNameSuffix));
                }
            }
        }
        for (String ip : ipCompoMap.keySet()) {
            for (String compo : ipCompoMap.get(ip)) {
                if (flowPerDestinationComponentMap.get(compo) != null) {
                    List<String> toWrite = flowPerDestinationComponentMap.get(compo);
                    toWrite.add(0, "# Rules for component "+compo);
                    toWrite = toWrite.stream().distinct().collect(Collectors.toList());
                    Files.write(Paths.get(resultFileNamePrefix + ip + resultFileNameSuffix),
                                toWrite, StandardOpenOption.APPEND);
                }
            }
        }

    }

    private static String usage() {
        return String.format("usage: java -D%s=<value> -D%s=<value> -jar <jar>", COMPO_IP_CSV, FLOW_MATRIX);
    }
}

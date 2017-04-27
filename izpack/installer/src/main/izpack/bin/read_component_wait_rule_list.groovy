#!/usr/bin/env groovy

def cli = new CliBuilder(usage: 'read_component_wait_rule_list.groovy [-h] -i <installdir> -t <component-type>')

// Define the list of options
cli.with {
  h longOpt: 'help', 'Show usage information'
  i longOpt: 'installdir', 'REGARDS installation directory', args: 1, required: true
  t longOpt: 'component-type', 'The component type (registry, admin, ...)', args: 1, required: true
}

// Retrieve options
def options = cli.parse(args)

// End if options are incorrect
if (!options) return

// Show usage text when -h or --help option is used.
if (options.h) {
  cli.usage()
  return
}

// Parse options
String installDir = options.i;
String componentType = options.t;

import java.nio.file.Paths;
import java.nio.file.Path;
import fr.cnes.regards.deployment.izpack.utils.XmlAccessor;
import fr.cnes.regards.deployment.izpack.utils.model.WaitRuleList;

// Path the xml file to read
Path waitRuleListFile = Paths.get(installDir, "config/" + componentType + "_wait_rule_list.xml");
println "waitRuleListFile :" + waitRuleListFile
WaitRuleList waitRuleList = XmlAccessor.readFromFile(waitRuleListFile, WaitRuleList.class);

println waitRuleList.toString();

System.exit(0);

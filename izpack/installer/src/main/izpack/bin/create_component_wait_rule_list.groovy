#!/usr/bin/env groovy

def cli = new CliBuilder(usage: 'create_component_wait_rule_list.groovy [-h] -i <installdir> -t <component-type> -w <wait-rules>')

// Define the list of options
cli.with {
  h longOpt: 'help', 'Show usage information'
  i longOpt: 'installdir', 'REGARDS installation directory', args: 1, required: true
  t longOpt: 'component-type', 'The component type (registry, admin, ...)', args: 1, required: true
  w longOpt: 'wait-rules', 'The XML wait rules as a string', args: 1, required: true
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
String regardsInstallDir = options.i;
String xmlWaitRules = options.w;
String componentType = options.t;

import java.nio.file.Paths;
import java.nio.file.Path;

def urlLoader = new GroovyClassLoader();
urlLoader.addURL((Paths.get(regardsInstallDir, "lib/utils.jar").toUri().toURL()));
println "ClassLoader :"
urlLoader.URLs.each{ println it }

println "Creation of " + componentType + " wait rules file"

// Print wait rules input
println "Xml input wait rules : " + xmlWaitRules;

// Component configuration file
Path componentFilePath = Paths.get(regardsInstallDir, "config/" + componentType + "_wait_rules.xml");
def cWaitRuleList = Class.forName("fr.cnes.regards.deployment.izpack.utils.model.WaitRuleList", true, urlLoader);
def cXmlAccessor = Class.forName("fr.cnes.regards.deployment.izpack.utils.XmlAccessor", true, urlLoader);
cXmlAccessor.writeToFile(xmlWaitRules, componentFilePath, cWaitRuleList);

System.exit(0);

#!/usr/bin/env groovy

def cli = new CliBuilder(usage: 'create_microservice_confile.groovy [-h] -i <regards install directory> -c <configuration string> -t <microservice type>')

// Define the list of options
cli.with {
  h longOpt: 'help', 'Show usage information'
  i longOpt: 'regards-installdir', args: 1, argName: 'installdir', 'regards install directory', required: true
  c longOpt: 'regards-microservice-config', args: 1, argName: 'microservice-config', 'configuration string', required: true
  t longOpt: 'regards-microservice-type', args: 1, argName: 'microservice-type', 'microservice type', required: true
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
String xmlConfig = options.c;
String componentType = options.t;

import java.nio.file.Paths;
import java.nio.file.Path;

def urlLoader = new GroovyClassLoader();
urlLoader.addURL((Paths.get(regardsInstallDir, "lib/regards-izpack-utils.jar").toUri().toURL()));
println "ClassLoader :"
urlLoader.URLs.each{ println it }

println "Creation of " + componentType + " configuration file"

// Print Config input
println "Xml input configuration : " + xmlConfig;

// Component configuration file
Path componentFilePath = Paths.get(regardsInstallDir, "config/" + componentType + "_config.xml");
Class.forName("fr.cnes.regards.deployment.izpack.utils.ComponentConfigListAccessor", true, urlLoader).writeToFile(xmlConfig, componentFilePath);

System.exit(0);

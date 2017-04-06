#!/usr/bin/env groovy

def cli = new CliBuilder(usage: 'get_microservice_info.groovy [-h] -i <regards install directory> [-n <microservice id>] -t <microservice type>')

// Create the list of options.
cli.with {
  h longOpt: 'help', 'Show usage information'
  i longOpt: 'regards-installdir', args: 1, argName: 'installdir', 'regards install directory', required: true
  n longOpt: 'regards-microservice-id', args: 1, argName: 'microservice-id', 'microservice id'
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
String microserviceType = options.t;

int id;
boolean idIsSet = false;
if (options.n) {
  id = Integer.valueOf(options.n);
  idIsSet = true;
}

import java.nio.file.Paths;
import java.nio.file.Path;
import fr.cnes.regards.deployment.izpack.utils.MicroserviceConfigListAccessor;
import fr.cnes.regards.deployment.izpack.utils.model.MicroserviceConfigList;

// Microservice Registry Configuration file
Path registryFilePath = Paths.get(regardsInstallDir, "config/" + microserviceType + "_config.xml");
MicroserviceConfigList microserviceConfigList = MicroserviceConfigListAccessor.readFromFile(registryFilePath);

if (idIsSet) {
  println microserviceConfigList.get(id).toString();
}
else {
  println microserviceConfigList.toString();
}

System.exit(0);

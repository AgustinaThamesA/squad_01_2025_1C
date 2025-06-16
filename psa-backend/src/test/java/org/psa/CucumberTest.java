package org.psa;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features") // Carpeta dentro de src/test/resources
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "org.psa.Steps") // Paquete de los Step Definitions
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty") // Output legible
public class CucumberTest {
    // No requiere métodos. Sirve como punto de entrada para correr los tests.
}

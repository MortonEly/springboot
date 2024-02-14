package com.abocode.swagger2doc.asciidoc;

/**
 * Description:
 * Define a  {@code DocumentationTest} implementations {@link InterfaceName}.
 *
 * @author: guanxianfei
 * @date: 2019/10/17
 */
import io.github.swagger2markup.*;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.builder.Swagger2MarkupExtensionRegistryBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DocumentationTest {
    
    private  String yamlPath="resources/swagger/swagger.yaml";

    @Test
    public void localSwaggerSpec() {
        // tag::localSwaggerSpec[]
        Path localSwaggerFile = Paths.get(yamlPath);
        Path outputDirectory = Paths.get("build/asciidoc");

        Swagger2MarkupConverter.from(localSwaggerFile) // <1>
                .build() // <2>
                .toFolder(outputDirectory); // <3>
        // end::localSwaggerSpec[]
    }

    @Test
    public void remoteSwaggerSpec() throws MalformedURLException {
        // tag::remoteSwaggerSpec[]
        URL remoteSwaggerFile = new URL("http://127.0.0.1:9081/v2/api-docs");
        Path outputDirectory = Paths.get("build/asciidoc");

        Swagger2MarkupConverter.from(remoteSwaggerFile) // <1>
                .build() // <2>
                .toFolder(outputDirectory); // <3>
        // end::remoteSwaggerSpec[]
    }

    public void convertIntoOneFile() {

        // tag::convertIntoOneFile[]
        Path localSwaggerFile = Paths.get(yamlPath);
        Path outputFile = Paths.get("build/swagger.adoc");

        Swagger2MarkupConverter.from(localSwaggerFile)
                .build()
                .toFile(outputFile);
        // end::convertIntoOneFile[]
    }


    public void convertIntoString() {

        // tag::convertIntoString[]
        Path localSwaggerFile = Paths.get(yamlPath);

        String documentation = Swagger2MarkupConverter.from(localSwaggerFile)
                .build()
                .toString();
        // end::convertIntoString[]
    }

    public void swagger2MarkupConfigBuilder() {
        Path localSwaggerFile = Paths.get(yamlPath);

        // tag::swagger2MarkupConfigBuilder[]
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder() //<1>
                .withMarkupLanguage(MarkupLanguage.MARKDOWN) //<2>
                .withOutputLanguage(Language.DE) //<3>
                .withPathsGroupedBy(GroupBy.TAGS) //<4>
                .build(); //<5>

        Swagger2MarkupConverter converter = Swagger2MarkupConverter.from(localSwaggerFile)
                .withConfig(config) //<6>
                .build();
        // end::swagger2MarkupConfigBuilder[]
    }

    public void swagger2MarkupConfigFromProperties() throws IOException {
        Path localSwaggerFile = Paths.get(yamlPath);

        // tag::swagger2MarkupConfigFromProperties[]
        Properties properties = new Properties();
        properties.load(DocumentationTest.class.getResourceAsStream("/config/config.properties")); //<1>

        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder(properties) //<2>
                .build();

        Swagger2MarkupConverter converter = Swagger2MarkupConverter.from(localSwaggerFile)
                .withConfig(config)
                .build();
        // end::swagger2MarkupConfigFromProperties[]
    }

    public void swagger2MarkupConfigFromMap() throws IOException {
        Path localSwaggerFile = Paths.get(yamlPath);

        // tag::swagger2MarkupConfigFromMap[]
        Map<String, String> configMap = new HashMap<>(); //<1>
        configMap.put(Swagger2MarkupProperties.MARKUP_LANGUAGE, MarkupLanguage.MARKDOWN.toString());
        configMap.put(Swagger2MarkupProperties.OUTPUT_LANGUAGE, Language.DE.toString());
        configMap.put(Swagger2MarkupProperties.PATHS_GROUPED_BY, GroupBy.TAGS.toString());

        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder(configMap) //<2>
                .build();

        Swagger2MarkupConverter converter = Swagger2MarkupConverter.from(localSwaggerFile)
                .withConfig(config)
                .build();
        // end::swagger2MarkupConfigFromMap[]
    }
    @Test
    public void swagger2MarkupExtensionRegistryBuilder() {
        Path localSwaggerFile = Paths.get("resources/swagger/swagger.yaml");

        // tag::swagger2MarkupExtensionRegistryBuilder[]

        Swagger2MarkupExtensionRegistry registry = new Swagger2MarkupExtensionRegistryBuilder() //<1>
                .withDefinitionsDocumentExtension(new MyExtension()) //<2>
                .build(); //<3>

        Swagger2MarkupConverter converter = Swagger2MarkupConverter.from(localSwaggerFile)
                .withExtensionRegistry(registry) //<4>
                .build();
        // end::swagger2MarkupExtensionRegistryBuilder[]
    }

    public void swagger2MarkupConfigFromCommonsConfiguration() throws IOException, ConfigurationException {
        Path localSwaggerFile = Paths.get(yamlPath);

        // tag::swagger2MarkupConfigFromCommonsConfiguration[]
        Configurations configs = new Configurations();
        Configuration configuration = configs.properties("config.properties"); //<1>

        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder(configuration) //<2>
                .build();

        Swagger2MarkupConverter converter = Swagger2MarkupConverter.from(localSwaggerFile)
                .withConfig(config)
                .build();
        // end::swagger2MarkupConfigFromCommonsConfiguration[]
    }
}
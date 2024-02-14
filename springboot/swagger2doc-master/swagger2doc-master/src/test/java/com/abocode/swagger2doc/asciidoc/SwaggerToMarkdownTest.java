package com.abocode.swagger2doc.asciidoc;
import com.abocode.swagger2doc.DocApplication;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DocApplication.class)
public class SwaggerToMarkdownTest {
    //生成markdowd文档
//    @Test
    public void generateAsciiDocs() throws Exception {
//        String url="http://192.168.125.177:8085/v2/api-docs";
        String url="http://127.0.0.1:8099/v2/api-docs";
        URL remoteSwaggerFile = new URL(url);
        Path outputDirectory = Paths.get("docs/asciidoc");

        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .build();
        Swagger2MarkupConverter.from(remoteSwaggerFile) // <1>
                .withConfig(config)
                .build() // <2>
                .toFolder(outputDirectory); // <3>
        // end::remoteSwaggerSpec[]
    }


}
package com.abocode.swagger2doc.asciidoc;

import io.github.swagger2markup.spi.DefinitionsDocumentExtension;
import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 *
 * @author: guanxianfei
 * @date: 2019/10/17
 */
@Slf4j
public class MyExtension extends DefinitionsDocumentExtension {
    @Override
    public void apply(Context context) {
       log.info("消息{}",context.getDefinitionName());
    }
}

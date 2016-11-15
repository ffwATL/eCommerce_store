package com.ffwatl.admin.web.thymeleaf.dialect;


import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.LinkedHashSet;
import java.util.Set;

public class LocaleDialect extends AbstractDialect implements IProcessorDialect {

    public static final String NAME = "Lang";
    protected LocaleDialect() {
        super(NAME);
    }

    @Override
    public String getPrefix() {
        return "locale";
    }

    @Override
    public int getDialectProcessorPrecedence() {
        return 0;
    }

    @Override
    public Set<IProcessor> getProcessors(String s) {
        final Set<IProcessor> processors = new LinkedHashSet<>();
        processors.add(new LocaleProcessor(s));
        return processors;
    }

}
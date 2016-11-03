package com.ffwatl.thymeleaf.dialect;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.exceptions.ConfigurationException;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.standard.processor.AbstractStandardConditionalVisibilityTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class LocaleProcessor extends AbstractStandardConditionalVisibilityTagProcessor {

    public static final int ATTR_PRECEDENCE = 300;
    public static final String ATTR_NAME = "ifLang";


    protected LocaleProcessor(String prefix) {
        super(TemplateMode.HTML, prefix, ATTR_NAME, ATTR_PRECEDENCE);
    }


    @Override
    protected boolean isVisible(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, AttributeName attributeName, String s) {
        if (!(iTemplateContext instanceof IWebContext)) {
            throw new ConfigurationException(
                    "Thymeleaf execution context is not a web context (implementation of " +
                            IWebContext.class.getName() + ". Cookie related tags can only be used in " +
                            "web environements.");
        }
        IWebContext context = (IWebContext) iTemplateContext;
        HttpServletRequest request = context.getRequest();
        final Locale locale = getLocaleResolver(request).resolveLocale(request);
        final String attributeValue = iProcessableElementTag.getAttributeValue(attributeName);
        if(StringUtils.isNotBlank(attributeValue)){
            if(locale.getLanguage().equals(attributeValue)) return true;
        }
        return false;
    }

    private static LocaleResolver getLocaleResolver(HttpServletRequest request) {
        return (LocaleResolver) request.getAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE);
    }
}
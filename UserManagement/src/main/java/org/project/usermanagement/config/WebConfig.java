package org.project.usermanagement.config;

import org.project.usermanagement.anotation.session.definition.CurrentSessionArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final CurrentSessionArgumentResolver currentSessionArgumentResolver;

    public WebConfig(CurrentSessionArgumentResolver userSessionArgumentResolver) {
        this.currentSessionArgumentResolver = userSessionArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentSessionArgumentResolver);
    }
}

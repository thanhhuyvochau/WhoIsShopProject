package org.project.usermanagement.anotation.session.definition;


import jakarta.servlet.http.HttpServletRequest;
import org.project.usermanagement.anotation.session.model.UserSession;
import org.project.usermanagement.constant.Role;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentSessionArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(CurrentSession.class) != null
                && parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

//        Integer userId = Integer.getInteger(request.getHeader("X-User-Id"));
//        String email = request.getHeader("X-User-Email");
//        Role role = Role.valueOf(request.getHeader("X-User-Role"));
        //TODO: Use temp value first, then implement at API GATEWAY
        Integer userId = 1;
        String email = "thanhhuyne113@gmail.com";
        String username = "thanhhuy113";
        Role role = Role.CUSTOMER;
        return new UserSession(userId, email, role, username);
    }
}

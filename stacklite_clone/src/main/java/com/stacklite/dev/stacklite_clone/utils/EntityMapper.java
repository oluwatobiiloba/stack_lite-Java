package com.stacklite.dev.stacklite_clone.utils;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.yaml.snakeyaml.util.UriEncoder.encode;

@RestController
public class EntityMapper {
    public static Map<String, Link> createLink (String linkName, Class<?> controllerClass,
                                                String methodName, @RequestParam(required = false) Map<String,String> queryParameters) {
        Map<String, Link> entity= new HashMap<>();
        WebMvcLinkBuilder link;
        if(queryParameters != null && !queryParameters.isEmpty()){
            String queryParameterString = queryParameters.entrySet().stream()
                    .map(p -> encode(p.getKey()) + "=" + encode(p.getValue()))
                    .reduce((p1, p2) -> p1 + "&" + p2)
                    .orElse("");
             link = WebMvcLinkBuilder.linkTo(controllerClass).slash(methodName.toLowerCase()+"?"+queryParameterString);
        }else{
             link = WebMvcLinkBuilder.linkTo(controllerClass).slash(methodName.toLowerCase());
        }
        entity.put("_link",link.withRel(linkName));
        return entity;
    }

}

//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
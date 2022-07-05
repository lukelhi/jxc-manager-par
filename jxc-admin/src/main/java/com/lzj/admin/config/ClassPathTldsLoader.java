package com.lzj.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * 进销存
 *
 * @author 进销存--lhy
 * @version 1.0
 */
public class ClassPathTldsLoader {

    /**
     * 指定路径，我们通过pom引入的
     * security.tld 中存放标签
     */
    private static final String SECURITY_TLD = "security.tld";
    final List<String> classPathTlds;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    public ClassPathTldsLoader(String... classPathTlds) {
        super();
        if (classPathTlds == null || classPathTlds.length <= 0) {
            this.classPathTlds = Arrays.asList(SECURITY_TLD);
        } else {
            this.classPathTlds = Arrays.asList(classPathTlds);
        }
    }

    @PostConstruct
    public void loadClassPathTlds() {
        freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(classPathTlds);
    }
}

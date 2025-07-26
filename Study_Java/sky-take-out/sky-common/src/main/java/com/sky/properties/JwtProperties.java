package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.jwt")
@Data
public class JwtProperties {
    /**
     * Spring Boot的属性绑定是大小写不敏感的
     * 它会自动处理各种命名约定之间的转换：
     * 配置文件中的admin-secret-key → Java字段的adminSecretKey
     * 配置文件中的admin-ttl → Java字段的adminTtl
     * 配置文件中的admin-token-name → Java字段的adminTokenName
     */

    /**
     * 管理端员工生成jwt令牌相关配置
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * 用户端微信用户生成jwt令牌相关配置
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}

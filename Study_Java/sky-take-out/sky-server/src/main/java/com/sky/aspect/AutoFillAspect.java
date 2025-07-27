package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MemberSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/*
 * @Auther:fz
 * @Date:2025/7/27
 * @Description:自定义切面，实现公共字段自动填充处理逻辑
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /**
     * 切入点
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){}

    /**
     * 前置通知，在通知中进行公共字段的赋值
     */
    @Before("autoFillPointCut()")  // 在切入点方法执行前执行
    public void autoFill(JoinPoint joinPoint) throws Exception {
        log.info("开始自动填充公共字段...");

        // 1. 获取到当前被拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//获取方法上的注解对象
        OperationType operationType = autoFill.value();//获取注解对象中的操作类型

        // 2. 获取方法参数（实体对象）
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) return;
        Object entity = args[0];

        // 3. 准备要填充的数据
        LocalDateTime now = LocalDateTime.now();  // 当前时间
        Long currentId = BaseContext.getCurrentId(); // 当前用户ID

        // 4. 根据操作类型填充不同字段
        if (operationType == OperationType.INSERT) {
            // 插入操作需要设置所有公共字段
            setField(entity, AutoFillConstant.SET_CREATE_TIME, now);
            setField(entity, AutoFillConstant.SET_CREATE_USER, currentId);
            setField(entity, AutoFillConstant.SET_UPDATE_TIME, now);
            setField(entity, AutoFillConstant.SET_UPDATE_USER, currentId);
        } else if (operationType == OperationType.UPDATE) {
            // 更新操作只需要设置更新相关字段
            setField(entity, AutoFillConstant.SET_UPDATE_TIME, now);
            setField(entity, AutoFillConstant.SET_UPDATE_USER, currentId);
        }
    }

    private void setField(Object obj, String methodName, Object value) throws Exception {
        // 获取对象的指定方法
        Method method = obj.getClass().getDeclaredMethod(methodName, value.getClass());
        // 调用方法设置值
        method.invoke(obj, value);
    }
}

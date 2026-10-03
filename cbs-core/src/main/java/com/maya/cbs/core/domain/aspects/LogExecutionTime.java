package com.maya.cbs.core.domain.aspects;

import java.lang.annotation.*;

@Target (ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {

}

package de.dbo.samples.springboot.jbehave2.IT.commons.context.example;

import de.dbo.samples.springboot.jbehave2.IT.commons.context.ContextThreadLocal;

public class XXXContextSteps {

    private static XXXContext myCtx() {
        return (XXXContext) ContextThreadLocal.contextLocal().getCtx(XXXContext.class);
    }

}

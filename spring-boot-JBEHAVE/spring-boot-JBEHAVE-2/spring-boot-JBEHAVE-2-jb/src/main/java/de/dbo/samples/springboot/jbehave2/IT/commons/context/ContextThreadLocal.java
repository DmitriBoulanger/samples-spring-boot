package de.dbo.samples.springboot.jbehave2.IT.commons.context;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread-Local context for all Steps-instances
 *
 * @author Dmitri Boulanger
 *
 * Programs are meant to be read by humans and only incidentally for computers to execute (D. Knuth)
 *
 */
public final class ContextThreadLocal {
    private static final Logger                          log                  = LoggerFactory.getLogger(ContextThreadLocal.class);

    // =================================================================================================================================
    // FACTORY
    // =================================================================================================================================

    private static final ThreadLocal<ContextThreadLocal> THREAD_LOCAL_CONTEXT = initialContext();

    private static final ThreadLocal<ContextThreadLocal> initialContext() {
        return new ThreadLocal<ContextThreadLocal>() {
            @Override
            protected final ContextThreadLocal initialValue() {
                return new ContextThreadLocal();
            }
        };
    }

    public static final ContextThreadLocal contextLocal() {
        return THREAD_LOCAL_CONTEXT.get();
    }

    // =================================================================================================================================
    // INSTANCE
    // =================================================================================================================================

    /**
    * available data in this thread-local context
    */
    private final Map<Class<? extends ContextAbstraction>, Object> ctx = new HashMap<Class<? extends ContextAbstraction>, Object>();

    /**
     * a singleton instance can be only obtained using the factory
     */
    private ContextThreadLocal() {
        clear();
    }

    public String print() {
        return " ... to be done ...";
    }

    public void clear() {
        THREAD_LOCAL_CONTEXT.remove();
    }

    public Object getCtx(final Class<? extends ContextAbstraction> key) {
        if (!ctx.containsKey(key)) {
            try {
                ctx.put(key, key.newInstance());
            }
            catch(Throwable e) {
                log.error("Can't initialize thread-local context for key " + key.getName() + ": ", e);
            }
        }
        return ctx.get(key);
    }
}

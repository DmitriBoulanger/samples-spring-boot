package de.dbo.samples.springboot.jbehave2.IT.commons.context;

/* java */
import java.util.HashMap;
import java.util.Map;

/* SLF4J */
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
    // =                                                  FACTORY
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

    protected static final ContextThreadLocal contextLocal() {
        return THREAD_LOCAL_CONTEXT.get();
    }

    public static final ContextData contextData(final Class<? extends ContextData> key) {
        return contextLocal().getContexData(key);
    }

    // =================================================================================================================================
    // =                                          INSTANCE
    // =================================================================================================================================´

    /**
     * a singleton instance can be only obtained using the factory
     */
    private ContextThreadLocal() {
        clear();
        if (log.isTraceEnabled()) {
            log.trace("created. HashCode=[" + hashCode() + "]");
        }
    }

    /**
     * available data-items in this thread-local context.
     * Data-item is identified by itself
     *
     * @see #getContexData(Class)
     *
     */
    private final Map<Class<? extends ContextData>, ContextData> data = new HashMap<Class<? extends ContextData>, ContextData>();

    /**
     * total clean-up
     */
    public void clear() {
        THREAD_LOCAL_CONTEXT.remove();
    }

    public ContextData getContexData(final Class<? extends ContextData> key) {
        if (!data.containsKey(key)) {
            try {
                data.put(key, key.newInstance());
            }
            catch(Throwable e) {
                log.error("Can't initialize thread-local context for key " + key.getName() + ": ", e);
            }
        }
        return data.get(key);
    }

    public StringBuilder print() {
        final StringBuilder sb = new StringBuilder();
        return sb;
    }
}

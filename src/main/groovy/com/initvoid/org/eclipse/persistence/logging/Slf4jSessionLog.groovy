package com.initvoid.org.eclipse.persistence.logging

import groovy.transform.CompileStatic
import org.eclipse.persistence.logging.AbstractSessionLog
import org.eclipse.persistence.logging.SessionLogEntry
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC

@CompileStatic
public class Slf4jSessionLog extends AbstractSessionLog
{
    public static final String MDC_SESSION_KEY     = 'EclipseLink.Session'
    public static final String MDC_CONNECTION_KEY  = 'EclipseLink.Connection'
    public static final String MDC_THREAD_KEY      = 'EclipseLink.Thread'
    public static final String MDC_DATE_KEY        = 'EclipseLink.Date'

    private static String getLoggerName (String sessionName, String namespace)
    {
        def nameBuilder = new StringBuilder('org.eclipse.persistence.logging')
        if (sessionName != null)
        {
            nameBuilder << '.session'
            if (!sessionName.empty)
            {
                nameBuilder << '.'
                nameBuilder << sessionName
            }
        }
        if (namespace == null || namespace?.empty)
        {
            nameBuilder << '.default'
        }
        else
        {
            nameBuilder << '.'
            nameBuilder << namespace
        }
        nameBuilder.toString()
    }

    private static Logger getLogger (String sessionName, String namespace)
    {
        def loggerName = getLoggerName(sessionName, namespace)
        LoggerFactory.getLogger(loggerName)
    }

    private static Logger getLogger (SessionLogEntry entry)
    {
        getLogger(entry.session?.name, entry.nameSpace)
    }

    private Logger getLogger (String namespace)
    {
        getLogger(session?.name, namespace)
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldLog(int level, String category)
    {
        def logger         = getLogger(category)
        def slf4jLogLevel  = Slf4jLogLevel.getByEclipseLinkLogLevel(level)

        return logEnabled(logger, slf4jLogLevel)
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(SessionLogEntry entry)
    {
        def logger  = getLogger(entry)
        def level   = Slf4jLogLevel.getByEclipseLinkLogLevel(entry.level)

        if(!logEnabled(logger, level))
        {
            return
        }

        def message    = formatMessage(entry)
        def exception  = entry.exception

        try
        {
            populateMdc(entry)
            if (exception)
            {
                logMessage(logger, level, message, exception)
            }
            else
            {
                logMessage(logger, level, message)
            }
        }
        finally
        {
            clearMdc()
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldPrintThread()
    {
        shouldPrintThread == null ? false : shouldPrintThread.booleanValue()
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldDisplayData()
    {
        shouldDisplayData == null ? false : shouldDisplayData.booleanValue()
    }

    private void populateMdc(SessionLogEntry entry)
    {
        if (entry.date != null)
        {
            MDC.put(MDC_DATE_KEY, getDateString(entry.date))
        }
        if (entry.session != null)
        {
            MDC.put(MDC_SESSION_KEY, getSessionString(entry.session))
        }
        if (entry.connection != null)
        {
            MDC.put(MDC_CONNECTION_KEY, getConnectionString(entry.connection))
        }
        if (entry.thread != null)
        {
            MDC.put(MDC_THREAD_KEY, getThreadString(entry.thread))
        }
    }

    private static void clearMdc()
    {
        MDC.remove(MDC_DATE_KEY)
        MDC.remove(MDC_SESSION_KEY)
        MDC.remove(MDC_CONNECTION_KEY)
        MDC.remove(MDC_THREAD_KEY)
    }

    private static boolean logEnabled(Logger logger, Slf4jLogLevel level)
    {
        switch (level)
        {
            case Slf4jLogLevel.ALL:
                throw new UnsupportedOperationException()
            case Slf4jLogLevel.TRACE:
                return logger.traceEnabled
            case Slf4jLogLevel.DEBUG:
                return logger.debugEnabled
            case Slf4jLogLevel.INFO:
                return logger.infoEnabled
            case Slf4jLogLevel.WARN:
                return logger.warnEnabled
            case Slf4jLogLevel.ERROR:
                return logger.errorEnabled
            case Slf4jLogLevel.OFF:
                return false
            default:
                return logger.infoEnabled
        }
    }

    private static void logMessage(Logger logger, Slf4jLogLevel level, String message, Throwable exception)
    {
        switch (level)
        {
            case Slf4jLogLevel.ALL:
                throw new UnsupportedOperationException()
            case Slf4jLogLevel.TRACE:
                logger.trace(message, exception)
                break
            case Slf4jLogLevel.DEBUG:
                logger.debug(message, exception)
                break
            case Slf4jLogLevel.INFO:
                logger.info(message, exception)
                break
            case Slf4jLogLevel.WARN:
                logger.warn(message, exception)
                break
            case Slf4jLogLevel.ERROR:
                logger.error(message, exception)
                break
            case Slf4jLogLevel.OFF:
                throw new UnsupportedOperationException()
                break
            default:
                logger.info(message, exception)
                break
        }
    }

    private static void logMessage(Logger logger, Slf4jLogLevel level, String message)
    {
        switch (level)
        {
            case Slf4jLogLevel.ALL:
                throw new UnsupportedOperationException()
                break
            case Slf4jLogLevel.TRACE:
                logger.trace(message)
                break
            case Slf4jLogLevel.DEBUG:
                logger.debug(message)
                break
            case Slf4jLogLevel.INFO:
                logger.info(message)
                break
            case Slf4jLogLevel.WARN:
                logger.warn(message)
                break
            case Slf4jLogLevel.ERROR:
                logger.error(message)
                break
            case Slf4jLogLevel.OFF:
                throw new UnsupportedOperationException()
                break
            default:
                logger.info(message)
                break
        }
    }
}
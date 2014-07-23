package com.initvoid.org.eclipse.persistence.logging;

import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;
import org.eclipse.persistence.sessions.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class Slf4jSessionLog extends AbstractSessionLog
{
    public static final String MDC_SESSION_KEY    = "EclipseLink.Session";
    public static final String MDC_CONNECTION_KEY = "EclipseLink.Connection";
    public static final String MDC_THREAD_KEY     = "EclipseLink.Thread";
    public static final String MDC_DATE_KEY       = "EclipseLink.Date";

    private static String getLoggerName(String sessionName, String namespace)
    {
        StringBuilder nameBuilder = new StringBuilder("org.eclipse.persistence.logging");
        if (sessionName != null)
        {
            nameBuilder.append(".session");
            if (!sessionName.isEmpty())
            {
                nameBuilder.append(".");
                nameBuilder.append(sessionName);
            }
        }

        if (namespace == null || (namespace.isEmpty()))
        {
            nameBuilder.append(".default");
        }
        else
        {
            nameBuilder.append(".");
            nameBuilder.append(namespace);
        }

        return nameBuilder.toString();
    }

    private static Logger getLogger(SessionLogEntry entry)
    {
        Session session = entry.getSession();
        String namespace = entry.getNameSpace();
        String sessionName = session == null ? null : session.getName();

        return getLogger(sessionName, namespace);
    }

    private static Logger getLogger(Session session, String namespace)
    {
        String sessionName = session == null ? null : session.getName();

        return getLogger(sessionName, namespace);
    }

    private static Logger getLogger(String sessionName, String namespace)
    {
        String loggerName = getLoggerName(sessionName, namespace);

        return LoggerFactory.getLogger(loggerName);
    }

    private static boolean logEnabled(Logger logger, Slf4jLogLevel level)
    {
        switch (level)
        {
            case ALL:
                throw new UnsupportedOperationException();
            case TRACE:
                return logger.isTraceEnabled();
            case DEBUG:
                return logger.isDebugEnabled();
            case INFO:
                return logger.isInfoEnabled();
            case WARN:
                return logger.isWarnEnabled();
            case ERROR:
                return logger.isErrorEnabled();
            case OFF:
                return false;
            default:
                return logger.isInfoEnabled();
        }
    }

    private static void logMessage(Logger logger, Slf4jLogLevel level, String message)
    {
        switch (level)
        {
            case ALL:
                throw new UnsupportedOperationException();
            case TRACE:
                logger.trace(message);
                break;
            case DEBUG:
                logger.debug(message);
                break;
            case INFO:
                logger.info(message);
                break;
            case WARN:
                logger.warn(message);
                break;
            case ERROR:
                logger.error(message);
                break;
            case OFF:
                throw new UnsupportedOperationException();
            default:
                logger.info(message);
                break;
        }
    }

    private static void logMessage(Logger logger, Slf4jLogLevel level, String message, Throwable exception)
    {
        switch (level)
        {
            case ALL:
                throw new UnsupportedOperationException();
            case TRACE:
                logger.trace(message, exception);
                break;
            case DEBUG:
                logger.debug(message, exception);
                break;
            case INFO:
                logger.info(message, exception);
                break;
            case WARN:
                logger.warn(message, exception);
                break;
            case ERROR:
                logger.error(message, exception);
                break;
            case OFF:
                throw new UnsupportedOperationException();
            default:
                logger.info(message, exception);
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldLog(int level, String category)
    {
        Logger logger = getLogger(session, category);
        Slf4jLogLevel slf4jLogLevel = Slf4jLogLevel.getByEclipseLinkLogLevel(level);

        return logEnabled(logger, slf4jLogLevel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(SessionLogEntry entry)
    {
        Logger logger = getLogger(entry);
        Slf4jLogLevel level = Slf4jLogLevel.getByEclipseLinkLogLevel(entry.getLevel());

        if (!logEnabled(logger, level))
        {
            return;
        }

        String message = formatMessage(entry);
        Throwable exception = entry.getException();

        try
        {
            populateMdc(entry);
            if (exception == null)
            {
                logMessage(logger, level, message);
            }
            else
            {
                logMessage(logger, level, message, exception);
            }
        }
        finally
        {
            clearMdc();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldPrintThread()
    {
        return shouldPrintThread != null && shouldPrintThread;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldDisplayData()
    {
        return shouldDisplayData != null && shouldDisplayData;
    }

    private void populateMdc(SessionLogEntry entry)
    {
        if (entry.getDate() != null)
        {
            MDC.put(MDC_DATE_KEY, getDateString(entry.getDate()));
        }
        if (entry.getSession() != null)
        {
            MDC.put(MDC_SESSION_KEY, getSessionString(entry.getSession()));
        }
        if (entry.getConnection() != null)
        {
            MDC.put(MDC_CONNECTION_KEY, getConnectionString(entry.getConnection()));
        }
        if (entry.getThread() != null)
        {
            MDC.put(MDC_THREAD_KEY, getThreadString(entry.getThread()));
        }
    }

    private static void clearMdc()
    {
        MDC.remove(MDC_DATE_KEY);
        MDC.remove(MDC_SESSION_KEY);
        MDC.remove(MDC_CONNECTION_KEY);
        MDC.remove(MDC_THREAD_KEY);
    }
}

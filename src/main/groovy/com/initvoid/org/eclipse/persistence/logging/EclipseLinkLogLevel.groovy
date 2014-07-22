package com.initvoid.org.eclipse.persistence.logging

import groovy.transform.CompileStatic
import org.eclipse.persistence.logging.SessionLog

@CompileStatic
enum EclipseLinkLogLevel
{
    ALL(      SessionLog.ALL,      SessionLog.ALL_LABEL),
    FINEST(   SessionLog.FINEST,   SessionLog.FINEST_LABEL),
    FINER(    SessionLog.FINER,    SessionLog.FINER_LABEL),
    FINE(     SessionLog.FINE,     SessionLog.FINE_LABEL),
    CONFIG(   SessionLog.CONFIG,   SessionLog.CONFIG_LABEL),
    INFO(     SessionLog.INFO,     SessionLog.INFO_LABEL),
    WARNING(  SessionLog.WARNING,  SessionLog.WARNING_LABEL),
    SEVERE(   SessionLog.SEVERE,   SessionLog.SEVERE_LABEL),
    OFF(      SessionLog.OFF,      SessionLog.OFF_LABEL)

    private final int     value
    private final String  label

    private static final Map<Integer,  EclipseLinkLogLevel> VALUE_MAP = new HashMap<>()
    private static final Map<String,   EclipseLinkLogLevel> LABEL_MAP = new HashMap<>()

    static {
        for (def level in values())
        {
            VALUE_MAP[level.value] = level
            LABEL_MAP[level.label] = level
        }
    }

    public static EclipseLinkLogLevel getByValue(int value)
    {
        VALUE_MAP[value]
    }

    public static EclipseLinkLogLevel getByLabel(String label)
    {
        LABEL_MAP[label]
    }

    private EclipseLinkLogLevel(int value, String label)
    {
        this.value = value
        this.label = label
    }

    public int getValue()
    {
        return value
    }

    public String getLabel()
    {
        return label
    }
}
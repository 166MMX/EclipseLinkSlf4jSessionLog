package com.initvoid.org.eclipse.persistence.logging;

import org.eclipse.persistence.logging.SessionLog;

import java.util.HashMap;
import java.util.Map;

public enum EclipseLinkLogLevel
{
    ALL(     SessionLog.ALL,     SessionLog.ALL_LABEL),
    FINEST(  SessionLog.FINEST,  SessionLog.FINEST_LABEL),
    FINER(   SessionLog.FINER,   SessionLog.FINER_LABEL),
    FINE(    SessionLog.FINE,    SessionLog.FINE_LABEL),
    CONFIG(  SessionLog.CONFIG,  SessionLog.CONFIG_LABEL),
    INFO(    SessionLog.INFO,    SessionLog.INFO_LABEL),
    WARNING( SessionLog.WARNING, SessionLog.WARNING_LABEL),
    SEVERE(  SessionLog.SEVERE,  SessionLog.SEVERE_LABEL),
    OFF(     SessionLog.OFF,     SessionLog.OFF_LABEL);

    private static final Map<Integer, EclipseLinkLogLevel> VALUE_MAP = new HashMap<>();
    private static final Map<String, EclipseLinkLogLevel>  LABEL_MAP = new HashMap<>();

    private final int    value;
    private final String label;

    static
    {
        for (EclipseLinkLogLevel level : values())
        {
            VALUE_MAP.put(level.value, level);
            LABEL_MAP.put(level.label, level);
        }
    }

    private EclipseLinkLogLevel(int value, String label)
    {
        this.value = value;
        this.label = label;
    }

    public static EclipseLinkLogLevel getByValue(int value)
    {
        return VALUE_MAP.get(value);
    }

    public static EclipseLinkLogLevel getByLabel(String label)
    {
        return LABEL_MAP.get(label);
    }

    public int getValue()
    {
        return value;
    }

    public String getLabel()
    {
        return label;
    }
}

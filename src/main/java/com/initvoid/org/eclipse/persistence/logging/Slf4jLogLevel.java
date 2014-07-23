package com.initvoid.org.eclipse.persistence.logging;

import java.util.HashMap;
import java.util.Map;

public enum Slf4jLogLevel
{
    ALL(new EclipseLinkLogLevel[] {
        EclipseLinkLogLevel.ALL
    }),
    TRACE(new EclipseLinkLogLevel[] {
        EclipseLinkLogLevel.FINEST
    }),
    DEBUG(new EclipseLinkLogLevel[] {
        EclipseLinkLogLevel.FINER,
        EclipseLinkLogLevel.FINE
    }),
    INFO(new EclipseLinkLogLevel[] {
        EclipseLinkLogLevel.CONFIG,
        EclipseLinkLogLevel.INFO
    }),
    WARN(new EclipseLinkLogLevel[] {
        EclipseLinkLogLevel.WARNING
    }),
    ERROR(new EclipseLinkLogLevel[] {
        EclipseLinkLogLevel.SEVERE
    }),
    OFF(new EclipseLinkLogLevel[] {
        EclipseLinkLogLevel.OFF
    });

    private static final Map<EclipseLinkLogLevel, Slf4jLogLevel> ECLIPSE_LINK_MAP = new HashMap<>();

    private EclipseLinkLogLevel[] eclipseLinkLogLevels;

    static
    {
        for (Slf4jLogLevel slf4jLogLevel : values())
        {
            for (EclipseLinkLogLevel eclipseLinkLogLevel : slf4jLogLevel.eclipseLinkLogLevels)
            {
                ECLIPSE_LINK_MAP.put(eclipseLinkLogLevel, slf4jLogLevel);
            }
        }
    }

    private Slf4jLogLevel(EclipseLinkLogLevel[] eclipseLinkLogLevels)
    {
        this.eclipseLinkLogLevels = eclipseLinkLogLevels;
    }

    public static Slf4jLogLevel getByEclipseLinkLogLevel(int eclipseLinkLogLevelValue)
    {
        EclipseLinkLogLevel eclipseLinkLogLevel = EclipseLinkLogLevel.getByValue(eclipseLinkLogLevelValue);
        return getByEclipseLinkLogLevel(eclipseLinkLogLevel);
    }

    public static Slf4jLogLevel getByEclipseLinkLogLevel(EclipseLinkLogLevel eclipseLinkLogLevel)
    {
        return ECLIPSE_LINK_MAP.get(eclipseLinkLogLevel);
    }
}

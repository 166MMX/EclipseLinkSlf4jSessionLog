package com.initvoid.org.eclipse.persistence.logging

import groovy.transform.CompileStatic

@CompileStatic
enum Slf4jLogLevel
{
    ALL([
    ]),
    TRACE([
            EclipseLinkLogLevel.ALL,
            EclipseLinkLogLevel.FINEST
    ]),
    DEBUG([
            EclipseLinkLogLevel.FINER,
            EclipseLinkLogLevel.FINE
    ]),
    INFO([
            EclipseLinkLogLevel.CONFIG,
            EclipseLinkLogLevel.INFO
    ]),
    WARN([
            EclipseLinkLogLevel.WARNING
    ]),
    ERROR([
            EclipseLinkLogLevel.SEVERE
    ]),
    OFF([
            EclipseLinkLogLevel.OFF
    ])

    private List<EclipseLinkLogLevel> eclipseLinkLogLevels

    private static final Map<EclipseLinkLogLevel, Slf4jLogLevel> ECLIPSE_LINK_MAP = new HashMap<>()

    static {
        for (def slf4jLogLevel in values())
        {
            for (def eclipseLinkLogLevel in slf4jLogLevel.eclipseLinkLogLevels)
            {
                ECLIPSE_LINK_MAP[eclipseLinkLogLevel] = slf4jLogLevel
            }
        }
    }

    public static Slf4jLogLevel getByEclipseLinkLogLevel(EclipseLinkLogLevel eclipseLinkLogLevel)
    {
        ECLIPSE_LINK_MAP[eclipseLinkLogLevel]
    }

    public static Slf4jLogLevel getByEclipseLinkLogLevel(int eclipseLinkLogLevelValue)
    {
        def eclipseLinkLogLevel = EclipseLinkLogLevel.getByValue(eclipseLinkLogLevelValue)
        getByEclipseLinkLogLevel(eclipseLinkLogLevel)
    }

    private Slf4jLogLevel(List<EclipseLinkLogLevel> eclipseLinkLogLevels)
    {
        this.eclipseLinkLogLevels = eclipseLinkLogLevels
    }
}

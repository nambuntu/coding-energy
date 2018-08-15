package de.vermietet.ecounter.domain;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class PeriodMap {
    public static Map<String, ConsumptionPeriod> periodMap = new HashMap<String, ConsumptionPeriod>() {{
        put("24h", new ConsumptionPeriod(24L, ChronoUnit.HOURS));
        put("12h", new ConsumptionPeriod(12L, ChronoUnit.HOURS));
        put("30d", new ConsumptionPeriod(30L, ChronoUnit.DAYS));
    }};
}

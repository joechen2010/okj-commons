package org.okj.commons.hyperic;

public enum MetricType {
    DISK("FileServer Mount", "UsePercent", "%"), MEMORY("Linux", "MemFree", "B"), CPU("CPU", "CpuUsage", "%");

    private final String prototypeName;

    private final String alias;

    private final String unit;

    public String getUnit() {
        return unit;
    }

    private MetricType(String prototypeName, String alias, String unit) {
        this.prototypeName = prototypeName;
        this.alias = alias;
        this.unit = unit;
    }

    public String getPrototypeName() {
        return prototypeName;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        return "prototype=" + this.prototypeName + ",alias=" + this.alias + ",unit=" + this.unit;
    }

}

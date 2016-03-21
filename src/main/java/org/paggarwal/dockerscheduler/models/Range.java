package org.paggarwal.dockerscheduler.models;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by paggarwal on 3/21/16.
 */
public class Range {
    private int start;
    private int end;
    private String units;

    public Range(int start, int end, String units) {
        this.start = start;
        this.end = end;
        this.units = units;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getUnits() {
        return units;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Range)) return false;
        Range range = (Range) o;
        return getStart() == range.getStart() &&
                getEnd() == range.getEnd() &&
                Objects.equal(getUnits(), range.getUnits());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getStart(), getEnd(), getUnits());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("start", start)
                .add("end", end)
                .add("units", units)
                .toString();
    }


    public static class Builder {
        private int start;
        private int end;
        private String units;

        private Builder() {
        }

        public static Builder aRange() {
            return new Builder();
        }

        public Builder withStart(int start) {
            this.start = start;
            return this;
        }

        public Builder withEnd(int end) {
            this.end = end;
            return this;
        }

        public Builder withUnits(String units) {
            this.units = units;
            return this;
        }

        public Builder but() {
            return aRange().withStart(start).withEnd(end).withUnits(units);
        }

        public Range build() {
            Range range = new Range(start, end, units);
            return range;
        }
    }
}

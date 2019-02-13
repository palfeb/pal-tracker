package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {

    TimeEntry find(long id);
    void delete(long id);
    TimeEntry create(TimeEntry timeEntry);
    List<TimeEntry> list();


    TimeEntry update(long l, TimeEntry updatedTimeEntry);
}

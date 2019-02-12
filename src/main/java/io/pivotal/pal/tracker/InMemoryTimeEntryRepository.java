package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    private HashMap<Long, TimeEntry> timeEntries = new HashMap<>();
    private long currentId = 1L;


    @Override
    public TimeEntry find(long id) {
        return timeEntries.get(id);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        Long id = currentId++;
        TimeEntry newTimeEntry = new TimeEntry(id,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
                );
        timeEntries.put(id, newTimeEntry);
        System.out.println("new time entry is " + newTimeEntry.getDate());
        System.out.println("TimeEntry now is " + timeEntries);
        return newTimeEntry;

    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(timeEntries.values());
    }

    @Override
    public TimeEntry update(long l, TimeEntry timeEntry) {
        if(find(l) == null) return null;

        TimeEntry updateTimeEntry = new TimeEntry(l,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );

        timeEntries.replace(l, updateTimeEntry);
        return updateTimeEntry;

    }

    @Override
    public void delete(long id) {
        System.out.println("Deleted the id " + id);
        timeEntries.remove(id);
    }
}

package io.pivotal.pal.tracker;

import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    TimeEntry  defaultTimeEntry = new TimeEntry();

    public TimeEntry create(TimeEntry timeEntry){
        return timeEntry;

    }

    public TimeEntry find(long id){
        return defaultTimeEntry;
    }

    public TimeEntry update(long id, TimeEntry timeEntry){
        return timeEntry;

    }

    public List<TimeEntry> list(){
        return null;
    }

    public void delete(long id){
        System.out.println("inside the delete method");
    }
}


package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TimeEntryController {

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
    }

    public ResponseEntity  create(TimeEntry timeEntry){
        return null;
    }

    public ResponseEntity<TimeEntry> read(long id){
        return null;
    }

    public ResponseEntity  update(long id, TimeEntry timeEntry){
        return null;
    }

    public ResponseEntity<TimeEntry> delete(long timeEntryId) {
        return null;
    }

    public ResponseEntity<List<TimeEntry>> list() {
        return null;
    }
}

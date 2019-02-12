package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.pivotal.pal.tracker.InMemoryTimeEntryRepository;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntriesRepo;

    public TimeEntryController(TimeEntryRepository timeEntriesRepo) {
        this.timeEntriesRepo = timeEntriesRepo;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {
        System.out.println("Inside the create controller method");
        System.out.println("the coming controller request is" + timeEntry.toString());
        TimeEntry createdTimeEntry = timeEntriesRepo.create(timeEntry);
        System.out.println("the createdRimeEntry request is" + createdTimeEntry.toString());
        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);

    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {
        System.out.println("Inside the read method");
        TimeEntry readTimeEntry = timeEntriesRepo.find(id);
        if(readTimeEntry != null) {
            return new ResponseEntity<>(readTimeEntry, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(readTimeEntry, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        System.out.println("Inside the list method");
        return new ResponseEntity<>(timeEntriesRepo.list(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody TimeEntry expected) {
        System.out.println("Inside the update controller method");
        TimeEntry updatedTimeEntry = timeEntriesRepo.update(id, expected);
        if(updatedTimeEntry != null) {
            return new ResponseEntity<>(updatedTimeEntry, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(updatedTimeEntry, HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long id) {
        System.out.println("Inside the delete controller method");
        timeEntriesRepo.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}

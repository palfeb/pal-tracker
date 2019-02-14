package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
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
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository timeEntriesRepo, MeterRegistry meterRegistry) {

        this.timeEntriesRepo = timeEntriesRepo;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {
        System.out.println("Inside the create controller method");
        System.out.println("the coming controller request is" + timeEntry.toString());
        TimeEntry createdTimeEntry = timeEntriesRepo.create(timeEntry);
        actionCounter.increment();
        System.out.println("the createdRimeEntry request is" + createdTimeEntry.toString());
        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);

    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {
        System.out.println("Inside the read method");
        TimeEntry readTimeEntry = timeEntriesRepo.find(id);
        if(readTimeEntry != null) {
            actionCounter.increment();
            return new ResponseEntity<>(readTimeEntry, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(readTimeEntry, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        System.out.println("Inside the list method");
        actionCounter.increment();
        return new ResponseEntity<>(timeEntriesRepo.list(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody TimeEntry expected) {
        System.out.println("Inside the update controller method");
        TimeEntry updatedTimeEntry = timeEntriesRepo.update(id, expected);
        if(updatedTimeEntry != null) {
            actionCounter.increment();
            return new ResponseEntity<>(updatedTimeEntry, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(updatedTimeEntry, HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long id) {
        System.out.println("Inside the delete controller method");
        actionCounter.increment();
        timeEntriesRepo.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}

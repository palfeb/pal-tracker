package io.pivotal.pal.tracker;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry find(long id) {
        return jdbcTemplate.query(
                "SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = ?",
                new Object[]{id},
                extractor);
    }

    @Override
    public void delete(long id) {
        System.out.println("Inside the delete method");
        jdbcTemplate.execute("DELETE from time_entries where id = " + id);
        System.out.println("Deleted id is" + id);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        String sql = "INSERT INTO time_entries " +
                "(project_id, user_id, date, hours) VALUES (?, ?, ?, ?)";
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    sql,
                    RETURN_GENERATED_KEYS
            );

            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());

            return statement;
        }, generatedKeyHolder);

        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public List<TimeEntry> list() {
        return this.jdbcTemplate.query("SELECT * FROM time_entries",
                new ResultSetExtractor<List<TimeEntry>>() {

                    @Override
                    public List<TimeEntry> extractData(ResultSet rs)
                            throws SQLException, DataAccessException {
                        List<TimeEntry> list = new ArrayList<TimeEntry>();
                        while(rs.next()){
                            TimeEntry onerow = new TimeEntry();
                            onerow.setId(rs.getLong("id"));
                            onerow.setProjectId(rs.getLong("project_id"));
                            onerow.setUserId(rs.getLong("user_id"));
                            onerow.setDate(rs.getDate("date").toLocalDate());
                            onerow.setHours(rs.getInt("hours"));
                            list.add(onerow);
                        }
                        return list;
                    }

                });

    }

    @Override
    public TimeEntry update(long l, TimeEntry timeEntry) {
        jdbcTemplate.execute("update time_entries set project_id = " + timeEntry.getProjectId() +
                ",user_id = " + timeEntry.getUserId() +
                ",date = '" + timeEntry.getDate() +
                "',hours = " + timeEntry.getHours() + " where id = " + l);
        return find(l);
    }

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;


}

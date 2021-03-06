package joke.generator.production.Repository;


import joke.generator.production.Model.Joke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Random;

@Repository
public class DataRepository {
    @Autowired
    JdbcTemplate template;

    public Joke getRandomJoke() {
        String sql = "SELECT * from jokes";
        RowMapper<Joke> rowMapper = new BeanPropertyRowMapper<>(Joke.class);
        List<Joke> jokes = template.query(sql, rowMapper);
        return selectRandomJoke(jokes);
    }

    public void addNewJoke(String joke_text){
        String sql = "INSERT INTO jokes (joke_text) VALUES (?)";
        template.update(sql, joke_text);
    }

    private Joke selectRandomJoke(List<Joke> jokes){
        Random random = new Random();
        return jokes.get(random.nextInt(jokes.size()));
    }
    public Joke updateJoke(Joke joke){
        String sql = "UPDATE jokes SET joke_text=? WHERE jokes.joke_id =?";
        template.update(sql, joke.getJoke_text(), joke.getJoke_id());
        return null;
    }

    public String deleteJoke(int joke_id){ //i want to return string how?
        String sqlCount = "SELECT * from jokes";
        RowMapper<Joke> rowMapper = new BeanPropertyRowMapper<>(Joke.class);
        List<Joke> jokes = template.query(sqlCount, rowMapper);
        String message;
        if (jokes.size()>10) {
            String sql = "DELETE from jokes WHERE joke_id = ?";
            template.update(sql, joke_id);
            message = "Joke deleted!";
        } else{
            message = "Joke can't be deleted, database have to contain at least 10 jokes.";

        }
        return message;
    }


}

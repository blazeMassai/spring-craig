package com.ree.tacob.data;

import com.ree.tacob.mymodels.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ree.tacob.data.IngredientRepository;


import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

        private final JdbcTemplate jdbc;

        @Autowired
        public JdbcIngredientRepository(JdbcTemplate jdbc){
            this.jdbc = jdbc;
        }

        //tag::finders[]
        @Override
        public Iterable<Ingredient> findAll() {
            return jdbc.query("select id, name, type from Ingredient",
                    this::mapRowToIngredient);
        }

    // tag::findOne[]
    @Override
    public Ingredient findById(String id) {
        return jdbc.queryForObject(
                "select id, name, type from Ingredient where id=?",
                this::mapRowToIngredient, id);
    }


    //tag::save[]
    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update(
                "insert into Ingredient (id, name, type) values (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString());
        return ingredient;
    }

    // tag::findOne[]
    //tag::finders[]
    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum)
            throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }


}


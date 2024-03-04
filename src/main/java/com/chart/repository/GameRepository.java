package com.chart.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.chart.model.Game;

public class GameRepository implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public GameRepository() {

	}

	public GameRepository(EntityManager manager) {
		this.manager = manager;
	}	
	
	public List<Game> search(String term){
		TypedQuery<Game> query = manager.createQuery("FROM Game WHERE name like :name", Game.class);
		query.setParameter("name", term + "%");
		return query.getResultList();
	}
	
	public Game save(Game game) {
		return manager.merge(game);
	}
	
	public List<Object[]> searchGenreQuantity() {
	    TypedQuery<Object[]> query = manager.createQuery(
	    		"SELECT g.genre, g.gameConsole.id, g.gameConsole.name, COUNT(g) FROM Game g GROUP BY g.genre, g.gameConsole.id, g.gameConsole.name", 
	    		Object[].class);
	    return query.getResultList();
	}
	
	public List<Game> allGames(){
		return manager.createQuery("from Game", Game.class).getResultList();
	}
}

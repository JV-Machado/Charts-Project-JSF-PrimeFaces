package com.chart.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.chart.model.GameConsole;

public class GameConsoleRepository implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public GameConsoleRepository() {
		
	}
	
	public GameConsoleRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public List<GameConsole> allConsoles(){
		return manager.createQuery("from GameConsole", GameConsole.class).getResultList();
	}
}

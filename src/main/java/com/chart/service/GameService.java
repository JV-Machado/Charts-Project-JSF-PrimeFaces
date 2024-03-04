package com.chart.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.chart.model.Game;
import com.chart.repository.GameRepository;
import com.chart.util.Transactional;

public class GameService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private GameRepository gameRepository;
	
	@Transactional
	public void save(Game game) {
		gameRepository.save(game);
	}
}

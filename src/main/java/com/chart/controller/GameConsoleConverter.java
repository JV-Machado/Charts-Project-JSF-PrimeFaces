package com.chart.controller;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.chart.model.GameConsole;

public class GameConsoleConverter implements Converter {

	private List<GameConsole> gameConsoleList;
	
	public GameConsoleConverter(List<GameConsole> gameConsoleList) {
		this.gameConsoleList = gameConsoleList;
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if ("Selecione...".equals(value)) {
	        return null;
	    }
		
		Long id = Long.valueOf(value);
		
		for(GameConsole gameConsole : gameConsoleList) {
			if(id.equals(gameConsole.getId())) {

				return gameConsole;
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null) {
			return null;
		}
		
		GameConsole gameConsole = (GameConsole) value;

		return gameConsole.getId().toString();
	}

}

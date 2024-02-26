package com.chart.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import com.chart.model.Game;
import com.chart.model.GameConsole;
import com.chart.repository.GameConsoleRepository;
import com.chart.repository.GameRepository;

@Named
@ViewScoped
public class ChartBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private GameRepository gameRepository;

	@Inject
	private GameConsoleRepository consoleRepository;
	
	private Game game;

	private String searchTerm;

	private PieChartModel pieChart;
	
	private BarChartModel barChart;
	private BarChartModel barPS1Chart = new BarChartModel();
	private BarChartModel barPS2Chart = new BarChartModel();
	private BarChartModel barDSChart = new BarChartModel();
	private BarChartModel barGBAChart = new BarChartModel();
	private BarChartModel barSNESChart = new BarChartModel();
	private BarChartModel bar3DSChart = new BarChartModel();
	
	private String selectedChartOption = "consolePieChart";
	private String selectedChartOptionGame = "Playstation 1";

	private List<Game> listGames;
	
	private List<GameConsole> listConsoles;
	
	private List<Object[]> listGenreConsole;

	@PostConstruct
    public void init() {
		allConsoles();
		allGames();
		quantityGamesGenrePerConsole();
        createPieModel();
        createBarModel();
        createBarGenderModel("Playstation 1", barPS1Chart);
        createBarGenderModel("Playstation 2", barPS2Chart);
        createBarGenderModel("Nintendo Ds", barDSChart);
        createBarGenderModel("GBA", barGBAChart);
        createBarGenderModel("SNES", barSNESChart);
        createBarGenderModel("Nintendo 3Ds", bar3DSChart);
    }
	
	public void createPieModel() {
		pieChart = new PieChartModel();

		for(GameConsole data : listConsoles) {
			pieChart.set(data.getName(), data.getUnitsSold());
		}
		pieChart.setTitle("Game Console x Units Sold");
		pieChart.setLegendPosition("w");
	}

	public void createBarModel() {
		barChart = new BarChartModel();
		
		ChartSeries series = new ChartSeries();
		series.setLabel("Units Sold");
		
		for(GameConsole data : listConsoles) {
			series.set(data.getName(), data.getUnitsSold());
		}
		
		barChart.addSeries(series);
		barChart.setTitle("Game Console x Units Sold");
        barChart.setLegendPosition("ne");
	}
	
	public void createBarGenderModel(String consoleName, BarChartModel barChart) {		
		ChartSeries series = new ChartSeries();
		
		series.setLabel("Quantity");
	
		for(Object[] data : listGenreConsole) {
			if(consoleName.equals(data[2])) {
				series.set(data[0], (Long) data[3]);
			} 
			
		}
		barChart.addSeries(series);
	
		barChart.setTitle("Quantidade x Gênero");
        barChart.setLegendPosition("ne");
	}
	
	public void createBarGenderModelPS2() {
		barPS2Chart = new BarChartModel();
		
		ChartSeries series = new ChartSeries();
		series.setLabel("Genre");
		
		for(Object[] data : listGenreConsole) {
			if("Playstation 2".equals(data[2])) {
				series.set(data[0], (Long) data[3]);
			}
		}
		
		barPS2Chart.addSeries(series);
		barPS2Chart.setTitle("Testing");
        barPS2Chart.setLegendPosition("ne");
        barPS2Chart.setShowPointLabels(true);
	}
	
	public void search() {
		listGames = gameRepository.search(searchTerm);
	}

	public void allGames() {
		listGames = gameRepository.allGames();
	}

	public void allConsoles() {
		listConsoles = consoleRepository.allConsoles();
	}
	
	public void quantityGamesGenrePerConsole() {
		listGenreConsole = gameRepository.searchGenreQuantity();
	}
	
	public void chartOption(String chartType) {
		selectedChartOption = chartType;
	}
	
	public boolean isChartSelected(String chartType) {
		return selectedChartOption != null && selectedChartOption.equals(chartType);
	}
	
	public void chartOptionGameGenre(String chartType) {
		selectedChartOptionGame = chartType;
	}
	
	public boolean isChartSelectedGameGenre(String chartType) {
		return selectedChartOptionGame != null && selectedChartOptionGame.equals(chartType);
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<Game> getListGames() {
		return listGames;
	}

	public void setListGames(List<Game> listGames) {
		this.listGames = listGames;
	}
	
	public List<GameConsole> getListConsoles() {
		return listConsoles;
	}

	public void setListConsoles(List<GameConsole> listConsoles) {
		this.listConsoles = listConsoles;
	}
	
	public List<Object[]> getListGenreConsole() {
		return listGenreConsole;
	}

	public void setListGenreConsole(List<Object[]> listGenreConsole) {
		this.listGenreConsole = listGenreConsole;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public PieChartModel getPieChart() {
		return pieChart;
	}

	public BarChartModel getBarChart() {
		return barChart;
	}

	public String getSelectedChart() {
		return selectedChartOption;
	}

	public void setSelectedChart(String selectedChart) {
		this.selectedChartOption = selectedChart;
	}
	
	public String getSelectedChartOptionGame() {
		return selectedChartOptionGame;
	}

	public void setSelectedChartOptionGame(String selectedChartOptionGame) {
		this.selectedChartOptionGame = selectedChartOptionGame;
	}

	public BarChartModel getBarPS1Chart() {
		return barPS1Chart;
	}

	public BarChartModel getBarPS2Chart() {
		return barPS2Chart;
	}

	public BarChartModel getBarDSChart() {
		return barDSChart;
	}

	public BarChartModel getBarGBAChart() {
		return barGBAChart;
	}

	public BarChartModel getBarSNESChart() {
		return barSNESChart;
	}
	
	public BarChartModel getBar3DSChart() {
		return bar3DSChart;
	}
	
}

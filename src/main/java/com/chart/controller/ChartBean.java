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
import com.chart.service.GameService;

@Named
@ViewScoped
public class ChartBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private GameRepository gameRepository;

	@Inject
	private GameConsoleRepository consoleRepository;
	
	@Inject
	private GameService gameService;
	
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
	
	private PieChartModel pieUnitsSoldPS1Chart = new PieChartModel();
	private PieChartModel pieUnitsSoldPS2Chart = new PieChartModel();
	private PieChartModel pieUnitsSoldDSChart = new PieChartModel();
	private PieChartModel pieUnitsSoldGBAChart = new PieChartModel();
	private PieChartModel pieUnitsSoldSNESChart = new PieChartModel();
	private PieChartModel pieUnitsSold3DSChart = new PieChartModel();

	
	private String selectedChartOption = "consolePieChart";
	private String selectedChartOptionGameGenre = "Playstation 1";
	private String selectedChartOptionGameUnitsSold = "Playstation 1";


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
        
        createBarModelQuantityGameGenrePerConsole("Playstation 1", barPS1Chart);
        createBarModelQuantityGameGenrePerConsole("Playstation 2", barPS2Chart);
        createBarModelQuantityGameGenrePerConsole("Nintendo Ds", barDSChart);
        createBarModelQuantityGameGenrePerConsole("GBA", barGBAChart);
        createBarModelQuantityGameGenrePerConsole("SNES", barSNESChart);
        createBarModelQuantityGameGenrePerConsole("Nintendo 3Ds", bar3DSChart);
        
        createPieModelGameUnitsSold("Playstation 1", pieUnitsSoldPS1Chart);
        createPieModelGameUnitsSold("Playstation 2", pieUnitsSoldPS2Chart);
        createPieModelGameUnitsSold("Nintendo Ds", pieUnitsSoldDSChart);
        createPieModelGameUnitsSold("GBA", pieUnitsSoldGBAChart);
        createPieModelGameUnitsSold("SNES", pieUnitsSoldSNESChart);
        createPieModelGameUnitsSold("Nintendo 3Ds", pieUnitsSold3DSChart);

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
	
	public void createBarModelQuantityGameGenrePerConsole(String consoleName, BarChartModel barChart) {		
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
	
	public void createPieModelGameUnitsSold(String consoleName, PieChartModel pieChart) {
				
		for(Game data : listGames) {
			if(consoleName.equals(data.getGameConsole().getName())) {
				pieChart.set(data.getName(), data.getUnitsSold());
			}
		}
		
		pieChart.setTitle("Unidade Vendidas x Jogo");
		pieChart.setLegendPosition("ne");
	}
	
	public void startNewGameRegister() {
		game = new Game();
	}
	
	public void save() {
		gameService.save(game);
		
		allGames();
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
	
	public boolean isChartSelectedGameGenre(String chartType) {
		return selectedChartOptionGameGenre != null && selectedChartOptionGameGenre.equals(chartType);
	}
	
	public boolean isChartSelectedGameUnitsSold(String chartType) {
		return selectedChartOptionGameUnitsSold != null && selectedChartOptionGameUnitsSold.equals(chartType);
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
	

	public String getSelectedChartOptionGameUnitsSold() {
		return selectedChartOptionGameUnitsSold;
	}

	public void setSelectedChartOptionGameUnitsSold(String selectedChartOptionGameUnitsSold) {
		this.selectedChartOptionGameUnitsSold = selectedChartOptionGameUnitsSold;
	}

	public String getSelectedChartOptionGameGenre() {
		return selectedChartOptionGameGenre;
	}
	
	public void setSelectedChartOptionGameGenre(String selectedChartOptionGameGenre) {
		this.selectedChartOptionGameGenre = selectedChartOptionGameGenre;
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

	public PieChartModel getPieUnitsSoldPS1Chart() {
		return pieUnitsSoldPS1Chart;
	}

	public PieChartModel getPieUnitsSoldPS2Chart() {
		return pieUnitsSoldPS2Chart;
	}

	public PieChartModel getPieUnitsSoldDSChart() {
		return pieUnitsSoldDSChart;
	}

	public PieChartModel getPieUnitsSoldGBAChart() {
		return pieUnitsSoldGBAChart;
	}

	public PieChartModel getPieUnitsSoldSNESChart() {
		return pieUnitsSoldSNESChart;
	}

	public PieChartModel getPieUnitsSold3DSChart() {
		return pieUnitsSold3DSChart;
	}

	
}

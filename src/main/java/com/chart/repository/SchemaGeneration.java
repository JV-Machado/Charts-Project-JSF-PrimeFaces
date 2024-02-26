package com.chart.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.chart.model.Game;

public class SchemaGeneration {
	
	public static void main(String[] args) {		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChartPU");
		
		EntityManager em = emf.createEntityManager();
		
		List<Game> lista = em.createQuery("from Game", Game.class)
				.getResultList();
		
		System.out.println(lista);
		
		em.close();
		emf.close();
	}

}

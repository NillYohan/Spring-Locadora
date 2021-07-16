package com.nill.conexaobanco.Filmes;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilmeRepositorio extends CrudRepository<Filme, Integer>{
	
	List<Filme> findByTitulo(String titulo);
	List<Filme> findByGenero(String genero);
	List<Filme> findByPreco(Double preco);
	List<Filme> findByNota(Double nota);
	
	Filme findByIdFilme(Integer idFilme);
}

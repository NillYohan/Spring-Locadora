package com.nill.conexaobanco.Filmes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/filme")
@CrossOrigin(origins = "*")
public class FilmeController {

	@Autowired
	private FilmeRepositorio repositorio;
	
	@PostMapping(path="/add")
	public @ResponseBody String novoFilme(@RequestParam String titulo, 
	@RequestParam String genero, @RequestParam Double preco, @RequestParam Double nota) {
		Filme filme = new Filme();
		filme.setTitulo(titulo);
		filme.setGenero(genero);
		filme.setPreco(preco);
		filme.setNota(nota);
		repositorio.save(filme);
		return "Filme Salvo";
	}
	
	@PostMapping(path="/addfilme")
	public @ResponseBody String novoFilmeJson(@RequestBody Filme novoFilme) {
		repositorio.save(novoFilme);
		return "Novo Filme Cadastrado";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Filme> listarFilmes(){
		return repositorio.findAll();
	}
	
	@GetMapping(path="/pesquisa")
	public @ResponseBody Optional<Filme>  pesquisaFilme(@RequestParam Integer id){
		return repositorio.findById(id);
	}
	
	@GetMapping(path = "/pesquisar/{id}")
	public @ResponseBody Optional<Filme> pesquisarFilme(@PathVariable(required = true, name="id") Integer id){
		return repositorio.findById(id);
	}
	
	@DeleteMapping(path = "/delete/{id}")
	public @ResponseBody String deleteFilme(@PathVariable(required = true, name="id") Integer id) {
		Optional<Filme> filme= repositorio.findById(id);
		if (filme.isPresent()) {
			repositorio.deleteById(id);
			return "Filme deletado";
		}
		else {
			return "Filme não encontrado para ser Deletado";
		}
	}
	
	@PutMapping(path = "/update/{id}")
	public @ResponseBody Optional<Filme> updateFilme(@PathVariable(required = true, name = "id") Integer id, @RequestBody Filme filme){
		Optional<Filme> f = repositorio.findById(id);
		if(f.isPresent()) {
			f.get().setTitulo(filme.getTitulo());
			f.get().setGenero(filme.getGenero());
			f.get().setPreco(filme.getPreco());
			f.get().setNota(filme.getNota());
			repositorio.save(f.get());
			return f;
		}
		return null;
	}
	
	@PutMapping(path = "/altera/{id}")
	public @ResponseBody ResponseEntity<Filme> alteraFilme(@PathVariable(required = true, name = "id") Integer id, @RequestBody Filme filme){
		Optional<Filme> f = repositorio.findById(id);
		if(f.isPresent()) {
			f.get().setTitulo(filme.getTitulo());
			f.get().setGenero(filme.getGenero());
			f.get().setPreco(filme.getPreco());
			f.get().setNota(filme.getNota());
			return ResponseEntity.ok(repositorio.save(f.get()));
		}
		return ResponseEntity.status(404).build();
	}
	
	@GetMapping(path = "/procurar_titulo/{titulo}")
	public @ResponseBody List<Filme> procurarTitulo(@PathVariable (required = true, name = "titulo") String titulo){
		return repositorio.findByTitulo(titulo);
	}
	
	@GetMapping(path = "/procurar_genero/{genero}")
	public @ResponseBody List<Filme> procurarGenero(@PathVariable (required = true, name = "genero") String genero){
		return repositorio.findByGenero(genero);
	}
	
	@GetMapping(path = "/procurar_preco/{preco}")
	public @ResponseBody List<Filme> procurarPreco(@PathVariable (required = true, name = "preco") Double preco){
		return repositorio.findByPreco(preco);
	}
	
	@GetMapping(path = "/procurar_nota/{nota}")
	public @ResponseBody List<Filme> procurarNota(@PathVariable (required = true, name = "nota") Double nota){
		return repositorio.findByNota(nota);
	}
}

package A.Projeto.API.API.Controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import A.Projeto.API.API.Models.Pessoa;
import A.Projeto.API.API.Repository.PessoaRepository;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaController {

	@Autowired //
	private PessoaRepository pessoaRepos;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Pessoa>> ListarPessoa() {
		
		return ResponseEntity.status(HttpStatus.OK).body(pessoaRepos.findAll());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> Salvar(@RequestBody Pessoa pessoa) {
		pessoa = pessoaRepos.save(pessoa);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(pessoa.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> Buscar(@PathVariable Integer id) {
		Optional<Pessoa> pessoa = pessoaRepos.findById(id);
		if(!pessoa.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(pessoa);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> Deletar(@PathVariable Integer id) {
		try {
			pessoaRepos.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}
	 
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> Editar(@RequestBody Pessoa pessoa, @PathVariable Integer id) {
		pessoa.setId(id);
		pessoaRepos.save(pessoa);
		
		return ResponseEntity.noContent().build();
	}
	 
	
	

}

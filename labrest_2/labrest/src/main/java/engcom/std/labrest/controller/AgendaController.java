package engcom.std.labrest.controller;

import engcom.std.labrest.entities.Pessoa;
import engcom.std.labrest.exceptions.PessoaNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/pessoas")
public class AgendaController {

    private final List<Pessoa> agenda = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong();

    @GetMapping
    public List<Pessoa> obterTodasPessoas() {
        return this.agenda;
    }

    @GetMapping("/{id}")
    public Pessoa obterPessoa(@PathVariable long id) {
        for (Pessoa p : this.agenda) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new PessoaNaoEncontradaException(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa adicionarPessoa(@RequestBody Pessoa p) {
        Pessoa n = new Pessoa(contador.incrementAndGet(), p.getNome(), p.getEmail());
        this.agenda.add(n);
        return n;
    }

    @PutMapping("/{id}")
    public Pessoa atualizarPessoa(@RequestBody Pessoa pessoa, @PathVariable long id) {
        for (Pessoa p : this.agenda) {
            if (p.getId() == id) {
                p.setNome(pessoa.getNome());
                p.setEmail(pessoa.getEmail());
                return p;
            }
        }
        throw new PessoaNaoEncontradaException(id);
    }

    @DeleteMapping("/{id}")
    public void excluirPessoa(@PathVariable long id) {
        if (!this.agenda.removeIf(p -> p.getId() == id)) {
            throw new PessoaNaoEncontradaException(id);
        }
    }

    @ControllerAdvice
    class PessoaNaoEncontrada {
        @ResponseBody
        @ExceptionHandler(PessoaNaoEncontradaException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        String pessoaNaoEncontrada(PessoaNaoEncontradaException p) {
            return p.getMessage();
        }
    }
}

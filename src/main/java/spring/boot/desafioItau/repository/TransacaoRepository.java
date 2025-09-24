package spring.boot.desafioItau.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import spring.boot.desafioItau.exception.NotFound;
import spring.boot.desafioItau.model.Transacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Getter
@Setter
public class TransacaoRepository {
    private final List<Transacao> banco = new ArrayList<>();

    private Long id = 1L;


    public Transacao save(Transacao t) {
        t.setId(id);
        id++;
        banco.add(t);
        return t;
    }

    public Optional<Transacao> findById(Long id) {
        return banco.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public void deleteById(Long id) {
        Transacao transacao = findById(id).orElseThrow(() -> new NotFound("não encontrado"));
        banco.remove(transacao);
    }

    public List<Transacao> findAll() {
        return banco;
    }

    public void deleteAll() {
        banco.clear();
    }
}

package com.beat.jetlogger.projections;

// interface para pegar apenas o nome da lista e quando ela foi criada
// (para exibição na tabela)
public interface GameListNameAndCreated {
    String getName();
    String getCreatedAt();
}
